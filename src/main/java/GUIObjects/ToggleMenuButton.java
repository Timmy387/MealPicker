package GUIObjects;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ToggleMenuButton extends ToggleButton {
    public ToggleMenuButton(String name, ToggleGroup tg){
        super(name);
        setMaxWidth(Double.MAX_VALUE);
        getStyleClass().add("selector-menu-button");
        HBox.setHgrow(this, Priority.ALWAYS);
        setToggleGroup(tg);
    }
}
