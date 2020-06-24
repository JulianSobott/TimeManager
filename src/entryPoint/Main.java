package entryPoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Theme;
import utils.ThemeLoader;

import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Theme initiation
        ThemeLoader.get().addThemes(
                new Theme("Dark", "/css/theme_dark.css", "dark"),
                new Theme("Light", "/css/theme_light.css", "light")
        );

        Parent root = FXMLLoader.load(getClass().getResource("mainPage/MainPage.fxml"));

        ThemeLoader.get().addRootNode(root);
        ThemeLoader.get().setTheme("dark");

        primaryStage.setTitle("Time Manager");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
