package backend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FigureGroup {
    private List<Figure> figures = new ArrayList<>();

    public FigureGroup() {

    }

    public FigureGroup(Figure figure) {
        add(figure);
    }

    public void add(Figure figure){
        figures.add(figure);
    }

    public void addAll(Iterable<Figure> figures){
        for(Figure figure : figures)
            add(figure);
    }

    public void addGroup(FigureGroup figureGroup){
        addAll(figureGroup.figures);
    }

    public void addAllGroups(Iterable<FigureGroup> figureGroups){
        for(FigureGroup figureGroup : figureGroups)
            addGroup(figureGroup);
    }

    public Collection<FigureGroup> ungroup() {
        List<FigureGroup> out = new ArrayList<>();

        for(Figure figure : figures) 
            out.add(new FigureGroup(figure));

        return out;
    }

    public int size(){
        return figures.size();
    }

    public List<Figure> getFigures(){
        return new ArrayList<>(figures);
    }

    private void applyConsumer(Consumer<Figure> consumer) {
        for(Figure figure : figures)
            consumer.accept(figure);
    }

    public void draw() {
        applyConsumer((figure) -> figure.draw());
    }

    public void setGradientToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setGradientToggled(toggle));
    }

    public void setShadowToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setShadowToggled(toggle));
    }

    public void setBevelToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setBevelToggled(toggle));
    }

    private boolean isToggled(Predicate<Figure> toggled) {
        for(Figure figure : figures)
            if (!toggled.test(figure)) return false;

        return true;
    }

    private boolean someToggled(Predicate<Figure> toggled) {
        int count = 0;
        for(Figure figure : figures)
            if (toggled.test(figure)) count++;

        return count != 0 && count != figures.size();
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

    public void setTags(Collection<String> tags){
        applyConsumer((figure) -> figure.setTags(tags));
    }

    public Set<String> getTags(){
        Set<String> tags = new HashSet<>();
        applyConsumer((figure) -> tags.addAll(figure.getTags()));
        return tags;
    }

    public boolean pointInFigure(Point p){
        for(Figure figure : figures){
            if(figure.pointInFigure(p))
                return true;
        }
        return false;
    }

    public void move(double deltaX, double deltaY){
        applyConsumer((figure) -> figure.move(deltaX, deltaY));
    }

    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        for(Figure figure : figures){
            if(figure.isFigureInRectangle(topLeft, bottomRight))
                return true;
        }
        return false;
    }

    public void scaleUp() {
        applyConsumer((figure) -> figure.scale(0.25));
    }

    public void scaleDown() {
        applyConsumer((figure) -> figure.scale(-0.25));
    }

    public void flipH() {
        applyConsumer((figure) -> figure.flipH());
    }

    public void flipV() {
        applyConsumer((figure) -> figure.flipV());
    }

    public void rotate() {
        applyConsumer((figure) -> figure.rotate());
    }

    public boolean isFigureVisible(boolean isFilteringByTags, String filterTag) {
        if(!isFilteringByTags) return true;
        for(Figure figure : figures)
            if (figure.hasTag(filterTag)) return true;
        return false;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        applyConsumer((figure) -> s.append(figure.toString()));
        return s.toString();
    }
}