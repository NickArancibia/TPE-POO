package frontend;

import backend.CanvasState;
import frontend.model.DrawableGroup;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {
    public MainFrame(CanvasState<DrawableGroup> canvasState) {
        AppMenuBar appMenuBar = new AppMenuBar();
        StatusPane statusPane = new StatusPane();
        ShapeDrawPropertiesPane drawPropertiesPane = new ShapeDrawPropertiesPane();
        TagFilterPane tagFilterPane = new TagFilterPane();
        PaintPane paintPane = new PaintPane(canvasState, statusPane, drawPropertiesPane, tagFilterPane);
        getChildren().add(appMenuBar);
        getChildren().add(drawPropertiesPane);
        getChildren().add(paintPane);
        getChildren().add(tagFilterPane);
        getChildren().add(statusPane);
    }
}
