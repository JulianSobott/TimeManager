package GuiElements;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
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

    private BooleanProperty showingSettings;
    public void setShowingSettings(boolean value) { showingSettingsProperty().set(value); }
    public boolean isShowingSettings() { return showingSettingsProperty().get(); }

    public BooleanProperty showingSettingsProperty() {
        if (showingSettings == null) {
            showingSettings = new SimpleBooleanProperty(this, "showingSettings", false);
        }
        return showingSettings;
    }

    private DoubleProperty imageSize;

    public final void setImageSize(double value) {
        this.imageSizeProperty().set(value);
    }

    public final double getImageSize() {
        return imageSizeProperty().get();
    }

    public final DoubleProperty imageSizeProperty() {
        if(imageSize == null) {
            imageSize = new SimpleDoubleProperty(this, "imageSize", 48);
        }
        return imageSize;
    }

    private BooleanProperty contentResizing;

    public final void setContentResizing(boolean value) {
        this.contentResizingProperty().set(value);
    }
    public final boolean isContentResizing() {
        return this.contentResizingProperty().get();
    }

    public BooleanProperty contentResizingProperty() {
        if(contentResizing == null) {
            contentResizing = new SimpleBooleanProperty(this, "contentResizing", false);
        }
        return contentResizing;
    }

    private BooleanProperty closeMenuAfterSelect;

    public void setCloseMenuAfterSelect(boolean value) { closeMenuAfterSelectProperty().set(value);}
    public boolean isCloseMenuAfterSelect() { return closeMenuAfterSelectProperty().get(); }

    public BooleanProperty closeMenuAfterSelectProperty() {
        if (closeMenuAfterSelect == null) {
            closeMenuAfterSelect = new SimpleBooleanProperty(this, "closeMenuAfterClose", true);
        }
        return closeMenuAfterSelect;
    }

    private ObjectProperty<TabSettings> settings;

    public void setSettings(TabSettings value) { settingsProperty().set(value); }
    public TabSettings getSettings() { return settingsProperty().get(); }

    public ObjectProperty<TabSettings> settingsProperty() {
        if (settings == null) {
            settings = new SimpleObjectProperty<>(this, "settings");
        }
        return settings;
    }

}
