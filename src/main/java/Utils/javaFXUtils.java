package Utils;

import javafx.scene.control.*;

public class javaFXUtils {
    public static String getToggleGroupText(ToggleGroup tg){
        Toggle button = tg.getSelectedToggle();
        if (button.isSelected()){
            if (button instanceof Button || button instanceof ToggleButton){
                return ((ButtonBase)button).getText();
            }
        }
        return "";
    }
}

