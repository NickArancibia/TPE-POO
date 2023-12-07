package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import backend.model.FigureGroup;
import backend.model.Point;

public class SelectionManager {
    private List<FigureGroup> selectedGroups = new ArrayList<>();

    public boolean selectFiguresInRect(Collection<FigureGroup> figures, Point topLeft, Point bottomRight, boolean isFilteringByTags, String filterTag) {
        clearSelection();
        boolean addedFigures = false;
        for(FigureGroup group : figures) {
            if(group.isFigureVisible(isFilteringByTags, filterTag) && group.isFigureInRectangle(topLeft, bottomRight)) {
                add(group);
                addedFigures = true;
            }
        }

        return addedFigures;
    }

    public void add(FigureGroup group) {
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

    public FigureGroup groupSelection() {
        FigureGroup group = new FigureGroup();
        group.addAllGroups(selectedGroups);
        return group;
    }

    public Collection<FigureGroup> ungroupSelection() {
        List<FigureGroup> ungrouped = new ArrayList<>();        

        for (FigureGroup group : selectedGroups)  
            ungrouped.addAll(group.ungroup());

        return ungrouped;
    }

    public Collection<FigureGroup> getSelection() {
        return selectedGroups;
    }

    public boolean isSelected(FigureGroup group) {
        return selectedGroups.contains(group);
    }

    public void applyActionToSelection(Consumer<FigureGroup> consumer) {
        for (FigureGroup group : selectedGroups) 
            consumer.accept(group);
    }

    private boolean isToggled(Predicate<FigureGroup> toggled) {
        for (FigureGroup group : selectedGroups)
            if (!toggled.test(group))
                return false;

        return true;
    }

    private boolean someToggled(Predicate<FigureGroup> toggled) {
        int count = 0;
        for (FigureGroup group : selectedGroups)
            if (toggled.test(group))
                count++;

        return count != 0 && count != selectedGroups.size();
    }

    public boolean isShadowToggled() {
        return isToggled(FigureGroup::isShadowToggled);
    }

    public boolean someShadowToggled() {
        return someToggled(FigureGroup::someShadowToggled);
    }

    public boolean isGradientToggled() {
        return isToggled(FigureGroup::isGradientToggled);
    }

    public boolean someGradientToggled() {
        return someToggled(FigureGroup::someGradientToggled);
    }

    public boolean isBevelToggled() {
        return isToggled(FigureGroup::isBevelToggled);
    }

    public boolean someBevelToggled() {
        return someToggled(FigureGroup::someBevelToggled);
    }
}
