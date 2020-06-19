package GuiElements;

import javafx.geometry.Side;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabWindow extends TabPane {

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
}
