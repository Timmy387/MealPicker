package GuiLayouts;
import GUIObjects.ToggleMenuButton;
import SQL.Connect;

import static SQL.rowChanges.setMealUsed;
import static SQL.rowReads.randomFood;
import static Utils.javaFXUtils.getToggleGroupText;
import static SQL.rowChanges.update;
import Utils.Sizes;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.util.Map;


public class FindFoodFrame extends VBox {
    private final Sizes sizes = Sizes.getSizes();
    private final TextArea result = new TextArea();
    private final Connection conn = Connect.getConnection();
    private final ToggleGroup foodTg = new ToggleGroup();
    private final ToggleGroup mealTg = new ToggleGroup();
    private final ToggleButton useFood = new ToggleButton("Choose\nthis meal");
    private Map<String, Object> meal;
    public FindFoodFrame(){
        ObservableList<Node> children = this.getChildren();
        setPadding(new Insets(sizes.padx));
        setAlignment(Pos.CENTER);

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

        Button findFood = new Button("Find Meal!");
        findFood.setFont(sizes.largeFont);
        findFood.setOnAction(e -> findMeal());
        children.add(findFood);
        result.setMaxWidth(sizes.width * 0.3);
        result.setMaxHeight(sizes.height * 0.3);
        result.setFont(sizes.largeFont);
        result.setWrapText(true);
        result.setDisable(true);
        children.add(result);

        useFood.setSelected(false);
        useFood.getStyleClass().add("selector-menu-button");
        useFood.setOnAction(e -> setMealUsed(conn, meal.get("id"), useFood.isSelected()));
        useFood.setFont(sizes.mediumFont);
    }


    private void findMeal(){
        if (!getChildren().contains(useFood)){
            getChildren().add(useFood);
        }
        useFood.setSelected(false);
        result.clear();
        meal = randomFood(conn, new String[]{"mealType", "foodType"},
                new Object[]{getToggleGroupText(mealTg), getToggleGroupText(foodTg)},
                false);
        int i = 0;
        for (String colName : meal.keySet()){
            if (colName.equals("name")) {
                result.insertText(i, colName);
                i += colName.length();
                result.insertText(i, ": ");
                i += 2;
                result.insertText(i, meal.get(colName).toString());
                i += meal.get(colName).toString().length();
                result.insertText(i, "\n");
            }
        }
    }
}
