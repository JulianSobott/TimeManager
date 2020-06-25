package GuiElements;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.concurrent.Callable;

public class TabCustom extends Tab {

    public TabCustom() {
        super();
        tabPaneProperty().addListener( l -> {
            if (getTabPane() != null) {
                updateIcon();
            }
        });
    }

    public TabCustom(String name, Node content, String iconPath, Node settingsNode) {
        super(name, content);
        setIconPath(iconPath);
        setSettingsNode(settingsNode);
        tabPaneProperty().addListener( l -> updateIcon());
    }

    private void updateIcon() {
        setIcon(getIconPath());
    }

    private void setIcon(String iconPath) {
        Node graphic;
        try {
            Image img = new Image(iconPath);
            ImageView imageView = new ImageView(img);
            imageView.fitWidthProperty().bind(getTabWindow().imageSizeProperty());
            imageView.fitHeightProperty().bind(getTabWindow().imageSizeProperty());
            graphic = imageView;
        } catch (IllegalArgumentException e) {
            // Image not found
            double size = getTabWindow().getImageSize();
            String textAlternative = getText().substring(0, 1).toUpperCase();
            StackPane sp = new StackPane();
            Text text = new Text(textAlternative);
            text.setBoundsType(TextBoundsType.VISUAL);
            sp.getChildren().add(text);

            text.setStyle("-fx-font-size: " + size);
            sp.setPrefSize(size, size);


            getTabWindow().imageSizeProperty().addListener(l -> {
                double newSize = getTabWindow().getImageSize();
                text.setStyle("-fx-font-size: " + newSize);
                sp.setPrefSize(newSize, newSize);
            });

            graphic = sp;
        }
        setGraphic(graphic);
    }

    private TabWindow getTabWindow() {
        return (TabWindow) getTabPane();
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/

    private StringProperty iconPath;

    public void setIconPath(String value) {
        iconPathProperty().set(value);
    }

    public String getIconPath() {
        return iconPathProperty().get();
    }

    public StringProperty iconPathProperty() {
        if (iconPath == null) {
            iconPath = new SimpleStringProperty(this, "icon", "");
        }
        return iconPath;
    }

    private ObjectProperty<Node> settingsNode;

    public void setSettingsNode(Node value) {
        settingsNodeProperty().set(value);
    }

    public Node getSettingsNode() {
        return settingsNodeProperty().get();
    }

    public ObjectProperty<Node> settingsNodeProperty() {
        if (settingsNode == null) {
            settingsNode = new SimpleObjectProperty<Node>(this, "icon", null);
        }
        return settingsNode;
    }

}
