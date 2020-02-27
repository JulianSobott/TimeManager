package Calendar.Gui.Settings;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalendarSettings implements Initializable {

    @FXML
    private AnchorPane anchorPaneSettings;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        makeFadeInTransition(0, 1,false);
    }




    private void makeFadeInTransition(int startValue, int targetValue, boolean exit) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(800));
        fadeTransition.setNode(anchorPaneSettings);
        fadeTransition.setFromValue(startValue);
        fadeTransition.setToValue(targetValue);
        fadeTransition.play();
    }



    @FXML
    private void closeWindow() throws InterruptedException {

        makeFadeInTransition(1, 0, true);

    }


}


