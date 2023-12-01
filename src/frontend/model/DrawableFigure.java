package frontend.model;

import backend.model.Figure;
import frontend.model.DrawableState;
import javafx.scene.canvas.GraphicsContext;

public interface DrawableFigure extends Figure {
    void draw(GraphicsContext gc);
}
