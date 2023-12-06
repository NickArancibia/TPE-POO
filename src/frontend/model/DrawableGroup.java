package frontend.model;

import java.util.function.Consumer;

import backend.model.Figure;
import backend.model.FigureGroup;
import frontend.TagFilterPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableGroup<T extends Figure & Drawable> extends FigureGroup<T> implements Drawable {
    public DrawableGroup(Color color) {
        super(color.toString());
    }

    public DrawableGroup() {
        super();
    }

    public DrawableGroup(T figure) {
        super(figure);
    }


    private void applyConsumer(Consumer<T> consumer) {
        for(T figure : getFigures())
            consumer.accept(figure);
    }

    @Override
    public void drawFigure(GraphicsContext gc) {
        applyConsumer((figure) -> figure.drawFigure(gc));
    }

    public boolean isFigureVisible(TagFilterPane tagFilterPane){
        return super.isFigureVisible(tagFilterPane.isFiltering(), tagFilterPane.getFilterTag());
    }
}
