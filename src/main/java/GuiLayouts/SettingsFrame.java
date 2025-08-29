package GuiLayouts;
import Utils.Settings;
import Utils.Sizes;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;


public class SettingsFrame extends VBox {
    private final Settings settings = Settings.getSettings();
    private final Sizes sizes = Sizes.getSizes();
    public SettingsFrame() {
        ObservableList<Node> children = this.getChildren();
        setPadding(new Insets(sizes.padx));
        setAlignment(Pos.CENTER);

        Label themeLabel = new Label("Select Theme:");
        children.add(themeLabel);

        ToggleGroup themeTg = new ToggleGroup();
        HBox themeBox = new HBox();
        themeBox.setMaxWidth(sizes.width * 0.3);
        themeBox.setSpacing(10);
        children.add(themeBox);

        ToggleMenuButton lightMode = new ToggleMenuButton("Light", themeTg);

        ToggleMenuButton darkMode = new ToggleMenuButton("Dark", themeTg);
        darkMode.setSelected(true);

        themeBox.getChildren().addAll(lightMode, darkMode);

        themeTg.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> {
            if (newValue != null) {
                ToggleButton selectedBtn = (ToggleButton) newValue;
                switchMode(selectedBtn.getText());
            }
        });
    }

    public void switchMode(String mode) {
        settings.setTheme(mode);
    }

    private static class ToggleMenuButton extends ToggleButton{
        public ToggleMenuButton(String name, ToggleGroup tg){
            super(name);
            setMaxWidth(Double.MAX_VALUE);
            getStyleClass().add("selector-menu-button");
            HBox.setHgrow(this, Priority.ALWAYS);
            setToggleGroup(tg);
        }
    }
}
