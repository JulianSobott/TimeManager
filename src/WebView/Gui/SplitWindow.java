package WebView.Gui;

import GuiElements.ButtonIcon;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Tree of SplitWindows. A leaf is a SplitWindow with exactly one SplitSide.
 *
 */


public class SplitWindow extends SplitPane {

    private final SplitSide startSide;
    private final SplitWindow parent;

    private BooleanProperty editing;

    public SplitWindow(BooleanProperty editing) {
        this.parent = null;
        this.editing = editing;
        this.startSide = new SplitSide(this);
        init();
    }

    public SplitWindow(SplitWindow parent) {
        this.parent = parent;
        this.startSide = new SplitSide(this);
        init();
    }

    public SplitWindow(SplitWindow parent, SplitSide splitSide) {
        this.parent = parent;
        splitSide.setParent(this);
        startSide = splitSide;
        init();
    }

    private void init() {
        getItems().add(startSide);
    }

    private void split(Orientation orientation) {
        setOrientation(orientation);
        getItems().clear();
        getItems().addAll(new SplitWindow(this), new SplitWindow(this, startSide));
    }

    private void close() {
        if (this.parent != null) {
            this.parent.getItems().remove(this);
            if (this.parent.getItems().size() == 0) {
                this.parent.close();
            }
        }
    }

    private BooleanProperty editingProperty() {
        if (editing == null) {
            return this.parent.editingProperty();
        }
        return editing;
    }

    static class SplitSide extends StackPane {
        private BooleanProperty editing;
        private final HBox editButtons;
        private final HBox urlContainer;
        private final ButtonIcon btnApply;
        private final TextField tfUrl;
        private final StackPane content;
        private final StackPane overlay;

        private SplitWindow parent;


        public SplitSide(SplitWindow parent) {
            this.parent = parent;
            double SPACE = 10.0;

            editButtons = new HBox();
            editButtons.setPadding(new Insets(SPACE, SPACE, 0, 0));
            editButtons.setSpacing(SPACE);
            double btnSize = 24;
            Button btnSplitVertically = new ButtonIcon("/Icons/icons8-web-64.png", btnSize);
            Button btnSplitHorizontally = new ButtonIcon("/Icons/icons8-bearbeiten-48.png", btnSize);
            Button btnClose = new ButtonIcon("/Icons/icons8-löschen-48.png", btnSize);
            btnSplitVertically.setOnMousePressed(e -> this.parent.split(Orientation.VERTICAL));
            btnSplitHorizontally.setOnMousePressed(e -> this.parent.split(Orientation.HORIZONTAL));
            btnClose.setOnMousePressed(e -> this.parent.close());
            editButtons.getChildren().addAll(btnSplitVertically, btnSplitHorizontally, btnClose);

            urlContainer = new HBox();
            urlContainer.setPadding(new Insets(0, SPACE, 0, SPACE));
            double urlHeight = 50;
            tfUrl = new TextField();
            tfUrl.setPromptText("https://google.de");
            tfUrl.setPrefHeight(urlHeight);
            tfUrl.setMinWidth(100);
            tfUrl.setPrefWidth(700);
            tfUrl.setAlignment(Pos.CENTER_LEFT);
            btnApply = new ButtonIcon("/Icons/icons8-bearbeiten-48.png", urlHeight);
            btnApply.setOnMousePressed(l -> {
                loadWebsite();
            });
            urlContainer.getChildren().addAll(tfUrl, btnApply);
            urlContainer.setSpacing(10);
            overlay = new StackPane() {
                @Override
                protected void layoutChildren() {
                    double buttonsWidth = editButtons.prefWidth(-1);
                    double x = getWidth() - buttonsWidth - snappedRightInset();
                    double y = snappedTopInset();
                    editButtons.relocate(x, y);
                    editButtons.resize(buttonsWidth, editButtons.prefHeight(-1));

                    double urlWidth = Math.min(urlContainer.prefWidth(-1), getWidth() - snappedRightInset() - snappedLeftInset());
                    double urlHeight = tfUrl.prefHeight(-1);
                    x = getWidth() / 2 - urlWidth / 2;
                    y = getHeight() / 2 - urlHeight / 2;
                    urlContainer.relocate(x, y);
                    urlContainer.resize(urlWidth, urlHeight);

                    double btnHeight = urlHeight - btnApply.snappedTopInset() - btnApply.snappedBottomInset();
                    btnApply.setSize(btnHeight);
                }
            };
            overlay.getChildren().addAll(editButtons, urlContainer);

            content = new StackPane();
            getChildren().addAll(content, overlay);
        }

        private void setContent(Node n) {
            overlay.visibleProperty().bind(this.parent.editingProperty());

        }

        private void loadWebsite() {
            String url = tfUrl.getText();

        }


        protected void layoutChildren() {
            double paddingW = snappedLeftInset() + snappedRightInset();
            double paddingH = snappedTopInset() + snappedBottomInset();
            content.resize(getWidth() - paddingW, getHeight() - paddingH);
            content.relocate(snappedLeftInset(), snappedRightInset());
            overlay.resize(getWidth() - paddingW, getHeight() - paddingH);
            overlay.relocate(snappedLeftInset(), snappedRightInset());
        }

        public void setParent(SplitWindow parent) {
            this.parent = parent;
        }
    }

}
