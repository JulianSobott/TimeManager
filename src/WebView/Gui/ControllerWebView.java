package WebView.Gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

    @FXML private Button btnToggleEdit;

    private SplitWindow startView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainWebView.setId("BoxWebView");
        startView = new SplitWindow(null, null);
        startView.createStartView(mainWebView);

        btnToggleEdit.setOnMousePressed(e -> {
            setEditing(!isEditing());
        });
    }

    private BooleanProperty editing;
    public void setEditing(boolean value) { editingProperty().set(value); }
    public boolean isEditing() { return editingProperty().get(); }

    public BooleanProperty editingProperty() {
        if (editing == null) {
            editing = new SimpleBooleanProperty(this, "editing");
        }
        return editing;
    }


}
