package entryPoint;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
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

        Popup popup = createPopup(calendarScene, controller);
        instance.rootScene = node.getParent().getScene();

        double x = instance.rootScene.getWindow().getX();
        double y = instance.rootScene.getWindow().getY();

        double width = instance.rootScene.getWidth();
        double height = instance.rootScene.getHeight();

        popup.setX(x + (width * xFactor));
        popup.setY(y + (height * yFactor));

        popup.show(node.getScene().getWindow());

    }


    private Popup createPopup(CalendarScene calendarScene, Object controller) {

        Parent window = instance.loadFxmlFile(calendarScene, controller);
        Popup popup = new Popup();
        popup.getContent().add(window);
        return popup;
    }


    public void loadSettingsSceneInBoarderLessNewWindow(CalendarScene calendarScene, Object controller,
                                                        Node node) {

        Popup popup = createPopup(calendarScene, controller);
        instance.rootScene = node.getParent().getScene();

        double x = instance.rootScene.getWindow().getX();
        double y = instance.rootScene.getWindow().getY();

        double width = instance.rootScene.getWidth();
        double height = instance.rootScene.getHeight();

        popup.setX(x + width - 350);
        popup.setY(y + 90);

        popup.show(node.getScene().getWindow());

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





    public void loadAnimationPopupWindow(Node node, AnchorPane anchorPaneRemove, CalendarScene calendarScene, Object controllerClass)  {

        Parent root = loadFxmlFile(calendarScene, controllerClass );

        Scene scene = node.getScene();

        root.translateXProperty().set(scene.getWidth());

        Popup parentContainer = (Popup) node.getScene().getWindow();

        parentContainer.getContent().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getContent().remove(anchorPaneRemove);
        });
        timeline.play();
    }


    /**
     * ################################### Header and filepath to the different Scenes #################################
     */


    public enum CalendarScene {
        NEW_LESSON("NewLesson/Lesson"),
        SETTINGS_CALENDAR("Settings/CalendarSettings"),
        EDIT_LESSON("EditLesson/EditLesson") ;


        private String fxmlPath;

        CalendarScene(String fxmlPath) {
            this.fxmlPath = fxmlPath + ".fxml";
        }
    }

}
