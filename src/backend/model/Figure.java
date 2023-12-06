package backend.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Figure {
    private String colorAsHex; 

    private boolean gradientToggled = false;
    private boolean shadowToggled = false;
    private boolean bevelToggled = false;

    private Set<String> tags = new HashSet<>();

    public Figure(String colorAsHex) {
        this.colorAsHex = colorAsHex;
    }

    public String getColorAsHex() {
        return colorAsHex;
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

    public void setTags(Collection<String> tags){
        this.tags = new HashSet<String>(tags);
    }

    public Set<String> getTags(){
        return tags;
    }

    public abstract boolean pointInFigure(Point p);

    public abstract void move(double deltaX, double deltaY);
    
    public abstract boolean isFigureInRectangle(Point topLeft, Point bottomRight);
    
    public abstract void scaleUp();
    
    public abstract void scaleDown();
    
    public abstract void flipV();
    
    public abstract void flipH();
    
    public abstract void rotate();
}
