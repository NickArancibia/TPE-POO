package frontend.model;
import javafx.scene.paint.Color;

public class DrawableState {
    private Color color;
    private boolean gradientToggled = false;
    private boolean shadowToggled = false;
    private boolean bevelToggled = false;

    void setColor(Color color) {
        this.color = color;
    }

    Color getColor() {
        return color;
    }

    void toggleGradient() {
        gradientToggled = !gradientToggled;
    }

    boolean isGradientToggled() {
        return gradientToggled;
    }

    void toggleShadow() {
        shadowToggled = !shadowToggled;
    }

    boolean isShadowToggled() {
        return shadowToggled;
    }

    void toggleBevel() {
        bevelToggled = !bevelToggled;
    }

    boolean isBevelToggled() {
        return bevelToggled;
    }
}
