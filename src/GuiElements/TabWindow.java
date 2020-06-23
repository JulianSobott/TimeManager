package GuiElements;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
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

    private DoubleProperty imageSize;

    public final void setImageSize(double value) {
        this.imageSizeProperty().set(value);
    }

    public final double getImageSize() {
        return this.imageSize != null ? this.imageSize.get() : 0;
    }

    public final DoubleProperty imageSizeProperty() {
        if(imageSize == null) {
            imageSize = new SimpleDoubleProperty(this, "imageSize", 48);
        }
        return imageSize;
    }

    public enum ContentResizing {
        OVERLAP, // Folded out menu overlaps tab content
        RESIZE  // Folded out menu resizes tab content
    }
    private ObjectProperty<ContentResizing> contentResizing;

    public final void setContentResizing(ContentResizing value) {
        this.contentResizingProperty().set(value);
    }

    private ObjectProperty<ContentResizing> contentResizingProperty() {
        if(contentResizing == null) {
            contentResizing = new SimpleObjectProperty<>(this, "contentResizing", ContentResizing.OVERLAP);
        }
        return contentResizing;
    }
}
