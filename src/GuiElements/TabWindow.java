package GuiElements;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabWindow extends TabPane {

    private BooleanProperty showingText;

    public TabWindow() {
        this((Tab[])null);
    }

    public TabWindow(Tab... tabs) {
        super(tabs);
        this.setSide(Side.LEFT);
        this.setTabDragPolicy(TabDragPolicy.REORDER);
    }

    @Override protected Skin<?> createDefaultSkin() {
        return new TabPaneSkinSide(this);
    }

    public final void setShowingText(boolean value) {
        this.showingTextProperty().set(value);
    }

    public final boolean isShowingText() {
        return this.showingText != null && this.showingText.get();
    }

    public final BooleanProperty showingTextProperty() {
        if (this.showingText == null) {
            this.showingText = new SimpleBooleanProperty(this, "showingText", false);
        }
        return this.showingText;
    }
}
