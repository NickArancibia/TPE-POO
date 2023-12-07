package backend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class FigureGroup extends ArrayList<Figure>{

    public FigureGroup() {

    }

    public FigureGroup(Figure figure) {
        this.add(figure);
    }

    public void addAllGroups(Iterable<FigureGroup> figureGroups){
        for(FigureGroup figureGroup : figureGroups)
            addAll(figureGroup);
    }

    public Collection<FigureGroup> ungroup() {
        List<FigureGroup> out = new ArrayList<>();
        forEach((figure) -> out.add(new FigureGroup(figure)));
        return out;
    }

    public void draw() {
        forEach(Figure::draw);
    }

    public void setGradientToggled(boolean toggle) {
        forEach((figure) -> figure.setGradientToggled(toggle));
    }

    public void setShadowToggled(boolean toggle) {
        forEach((figure) -> figure.setShadowToggled(toggle));
    }

    public void setBevelToggled(boolean toggle) {
        forEach((figure) -> figure.setBevelToggled(toggle));
    }

    private boolean isToggled(Predicate<Figure> toggled) {
        for(Figure figure : this)
            if (!toggled.test(figure)) return false;
        return true;
    }

    private boolean someToggled(Predicate<Figure> toggled) {
        int count = 0;
        for(Figure figure : this)
            if (toggled.test(figure)) count++;
        return count != 0 && count != this.size();
    }

    public boolean isShadowToggled() {
        return isToggled(Figure::isShadowToggled);
    }

    public boolean someShadowToggled() {
        return someToggled(Figure::isShadowToggled);
    }

    public boolean isGradientToggled() {
        return isToggled(Figure::isGradientToggled);
    }

    public boolean someGradientToggled() {
        return someToggled(Figure::isGradientToggled);
    }

    public boolean isBevelToggled() {
        return isToggled(Figure::isBevelToggled);
    }

    public boolean someBevelToggled() {
        return someToggled(Figure::isBevelToggled);
    }

    public void setTags(Collection<String> tags){
        forEach((figure) -> figure.setTags(tags));
    }

    public Set<String> getTags(){
        Set<String> tags = new HashSet<>();
        forEach((figure) -> tags.addAll(figure.getTags()));
        return tags;
    }

    public boolean pointInFigure(Point p){
        for(Figure figure : this)
            if(figure.pointInFigure(p)) return true;
        return false;
    }

    public void move(double deltaX, double deltaY){
        forEach((figure) -> figure.move(deltaX, deltaY));
    }

    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        for(Figure figure : this)
            if(figure.isFigureInRectangle(topLeft, bottomRight)) return true;
        return false;
    }

    public void scaleUp() {
        forEach(Figure::scaleUp);
    }

    public void scaleDown() {
        forEach(Figure::scaleDown);
    }

    public void flipH() {
        forEach(Figure::flipH);
    }

    public void flipV() {
        forEach(Figure::flipV);
    }

    public void rotate() {
        forEach(Figure::rotate);
    }

    public boolean hasTag(String filterTag) {
        for(Figure figure : this)
            if (figure.hasTag(filterTag)) return true;
        return false;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        forEach((figure) -> s.append(figure.toString()));
        return s.toString();
    }
}