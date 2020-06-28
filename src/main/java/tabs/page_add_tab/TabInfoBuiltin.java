package tabs.page_add_tab;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import tabs.InstalledTabs;
import tabs.SingleChildLayout;
import tabs.TabData;
import tabs.Tabs;

import java.io.IOException;

public class TabInfoBuiltin extends TabInfo{

    private String mainPageFile;
    private String settingsPageFile;


    public TabInfoBuiltin(TabData tab, SingleChildLayout paneTabInfo, String mainPageFile, String settingsPageFile) {
        super(tab, paneTabInfo);
        this.mainPageFile = mainPageFile;
        this.settingsPageFile = settingsPageFile;
    }

    @Override
    public void openDescription() {
        Label lblLongDescription = new Label(tab.longDescription);
        lblLongDescription.setWrapText(true);
        lblLongDescription.prefWidthProperty().bind(paneTabInfo.widthProperty());
        paneTabInfo.set(lblLongDescription);
    }

    @Override
    public void installTab() {
        FxmlController mainPage = loadFxml(mainPageFile);
        FxmlController settingsPage = loadFxml(settingsPageFile);
        Tabs.TabWrapper tabWrapper = new Tabs.TabWrapper(mainPage.controller, mainPage.fxml,
                settingsPage.controller, settingsPage.fxml, tab);
        InstalledTabs.get().addTab(tabWrapper);
    }

    private FxmlController loadFxml(String filePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
            Pane fxml = fxmlLoader.load();
            Object controller = fxmlLoader.getController();
            return new FxmlController(controller, fxml);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return new FxmlController(null, null);
    }

    static class FxmlController {
        Object controller;
        Pane fxml;

        public FxmlController(Object controller, Pane fxml) {
            this.controller = controller;
            this.fxml = fxml;
        }
    }
}
