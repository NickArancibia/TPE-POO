package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import backend.model.Figure;
import backend.model.FigureGroup;
import backend.model.Point;

public class SelectionManager<T extends FigureGroup<? extends Figure>> {
    private Supplier<T> groupFactory;
    private List<T> selectedGroups = new ArrayList<>();

    public SelectionManager(Supplier<T> groupFactory) {
        setGroupFactory(groupFactory);
    }

    public void setGroupFactory(Supplier<T> groupFactory) {
        this.groupFactory = groupFactory;
    }

    public boolean selectFiguresInRect(Collection<T> figures, Point topLeft, Point bottomRight, boolean isFilteringByTags, String filterTag) {
        clearSelection();
        boolean addedFigures = false;
        for(T group : figures) {
            if(group.isFigureVisible(isFilteringByTags, filterTag) && group.isFigureInRectangle(topLeft, bottomRight)) {
                add(group);
                addedFigures = true;
            }
        }

        return addedFigures;
    }

    public void add(T group) {
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

    public T groupSelection() {
        T group = groupFactory.get();
        group.addAll(selectedGroups);
        return group;
    }

    public Collection<T> ungroupSelection() {
        List<T> ungrouped = new ArrayList<>();        

        for (T group : selectedGroups)  
            ungrouped.addAll((List<T>)group.ungroup());

        return ungrouped;
    }

    public Collection<T> getSelection() {
        return selectedGroups;
    }

    public boolean isSelected(T group) {
        return selectedGroups.contains(group);
    }

    public void applyActionToSelection(Consumer<T> consumer) {
        for (T group : selectedGroups) 
            consumer.accept(group);
    }

    private boolean isToggled(Predicate<T> toggled) {
        for (T group : selectedGroups)
            if (!toggled.test(group))
                return false;

        return true;
    }

    private boolean someToggled(Predicate<T> toggled) {
        int count = 0;
        for (T group : selectedGroups)
            if (toggled.test(group))
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
