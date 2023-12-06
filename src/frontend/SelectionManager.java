package frontend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import backend.model.Figure;
import backend.model.Point;
import frontend.model.DrawableGroup;

public class SelectionManager {
    List<DrawableGroup<? extends Figure>> selectedGroups = new ArrayList<>();

    public boolean selectFiguresInRect(Collection<DrawableGroup<? extends Figure>> figures, Point topLeft, Point bottomRight, TagFilterPane tagFilterPane) {
        clearSelection();
        boolean addedFigures = false;
        for(DrawableGroup<? extends Figure> group : figures) {
            if( group.isFigureVisible(tagFilterPane) && group.isFigureInRectangle(topLeft, bottomRight)) {
                add(group);
                addedFigures = true;
            }
        }

        return addedFigures;
    }

    public void add(DrawableGroup<? extends Figure> group) {
        if(!selectedGroups.contains(group))
            selectedGroups.add(group);
    }

    public void clearSelection() {
        selectedGroups.clear();
    }

    public boolean atLeastTwoSelected() {
        return selectedGroups.size() >= 2;
    }

    public boolean noneSelected(){
        return selectedGroups.isEmpty();
    }

    public DrawableGroup<? extends Figure> groupSelection() {
        DrawableGroup<? extends Figure> group = new DrawableGroup<>();
        group.addAllGroups(selectedGroups);
        return group;
    }

    public Collection<DrawableGroup<? extends Figure>> ungroupSelection() {
        List<DrawableGroup<? extends Figure>> ungrouped = new ArrayList<>();        

        for (DrawableGroup<? extends Figure> group : selectedGroups) { 
            ungrouped.addAll(group.ungroup());
        }

        return ungrouped;
    }

    public Collection<DrawableGroup<? extends Figure>> getSelection() {
        return selectedGroups;
    }

    public boolean isSelected(DrawableGroup<? extends Figure> group) {
        return selectedGroups.contains(group);
    }

    public void applyActionToSelection(Consumer<DrawableGroup<? extends Figure>> consumer) {
        for (DrawableGroup<? extends Figure> group : selectedGroups) 
            consumer.accept(group);
    }

    private boolean isToggled(Function<DrawableGroup<? extends Figure>, Boolean> toggled) {
        for (DrawableGroup<? extends Figure> group : selectedGroups)
            if (!toggled.apply(group))
                return false;

        return true;
    }

    private boolean someToggled(Function<DrawableGroup<? extends Figure>, Boolean> toggled) {
        int count = 0;
        for (DrawableGroup<? extends Figure> group : selectedGroups)
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
