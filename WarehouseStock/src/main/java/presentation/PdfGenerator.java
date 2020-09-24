package presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class PdfGenerator {
    /**
     * Stores the number of Clients reports.
     */
    private static int Clients = 1;     //se vor folosi pentru a determina numarul report-ului generat.
                                 //acestea for fi accesate prin reflectie (numele field-urilor sunt identice cu numele claselor(tabelelor) pentru care se va face reportul)
    /**
     * Stores the number of Products reports.
     */
    private static int Products = 1;
    /**
     * Stores the number of Orders reports.
     */
    private static int Orders = 1;

    /**
     * Stores the bill number;
     */
    private static int bill = 1;
    /**
     * Stores the billNoStick number
     */
    private static int billNoStock = 1;

    /**
     * Generates a reprot pdf according to the received ArrayList
     * @param list  The data to be be written into the pdf.
     */
    public static void reportPdf(ArrayList list) {
        Document document = null;
        try {
            document = new Document();
            String className = list.get(0).getClass().getSimpleName();      //se obtine numele clasei obiectelor care formeaza ArrayList-ul
            Field num = PdfGenerator.class.getDeclaredField(className);    //se va obtine un field din aceasta clasa prin trimiterea ca parametru numele clasei a obiectelor din ArrayList
            Object val = num.get(PdfGenerator.class);                         //se va obtine valoarea field-ului
            PdfWriter.getInstance(document, new FileOutputStream("report" + className + val + ".pdf")); //se creeaza pdf-ul care o sa aib forma "report/Class_Name/NumReport/.pdf"
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(num.getName(), PdfGenerator.class);  //se va crea un drescriptor pentru proprietati al field-ului gasit
            Method method = propertyDescriptor.getWriteMethod();                                            //se va gasi metoda necesara pentru a scrie in field
            method.invoke(PdfGenerator.class, val);                                                         //se apeleaza metoda care incrementeaza valoarea field-ului
            document.open();
            Field fieldlist[] = list.get(0).getClass().getDeclaredFields();            //se obtine o lista cu toate field-urile clasei pentru care vom genera header-ul de tabel
            PdfPTable table = new PdfPTable(fieldlist.length - 1);          //se va genera tabelul cu numarul necesar de coloane ( -1 pentru ca nu se va afisa coloana deleted)
            for (Field field : fieldlist) {
                if (!field.getName().equals("deleted")) {
                    field.setAccessible(true);
                    PdfPHeaderCell header = new PdfPHeaderCell();               //se vor genera header cell-uri pentru tabel
                    header.setBackgroundColor(BaseColor.CYAN);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(field.getName()));              //se seteaza in cell numele field-ului care reprezinta si numele coloane
                    table.addCell(header);
                }
            }
            for (Object obj : list) {                           //se va itera pe toate obiectele din arrayList
                for (Field field : fieldlist) {                 //pentru fiecare obiect se va itera prin field-urile acestuia
                    if (!field.getName().equals("deleted")) {
                        field.setAccessible(true);
                        Object value;
                        value = field.get(obj);                    //se obtine valoarea field-ului
                        PdfPCell cell = new PdfPCell(new Paragraph(value.toString()));      //se seteaza textul din celula la valoarea field-ului
                        table.addCell(cell);
                    }
                }
            }
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException | IllegalAccessException | NoSuchFieldException | IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a bill in pdf format.
     * @param cname Name of the client that placed the order
     * @param pname Product needed
     * @param quantity  Quantity of the product
     * @param priceOrder total price
     */
    public static void billPdf(String cname, String pname, int quantity, int priceOrder) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("bill" + bill + ".pdf"));
            bill++;
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Paragraph chunk = new Paragraph("Clientul " + cname + " a comandat " + pname + " in cantitate de " + quantity + " cu pretul total de " + priceOrder, font);

            document.add(chunk);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generated a under stock bill
     * @param cname Name of the client that placed the order.
     * @param pname Name of the product ordered.
     * @param quantity  Quantity of the ordered product.
     */
    public static void billLowOnStock(String cname, String pname, int quantity) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("billLowStock" + billNoStock + ".pdf"));
            billNoStock++;
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Paragraph chunk = new Paragraph("Stoc insuficient pentru comanda urmatoare: " + cname + " comanda " + pname + " in cantitate de " + quantity + ".", font);

            document.add(chunk);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getClients() {
        return Clients;
    }

    public static void setClients(int clients) {
        Clients = clients + 1;
    }

    public static int getProducts() {
        return Products;
    }

    public static void setProducts(int products) {
        Products = products + 1;
    }

    public static int getOrders() {
        return Orders;
    }

    public static void setOrders(int orders) {
        Orders = orders + 1;
    }
}