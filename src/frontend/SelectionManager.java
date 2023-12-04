package frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import backend.model.Point;
import frontend.model.DrawableGroup;

public class SelectionManager {
    List<DrawableGroup> selectedGroups = new ArrayList<>();

    public void addToSelectionIfFigureInSelection(Collection<DrawableGroup> figures, Point topLeft, Point bottomRight) {
        for(DrawableGroup group : figures){
            if(group.isFigureInRectangle(topLeft, bottomRight))
                selectedGroups.add(group);
        }
    }

    public void add(DrawableGroup group) {
        selectedGroups.add(group);
    }

    public void clearSelection() {
        selectedGroups.clear();
    }

    public boolean canGroup() {
        return selectedGroups.size() >= 2;
    }

    public boolean canUngroup() {
        return !selectedGroups.isEmpty();
    }

    public DrawableGroup groupSelection() {
        DrawableGroup group = new DrawableGroup();
        group.addAll(selectedGroups);
        return group;
    }

    public Collection<DrawableGroup> ungroupSelection() {
        List<DrawableGroup> ungrouped = new ArrayList<>();        

        for (DrawableGroup group : selectedGroups) 
            ungrouped.addAll(group.ungroup());

        return ungrouped;
    }

    public Collection<DrawableGroup> getSelection() {
        return selectedGroups;
    }

    public boolean isSelected(DrawableGroup group) {
        return selectedGroups.contains(group);
    }

    public void applyActionToSelection(Consumer<DrawableGroup> consumer) {
        for (DrawableGroup group : selectedGroups) 
            consumer.accept(group);
    }
}
