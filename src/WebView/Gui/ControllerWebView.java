package WebView.Gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWebView implements Initializable {


    @FXML
    private AnchorPane anchorPaneWebview;

    @FXML
    private SplitPane mainWebView;

    private SplitWindow startView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainWebView.setId("BoxWebView");
        startView = new SplitWindow(null, null);
        startView.createStartView(mainWebView);
        setButtonFunction();
    }

    private void setButtonFunction(){
        anchorPaneWebview.getChildren().get(1).setOnMouseClicked(event -> {
            startView.showEditButtons();
        });
    }

}
