package frontend;

import backend.CanvasState;
import frontend.model.DrawableGroup;
import frontend.TagFilterPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainFrame extends VBox {
    public MainFrame(CanvasState<DrawableGroup> canvasState) {
        AppMenuBar appMenuBar = new AppMenuBar();
        StatusPane statusPane = new StatusPane();
        ShapeDrawPropertiesPane drawPropertiesPane = new ShapeDrawPropertiesPane();
        TagFilterPane tagFilterPane = new TagFilterPane();
        ButtonsBoxPane buttonsPane = new ButtonsBoxPane();
        PaintPane paintPane = new PaintPane(canvasState, statusPane, drawPropertiesPane, tagFilterPane,buttonsPane);
        getChildren().add(appMenuBar);
        getChildren().add(drawPropertiesPane);
        getChildren().add(new HBox(buttonsPane,paintPane));
        getChildren().add(tagFilterPane);
        getChildren().add(statusPane);
    }
}
