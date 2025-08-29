package GuiLayouts;
import Utils.Sizes;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import GUIObjects.ToggleMenuButton;


public class MainLayout extends VBox {
    private Node currentScreen = null;
    private final Sizes sizes = Sizes.getSizes();
    private final double btnPadx = sizes.padx / 4;
    private final double btnPady = sizes.pady / 4;
    private final ToggleGroup tg = new ToggleGroup();
    public MainLayout(){

        HBox topMenu = new HBox();
        this.getChildren().add(topMenu);
        topMenu.setSpacing(btnPadx);
        topMenu.setPadding(new Insets(btnPady, btnPadx, btnPady, btnPadx));

        tg.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                // reselect the old one if user tried to unselect everything
                oldVal.setSelected(true);
            }
        });

        ToggleMenuButton findFoodBtn = new ToggleMenuButton("Find a meal", tg);
        findFoodBtn.setSelected(true);
        ToggleMenuButton addFoodBtn = new ToggleMenuButton("Add a Food", tg);
        ToggleMenuButton groceryListBtn = new ToggleMenuButton("Grocery List", tg);
        ToggleMenuButton settingsBtn = new ToggleMenuButton("Settings", tg);

        topMenu.getChildren().addAll(findFoodBtn, addFoodBtn, groceryListBtn, settingsBtn);

        StackPane screens = new StackPane();
        this.getChildren().add(screens);

        FindFoodFrame findFood = new FindFoodFrame();
        AddFoodFrame addFood = new AddFoodFrame();
        addFood.setVisible(false);
        GroceryListFrame groceryList = new GroceryListFrame();
        groceryList.setVisible(false);
        SettingsFrame settingsFrame = new SettingsFrame();
        settingsFrame.setVisible(false);
        screens.getChildren().addAll(findFood, addFood, groceryList, settingsFrame);

        findFoodBtn.setOnAction(e -> switchScreen(findFood));
        addFoodBtn.setOnAction(e -> switchScreen(addFood));
        groceryListBtn.setOnAction(e -> switchScreen(groceryList));
        settingsBtn.setOnAction(e -> switchScreen(settingsFrame));

        switchScreen(findFood);
    }


    private void switchScreen(Node screen){
        if (this.currentScreen != null){
            currentScreen.setVisible(false);
        }
        currentScreen = screen;
        screen.setVisible(true);
    }

}
