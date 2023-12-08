package frontend;

import backend.CanvasState;
import backend.SelectionManager;
import backend.model.FigureGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {
    public MainFrame(CanvasState canvasState, SelectionManager selectionManager) {
        AppMenuBar appMenuBar = new AppMenuBar();
        StatusPane statusPane = new StatusPane();
        ShapeDrawPropertiesPane drawPropertiesPane = new ShapeDrawPropertiesPane();
        TagFilterPane tagFilterPane = new TagFilterPane();
        ButtonsBoxPane buttonsPane = new ButtonsBoxPane();
        PaintPane paintPane = new PaintPane(canvasState, selectionManager, statusPane, drawPropertiesPane, tagFilterPane, buttonsPane);
        getChildren().add(appMenuBar);
        getChildren().add(drawPropertiesPane);
        getChildren().add(new HBox(buttonsPane,paintPane));
        getChildren().add(tagFilterPane);
        getChildren().add(statusPane);

        drawPropertiesPane.getShadowCheckBox().setOnAction(e -> {
            selectionManager.applyActionToSelection((group) -> group.setShadowToggled(drawPropertiesPane.getShadowCheckBox().isSelected()));
            paintPane.redrawCanvas();
        });

        drawPropertiesPane.getGradientCheckBox().setOnAction(e -> {
            selectionManager.applyActionToSelection((group) -> group.setGradientToggled(drawPropertiesPane.getGradientCheckBox().isSelected()));
            paintPane.redrawCanvas();
        });

        drawPropertiesPane.getBevelCheckBox().setOnAction(e -> {
            selectionManager.applyActionToSelection((group) -> group.setBevelToggled(drawPropertiesPane.getBevelCheckBox().isSelected()));
            paintPane.redrawCanvas();
        });

        tagFilterPane.getShowAllButton().setOnAction(e -> paintPane.clearSelectionAndRedraw());
        tagFilterPane.getFilterTagButton().setOnAction(e -> paintPane.clearSelectionAndRedraw());
        tagFilterPane.getFilterTagTextField().textProperty().addListener((observable, oldValue, newValue) -> paintPane.redrawCanvas());
        
        buttonsPane.getDeleteButton().setOnAction(event -> {
            selectionManager.getSelection().forEach(canvasState::remove);
            paintPane.clearSelectionAndRedraw();
        });

        buttonsPane.getScaleUpButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::scaleUp);
            paintPane.redrawCanvas();
        });

        buttonsPane.getScaleDownButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::scaleDown);
            paintPane.redrawCanvas();
        });

        buttonsPane.getFlipHButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::flipH);
            paintPane.redrawCanvas();
        });

        buttonsPane.getFlipVButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::flipV);
            paintPane.redrawCanvas();
        });

        buttonsPane.getRotateButton().setOnAction(event -> {
            selectionManager.applyActionToSelection(FigureGroup::rotate);
            paintPane.redrawCanvas();
        });

        buttonsPane.getGroupButton().setOnAction(event -> {
            if(!selectionManager.atLeastTwoSelected())
                statusPane.updateStatus("Para agrupar es necesario seleccionar 2 o más grupos");
            else{
                canvasState.add(selectionManager.groupSelection());
                statusPane.updateStatus(String.format("Se agrupo: %s", selectionManager.getSelection().toString()));
                canvasState.removeAll(selectionManager.getSelection());
            }
            paintPane.clearSelectionAndRedraw();
        });

        buttonsPane.getUngroupButton().setOnAction(event -> {
            if(selectionManager.noneSelected())
                statusPane.updateStatus("Para desagrupar primero seleccione un grupo");
            else{
                canvasState.removeAll(selectionManager.getSelection());
                canvasState.addAll(selectionManager.ungroupSelection());
            }
            paintPane.clearSelectionAndRedraw();
        });

        buttonsPane.getSaveTagsButton().setOnAction(event ->{
            selectionManager.applyActionToSelection(group -> group.setTags(buttonsPane.getTagsFromTagsArea()));
            paintPane.clearSelectionAndRedraw();
        });
    }
}
