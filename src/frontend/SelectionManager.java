package frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import backend.model.Point;
import frontend.model.DrawableGroup;

public class SelectionManager {
    List<DrawableGroup> selectedGroups = new ArrayList<>();

    public boolean addToSelectionIfVisibleFigureInSelection(Collection<DrawableGroup> figures, Point topLeft, Point bottomRight, TagFilterPane tagFilterPane) {
        boolean addedFigures = false;
        for(DrawableGroup group : figures) {
            if(group.isFigureVisible(tagFilterPane) && group.isFigureInRectangle(topLeft, bottomRight)) {
                add(group);
                addedFigures = true;
            }
        }

        return addedFigures;
    }

    public void add(DrawableGroup group) {
        if(!selectedGroups.contains(group))
            selectedGroups.add(group);
    }

    public void clearSelection() {
        selectedGroups.clear();
    }

    public boolean canGroup() {
        return selectedGroups.size() >= 2;
    }

    public boolean noSelection(){
        return selectedGroups.isEmpty();
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

        for (DrawableGroup group : selectedGroups) { 
            ungrouped.addAll(group.ungroup());
        }

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

    private boolean isToggled(Function<DrawableGroup, Boolean> toggled) {
        for (DrawableGroup group : selectedGroups)
            if (!toggled.apply(group))
                return false;

        return true;
    }

    private boolean someToggled(Function<DrawableGroup, Boolean> toggled) {
        int count = 0;
        for (DrawableGroup group: selectedGroups)
            if (toggled.apply(group))
                count++;

        return count != 0 && count != selectedGroups.size();
    }

    public boolean isShadowToggled() {
        return isToggled((figure) -> figure.isShadowToggled());
    }

    public boolean someShadowToggled() {
        return someToggled((figure) -> figure.isShadowToggled());
    }

    public boolean isGradientToggled() {
        return isToggled((figure) -> figure.isGradientToggled());
    }

    public boolean someGradientToggled() {
        return someToggled((figure) -> figure.isGradientToggled());
    }

    public boolean isBevelToggled() {
        return isToggled((figure) -> figure.isBevelToggled());
    }

    public boolean someBevelToggled() {
        return someToggled((figure) -> figure.isBevelToggled());
    }
}
