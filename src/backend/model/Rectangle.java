package backend.model;

public class Rectangle implements Figure {
    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
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

    public void scaleGrowth(){
        scale(topLeft,bottomRight);
    }

    public void scaleReduce(){
        scale(bottomRight,topLeft);
    }

    private void scale(Point newTopLeft, Point newBottomRight){
        double deltaX = getBase()*0.25/2;
        double deltaY = getHeight()*0.25 /2;
        newTopLeft.move(-deltaX,-deltaY);
        newBottomRight.move(deltaX,deltaY);
    }

    @Override
    public void turnAroundH() {
        turnAround(getBase(),0);
    }

    @Override
    public void turnAroundV() {
        turnAround(0,getHeight());

    }

    private void turnAround(double deltaX,double deltaY){
        topLeft.move(deltaX,deltaY);
        bottomRight.move(deltaX,deltaY);
    }

    @Override
    public void spin() {
       double delta = (getBase() - getHeight())/2;
       topLeft.move(delta,-delta);
       bottomRight.move(-delta,delta);
    }
}
