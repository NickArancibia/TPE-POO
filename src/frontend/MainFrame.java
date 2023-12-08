package frontend;

import backend.CanvasState;
import backend.SelectionManager;
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
    }
}
