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
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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
    private SettingsPane0 settingsPane;

    private static final double ANIMATION_DURATION = 150; // millis

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    protected TabPaneSkinSide(TabWindow control) {
        super(control);
        tabPaneBehavior = new TabWindowBehavior(control);

        tabMenu = new TabMenu();
        settingsPane = new SettingsPane0();

        tabContentRegions = FXCollections.observableArrayList();

        for(Tab t : control.getTabs()) {
            addTabContent((TabCustom) t);
        }

        // TabMenu always on top
        getChildren().add(tabMenu);
        getChildren().add(settingsPane);

        // listeners
        control.showingTextProperty().addListener(e -> showText(control.isShowingText()));
        if (control.isCloseMenuAfterSelect()) {
            // TODO: handle changes of property
            control.getSelectionModel().selectedIndexProperty().addListener(e ->  control.setShowingText(false) );
        }
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
        double contentStartY = y;

        for(TabContentRegion tabContentRegion : tabContentRegions) {
            tabContentRegion.relocate(contentStartX, contentStartY);
            tabContentRegion.resize(contentWidth, contentHeight);
        }

        double settingsWidth = settingsPane.prefWidth(-1);
        double settingsHeight = h;
        double settingsStartX = w - settingsWidth;
        settingsPane.resize(settingsWidth, settingsHeight);
        settingsPane.relocate(settingsStartX, y);
        settingsPane.setMaxWidth(contentWidth);
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
                    tabsToAdd.remove(tabContentRegion.tab);
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

        settingsPane.addTab(tab);
    }

    private void removeTabContent(TabCustom tab) {
        for (TabContentRegion region : tabContentRegions) {
            if(region.tab.equals(tab)) {
                // TODO: remove listeners
                tabContentRegions.remove(region);
                getChildren().remove(region);
                break;
            }
        }
        settingsPane.removeTab(tab);
    }

    private void showText(boolean isShowing, boolean animation) {
        showArea(tabMenu, isShowing, animation);
    }

    private void showText(boolean isShowing) {
        showText(isShowing, true);
    }

    private void showArea(AnimationArea area, boolean isShowing, boolean animation) {
        double start;
        double end;
        Duration duration = animation ? Duration.millis(ANIMATION_DURATION) : Duration.millis(0.1);
        if(isShowing) {
            start = 0.0;
            end = 1.0;
        } else {
            start = 1.0;
            end = 0.0;
        }
        double width = area.computePrefWidth(-1);
        area.resize(width, area.getHeight());
        area.animationTransition.set(start);
        area.currentAnimation = createTimeline(area, duration, end, null);
        area.currentAnimation.play();
    }



    private Timeline createTimeline(final AnimationArea area, final Duration duration, final double endValue,
                                    final EventHandler<ActionEvent> func) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);

        KeyValue keyValue = new KeyValue(area.animationTransition, endValue, Interpolator.LINEAR);
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


    class TabMenu extends AnimationArea {

        private final ObservableList<TabLabel> tabLabels;
        private final VBox labelsContainer;
        private final ToggleButton btnToggleCollapse;
        private final Rectangle clipLabels;


        public TabMenu() {
            getStyleClass().setAll("tab-menu");

            // tab labels
            clipLabels = new Rectangle();
            tabLabels = FXCollections.observableArrayList();
            labelsContainer = new VBox();
            labelsContainer.setClip(clipLabels);
            int i = 0;
            for (Tab tab : getSkinnable().getTabs()) {
                addTab((TabCustom) tab, i++);
            }
            getChildren().add(labelsContainer);

            // Menu button
            btnToggleCollapse = new ToggleButton();
            Image img = new Image("/Icons/list-48dp.png");
            ImageView imgView = new ImageView(img);
            imgView.fitWidthProperty().bind(getSkinnable().imageSizeProperty());
            imgView.fitHeightProperty().bind(getSkinnable().imageSizeProperty());
            btnToggleCollapse.setGraphic(imgView);
            getChildren().add(btnToggleCollapse);
            btnToggleCollapse.getStyleClass().setAll("btn-menu");


            getSkinnable().showingTextProperty().bindBidirectional(btnToggleCollapse.selectedProperty());
        }

        @Override
        protected void layoutChildren() {
            double startX = snappedLeftInset();
            double startY = snappedTopInset();
            double w = snapSize(getWidth()  - snappedLeftInset() - snappedRightInset()) ;
            double h = snapSize(getHeight() - snappedTopInset() - snappedBottomInset());

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

            nonOverlapProperty().set(iconBarWidth());
        }

        @Override
        protected double computePrefWidth(double height) {
            if (labelsContainer.getChildren().size() == 0) {
                return iconBarWidth();
            }
            return snapSize(iconBarWidth() +
                    (labelsContainer.prefWidth(height) - iconBarWidth() + snappedLeftInset() + snappedRightInset())
                            * animationTransition.getValue());
        }

        private double iconBarWidth() {
            // btnToggleCollapse must have same left border width as icons. Otherwise add icon insets
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

        protected final TabCustom tab;
        protected Label label;


        public TabLabel(TabCustom tab) {
            this.tab = tab;
            getStyleClass().setAll("tab-label");

            double size = getSkinnable().getImageSize();
            label = new Label(tab.getText(), tab.getGraphic());
            label.getStyleClass().clear();

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

            tab.selectedProperty().addListener(e -> {
                pseudoClassStateChanged(SELECTED_PSEUDOCLASS_STATE, tab.isSelected());
                requestLayout();
            });
            pseudoClassStateChanged(SELECTED_PSEUDOCLASS_STATE, tab.isSelected());
        }

        public Tab getTab() {
            return tab;
        }

        @Override
        protected double computePrefWidth(double height) {
            return label.prefWidth(height) + snappedLeftInset() + snappedRightInset();
        }


    }

    private static final PseudoClass SELECTED_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("selected");

    static class TabVisibleRegion extends StackPane {
        protected TabCustom tab;

        private final InvalidationListener tabSelectedListener = v -> setVisible(tab.isSelected());

        private final WeakInvalidationListener weakTabSelectedListener =
                new WeakInvalidationListener(tabSelectedListener);

        public TabVisibleRegion(TabCustom tab) {
            this.tab = tab;
            setVisible(tab.isSelected());
            tab.selectedProperty().addListener(weakTabSelectedListener);
        }

        public TabCustom getTab() {
            return tab;
        }
    }

    static class TabContentRegion extends TabVisibleRegion {

        // TODO: TabContentListener


        public TabContentRegion(TabCustom tab) {
            super(tab);
            getChildren().setAll(tab.getContent());
        }
    }


    class TabSettingsRegion extends TabVisibleRegion {

        public TabSettingsRegion(TabCustom tab) {
            super(tab);

            Node tabSettings = tab.getSettingsNode();
            if (tabSettings == null) {
                tabSettings = new Label("No settings for this tab available.");
            } else {
                ((TabSettings)tabSettings).setCloseSettingsAction(n -> getSkinnable().setShowingSettings(false));
            }
            getChildren().add(tabSettings);
        }
    }

    class SettingsPane3 extends StackPane {
        // Tabs
        private ObservableList<TabSettingsRegion> tabSettingsRegions;

        public SettingsPane3() {
            tabSettingsRegions = FXCollections.observableArrayList();
        }

        public void addTab(TabCustom tab) {
            TabSettingsRegion region = new TabSettingsRegion(tab);
            tabSettingsRegions.add(region);
            getChildren().add(region);
        }

        public void removeTab(TabCustom tab) {
            for (TabSettingsRegion region : tabSettingsRegions) {
                if (region.getTab().equals(tab)) {
                    tabSettingsRegions.remove(region);
                    getChildren().remove(region);
                    break;
                }
            }
        }
        private TabSettingsRegion getCurrentRegion() {
            for (TabSettingsRegion tabSettingsRegion : tabSettingsRegions) {
                if (tabSettingsRegion.isVisible()) {
                    return tabSettingsRegion;
                }
            }
            return null;
        }

    }

    class SettingsPane2 extends StackPane {
        // TabsContainer and General

        private final SettingsPane3 tabsPane;
        private final StackPane generalPane;

        public SettingsPane2() {
            // General
            generalPane = new StackPane();
            getChildren().add(generalPane);
            Node generalSettings = getSkinnable().getSettings();
            if (generalSettings == null) {
                generalSettings = new Label("No general settings available.");
            } else {
                ((TabSettings)generalSettings).setCloseSettingsAction(n -> getSkinnable().setShowingSettings(false));
            }
            generalPane.getChildren().add(generalSettings);

            // Tabs
            tabsPane = new SettingsPane3();
            getChildren().add(tabsPane);
        }

        public void showSettings(Settings settings) {
            boolean showGeneral = settings == Settings.GENERAL;
            generalPane.setVisible(showGeneral);
            tabsPane.setVisible(!showGeneral);

        }
    }

    public enum Settings {
        CUSTOM, GENERAL
    }

    class SettingsPane1 extends VBox {
        // Control + settings

        private final SettingsPane2 settingsPane2;

        public SettingsPane1() {
            // Control
            Pane paneControl = new HBox();
            getChildren().add(paneControl);

            ToggleGroup toggleGroup = new ToggleGroup();

            ToggleButton btnCustom = new ToggleButton("Custom");
            paneControl.getChildren().add(btnCustom);
            btnCustom.setToggleGroup(toggleGroup);

            ToggleButton btnGeneral = new ToggleButton("General");
            paneControl.getChildren().add(btnGeneral);
            btnGeneral.setToggleGroup(toggleGroup);

            // SettingsPane2
            settingsPane2 = new SettingsPane2();
            getChildren().add(settingsPane2);

            // bindings
            btnCustom.prefWidthProperty().bind(widthProperty().divide(2));
            btnGeneral.prefWidthProperty().bind(widthProperty().divide(2));

            // Style classes
            paneControl.getStyleClass().setAll("control-pane");
            btnCustom.getStyleClass().setAll("btn-control");
            btnGeneral.getStyleClass().setAll("btn-control");

            // Actions
            isGeneralSelectedProperty().bind(toggleGroup.selectedToggleProperty().isEqualTo(btnGeneral));

            isGeneralSelectedProperty().addListener(l -> {
                // TODO: Always one selected
                if (isGeneralSelectedProperty().get()) {
                    settingsPane2.showSettings(Settings.GENERAL);
                } else {
                    settingsPane2.showSettings(Settings.CUSTOM);
                }
            });

            // Default value
            toggleGroup.selectToggle(btnCustom);
        }


        private BooleanProperty isGeneralSelected;

        private BooleanProperty isGeneralSelectedProperty() {
            if (isGeneralSelected == null) {
                isGeneralSelected = new SimpleBooleanProperty(this, "isGeneralSelected", false);
            }
            return isGeneralSelected;
        }

    }

    class SettingsPane0 extends AnimationArea {
        // Settings button + content

        private ToggleButton btnSettings;
        private SettingsPane1 settingsPane1;

        private Rectangle clip;

        public SettingsPane0() {

            // Content
            settingsPane1 = new SettingsPane1();
            getChildren().add(settingsPane1);

            clip = new Rectangle();
            settingsPane1.setClip(clip);

            // Button
            btnSettings = new ToggleButton("Settings"); // TODO: replace with icon
            btnSettings.getStyleClass().setAll("btn-settings");
            getChildren().add(btnSettings);

            // Listeners
            getSkinnable().showingSettingsProperty().bindBidirectional(btnSettings.selectedProperty());

            getSkinnable().getSelectionModel().selectedItemProperty().addListener(l -> requestLayout());
            getSkinnable().showingSettingsProperty().addListener(
                    e->showArea(this, getSkinnable().isShowingSettings(), true));
            showArea(this, getSkinnable().isShowingSettings(), false);

            // Style classes
            settingsPane1.getStyleClass().setAll("tab-settings-pane");
        }

        @Override
        protected void layoutChildren() {
            double x = snappedLeftInset();
            double y = snappedTopInset();
            double btnW = btnSettings.prefWidth(-1);
            double btnH = btnSettings.prefHeight(-1);
            btnSettings.getTransforms().clear();
            btnSettings.getTransforms().add(new Translate(btnH, -btnW / 2));
            btnSettings.getTransforms().add(new Rotate(90));

            btnSettings.resize(btnSettings.prefWidth(-1), btnSettings.prefHeight(-1));
            btnSettings.relocate(x, getHeight() / 2 + btnSettings.prefHeight(-1) / 2);

            x = x + btnSettings.prefHeight(-1);
            double width = settingsPane1.prefWidth(-1);
            double height = getHeight() - snappedBottomInset() - snappedTopInset();
            settingsPane1.resize(width, height);
            settingsPane1.relocate(x, y);
            clip.setX(0);
            clip.setY(0);
            clip.setWidth(width * animationTransition.get());
            clip.setHeight(height);

        }

        @Override
        protected double computePrefWidth(double height) {
            double padding = snappedLeftInset() + snappedRightInset();
            double settingsWidth = settingsPane1.prefWidth(height);
            return btnSettings.prefHeight(-1) + padding + (settingsWidth * animationTransition.get());
        }

        public void addTab(TabCustom tab) {
            settingsPane1.settingsPane2.tabsPane.addTab(tab);
        }

        public void removeTab(TabCustom tab) {
            settingsPane1.settingsPane2.tabsPane.removeTab(tab);
        }
    }

    class AnimationArea extends StackPane {
        protected final DoubleProperty animationTransition =
                new SimpleDoubleProperty(this, "animationTransition", 1.0) {
                    @Override protected void invalidated() { requestLayout(); }
                };
        protected Timeline currentAnimation;

        @Override
        protected double computePrefWidth(double height) {
            return super.computePrefWidth(height);
        }
    }
}
