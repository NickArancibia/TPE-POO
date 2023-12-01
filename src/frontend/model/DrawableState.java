package frontend.model;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class DrawableState {
    private Color color;
    private boolean gradientToggled = false;
    private boolean shadowToggled = false;
    private boolean bevelToggled = false;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setGradientToggled(boolean toggle) {
        gradientToggled = toggle;
    }

    public boolean isGradientToggled() {
        return gradientToggled;
    }

    public void setShadowToggled(boolean toggle) {
        shadowToggled = toggle;
    }

    public boolean isShadowToggled() {
        return shadowToggled;
    }

    public void setBevelToggled(boolean toggle) {
        bevelToggled = toggle;
    }

    public boolean isBevelToggled() {
        return bevelToggled;
    }
}
