package tabs.page_add_tab;

import javafx.scene.control.Label;
import tabs.SingleChildLayout;
import tabs.TabData;

public class TabInfoBuiltin extends TabInfo{
    public TabInfoBuiltin(TabData tab, SingleChildLayout paneTabInfo) {
        super(tab, paneTabInfo);
    }

    @Override
    public void openDescription() {
        Label lblLongDescription = new Label(tab.longDescription);
        paneTabInfo.set(lblLongDescription);
    }

    @Override
    public void installTab() {

    }
}
