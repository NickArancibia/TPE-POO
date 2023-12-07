package backend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FigureGroup<T extends Figure> extends Figure{
    private List<T> figures = new ArrayList<>();

    public FigureGroup(String colorAsHex) {
        super(colorAsHex);
    }

    public FigureGroup() {
        super("#ffffff");
    }

    public FigureGroup(T figure) {
        super(figure.getColorAsHex());
        add(figure);
    }

    public void add(T figure){
        figures.add(figure);
        getTags().addAll(figure.getTags());
    }

    @SuppressWarnings("unchecked")
    public void addAll(Iterable<? extends  Figure> figures){
        for(Figure figure : figures)
            add((T)figure);
    }

    public void addGroup(FigureGroup<T> figureGroup){
        addAll(figureGroup.figures);
        getTags().addAll(figureGroup.getTags());
    }

    @SuppressWarnings("unchecked")
    public void addAllGroups(Iterable<? extends FigureGroup<? extends Figure>> figureGroups){
        for(FigureGroup<? extends Figure> figureGroup : figureGroups)
            addGroup((FigureGroup<T>) figureGroup);
    }

    @SuppressWarnings("all")
    public Collection<FigureGroup<T>> ungroup(Supplier<? extends FigureGroup> groupFactory) {
        List<FigureGroup<T>> out = new ArrayList<>();

        for(T figure : getFigures()) {
            FigureGroup<T> newGroup = groupFactory.get();
            newGroup.add(figure);
            out.add(newGroup);
        }

        return out;
    }

    public int size(){
        return figures.size();
    }

    public List<T> getFigures(){
        return new ArrayList<>(figures);
    }

    private void applyConsumer(Consumer<T> consumer) {
        for(T figure : figures)
            consumer.accept(figure);
    }

    @Override
    public void setGradientToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setGradientToggled(toggle));
    }

    @Override
    public void setShadowToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setShadowToggled(toggle));
    }

    @Override
    public void setBevelToggled(boolean toggle) {
        applyConsumer((figure) -> figure.setBevelToggled(toggle));
    }

    private boolean isToggled(Predicate<T> toggled) {
        for(T figure : figures)
            if (!toggled.test(figure)) return false;

        return true;
    }

    private boolean someToggled(Predicate<T> toggled) {
        int count = 0;
        for(T figure : figures)
            if (toggled.test(figure)) count++;

        return count != 0 && count != figures.size();
    }

    @Override
    public boolean isShadowToggled() {
        return isToggled((figure) -> figure.isShadowToggled());
    }

    public boolean someShadowToggled() {
        return someToggled((figure) -> figure.isShadowToggled());
    }

    @Override
    public boolean isGradientToggled() {
        return isToggled((figure) -> figure.isGradientToggled());
    }

    public boolean someGradientToggled() {
        return someToggled((figure) -> figure.isGradientToggled());
    }

    @Override
    public boolean isBevelToggled() {
        return isToggled((figure) -> figure.isBevelToggled());
    }

    public boolean someBevelToggled() {
        return someToggled((figure) -> figure.isBevelToggled());
    }

    public void setTags(Collection<String> tags){
        super.setTags(tags);
        applyConsumer((figure) -> figure.setTags(tags));
    }

    @Override
    public boolean pointInFigure(Point p){
        for(T figure : figures){
            if(figure.pointInFigure(p))
                return true;
        }
        return false;
    }

    @Override
    public void move(double deltaX, double deltaY){
        applyConsumer((figure) -> figure.move(deltaX, deltaY));
    }

    @Override
    public boolean isFigureInRectangle(Point topLeft, Point bottomRight){
        for(Figure figure : figures){
            if(figure.isFigureInRectangle(topLeft, bottomRight))
                return true;
        }
        return false;
    }
    @Override
    public void scale(double delta) {
        applyConsumer((figure) -> figure.scale(delta));
    }

    @Override
    public void flipH() {
        applyConsumer((figure) -> figure.flipH());
    }

    @Override
    public void flipV() {
        applyConsumer((figure) -> figure.flipV());
    }

    @Override
    public void rotate() {
        applyConsumer((figure) -> figure.rotate());
    }

    @Override
    public boolean isFigureVisible(boolean isFilteringByTags, String filterTag) {
        if(!isFilteringByTags) return true;
        for(T figure : getFigures())
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