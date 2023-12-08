package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TagFilterPane extends BorderPane{
    private final Label showTagsLabel;
    private final RadioButton showAllButton;
    private final RadioButton filterTagButton;
    private final TextField filterTagField;
    private final ToggleGroup showTagsGroup;

    public TagFilterPane(){
        setStyle("-fx-background-color: #999");
		HBox tagsBox = new HBox(10);
		tagsBox.setAlignment(Pos.CENTER);
		tagsBox.setPadding(new Insets(5));
		tagsBox.setStyle("-fx-background-color: #999");
        
		showTagsGroup = new ToggleGroup();

        showTagsLabel = new Label("Mostrar Etiquetas: ");
		tagsBox.getChildren().add(showTagsLabel);

        showAllButton = new RadioButton("Todas");
		showAllButton.setToggleGroup(showTagsGroup);
		showAllButton.setCursor(Cursor.HAND);
        showAllButton.setSelected(true);
		tagsBox.getChildren().add(showAllButton);

        filterTagButton = new RadioButton("Sólo: ");
		filterTagButton.setToggleGroup(showTagsGroup);
		filterTagButton.setCursor(Cursor.HAND);
		tagsBox.getChildren().add(filterTagButton);

        filterTagField = new TextField();
		tagsBox.getChildren().add(filterTagField);

        setCenter(tagsBox);
    }

    public TextField getFilterTagTextField() {
        return filterTagField;
    }

    public String getFilterTag(){
        String[] filterTag = filterTagField.getText().split(" ");
        if(filterTag.length > 0)
            return filterTagField.getText().split(" ")[0];
        else
            return "";
    }

    public boolean isFiltering(){
        return filterTagButton.isSelected();
    }

    public RadioButton getShowAllButton(){
        return showAllButton;
    }

    public RadioButton getFilterTagButton(){
        return filterTagButton;
    }
}
