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

public class PaintPane extends BorderPane {
    // BackEnd
    private final CanvasState canvasState;

    // Canvas y relacionados
    Canvas canvas = new Canvas(800, 600);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    // Dibujar una figura
    private Point startPoint;

    // Seleccionar una figura
    private final SelectionManager selectionManager;

    private final double toleranceForMouseClick = 5.0;

    // DrawPropertiesPane
    private final ShapeDrawPropertiesPane drawPropertiesPane;

    // TagFilterPane
    private final TagFilterPane tagFilterPane;

    //DrawManagers
    private final RectangleDrawManager rectangleDrawManager = new RectangleDrawManager(gc);
    private final EllipseDrawManager ellipseDrawManager = new EllipseDrawManager(gc);

    public PaintPane(CanvasState canvasState, SelectionManager selectionManager, StatusPane statusPane, ShapeDrawPropertiesPane drawPropertiesPane, TagFilterPane tagFilterPane, ButtonsBoxPane buttonsPane) {
        this.canvasState = canvasState;
        this.selectionManager = selectionManager;
        this.tagFilterPane = tagFilterPane;
        this.drawPropertiesPane = drawPropertiesPane;
        gc.setLineWidth(1);

        canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

        canvas.setOnMouseReleased(event -> {
            Point endPoint = new Point(event.getX(), event.getY());
            if (startPoint == null || endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
                return;
            }
            if (buttonsPane.getRectangleButton().isSelected()) {
                createFigure(new Rectangle(startPoint, endPoint, buttonsPane.getFillColorPickerColorAsString(), rectangleDrawManager));
            } else if (buttonsPane.getCircleButton().isSelected()) {
                double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
                createFigure(new Circle(startPoint, circleRadius, buttonsPane.getFillColorPickerColorAsString(), ellipseDrawManager));
            } else if (buttonsPane.getSquareButton().isSelected()) {
                double size = Math.abs(endPoint.getX() - startPoint.getX());
                createFigure(new Square(startPoint, size, buttonsPane.getFillColorPickerColorAsString(), rectangleDrawManager));
            } else if(buttonsPane.getEllipseButton().isSelected()) {
                Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
                double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
                double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
                createFigure(new Ellipse(centerPoint, sMayorAxis, sMinorAxis, buttonsPane.getFillColorPickerColorAsString(), ellipseDrawManager));
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
                statusPane.updateStatus(clickOnFigure(eventPoint, canvasState, label) ? label.toString() : (selectionManager.noneSelected() ? "Ninguna figura encontrada" : eventPoint.toString()));

                if(selectionManager.atLeastTwoSelected()){
                    buttonsPane.getTagsArea().setDisable(true);
                    buttonsPane.getSaveTagsButton().setDisable(true);
                } else{
                    buttonsPane.getTagsArea().setDisable(false);
                    buttonsPane.getSaveTagsButton().setDisable(false);
                    if(!selectionManager.noneSelected()){
                        Set<String> tags = selectionManager.getSelection().iterator().next().getTags();
                        buttonsPane.setTags(tags);
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
            FigureGroup group = iter.previous(); 
            if(group.pointInFigure(eventPoint) && (!tagFilterPane.isFiltering() || group.hasTag(tagFilterPane.getFilterTag()))) {
                selectionManager.add(group);
                drawPropertiesPane.setState(group.isShadowToggled(), group.isGradientToggled(), group.isBevelToggled());
                drawPropertiesPane.setSomeState(group.someShadowToggled(), group.someGradientToggled(), group.someBevelToggled());
                label.append(group.toString());
                return true;
            }
        }
        return false;
    }

    public void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(FigureGroup group : canvasState) {
            if (!tagFilterPane.isFiltering() || group.hasTag(tagFilterPane.getFilterTag()))
                group.draw(selectionManager.isSelected(group));
        }
    }

    public void clearSelectionAndRedraw(){
        selectionManager.clearSelection();
        redrawCanvas();
    }
}
