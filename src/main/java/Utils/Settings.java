package Utils;

import javafx.scene.Scene;

import java.util.Objects;

public class Settings {
    private String theme = "Light";
    private Scene scene;

    private static final Settings instance = new Settings();
    private Settings() {}

    public void setScene(Scene scene) {
        this.scene = scene;
        setTheme(this.theme);
    }

    public static Settings getSettings() {
        return instance;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
        if (theme.equals("Light")) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/lightMode.css")).toExternalForm());
        } else if (theme.equals("Dark")) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/darkMode.css")).toExternalForm());
        }
    }
}
