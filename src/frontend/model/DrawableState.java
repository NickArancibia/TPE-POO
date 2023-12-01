package frontend.model;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class DrawableState {
    private Color color;
    private boolean gradientToggled = false;
    private boolean shadowToggled = false;
    private boolean bevelToggled = false;

    void setColor(Color color) {
        this.color = color;
    }

    void setDrawProperties(GraphicsContext gc) {
        gc.setFill(color);
    }

    void toggleGradient() {
        gradientToggled = !gradientToggled;
    }

    void toggleShadow() {
        shadowToggled = !shadowToggled;
    }

    void toggleBevel() {
        bevelToggled = !bevelToggled;
    }
}
