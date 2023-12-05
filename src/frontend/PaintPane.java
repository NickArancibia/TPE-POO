package frontend;

import java.util.Arrays;
import java.util.Collection;

import backend.CanvasState;
import backend.model.*;
import frontend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {
    // BackEnd
    CanvasState<DrawableGroup> canvasState;

    // Canvas y relacionados
    Canvas canvas = new Canvas(800, 600);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color lineColor = Color.BLACK;
    Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	Button deleteButton = new Button("Borrar");
	Button groupButton = new Button("Agrupar");
	Button ungroupButton = new Button("Desagrupar");
	Button rotateButton = new Button("Girar D");
	Button flipVButton = new Button("Voltear V");
	Button flipHButton = new Button("Voltear H");
	Button scaleUpButton = new Button("Escalar +");
	Button scaleDownButton = new Button("Escalar -");

    Label tagsLabel = new Label("Etiquetas");
    TextArea tagsArea = new TextArea();
    Button saveTagsButton = new Button("Guardar");

    // Selector de color de relleno
    ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

    // Dibujar una figura
    Point startPoint;

    // Seleccionar una figura
    SelectionManager selectionManager = new SelectionManager();

    boolean movingFigures = false;

    // TagFilterPane
    TagFilterPane tagFilterPane;

    // StatusBar
    StatusPane statusPane;

	public PaintPane(CanvasState<DrawableGroup> canvasState, StatusPane statusPane, ShapeDrawPropertiesPane drawPropertiesPane, TagFilterPane tagFilterPane) {
		this.canvasState = canvasState;
        this.tagFilterPane = tagFilterPane;
		this.statusPane = statusPane;
		ToggleButton[] toggleToolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton};
		Button[] toolsArr = {groupButton, ungroupButton, rotateButton, flipVButton, flipHButton, scaleUpButton, scaleDownButton, deleteButton};
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
        saveTagsButton.setMinWidth(90);
		saveTagsButton.setCursor(Cursor.HAND);
		tagsArea.setMaxHeight(50);
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

        drawPropertiesPane.getShadowCheckBox().setOnAction(e -> {
            selectionManager.applyActionToSelection((group) -> group.setShadowToggled(drawPropertiesPane.getShadowCheckBox().isSelected()));
            redrawCanvas();
        });

        drawPropertiesPane.getGradientCheckBox().setOnAction(e -> {
            selectionManager.applyActionToSelection((group) -> group.setGradientToggled(drawPropertiesPane.getGradientCheckBox().isSelected()));
            redrawCanvas();
        });

        drawPropertiesPane.getBevelCheckBox().setOnAction(e -> {
            selectionManager.applyActionToSelection((group) -> group.setBevelToggled(drawPropertiesPane.getBevelCheckBox().isSelected()));
            redrawCanvas();
        });

        tagFilterPane.getShowAllButton().setOnAction(e -> clearSelectionAndRedraw());

        tagFilterPane.getFilterTagButton().setOnAction(e -> clearSelectionAndRedraw());

        canvas.setOnMousePressed(event -> {
            startPoint = new Point(event.getX(), event.getY());
            movingFigures = false;
        });

        canvas.setOnMouseReleased(event -> {
            Point endPoint = new Point(event.getX(), event.getY());
            if (startPoint == null) {
                return;
            }
            if (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
                return;
            }
            DrawableFigure<? extends Figure> newFigure = null;
            if (rectangleButton.isSelected()) {
                newFigure = new DrawableRectangle(startPoint, endPoint, fillColorPicker.getValue());
            } else if (circleButton.isSelected()) {
                double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new DrawableCircle(startPoint, circleRadius, fillColorPicker.getValue());
            } else if (squareButton.isSelected()) {
                double size = Math.abs(endPoint.getX() - startPoint.getX());
                newFigure = new DrawableSquare(startPoint, size, fillColorPicker.getValue());
            } else if(ellipseButton.isSelected()) {
                Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
                double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
                newFigure = new DrawableEllipse(centerPoint, sMayorAxis, sMinorAxis, fillColorPicker.getValue());
            } else if (selectionButton.isSelected()){
                if(!movingFigures) {
                    selectionManager.clearSelection();
                    boolean addedFigures = selectionManager.addToSelectionIfFigureInSelection(canvasState.figures(), startPoint, endPoint, tagFilterPane.isFiltering(), tagFilterPane.getFilterTag());

                    if (addedFigures) {
                        drawPropertiesPane.setState(selectionManager.isShadowToggled(), selectionManager.isGradientToggled(), selectionManager.isBevelToggled());
                        drawPropertiesPane.setSomeState(selectionManager.someShadowToggled(), selectionManager.someGradientToggled(), selectionManager.someBevelToggled());
                    }
                }
                
                redrawCanvas();
                return;
            } else {
                return;
            }
            DrawableGroup newGroup = new DrawableGroup(fillColorPicker.getValue());
            newGroup.add(newFigure);
            newGroup.setShadowToggled(drawPropertiesPane.getShadowCheckBox().isSelected());
            newGroup.setGradientToggled(drawPropertiesPane.getGradientCheckBox().isSelected());
            newGroup.setBevelToggled(drawPropertiesPane.getBevelCheckBox().isSelected());
            canvasState.addFigure(newGroup);
            startPoint = null;
            clearSelectionAndRedraw();
        });

        canvas.setOnMouseMoved(event -> {
            Point eventPoint = new Point(event.getX(), event.getY());
            boolean found = false;
            StringBuilder label = new StringBuilder();
            for(DrawableGroup figure : canvasState.figures()) {
                if(figure.pointInFigure(eventPoint) && figure.isFigureVisible(tagFilterPane.isFiltering(), tagFilterPane.getFilterTag())) {
                    found = true;
                    label.append(figure.toString());
                }
            }
            if(found) {
                statusPane.updateStatus(label.toString());
            } else {
                statusPane.updateStatus(eventPoint.toString());
            }
        });

        canvas.setOnMouseClicked(event -> {
            if(selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                boolean found = false;
                StringBuilder label = new StringBuilder("Se seleccionó: ");
                for (DrawableGroup figure : canvasState.figures()) {
                    if(figure.pointInFigure(eventPoint) && figure.isFigureVisible(tagFilterPane.isFiltering(), tagFilterPane.getFilterTag())) {
                        found = true;
                        selectionManager.add(figure);
                        drawPropertiesPane.setState(figure.isShadowToggled(), figure.isGradientToggled(), figure.isBevelToggled());
                        drawPropertiesPane.setSomeState(figure.someShadowToggled(), figure.someGradientToggled(), figure.someBevelToggled());
                        label.append(figure.toString());
                    }
                }
                if (found) {
                    statusPane.updateStatus(label.toString());
                } else {
                    statusPane.updateStatus("Ninguna figura encontrada");
                }
                if(selectionManager.canGroup()){
                    tagsArea.setDisable(true);
                    saveTagsButton.setDisable(true);
                }else{
                    tagsArea.setDisable(false);
                    saveTagsButton.setDisable(false);
                    if(!selectionManager.noSelection()){
                        String text = stringifyTags(selectionManager.getSelection().iterator().next().getTags());
                        tagsArea.setText(text);
                    }
                }
                redrawCanvas();
            }
        });

        canvas.setOnMouseDragged(event -> {
            if(selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
                double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
                selectionManager.applyActionToSelection((group) -> group.move(diffX, diffY));
                movingFigures = !selectionManager.noSelection();
                redrawCanvas();
            }
        });

        deleteButton.setOnAction(event -> {
            for (DrawableGroup group : selectionManager.getSelection())
                canvasState.deleteFigure(group);
            clearSelectionAndRedraw();
        });

        scaleUpButton.setOnAction(event -> {
            selectionManager.applyActionToSelection(DrawableGroup::scaleUp);
            redrawCanvas();
        });

        scaleDownButton.setOnAction(event -> {
            selectionManager.applyActionToSelection(DrawableGroup::scaleDown);
            redrawCanvas();
        });

        flipHButton.setOnAction(event -> {
            selectionManager.applyActionToSelection(DrawableGroup::flipH);
            redrawCanvas();
        });

        flipVButton.setOnAction(event -> {
            selectionManager.applyActionToSelection(DrawableGroup::flipV);
            redrawCanvas();
        });

        rotateButton.setOnAction(event -> {
            selectionManager.applyActionToSelection(DrawableGroup::rotate);
            redrawCanvas();
        });

        groupButton.setOnAction(event -> {
            if(!selectionManager.canGroup())
                statusPane.updateStatus("Para agrupar es necesario seleccionar 2 o más grupos");
            else{
                canvasState.addFigure(selectionManager.groupSelection());
                canvasState.deleteFigures(selectionManager.getSelection());
            }
            clearSelectionAndRedraw();
        });

        ungroupButton.setOnAction(event -> {
            if(!selectionManager.canUngroup())
                statusPane.updateStatus("Para desagrupar primero seleccione un grupo");
            else{
                canvasState.addAllFigures(selectionManager.ungroupSelection());
                canvasState.deleteFigures(selectionManager.getSelection());
            }
            clearSelectionAndRedraw();
        });

        saveTagsButton.setOnAction(event ->{
            selectionManager.applyActionToSelection(group -> group.setTags(parseTags(tagsArea.getText())));
        });

        setLeft(buttonsBox);
        setRight(canvas);
    }

    private void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(DrawableGroup group : canvasState.figures()) {
            gc.setStroke(selectionManager.isSelected(group) ? Color.RED : lineColor);
            if(group.isFigureVisible(tagFilterPane.isFiltering(), tagFilterPane.getFilterTag()))
                group.draw(gc);
        }
    }

    private void clearSelectionAndRedraw(){
        selectionManager.clearSelection();
        redrawCanvas();
    }

    private Collection<String> parseTags(String text){
        return Arrays.asList(text.split("[ \n]"));
    }

    private String stringifyTags(Collection<String> tags){
        return String.join("\n", tags);
    }
}
