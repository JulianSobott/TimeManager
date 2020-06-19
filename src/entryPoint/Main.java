package entryPoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        setUserAgentStylesheet(getClass().getResource("debug_styles.css").toString());
        Parent root = FXMLLoader.load(getClass().getResource("mainPage/MainPage.fxml"));
        primaryStage.setTitle("Time Manager");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
