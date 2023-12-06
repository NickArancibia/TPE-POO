package backend.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Figure {
    
    private Set<String> tags = new HashSet<>();

    public abstract boolean pointInFigure(Point p);
    
    public abstract void move(double deltaX, double deltaY);
    
    public abstract boolean isFigureInRectangle(Point topLeft, Point bottomRight);
    
    public abstract void scaleUp();
    
    public abstract void scaleDown();
    
    public abstract void flipV();
    
    public abstract void flipH();
    
    public abstract void rotate();

    public void setTags(Set<String> tags){
        this.tags = tags; 
    }

    public Set<String> getTags(){
        return tags;
    }

    public boolean hasTag(String tag){
        return tags.contains(tag);
    }
}
