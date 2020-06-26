package GuiElements;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.beans.EventHandler;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

@DefaultProperty("content")
public class TabSettings extends Control {

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TabSettingsSkin(this);
    }

    private ObjectProperty<Node> content;
    public void setContent(Node value) { contentProperty().set(value); }
    public Node getContent() { return contentProperty().get(); }

    public ObjectProperty<Node> contentProperty() {
        if (content == null) {
            content = new SimpleObjectProperty<>(this, "content");
        }
        return content;
    }

    private Consumer<Void> closeSettingsAction;

    public void setCloseSettingsAction(Consumer<Void> e) {
        this.closeSettingsAction = e;
    }

    public void closeSettings() {
        closeSettingsAction.accept(null);
    }

    public TabWindow getTabWindow() {
        Node n = this;
        while (n.getParent() != null && !(n instanceof TabWindow)) {
            n = n.getParent();
        }
        if (n instanceof TabWindow) {
            return (TabWindow) n;
        } else {
            return null;
        }
    }
}
