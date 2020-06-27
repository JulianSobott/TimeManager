package entryPoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import themes.Theme;
import themes.ThemeLoader;

public class Main extends Application {

    public static Main application;

    @Override
    public void start(Stage primaryStage) throws Exception{
        application = this;

        // Theme initiation
        ThemeLoader.get().addThemes(
                new Theme("Dark", "/css/theme_dark.css", "dark"),
                new Theme("Light", "/css/theme_light.css", "light"),
                new Theme("Material", "/css/theme_material.css", "material"),
                new Theme("Reddish", "/css/theme_reddish.css", "reddish")
        );
        ThemeLoader.get().setPermanentStylesheets("/entryPoint/MainDesign.css", "/css/color_classes.css");
        ThemeLoader.get().setTheme("dark");

        setUserAgentStylesheet(getClass().getResource("debug_styles.css").toString());
        Parent root = FXMLLoader.load(getClass().getResource("mainPage/MainPage.fxml"));

        ThemeLoader.get().addRootNode(root);


        primaryStage.setTitle("Time Manager");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
