package presentation;

import businessLayer.ClientBll;
import businessLayer.OrderBll;
import businessLayer.ProductBll;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class used to parse the commands from the input file and calls
 * the logic layer to do the needed actions.
 */
public class CommandParser {
    /**
     * Used to open and parse the file into commands.
     * @param file path to the input file
     */
    public CommandParser(String file){
        try {
            Scanner scanner = new Scanner(new File(file));
            String line;
            while( scanner.hasNextLine()){      //se citeste pana cand nu mai exista linii
                line = scanner.nextLine();
                if(line.equals("Report client")){                       //la gasirea unei comenzi de tipul Report se va apela clasa PdfGenerator care face parte
                    PdfGenerator.reportPdf(ClientBll.reportTable());        //din acelas pachet pentru a genera pdf-ul din datele obtinute din interogarea reportTable()
                    continue;
                }
                if(line.equals("Report order")){
                    PdfGenerator.reportPdf(OrderBll.reportTable());
                    continue;
                }
                if(line.equals("Report product")){
                    PdfGenerator.reportPdf(ProductBll.reportTable());
                    continue;
                }
                String result[] = line.split(": ");

                if(result[0].equals("Insert client")){
                    result = result[1].split(", ");
                    ClientBll.insertClient(result[0], result[1]);       //apeleaza bussiness logic layer care se va ocupa mai departe de inserarea
                    continue;                                                   // prin apelarea layer-uluidata acces
                }

                if(result[0].equals("Insert product")){
                    result = result[1].split(", ");
                    ProductBll.insertProduct(result[0],Integer.valueOf(result[1]), Double.valueOf(result[2]));
                    continue;
                }
                if(result[0].equals("Order")){
                    result = result[1].split(", ");
                    boolean  err = OrderBll.insertOrder(result[0], result[1], Integer.valueOf(result[2])); //se va returna daca s-a introdus cu succes sau nu comanda in tabel
                    if(err){
                        PdfGenerator.billLowOnStock(result[0], result[1], Integer.valueOf(result[2]));  //in caz de eroare se va genera un bill cu undeStock
                    }else {
                        int totalPrice = OrderBll.getTotalPrice();
                        PdfGenerator.billPdf(result[0], result[1], Integer.valueOf(result[2]), totalPrice); //in caz de succes se va genera bill-ull cu pretul comenzii
                    }
                    continue;
                }

                if(result[0].equals("Delete client")){
                    result = result[1].split(", ");
                    ClientBll.deleteClient(result[0],result[1]);
                    continue;
                }
                if(result[0].equals("Delete Product")){
                    //delete in db
                    ProductBll.deleteProduct(result[1]);
                    continue;
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + file + " not found.");
        }
    }
}
