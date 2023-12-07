package frontend;

import java.util.*;

import backend.CanvasState;
import backend.SelectionManager;
import backend.model.*;
import frontend.drawManagers.EllipseDrawManager;
import frontend.drawManagers.RectangleDrawManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class PaintPane extends BorderPane {
    // BackEnd
    CanvasState canvasState;

    // Canvas y relacionados
    Canvas canvas = new Canvas(800, 600);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color lineColor = Color.BLACK;

    // Dibujar una figura
    Point startPoint;

    // Seleccionar una figura
    SelectionManager selectionManager = new SelectionManager();

    private final double toleranceForMouseClick = 5.0;

    // DrawPropertiesPane
    ShapeDrawPropertiesPane drawPropertiesPane;

    // TagFilterPane
    TagFilterPane tagFilterPane;

    // StatusBar
    StatusPane statusPane;

    //ButtonsPane
    ButtonsBoxPane buttonsPane;

    //DrawManagers
    RectangleDrawManager rectangleDrawManager = new RectangleDrawManager(gc);
    EllipseDrawManager ellipseDrawManager = new EllipseDrawManager(gc);

    public PaintPane(CanvasState canvasState, StatusPane statusPane, ShapeDrawPropertiesPane drawPropertiesPane, TagFilterPane tagFilterPane,ButtonsBoxPane buttonsPane) {
        this.canvasState = canvasState;
        this.tagFilterPane = tagFilterPane;
        this.drawPropertiesPane = drawPropertiesPane;
        this.statusPane = statusPane;
        this.buttonsPane = buttonsPane;
        buttonsPane.init(gc);

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
        tagFilterPane.getFilterTagTextField().textProperty().addListener((observable, oldValue, newValue) -> redrawCanvas());

        canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

        buttonsPane.getDeleteButton().setOnAction(event -> {
            selectionManager.getSelection().forEach(canvasState::remove);
            clearSelectionAndRedraw();
        });

        buttonsPane.getScaleUpButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::scaleUp);
            redrawCanvas();
        });

        buttonsPane.getScaleDownButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::scaleDown);
            redrawCanvas();
        });

        buttonsPane.getFlipHButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::flipH);
            redrawCanvas();
        });

        buttonsPane.getFlipVButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::flipV);
            redrawCanvas();
        });

        buttonsPane.getRotateButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::rotate);
            redrawCanvas();
        });

        buttonsPane.getGroupButton().setOnAction(event -> {
            if(!selectionManager.atLeastTwoSelected())
                statusPane.updateStatus("Para agrupar es necesario seleccionar 2 o más grupos");
            else{
                canvasState.add(selectionManager.groupSelection());
                statusPane.updateStatus(String.format("Se agrupo: %s", selectionManager.getSelection().toString()));
                canvasState.removeAll(selectionManager.getSelection());
            }
            clearSelectionAndRedraw();
        });

        buttonsPane.getUngroupButton().setOnAction(event -> {
            if(selectionManager.noneSelected())
                statusPane.updateStatus("Para desagrupar primero seleccione un grupo");
            else{
                canvasState.removeAll(selectionManager.getSelection());
                canvasState.addAll(selectionManager.ungroupSelection());
            }
            clearSelectionAndRedraw();
        });

        buttonsPane.getSaveTagsButton().setOnAction(event ->{
            selectionManager.applyActionToSelection(group -> group.setTags(parseTags(buttonsPane.getTagsArea().getText())));
            clearSelectionAndRedraw();
        });

        canvas.setOnMouseReleased(event -> {
            Point endPoint = new Point(event.getX(), event.getY());
            if (startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
                return;
            }
            if (buttonsPane.getRectangleButton().isSelected()) {
                createFigure(new Rectangle(startPoint, endPoint, buttonsPane.getFillColorPicker().getValue().toString(), rectangleDrawManager));
            } else if (buttonsPane.getCircleButton().isSelected()) {
                double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                createFigure(new Circle(startPoint, circleRadius, buttonsPane.getFillColorPicker().getValue().toString(), ellipseDrawManager));
            } else if (buttonsPane.getSquareButton().isSelected()) {
                double size = Math.abs(endPoint.getX() - startPoint.getX());
                createFigure(new Square(startPoint, size, buttonsPane.getFillColorPicker().getValue().toString(), rectangleDrawManager));
            } else if(buttonsPane.getEllipseButton().isSelected()) {
                Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
                double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
                createFigure(new Ellipse(centerPoint, sMayorAxis, sMinorAxis, buttonsPane.getFillColorPicker().getValue().toString(), ellipseDrawManager));
            } else if (buttonsPane.getSelectionButton().isSelected()){
                if(!isMovingFigures(endPoint)) {
                    boolean addedFigures = selectionManager.selectFiguresInRect(canvasState, startPoint, endPoint, tagFilterPane.isFiltering(), tagFilterPane.getFilterTag());
                    if (addedFigures) {
                        drawPropertiesPane.setState(selectionManager.isShadowToggled(), selectionManager.isGradientToggled(), selectionManager.isBevelToggled());
                        drawPropertiesPane.setSomeState(selectionManager.someShadowToggled(), selectionManager.someGradientToggled(), selectionManager.someBevelToggled());
                    }
                }
                redrawCanvas();
            }
        });

        canvas.setOnMouseMoved(event -> {
            Point eventPoint = new Point(event.getX(), event.getY());
            boolean found = false;
            StringBuilder label = new StringBuilder();
            for(FigureGroup group : canvasState) {
                if(group.pointInFigure(eventPoint) && (!tagFilterPane.isFiltering() || group.hasTag(tagFilterPane.getFilterTag()))) {
                    found = true;
                    label.append(group.toString());
                }
            }
            statusPane.updateStatus(found ? label.toString() : eventPoint.toString());
        });

        canvas.setOnMouseClicked(event -> {
            if(buttonsPane.getSelectionButton().isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                StringBuilder label = new StringBuilder("Se seleccionó: ");
                statusPane.updateStatus(clickOnFigure(eventPoint, canvasState, label) ? label.toString() : "Ninguna figura encontrada");

                if(selectionManager.atLeastTwoSelected()){
                    buttonsPane.getTagsArea().setDisable(true);
                    buttonsPane.getSaveTagsButton().setDisable(true);
                } else{
                    buttonsPane.getTagsArea().setDisable(false);
                    buttonsPane.getSaveTagsButton().setDisable(false);
                    if(!selectionManager.noneSelected()){
                        String text = stringifyTags(selectionManager.getSelection().iterator().next().getTags());
                        buttonsPane.getTagsArea().setText(text);
                    }
                }
                redrawCanvas();
            }
        });

        canvas.setOnMouseDragged(event -> {
            if(buttonsPane.getSelectionButton().isSelected()) {
                Point eventPoint = new Point(event.getX(), event.getY());
                double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
                double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
                selectionManager.applyActionToSelection((group) -> group.move(diffX, diffY));
                redrawCanvas();
            }
        });
        setRight(canvas);
    }

    private boolean isMovingFigures(Point endPoint){
        double dist = startPoint.distance(endPoint);
        return dist > toleranceForMouseClick && !selectionManager.noneSelected();
    }

    private void createFigure(Figure figure){
        FigureGroup newGroup = new FigureGroup();
        newGroup.add(figure);
        newGroup.setShadowToggled(drawPropertiesPane.getShadowCheckBox().isSelected());
        newGroup.setGradientToggled(drawPropertiesPane.getGradientCheckBox().isSelected());
        newGroup.setBevelToggled(drawPropertiesPane.getBevelCheckBox().isSelected());
        canvasState.add(newGroup);
        startPoint = null;
        clearSelectionAndRedraw();
    }

    private boolean clickOnFigure(Point eventPoint, List<FigureGroup> figures, StringBuilder label){
        ListIterator<FigureGroup> iter = figures.listIterator(figures.size());
        while (iter.hasPrevious()) {
            FigureGroup figure = iter.previous(); 
            if(figure.pointInFigure(eventPoint) && (!tagFilterPane.isFiltering() || figure.hasTag(tagFilterPane.getFilterTag()))) {
                selectionManager.add(figure);
                drawPropertiesPane.setState(figure.isShadowToggled(), figure.isGradientToggled(), figure.isBevelToggled());
                drawPropertiesPane.setSomeState(figure.someShadowToggled(), figure.someGradientToggled(), figure.someBevelToggled());
                label.append(figure.toString());
                return true;
            }
        }
        return false;
    }

    private void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(FigureGroup group : canvasState) {
            gc.setStroke(selectionManager.isSelected(group) ? Color.RED : lineColor);

            if (!tagFilterPane.isFiltering() || group.hasTag(tagFilterPane.getFilterTag()))
                group.draw();
        }
    }

    private void clearSelectionAndRedraw(){
        selectionManager.clearSelection();
        redrawCanvas();
    }

    private Set<String> parseTags(String text){
        return new HashSet<>(Arrays.asList(text.split("[ \n]")));
    }

    private String stringifyTags(Set<String> tags){
        return String.join("\n", tags);
    }
}
