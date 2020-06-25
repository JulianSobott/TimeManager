package GuiElements;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TabCustom extends Tab {

    public TabCustom() {
        super();
    }

    public TabCustom(String name, Node content, String iconPath, Node settingsNode) {
        super(name, content);
        iconPathProperty().set(iconPath);
        settingsNodeProperty().set(settingsNode);
    }

    private void setIcon(String iconPath) {
        Image img = new Image(iconPath);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(getTabWindow().getImageSize());
        imageView.setFitHeight(getTabWindow().getImageSize());
        setGraphic(imageView);
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
