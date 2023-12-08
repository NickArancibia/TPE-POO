package backend;

import backend.model.Figure;

public interface DrawManager<T extends Figure> {
    void drawShape(T figure);
    void drawBevel(T figure);
    void drawGradient(T figure);
    void drawShadow(T figure);
    void drawBorder(T figure, boolean selected);
    default void draw(T figure,boolean selected) {
        drawShadow(figure);
        drawBevel(figure);
        drawGradient(figure);
        drawShape(figure);
        drawBorder(figure,selected);
        }


}
