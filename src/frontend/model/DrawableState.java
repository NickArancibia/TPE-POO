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

    void setGradientToggled(boolean toggle) {
        gradientToggled = toggle;
    }

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
    }

    boolean isBevelToggled() {
        return bevelToggled;
    }
}
