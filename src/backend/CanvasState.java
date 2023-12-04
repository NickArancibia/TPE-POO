package backend;

import backend.model.Figure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CanvasState <T extends Figure> {
    private final List<T> list = new ArrayList<>();

    public void addFigure(T figure) {
        list.add(figure);
    }

    public void addAllFigures(Collection<T> figures) {
        list.addAll(figures);
    }

    public void deleteFigure(T figure) {
        list.remove(figure);
    }

    public void deleteFigures(Collection<T> figures) {
        list.removeAll(figures);
    }

    public Collection<T> figures() {
        return list;
    }
}
