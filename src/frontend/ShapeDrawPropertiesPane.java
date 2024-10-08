package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ShapeDrawPropertiesPane extends BorderPane {
    private final CheckBox shadowCheckBox;
    private final CheckBox gradientCheckBox;
    private final CheckBox bevelCheckBox;

    public ShapeDrawPropertiesPane() {
        setStyle("-fx-background-color: #999");
        HBox checksBox = new HBox(4);  
        checksBox.setPadding(new Insets(5));

        Label effectsLabel = new Label("Efectos: ");
        checksBox.getChildren().add(effectsLabel);

        shadowCheckBox = new CheckBox("Sombra");
        checksBox.getChildren().add(shadowCheckBox);

        gradientCheckBox = new CheckBox("Gradiente");
        checksBox.getChildren().add(gradientCheckBox);

        bevelCheckBox = new CheckBox("Biselado");
        checksBox.getChildren().add(bevelCheckBox);

        checksBox.setAlignment(Pos.CENTER);

        setCenter(checksBox);
    }

    public void setState(boolean isShadowToggled, boolean isGradientToggled, boolean isBevelToggled) {
        shadowCheckBox.setSelected(isShadowToggled);
        gradientCheckBox.setSelected(isGradientToggled);
        bevelCheckBox.setSelected(isBevelToggled);
    }

    public void setSomeState(boolean someShadowToggled, boolean someGradientToggled, boolean someBevelToggled) {
        shadowCheckBox.indeterminateProperty().set(someShadowToggled);
        gradientCheckBox.indeterminateProperty().set(someGradientToggled);
        bevelCheckBox.indeterminateProperty().set(someBevelToggled);
    }

    public CheckBox getShadowCheckBox() {
        return shadowCheckBox;
    }

    public CheckBox getGradientCheckBox() {
        return gradientCheckBox;
    }

    public CheckBox getBevelCheckBox() {
        return bevelCheckBox;
    }
}
