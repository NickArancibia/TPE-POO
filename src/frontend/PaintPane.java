package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
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
	ToggleButton deleteButton = new ToggleButton("Borrar");
	ToggleButton spinButton = new ToggleButton("Girar D");
	ToggleButton turnAroundVButton = new ToggleButton("Voltear V");
	ToggleButton turnAroundHButton = new ToggleButton("Voltear H");
	ToggleButton scaleGrowButton = new ToggleButton("Escalar +");
	ToggleButton scaleReduceButton = new ToggleButton("Escalar -");



	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	DrawableGroup selectedFigure;

	// StatusBar
	StatusPane statusPane;

	public PaintPane(CanvasState<DrawableGroup> canvasState, StatusPane statusPane, ShapeDrawPropertiesPane drawPropertiesPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, spinButton, turnAroundVButton, turnAroundHButton, scaleGrowButton, scaleReduceButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		drawPropertiesPane.getShadowCheckBox().setOnAction(e -> {
			if (selectedFigure != null)
				selectedFigure.setShadowToggled(drawPropertiesPane.getShadowCheckBox().isSelected());
			redrawCanvas();
		});

		drawPropertiesPane.getGradientCheckBox().setOnAction(e -> {
			if (selectedFigure != null)
				selectedFigure.setGradientToggled(drawPropertiesPane.getGradientCheckBox().isSelected());
			redrawCanvas();
		});

		drawPropertiesPane.getBevelCheckBox().setOnAction(e -> {
			if (selectedFigure != null)
				selectedFigure.setBevelToggled(drawPropertiesPane.getBevelCheckBox().isSelected());
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
			DrawableFigure newFigure = null;
			if (rectangleButton.isSelected()) {
				newFigure = new DrawableRectangle(startPoint, endPoint, fillColorPicker.getValue());
			} else if (circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new DrawableCircle(startPoint, circleRadius, fillColorPicker.getValue());
			} else if (squareButton.isSelected()) {
				double size = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new DrawableSquare(startPoint, size, fillColorPicker.getValue());
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
						selectedFigure = figure;
						drawPropertiesPane.setState(figure.isShadowToggled(), figure.isGradientToggled(), figure.isBevelToggled());
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
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
				if (selectedFigure != null){
					selectedFigure.move(diffX, diffY);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		scaleGrowButton.setOnAction(event -> {
			if (selectedFigure != null) {
				selectedFigure.scaleGrowth();
				selectedFigure = null;
				redrawCanvas();
			}
		});

		scaleReduceButton.setOnAction(event -> {
			if (selectedFigure != null) {
				selectedFigure.scaleReduce();
				selectedFigure = null;
				redrawCanvas();
			}
		});

		turnAroundHButton.setOnAction(event -> {
			if (selectedFigure != null) {
				selectedFigure.turnAroundH();
				selectedFigure = null;
				redrawCanvas();
			}
		});

		turnAroundVButton.setOnAction(event ->{
			if (selectedFigure !=null){
				selectedFigure.turnAroundV();
				selectedFigure = null;
				redrawCanvas();
			}
		});

		spinButton.setOnAction(event ->{
			if (selectedFigure !=null){
				selectedFigure.spin();
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(DrawableGroup figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			figure.draw(gc);
		}
	}
}
