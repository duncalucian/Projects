package dataLayer;

import businessLayer.MenuItem;
import businessLayer.Restaurant;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSerializator {

    public static void serializator(){     //executa serializarea
        try {
            FileOutputStream fileOut = new FileOutputStream("restaurant.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Restaurant.getInstance().getMenu());
            out.close();
            fileOut.close();
            Platform.exit();
            System.out.println("Serialized");
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deserializator(List<String> param){     //executa serializarea
        try {
            if(param.size() == 1){
            FileInputStream fileIn = new FileInputStream(param.get(0));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Restaurant.getInstance().setMenu((ArrayList<MenuItem>) in.readObject());
            in.close();
            fileIn.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
