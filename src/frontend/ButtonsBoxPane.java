package frontend;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ButtonsBoxPane extends BorderPane {
    private final ToggleButton selectionButton;
    private final ToggleButton rectangleButton;
    private final ToggleButton circleButton;
    private final ToggleButton squareButton;
    private final ToggleButton ellipseButton;

    private final Button deleteButton;
    private final Button groupButton;
    private final Button ungroupButton;
    private final Button rotateButton;
    private final Button flipVButton;
    private final Button flipHButton;
    private final Button scaleUpButton;
    private final Button scaleDownButton;

    private final Label tagsLabel;
    private final TextArea tagsArea;
    private final Button saveTagsButton;

    private Color defaultFillColor = Color.YELLOW;
    private ColorPicker fillColorPicker;

    private ToggleButton[] toggleToolsArr;
    private Button[] toolsArr;

    public ButtonsBoxPane(){
        selectionButton = new ToggleButton("Seleccionar");
        rectangleButton = new ToggleButton("Rectángulo");
        circleButton = new ToggleButton("Círculo");
        squareButton = new ToggleButton("Cuadrado");
        ellipseButton = new ToggleButton("Elipse");
        deleteButton = new Button("Borrar");
        groupButton = new Button("Agrupar");
        ungroupButton = new Button("Desagrupar");
        rotateButton = new Button("Girar D");
        flipVButton = new Button("Voltear V");
        flipHButton = new Button("Voltear H");
        scaleUpButton = new Button("Escalar +");
        scaleDownButton = new Button("Escalar -");
        saveTagsButton = new Button("Guardar");

        tagsArea = new TextArea();
        tagsArea.setMaxHeight(50);
        saveTagsButton.setMinWidth(90);
        saveTagsButton.setCursor(Cursor.HAND);
        tagsLabel = new Label("Etiquetas");

        fillColorPicker = new ColorPicker(defaultFillColor);

        toggleToolsArr = new ToggleButton[]{selectionButton, rectangleButton, circleButton, squareButton, ellipseButton};
        toolsArr = new Button[]{groupButton, ungroupButton, rotateButton, flipVButton, flipHButton, scaleUpButton, scaleDownButton, deleteButton};

        ToggleGroup toggleTools = new ToggleGroup();
        for (ToggleButton tool : toggleToolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(toggleTools);
            tool.setCursor(Cursor.HAND);
        }

        for (Button tool : toolsArr){
            tool.setMinWidth(90);
            tool.setCursor(Cursor.HAND);
        }
    }

    public void init(GraphicsContext gc){
        VBox buttonsBox = new VBox(5);
        buttonsBox.getChildren().addAll(toggleToolsArr);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().add(fillColorPicker);
        buttonsBox.getChildren().add(tagsLabel);
        buttonsBox.getChildren().add(tagsArea);
        buttonsBox.getChildren().add(saveTagsButton);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);
        gc.setLineWidth(1);
        setLeft(buttonsBox);
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getGroupButton() {
        return groupButton;
    }

    public Button getFlipHButton() {
        return flipHButton;
    }

    public Button getFlipVButton() {
        return flipVButton;
    }

    public ToggleButton getCircleButton() {
        return circleButton;
    }

    public Button getRotateButton() {
        return rotateButton;
    }

    public Button getSaveTagsButton() {
        return saveTagsButton;
    }

    public Button getScaleDownButton() {
        return scaleDownButton;
    }

    public Button getScaleUpButton() {
        return scaleUpButton;
    }

    public Button getUngroupButton() {
        return ungroupButton;
    }

    public ToggleButton getEllipseButton() {
        return ellipseButton;
    }

    public ToggleButton getRectangleButton() {
        return rectangleButton;
    }

    public TextArea getTagsArea() {
        return tagsArea;
    }

    public Set<String> getTagsFromTagsArea(){
        return new HashSet<>(Arrays.asList(tagsArea.getText().split("[ \n]")));
    }

    public ToggleButton getSelectionButton() {
        return selectionButton;
    }

    public ToggleButton getSquareButton() {
        return squareButton;
    }

    public ColorPicker getFillColorPicker() {
        return fillColorPicker;
    }
}