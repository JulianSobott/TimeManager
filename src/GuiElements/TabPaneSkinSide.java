/*
 * Copyright (c) 2011, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package GuiElements;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class TabPaneSkinSide extends SkinBase<TabWindow> {

    /***************************************************************************
     *                                                                         *
     * Enums                                                                   *
     *                                                                         *
     **************************************************************************/


    /***************************************************************************
     *                                                                         *
     * Private fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final TabWindowBehavior tabPaneBehavior;

    private ObservableList<TabContentRegion> tabContentRegions;
    private TabMenu tabMenu;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    protected TabPaneSkinSide(TabWindow control) {
        super(control);
        tabPaneBehavior = new TabWindowBehavior(control);

        tabMenu = new TabMenu();

        tabContentRegions = FXCollections.observableArrayList();

        for(Tab t : control.getTabs()) {
            addTabs(t);
        }

        // TabMenu always on top
        getChildren().add(tabMenu);
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
    @Override public void dispose() {
        super.dispose();

        if (tabPaneBehavior != null) {
            tabPaneBehavior.dispose();
        }
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        // Menu
        double menuWidth = tabMenu.getNonOverlapWidth();
        double menuHeight = h;

        tabMenu.resize(menuWidth, menuHeight);
        tabMenu.relocate(x, y);

        // Content
        double contentWidth = w - menuWidth;
        double contentHeight = h;
        double contentStartX = menuWidth;
        double contentStartY = 0;

        for(TabContentRegion tabContentRegion : tabContentRegions) {
            tabContentRegion.relocate(contentStartX, contentStartY);
            tabContentRegion.resize(contentWidth, contentHeight);
        }
    }

    private void addTabs(Tab... tabs) {
        for (Tab tab: tabs) {
            addTabContent(tab);
            tabMenu.addTab(tab);
        }
    }

    private void addTabContent(Tab tab) {
        TabContentRegion region = new TabContentRegion(tab);
        tabContentRegions.add(region);
        getChildren().add(region);
    }


    /***************************************************************************
     *                                                                         *
     * Support classes                                                         *
     *                                                                         *
     **************************************************************************/


    class TabMenu extends StackPane {

        private final ObservableList<TabLabel> tabLabels;
        private final VBox labelsContainer;
        private final ToggleButton btnToggleCollapse;

        public TabMenu() {
            tabLabels = FXCollections.observableArrayList();
            labelsContainer = new VBox();


            btnToggleCollapse = new ToggleButton();

            Image img = new Image("/Icons/list-48dp.png");
            ImageView imgView = new ImageView(img);
            double size = getSkinnable().getImageSize(); // TODO: bind to property
            imgView.setFitWidth(size);
            imgView.setFitHeight(size);
            btnToggleCollapse.setGraphic(imgView);
            getChildren().add(btnToggleCollapse);
            getStyleClass().setAll("debug");
            btnToggleCollapse.getStyleClass().clear();
            getChildren().add(labelsContainer);
        }

        @Override
        protected void layoutChildren() {
            // Menu button
            btnToggleCollapse.relocate(0, 0);
            btnToggleCollapse.resize(btnToggleCollapse.prefWidth(-1), btnToggleCollapse.prefHeight(-1));
            // labels container
            double spacing = 20;
            labelsContainer.relocate(0, btnToggleCollapse.prefHeight(-1) + spacing);
            labelsContainer.resize(labelsContainer.prefWidth(-1), labelsContainer.prefHeight(-1));

            // TODO: maybe find solution that isn't binding it here, but in constructor
            // TODO: differentiate between overlap and not
            nonOverlapProperty().bind(getSkinnable().imageSizeProperty().add(labelsContainer.snappedLeftInset()));
        }

        // Public API

        public void addTab(Tab tab) {
            TabLabel tabLabel = new TabLabel(tab);
            tabLabels.add(tabLabel);
            labelsContainer.getChildren().add(tabLabel);
        }

        public void removeTab(Tab tab) {
            for (int i = 0; i < tabLabels.size(); i++) {
                if(tabLabels.get(i).getTab() == tab) {
                    tabLabels.remove(i);
                    break;
                }
            }
        }

        // Properties

        private DoubleProperty nonOverlapWidth;

        public DoubleProperty nonOverlapProperty() {
            if (nonOverlapWidth == null) {
                nonOverlapWidth =  new SimpleDoubleProperty(this, "nonOverlapWidth", -1);
            }
            return nonOverlapWidth;
        }

        public double getNonOverlapWidth() {
            return nonOverlapProperty().get();
        }
    }

    class TabLabel extends StackPane {

        private final Tab tab;
        private Label label;

        public TabLabel(Tab tab) {
            this.tab = tab;
            double size = getSkinnable().getImageSize();
            Node graphic = tab.getGraphic();
            if (graphic != null) {
                // TODO: handle other elements
                ImageView img = (ImageView) graphic;
                img.setFitWidth(size);
                img.setFitHeight(size);
            }
            label = new Label(tab.getText(), graphic);
            getChildren().add(label);
        }

        public Tab getTab() {
            return tab;
        }

        @Override
        protected double computePrefWidth(double height) {
            return label.prefWidth(height);
        }
    }


    static class TabContentRegion extends StackPane {

        // TODO: TabContentListener

        private Tab tab;

        private final InvalidationListener tabSelectedListener = v -> setVisible(tab.isSelected());

        private final WeakInvalidationListener weakTabSelectedListener =
                new WeakInvalidationListener(tabSelectedListener);

        public TabContentRegion(Tab tab) {
            this.tab = tab;
            getChildren().setAll(tab.getContent());

            setVisible(tab.isSelected());
            tab.selectedProperty().addListener(weakTabSelectedListener);
        }
    }
}
