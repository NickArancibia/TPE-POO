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

    void setGradientToggled(boolean toggle) {
        gradientToggled = toggle;
    }

<<<<<<< HEAD
    void toggleShadow() {
        shadowToggled = !shadowToggled;
    }

    void toggleBevel() {
        bevelToggled = !bevelToggled;
=======
    boolean isGradientToggled() {
        return gradientToggled;
    }

    void setShadowToggled(boolean toggle) {
        shadowToggled = toggle;
    }

    boolean isShadowToggled() {
        return shadowToggled;
    }

    void setBevelToggled(boolean toggle) {
        bevelToggled = toggle;
>>>>>>> 695a69b06aaac97a227dc4403800c56d68c9f867
    }
}
