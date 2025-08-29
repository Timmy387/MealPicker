package GuiLayouts;
import Utils.Sizes;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static SQL.rowChanges.insert;
import GUIObjects.ToggleMenuButton;
import javafx.stage.Popup;

import java.sql.Connection;
import static Utils.javaFXUtils.getToggleGroupText;


public class AddFoodFrame extends VBox {
    private final Sizes sizes = Sizes.getSizes();
    private final Connection conn = SQL.Connect.getConnection();
    private final ToggleGroup foodTg = new ToggleGroup();
    private final ToggleGroup mealTg = new ToggleGroup();
    private final TextField foodName = new TextField();
    private final Spinner<Integer> weight = new Spinner<>();
    public AddFoodFrame(){
        ObservableList<Node> children = this.getChildren();
        setPadding(new Insets(sizes.padx));
        setAlignment(Pos.CENTER);

        VBox nameField = new VBox(2);
        nameField.setMaxWidth(sizes.width / 2);
        children.add(nameField);
        Label nameLabel = new Label("Food Name:");
        nameLabel.setFont(sizes.smallFont);
        nameField.getChildren().addAll(nameLabel, foodName);
        foodName.setMaxWidth(sizes.width / 2);
        foodName.setFont(sizes.smallFont);

        setMargin(nameField, new Insets(sizes.pady));

        HBox mealType = new HBox(2);
        mealType.setMaxWidth(sizes.width * 0.75);
        ToggleMenuButton dinner = new ToggleMenuButton("Dinner", mealTg);
        mealType.getChildren().addAll(
                new ToggleMenuButton("Breakfast", mealTg),
                new ToggleMenuButton("Lunch", mealTg),
                dinner,
                new ToggleMenuButton("Snack", mealTg),
                new ToggleMenuButton("Dessert", mealTg)
        );
        children.add(mealType);
        setMargin(mealType, new Insets(sizes.pady));
        dinner.setSelected(true);

        mealTg.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                oldVal.setSelected(true);
            }
        });

        HBox foodType = new HBox(2);
        foodType.setMaxWidth(sizes.width * 0.5);
        ToggleMenuButton mainCourse = new ToggleMenuButton("Main Course", foodTg);
        foodType.getChildren().addAll(
                mainCourse,
                new ToggleMenuButton("Vegetable", foodTg)
        );
        children.add(foodType);
        setMargin(foodType, new Insets(sizes.pady));
        mainCourse.setSelected(true);

        foodTg.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                // reselect the old one if user tried to unselect everything
                oldVal.setSelected(true);
            }
        });

        weight.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1) {
        });
        children.add(weight);
        setMargin(weight, new Insets(sizes.pady));

        Button addFoodBtn = new Button("Add Food");
        addFoodBtn.setFont(sizes.mediumFont);
        addFoodBtn.setOnAction(e -> addFoodClicked());
        children.add(addFoodBtn);
        setMargin(addFoodBtn, new Insets(sizes.pady));
    }


    private void addFoodClicked(){
        for (int i = 0; i < weight.getValue(); i++) {
            if (this.foodName.getText().isEmpty() || !insert(conn, "foods",
                    new String[]{"name", "mealType", "foodType", "used"},
                    this.foodName.getText(),
                    getToggleGroupText(mealTg),
                    getToggleGroupText(foodTg),
                    0)) {
                // create failure popup
//                Popup popup = new Popup();
//                Label msg = new Label("Failed to add food!");
//                msg.setFont(sizes.mediumFont);
//                msg.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-padding: 10;");
//                popup.getContent().add(msg);
//                popup.setAutoHide(true);
//                popup.show(this.getScene().getWindow());
                return;
            }
        }
        foodName.clear();
        weight.getValueFactory().setValue(1);
        // create popup
    }
}
