package backend.model;

public class Rectangle extends Figure {
    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight, String colorAsHex) {
        super(colorAsHex);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Rectangle(Point topLeft, double width, double height, String colorAsHex) {
        this(topLeft, new Point(topLeft.getX() + width, topLeft.getY() + height), colorAsHex);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean pointInFigure(Point p) {
        return p.getX() > topLeft.getX() && p.getX() < bottomRight.getX() &&
			   p.getY() > topLeft.getY() && p.getY() < bottomRight.getY();
    }

    @Override
    public void move(double deltaX, double deltaY) {
        topLeft.move(deltaX, deltaY);
        bottomRight.move(deltaX, deltaY);
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        return this.topLeft.isPointInRectangle(topLeft, bottomRight) && this.bottomRight.isPointInRectangle(topLeft, bottomRight);
    }
    public double getBase(){
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    public double getHeight(){
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    @Override
    public void scale(double factor){
        double deltaX = getBase()*factor/2;
        double deltaY = getHeight()*factor /2;
        topLeft.move(-deltaX,-deltaY);
        bottomRight.move(deltaX,deltaY);
    }

    @Override
    public void flipH() {
        turnAround(getBase(),0);
    }

    @Override
    public void flipV() {
        turnAround(0,getHeight());

    }

    private void turnAround(double deltaX,double deltaY){
        topLeft.move(deltaX,deltaY);
        bottomRight.move(deltaX,deltaY);
    }

    @Override
    public void rotate() {
       double delta = (getBase() - getHeight())/2;
       topLeft.move(delta,-delta);
       bottomRight.move(-delta,delta);
    }
}
