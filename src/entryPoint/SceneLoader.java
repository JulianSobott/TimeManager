package entryPoint;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class SceneLoader {

    private static SceneLoader instance;
    private Scene rootScene;

    private SceneLoader() {

    }

    public static SceneLoader getInstance() {
        if (instance == null)
            instance = new SceneLoader();

        return instance;
    }


    public void loadSceneInNewWindow(CalendarScene calendarScene, Object controller, String title) {

        Parent window = instance.loadFxmlFile(calendarScene, controller);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setMinWidth(500);
        stage.setMinHeight(600);
        stage.setScene(new Scene(window));
        stage.show();
        instance.rootScene = stage.getScene();
    }

    public void loadSceneInExistingWindow(CalendarScene calendarScene, Object controller) {
        Parent window = instance.loadFxmlFile(calendarScene, controller);
        instance.rootScene.setRoot(window);
    }


    public void loadSceneInNewWindowWithoutButtons(CalendarScene calendarScene, Object controller,
                                                   Node node, double xFactor, double yFactor) {

        Stage stage =  createStage(calendarScene, controller);
        instance.rootScene = node.getParent().getScene();

        double x = instance.rootScene.getWindow().getX();
        double y = instance.rootScene.getWindow().getY();

        double width = instance.rootScene.getWidth();
        double height = instance.rootScene.getHeight();

        stage.setX(x + (width * xFactor));
        stage.setY(y + (height * yFactor));

        stage.show();

    }


    private Stage createStage(CalendarScene calendarScene, Object controller){

        Parent window = instance.loadFxmlFile(calendarScene, controller);
        Stage stage = new Stage();
        stage.setScene(new Scene(window));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.resizableProperty().setValue(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }


    public void loadSettingsSceneInBoarderLessNewWindow(CalendarScene calendarScene, Object controller,
                                                        Node node) {

        Stage stage = createStage(calendarScene, controller);
        instance.rootScene = node.getParent().getScene();

        double x = instance.rootScene.getWindow().getX();
        double y = instance.rootScene.getWindow().getY();

        double width = instance.rootScene.getWidth();
        double height = instance.rootScene.getHeight();

        stage.setX(x + width - 350);
        stage.setY(y + 90);

        stage.show();

    }


    private Parent loadFxmlFile(CalendarScene calendarScene, Object controllerClass) {

        Parent window = null;
        String filePath = "/Calendar/Gui/" + calendarScene.fxmlPath;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
            fxmlLoader.setController(controllerClass);
            window = fxmlLoader.load();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return window;
    }



    /**
     * ################################### Header and filepath to the different Scenes #################################
     */


    public enum CalendarScene {
        NEW_LESSON("NewLesson/Lesson"),
        SETTINGS_CALENDAR("Settings/CalendarSettings")
        ;

        private String fxmlPath;

        CalendarScene(String fxmlPath) {
            this.fxmlPath = fxmlPath + ".fxml";
        }
    }

}
