package tabs.page_add_tab;

import GuiElements.CustomWidget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tabs.*;

public class TabInfo extends CustomWidget {

    @FXML
    private VBox container;

    @FXML
    private Label lblName;

    @FXML
    private Label lblShortDescription;

    private final TabData tab;
    private final SingleChildLayout paneTabInfo;
    private final AddTabPage.ReloadList parentReload;


    public TabInfo(TabData tab, SingleChildLayout paneTabInfo, AddTabPage.ReloadList reloadList) {
        super("tabInfo.fxml");
        this.tab = tab;
        this.paneTabInfo = paneTabInfo;
        this.parentReload = reloadList;

        lblName.setText(tab.name);
        lblShortDescription.setText(tab.shortDescription);
        this.setOnMouseClicked(e -> openDescription());
    }


    public void openDescription() {
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        engine.load("http://127.0.0.1:8080/plugins/info/" + tab.name + "/html");
        paneTabInfo.set(browser);
        browser.setStyle("-fx-border-color: red; -fx-border-width: 2px");

        Sizing.setHeight(browser.prefHeightProperty(), paneTabInfo, 1);
        Sizing.setWidth(browser.prefWidthProperty(), paneTabInfo, 1);
    }

    public void installTab() {
        Tabs.TabWrapper tab = Tabs.installTab(this.tab);
        InstalledTabs.get().addTab(tab);
        // TODO: Select tab

        parentReload.reloadList();
    }
}
