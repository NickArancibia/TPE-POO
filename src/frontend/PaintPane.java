package frontend;

import java.util.ArrayList;
import java.util.List;

import backend.CanvasState;
import backend.model.*;
import frontend.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    ToggleButton deleteButton = new ToggleButton("Borrar");
    ToggleButton groupButton = new ToggleButton("Agrupar");
    ToggleButton ungroupButton = new ToggleButton("Desagrupar");
    ToggleButton rotateButton = new ToggleButton("Girar D");
    ToggleButton flipVButton = new ToggleButton("Voltear V");
    ToggleButton flipHButton = new ToggleButton("Voltear H");
    ToggleButton scaleUpButton = new ToggleButton("Escalar +");
    ToggleButton scaleDownButton = new ToggleButton("Escalar -");

    Label tagsLabel = new Label("Etiquetas");
    TextArea tagsArea = new TextArea();
    Button saveTagsButton = new Button("Guardar");

    // Botones Barra Inferior

    Label showTagsLabel = new Label("Mostrar Etiquetas: ");
    RadioButton showAllButton = new RadioButton("Todas");
    RadioButton filterTagButton = new RadioButton("Sólo: ");
    TextField filterTagField = new TextField();

    // Selector de color de relleno
    ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

    // Dibujar una figura
    Point startPoint;

    // Seleccionar una figura
    List<DrawableGroup> selectedGroups = new ArrayList<>();

    // StatusBar
    StatusPane statusPane;

    public PaintPane(CanvasState<DrawableGroup> canvasState, StatusPane statusPane, ShapeDrawPropertiesPane drawPropertiesPane) {
        this.canvasState = canvasState;
        this.statusPane = statusPane;
        ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, groupButton, ungroupButton, rotateButton, flipVButton, flipHButton, scaleUpButton, scaleDownButton, deleteButton};
        ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        tagsArea.setMaxHeight(50);
        ToggleGroup showTagsGroup = new ToggleGroup();
        showAllButton.setToggleGroup(showTagsGroup);
        filterTagButton.setToggleGroup(showTagsGroup);
        saveTagsButton.setCursor(Cursor.HAND);
        showAllButton.setCursor(Cursor.HAND);
        filterTagButton.setCursor(Cursor.HAND);
        VBox buttonsBox = new VBox(5);
        buttonsBox.getChildren().addAll(toolsArr);
        buttonsBox.getChildren().add(fillColorPicker);
        buttonsBox.getChildren().add(tagsLabel);
        buttonsBox.getChildren().add(tagsArea);
        buttonsBox.getChildren().add(saveTagsButton);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.setStyle("-fx-background-color: #999");
        buttonsBox.setPrefWidth(100);
        HBox tagsBox = new HBox(10);
        tagsBox.setAlignment(Pos.CENTER);
        tagsBox.getChildren().add(showTagsLabel);
        tagsBox.getChildren().add(showAllButton);
        tagsBox.getChildren().add(filterTagButton);
        tagsBox.getChildren().add(filterTagField);
        tagsBox.setPadding(new Insets(5));
        tagsBox.setStyle("-fx-background-color: #999");
        gc.setLineWidth(1);

        drawPropertiesPane.getShadowCheckBox().setOnAction(e -> {
            for (DrawableGroup group : selectedGroups)
                group.setShadowToggled(drawPropertiesPane.getShadowCheckBox().isSelected());
            redrawCanvas();
        });

        drawPropertiesPane.getGradientCheckBox().setOnAction(e -> {
            for (DrawableGroup group : selectedGroups)
                group.setGradientToggled(drawPropertiesPane.getGradientCheckBox().isSelected());
            redrawCanvas();
        });

        drawPropertiesPane.getBevelCheckBox().setOnAction(e -> {
            for (DrawableGroup group : selectedGroups)
                group.setBevelToggled(drawPropertiesPane.getBevelCheckBox().isSelected());
            redrawCanvas();
        });

        canvas.setOnMousePressed(event -> {
            startPoint = new Point(event.getX(), event.getY());
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
            } else if (selectionButton.isSelected() && !canvasState.figures().isEmpty()){
                for(DrawableGroup group : canvasState.figures()){
                    if(group.isFigureInRectangle(startPoint, endPoint))
                        selectedGroups.add(group);
                }
                redrawCanvas();
                return;
            } else {
                return;
            }
            DrawableGroup newGroup = new DrawableGroup(fillColorPicker.getValue());
            newGroup.add(newFigure);
            canvasState.addFigure(newGroup);
            startPoint = null;
            redrawCanvas();
        });

        canvas.setOnMouseMoved(event -> {
            Point eventPoint = new Point(event.getX(), event.getY());
            boolean found = false;
            StringBuilder label = new StringBuilder();
            for(DrawableGroup figure : canvasState.figures()) {
                if(figure.pointInFigure(eventPoint)) {
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
                    if(figure.pointInFigure(eventPoint)) {
                        found = true;
                        // Decidir si este clear deberia ocurrir o no
                        //selectedGroups.clear(); 
                        selectedGroups.add(figure);
                        drawPropertiesPane.setState(figure.isShadowToggled(), figure.isGradientToggled(), figure.isBevelToggled());
                        label.append(figure.toString());
                    }
                }
                if (found) {
                    statusPane.updateStatus(label.toString());
                } else {
                    selectedGroups.clear();
                    statusPane.updateStatus("Ninguna figura encontrada");
                }
                redrawCanvas();
            }
        });

        canvas.setOnMouseDragged(event -> {
            if(selectionButton.isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
                double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
                for (DrawableGroup group : selectedGroups){
                    group.move(diffX, diffY);
                }
                redrawCanvas();
            }
        });

        deleteButton.setOnAction(event -> {
            for (DrawableGroup group : selectedGroups)
                canvasState.deleteFigure(group);
            selectedGroups.clear();
            // performAction(() -> canvasState.deleteFigure(selectedFigure));
        });

        scaleUpButton.setOnAction(event -> {
            for (DrawableGroup group : selectedGroups)
                group.scaleUp();
            selectedGroups.clear();

            //	performAction(selectedFigure::scaleUp);
        });

        scaleDownButton.setOnAction(event -> {
            for (DrawableGroup group : selectedGroups)
                group.scaleDown();
            selectedGroups.clear();
            // performAction(selectedFigure::scaleDown);
        });

        flipHButton.setOnAction(event -> {
            for (DrawableGroup group : selectedGroups)
                group.flipH();
            selectedGroups.clear();

            // performAction(selectedFigure::flipH);
        });

        flipVButton.setOnAction(event -> {
            for (DrawableGroup group : selectedGroups)
                group.flipV();
            selectedGroups.clear();

            // performAction(selectedFigure::flipV);
        });

        rotateButton.setOnAction(event -> {
            for (DrawableGroup group : selectedGroups)
                group.rotate();
            selectedGroups.clear();
            redrawCanvas();

            // performAction(selectedFigure::rotate);
        });

        groupButton.setOnAction(event -> {
            if(selectedGroups.size() == 1)
                statusPane.updateStatus("No se puede agrupar un unico grupo");
            else if(selectedGroups.isEmpty())
                statusPane.updateStatus("Para agrupar es necesario seleccionar 2 o mas grupos");
            else{
                DrawableGroup newGroup = new DrawableGroup(fillColorPicker.getValue());
                List<DrawableGroup> toRemove = new ArrayList<>();
                for(DrawableGroup group : canvasState.figures()){
                    if(selectedGroups.contains(group)){
                        newGroup.addAll(group);
                        toRemove.add(group);
                    }
                }
                canvasState.addFigure(newGroup);
                canvasState.figures().removeAll(toRemove);
            }
            selectedGroups.clear();
            redrawCanvas();
        });

        ungroupButton.setOnAction(event -> {
            if(selectedGroups.isEmpty())
                statusPane.updateStatus("Para desagrupar primero seleccione un grupo");
            else{
                for(DrawableGroup group : selectedGroups){
                    if(group.size() != 1){
                        for(DrawableFigure<? extends Figure> figure : group.getFigures()){
                            DrawableGroup groupToAdd = new DrawableGroup(fillColorPicker.getValue());
                            groupToAdd.add(figure);
                            canvasState.addFigure(groupToAdd);
                        }
                        canvasState.deleteFigure(group);
                    }
                }

            }
            selectedGroups.clear();
            redrawCanvas();
        });

        setLeft(buttonsBox);
        setRight(canvas);
        setBottom(tagsBox);
    }

    /*
       private void performAction(Runnable action){
       if (selectedFigure !=null){
       action.run();
       selectedFigure = null;
       redrawCanvas();
       }
       }*/

    void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(DrawableGroup figure : canvasState.figures()) {
            if(selectedGroups.contains(figure)) {
                gc.setStroke(Color.RED);
            } else {
                gc.setStroke(lineColor);
            }
            figure.draw(gc);
        }
    }
}
