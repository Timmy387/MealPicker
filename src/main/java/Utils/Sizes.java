package Utils;

import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class Sizes {
    private static final Sizes instance = new Sizes();

    public static Sizes getSizes(){
        return instance;
    }

    private final Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    public final double screenWidth = screenBounds.getWidth();
    public final double screenHeight = screenBounds.getHeight();

    public final double padx = screenWidth / 100;
    public final double pady = screenHeight / 100;
    public final double width = screenWidth / 2;
    public final double height = screenHeight / 2;

    public final Font smallFont = new Font("Roboto", height / 40);
    public final Font mediumFont = new Font("Roboto", height / 36);
    public final Font largeFont = new Font("Roboto", height / 32);
    public final Font subtitleFont = new Font("Roboto", height / 25);
    public final Font titleFont = new Font("Roboto", height / 20);
}
