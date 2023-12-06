package backend.model;

public abstract class Figure {
    
    public abstract boolean pointInFigure(Point p);
    
    public abstract void move(double deltaX, double deltaY);
    
    public abstract boolean isFigureInRectangle(Point topLeft, Point bottomRight);
    
    public abstract void scaleUp();
    
    public abstract void scaleDown();
    
    public abstract void flipV();
    
    public abstract void flipH();
    
    public abstract void rotate();
}
