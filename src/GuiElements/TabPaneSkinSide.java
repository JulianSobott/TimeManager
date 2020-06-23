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

import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
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

    private final TabPaneBehavior tabPaneBehavior;

    private ObservableList<TabContentRegion> tabContentRegions;
    private TabMenu tabMenu;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    protected TabPaneSkinSide(TabWindow control) {
        super(control);
        tabPaneBehavior = new TabPaneBehavior(control);

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
        double menuWidth = tabMenu.prefWidth(-1);
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


    static class TabMenu extends StackPane {

        private final ObservableList<TabLabel> tabLabels;
        private final VBox labelsContainer;

        public TabMenu() {
            tabLabels = FXCollections.observableArrayList();
            labelsContainer = new VBox();

            labelsContainer.getStyleClass().setAll("debug");
            getChildren().add(labelsContainer);
        }

        @Override
        protected void layoutChildren() {
            labelsContainer.relocate(0, 0);
            labelsContainer.resize(labelsContainer.prefWidth(-1), labelsContainer.prefHeight(-1));
        }

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

    }

    static class TabLabel extends StackPane {

        private final Tab tab;
        private Label label;

        public TabLabel(Tab tab) {
            this.tab = tab;
            label = new Label(tab.getText(), tab.getGraphic());
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

        private Tab tab;

        public TabContentRegion(Tab tab) {
            this.tab = tab;
            getChildren().setAll(tab.getContent());
        }

    }


}
