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

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
    private Rectangle tabMenuClip;

    private static final double ANIMATION_DURATION = 500; // millis

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    protected TabPaneSkinSide(TabWindow control) {
        super(control);
        tabPaneBehavior = new TabWindowBehavior(control);

        tabMenu = new TabMenu();
        tabMenuClip = new Rectangle();
//        tabMenu.setClip(tabMenuClip);

        tabContentRegions = FXCollections.observableArrayList();

        for(Tab t : control.getTabs()) {
            addTabContent((TabCustom) t);
        }

        // TabMenu always on top
        getChildren().add(tabMenu);

        // listeners
        registerChangeListener(control.showingTextProperty(), e -> showText(control.isShowingText()));
        initializeTabListener();

        // Init menu width
        showText(control.isShowingText(), false);
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
        tabMenuClip.setX(x);
        tabMenuClip.setX(y);
        tabMenuClip.setWidth(menuWidth);
        tabMenuClip.setHeight(menuHeight);

        // Content
        double menuNonOverlapWidth;
        if (getSkinnable().getContentResizing() == TabWindow.ContentResizing.RESIZE) {
            menuNonOverlapWidth = menuWidth;
        } else {
            menuNonOverlapWidth = tabMenu.iconBarWidth();
        }
        double contentWidth = w - menuNonOverlapWidth;
        double contentHeight = h;
        double contentStartX = menuNonOverlapWidth;
        double contentStartY = 0;

        for(TabContentRegion tabContentRegion : tabContentRegions) {
            tabContentRegion.relocate(contentStartX, contentStartY);
            tabContentRegion.resize(contentWidth, contentHeight);
            tabContentRegion.getStyleClass().add("debug");
        }
    }

    /***************************************************************************
     *                                                                         *
     * Private Methods                                                              *
     *                                                                         *
     **************************************************************************/

    private void initializeTabListener() {
        getSkinnable().getTabs().addListener((ListChangeListener<Tab>) c -> {
            List<TabCustom> tabsToRemove = new ArrayList<>();
            List<TabCustom> tabsToAdd = new ArrayList<>();

            int insertPos = -1;

            while (c.next()) {
                if (c.wasPermutated()) {
                    // TODO
                }
                if (c.wasRemoved()) {
                    tabsToRemove.addAll((Collection<? extends TabCustom>) c.getRemoved());
                }
                if (c.wasAdded()) {
                    tabsToAdd.addAll((Collection<? extends TabCustom>) c.getAddedSubList());
                    insertPos = c.getFrom();
                }
            }

            tabsToRemove.removeAll(tabsToAdd);
            removeTabs(tabsToRemove);

            if (!tabsToAdd.isEmpty()) {
                for (TabContentRegion tabContentRegion : tabContentRegions) {
                    if (tabsToAdd.contains(tabContentRegion.tab)) {
                        tabsToAdd.remove(tabContentRegion.tab);
                    }
                }

                addTabs(tabsToAdd, insertPos == -1 ? tabContentRegions.size() : insertPos);
            }
        });
    }

    private void addTabs(List<? extends Tab> addedList, int from) {
        int i = from;
        for (Tab tab: addedList) {
            TabCustom tabCustom = (TabCustom) tab;
            addTabContent(tabCustom);
            tabMenu.addTab(tabCustom, i++);
            // TODO: add animation
        }
    }

    private void removeTabs(List<? extends TabCustom> tabs) {
        for (final TabCustom tab : tabs) {
            // TODO: remove listeners
            removeTabContent(tab);
            tabMenu.removeTab(tab);
        }
    }

    private void addTabContent(TabCustom tab) {
        TabContentRegion region = new TabContentRegion(tab);
        tabContentRegions.add(region);
        getChildren().add(region);
    }

    private void removeTabContent(Tab tab) {
        for (TabContentRegion region : tabContentRegions) {
            if(region.tab.equals(tab)) {
                // TODO: remove listeners
                tabContentRegions.remove(region);
                getChildren().remove(region);
                break;
            }
        }
    }

    private void showText(boolean isShowing, boolean animation) {
        double start;
        double end;
        Duration duration = animation ? Duration.millis(ANIMATION_DURATION) : Duration.millis(1.0);
        if(isShowing) {
            start = 0.0;
            end = 1.0;
        } else {
            start = 1.0;
            end = 0.0;
        }
        double width = tabMenu.computePrefWidth(-1);
        tabMenu.resize(width, tabMenu.getHeight());
        tabMenu.animationTransition.set(start);
        tabMenu.currentAnimation = createTimeline(tabMenu, duration, end, null);
        tabMenu.currentAnimation.play();
    }

    private void showText(boolean isShowing) {
        showText(isShowing, true);
    }

    private Timeline createTimeline(final TabMenu tabMenu, final Duration duration, final double endValue,
                                    final EventHandler<ActionEvent> func) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);

        KeyValue keyValue = new KeyValue(tabMenu.animationTransition, endValue, Interpolator.LINEAR);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(duration, keyValue));

        timeline.setOnFinished(func);
        return timeline;
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
        private final Rectangle clipLabels;

        // animation
        private final DoubleProperty animationTransition =
                new SimpleDoubleProperty(this, "animationTransition", 1.0) {
                    @Override protected void invalidated() { requestLayout(); }
        };
        private Timeline currentAnimation;


        public TabMenu() {
            tabLabels = FXCollections.observableArrayList();
            labelsContainer = new VBox();

            btnToggleCollapse = new ToggleButton();

            clipLabels = new Rectangle();
            labelsContainer.setClip(clipLabels);

            // TODO: remove
//            labelsContainer.getStyleClass().add("debug-bold");
            getStyleClass().add("debug-bold-2");
            setStyle("-fx-background-color: yellow");

            // tab labels
            int i = 0;
            for (Tab tab : getSkinnable().getTabs()) {
                addTab((TabCustom) tab, i++);
            }

            // Menu button
            Image img = new Image("/Icons/list-48dp.png");
            ImageView imgView = new ImageView(img);
            double size = getSkinnable().getImageSize(); // TODO: bind to property
            imgView.setFitWidth(size);
            imgView.setFitHeight(size);
            btnToggleCollapse.setGraphic(imgView);
            getChildren().add(btnToggleCollapse);
            btnToggleCollapse.getStyleClass().clear();
            getChildren().add(labelsContainer);
        }

        @Override
        protected void layoutChildren() {
            double startX = snappedLeftInset();
            double startY = snappedTopInset();
            double w = snapSizeX(getWidth()  - snappedLeftInset() - snappedRightInset()) ;
            double h = snapSizeX(getHeight() - snappedTopInset() - snappedBottomInset());

            // Menu button
            btnToggleCollapse.relocate(startX, startY);
            btnToggleCollapse.resize(btnToggleCollapse.prefWidth(-1), btnToggleCollapse.prefHeight(-1));

            // labels container
            double spacing = 20;
            double spaceTop = btnToggleCollapse.prefHeight(-1) + spacing + startY;
            double labelsHeight = h - spaceTop + startY;
            labelsContainer.relocate(startX, spaceTop);
            labelsContainer.resize(labelsContainer.prefWidth(-1), labelsHeight);

            clipLabels.setX(0);
            clipLabels.setY(0);
            clipLabels.setWidth(computePrefWidth(-1) - snappedRightInset() - snappedLeftInset());
            clipLabels.setHeight(Double.MAX_VALUE);


            // Toggle menu on click
            getSkinnable().showingTextProperty().bind(btnToggleCollapse.selectedProperty());

            nonOverlapProperty().set(iconBarWidth());
        }

        @Override
        protected double computePrefWidth(double height) {
            return snapSizeX(iconBarWidth() +
                    (labelsContainer.prefWidth(height) - iconBarWidth() + snappedLeftInset() + snappedRightInset())
                            * animationTransition.getValue());
        }

        private double iconBarWidth() {
            return btnToggleCollapse.prefWidth(-1) + snappedLeftInset() + snappedRightInset()
                    + labelsContainer.snappedLeftInset() + labelsContainer.snappedRightInset();
        }

        // Public API

        public void addTab(TabCustom tab, int addToIndex) {
            TabLabel tabLabel = new TabLabel(tab);
            tabLabels.add(addToIndex, tabLabel);
            labelsContainer.getChildren().add(addToIndex, tabLabel);
        }

        public void removeTab(TabCustom tab) {
            for(TabLabel tabLabel : tabLabels) {
                if (tabLabel.getTab() == tab) {
                    labelsContainer.getChildren().remove(tabLabel);
                    tabLabels.remove(tabLabel);
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

        private final TabCustom tab;
        private Label label;

        public TabLabel(TabCustom tab) {
            this.tab = tab;
            double size = getSkinnable().getImageSize();
            label = new Label(tab.getText(), tab.getGraphic());
            label.getStyleClass().add("debug");

            label.setMaxWidth(Double.MAX_VALUE);
            getChildren().add(label);

            setOnMousePressed(e -> {
                if (tab.isDisable()) {
                    return;
                }
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    tabPaneBehavior.selectTab(tab);
                }
                if (e.getButton().equals(MouseButton.MIDDLE)) {
                    tabPaneBehavior.closeTab(tab);
                }
                // TODO: context menu + close
            });
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

        private TabCustom tab;

        private final InvalidationListener tabSelectedListener = v -> setVisible(tab.isSelected());

        private final WeakInvalidationListener weakTabSelectedListener =
                new WeakInvalidationListener(tabSelectedListener);

        public TabContentRegion(TabCustom tab) {
            this.tab = tab;
            getChildren().setAll(tab.getContent());

            setVisible(tab.isSelected());
            tab.selectedProperty().addListener(weakTabSelectedListener);
        }
    }
}
