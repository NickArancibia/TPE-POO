package frontend.model;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface DrawableFigure extends Figure {
    void draw(GraphicsContext gc);
    void setColor(Color color);

    // void toggleGradient();
    // void toggleShadow();
    // void toggleBevel();
}
