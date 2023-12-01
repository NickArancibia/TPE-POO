package frontend;

import backend.CanvasState;
import frontend.model.DrawableFigure;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {
    public MainFrame(CanvasState<DrawableFigure> canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        ShapeDrawPropertiesPane drawPropertiesPane = new ShapeDrawPropertiesPane();
        getChildren().add(drawPropertiesPane);
        getChildren().add(new PaintPane(canvasState, statusPane, drawPropertiesPane));
        getChildren().add(statusPane);
    }
}
