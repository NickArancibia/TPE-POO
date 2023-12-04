package backend.model;

import java.util.ArrayList;

public class FigureGroup<T extends Figure> extends ArrayList<T> implements Figure{
    @Override
    public boolean pointInFigure(Point p){
        for(T figure : this){
            if(figure.pointInFigure(p))
                return true;
        }
        return false;
    }

    @Override
    public void move(double deltaX, double deltaY){
        for(T figure : this){
            figure.move(deltaX, deltaY);
        }
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        for(Figure figure : this){
            if(figure.isFigureInRectangle(topLeft, bottomRight))
                return true;
        }
        return false;
    }

    @Override
    public void scaleUp() {
        for(Figure figure : this){
            figure.scaleUp();
        }
    }

    @Override
    public void scaleDown() {
        for(Figure figure : this){
            figure.scaleDown();
        }
    }

    @Override
    public void flipH() {
        for(Figure figure : this){
            figure.flipH();
        }
    }

    @Override
    public void flipV() {
        for(Figure figure : this){
            figure.flipV();
        }
    }

    @Override
    public void rotate() {
        for(Figure figure : this){
            figure.rotate();
        }
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(T figure : this) {
            s.append(figure.toString());
        }
        return s.toString();
    }
}
