package sample;
import businessLayer.Restaurant;
import controller.ChefController;
import controller.MainController;
import dataLayer.RestaurantSerializator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

//java --module-path /home/dunca/Documents/Libraries/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar Assignment4.jar

        RestaurantSerializator.deserializator(getParameters().getRaw());    //face deserializarea

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent p = fxmlLoader.load(getClass().getResource("/view/chefScene.fxml").openStream());
        ChefController controller = (ChefController) fxmlLoader.getController();
        Restaurant restaurant = Restaurant.getInstance();
        restaurant.addPropertyChangeListener(controller);                   //adaugam in restaurant clasa ChefController ca Observer

        Scene chefView = new Scene(p, 370, 310);       //setez instanta controller-ului interfetei Chef in MainController
        MainController.setChef(chefView);                  //deoarece atunci cand schimbam view-urile nu avem voie sa cream alta instana a clasei
                                                                  //fiindca se pierder Observer-ul instantei acesteia

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScene.fxml"));
        Scene scene = new Scene(root, 450, 303);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Lambada Restaurant");
        primaryStage.setScene(scene);                   //se porneste aplicatia cu mainScene
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
