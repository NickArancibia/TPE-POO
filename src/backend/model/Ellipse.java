package backend.model;

public class Ellipse implements Figure {
    private final Point centerPoint;
    private double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    @Override
    public boolean pointInFigure(Point p) {
        return ((Math.pow(p.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) + (Math.pow(p.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }

    @Override
    public void move(double deltaX, double deltaY) {
        centerPoint.move(deltaX, deltaY);
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        return centerPoint.isPointInRectangle(topLeft, bottomRight) &&
                Math.abs(topLeft.getX() - bottomRight.getX()) >= sMayorAxis &&
                Math.abs(topLeft.getY() - bottomRight.getY()) >= sMinorAxis;
    }
    @Override
    public void scaleGrowth() {
        sMayorAxis = sMayorAxis*1.25;
        sMinorAxis = sMinorAxis*1.25;
    }
    @Override
    public void scaleReduce() {
        sMayorAxis = sMayorAxis*0.75;
        sMinorAxis = sMinorAxis*0.75;
    }
    @Override
    public void turnAroundV(){
        centerPoint.move(0,sMinorAxis);
    }
    @Override
    public void turnAroundH(){
        centerPoint.move(sMayorAxis,0);
    }
    @Override
    public void spin(){
        double auxiliarAxis = sMayorAxis;
        sMayorAxis = sMinorAxis;
        sMinorAxis = auxiliarAxis;
    }
}
