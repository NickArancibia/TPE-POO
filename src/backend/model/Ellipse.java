package backend.model;

public class Ellipse extends Figure {
    private final Point centerPoint;
    private double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis, String colorAsHex) {
        super(colorAsHex);
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
        return (new Point(centerPoint.getX() - sMayorAxis / 2, centerPoint.getY() - sMinorAxis / 2)).isPointInRectangle(topLeft, bottomRight)
            && (new Point(centerPoint.getX() + sMayorAxis / 2, centerPoint.getY() + sMinorAxis / 2 )).isPointInRectangle(topLeft, bottomRight);
    }
    @Override
    public void scaleUp() {
        scale(0.25);
    }
    @Override
    public void scaleDown() {
        scale(-0.25);
    }

    private void scale(double factor){
        sMayorAxis += sMayorAxis*factor;
        sMinorAxis += sMinorAxis*factor;
    }
    @Override
    public void flipV(){
        centerPoint.move(0,sMinorAxis);
    }
    @Override
    public void flipH(){
        centerPoint.move(sMayorAxis,0);
    }
    @Override
    public void rotate(){
        double auxiliarAxis = sMayorAxis;
        sMayorAxis = sMinorAxis;
        sMinorAxis = auxiliarAxis;
    }
}
