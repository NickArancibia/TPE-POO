package backend.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import backend.DrawManager;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Figure {
    private final String colorAsHex;

    private boolean gradientToggled = false;
    private boolean shadowToggled = false;
    private boolean bevelToggled = false;
    private Set<String> tags = new HashSet<>();

    private final DrawManager drawManager;

    public Figure(String colorAsHex, DrawManager drawManager) {
        tags.add("");
        this.colorAsHex = colorAsHex;
        this.drawManager = drawManager;
    }

    public void draw(boolean selected) {
        drawManager.draw(this,selected);
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
        this.tags = new HashSet<>(tags);
    }

    public boolean hasTag(String tag){
        return tags.contains(tag);
    }

    public Set<String> getTags(){
        return tags;
    }

    public abstract boolean pointInFigure(Point p);

    public abstract void move(double deltaX, double deltaY);
    
    public abstract boolean isFigureInRectangle(Point topLeft, Point bottomRight);
    
    public abstract void scale(double delta);
    public void scaleUp(){scale(0.25);}
    public void scaleDown(){scale(-0.25);}
    
    public abstract void flipV();
    public abstract void flipH();
    
    public abstract void rotate();
}
