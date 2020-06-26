package GuiElements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ButtonIcon extends Button {

    public ButtonIcon() {
        super();
        getStyleClass().setAll("btn-icon-only");

        iconProperty().addListener(e -> {
            ImageView view = new ImageView(new Image(getIcon()));
            view.fitHeightProperty().bind(sizeProperty());
            view.fitWidthProperty().bind(sizeProperty());
            setGraphic(view);
        });

        setOnMousePressed(r -> System.out.println("Clicked"));
    }

    private StringProperty icon;
    public void setIcon(String value) { iconProperty().set(value); }
    public String getIcon() { return iconProperty().get(); }

    public StringProperty iconProperty() {
        if (icon == null) {
            icon = new SimpleStringProperty(this, "icon");
        }
        return icon;
    }

    private DoubleProperty size;
    public void setSize(Double value) { sizeProperty().set(value); }
    public Double getSize() { return sizeProperty().get(); }

    public DoubleProperty sizeProperty() {
        if (size == null) {
            size = new SimpleDoubleProperty(this, "size");
        }
        return size;
    }


}
