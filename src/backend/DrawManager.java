package backend;

import backend.model.Figure;

public interface DrawManager<T extends Figure> {
    void drawShape(T figure);
    void drawBevel(T figure);
    void drawGradient(T figure);
    void drawShadow(T figure);

    default void draw(T figure) {
        drawShadow(figure);
        drawGradient(figure);
        drawShape(figure);
        drawBevel(figure);
    }
}
