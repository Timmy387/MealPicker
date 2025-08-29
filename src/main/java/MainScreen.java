import GuiLayouts.MainLayout;
import Utils.Settings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utils.Sizes;


public class MainScreen extends Application{

    private final Sizes sizes = Sizes.getSizes();

    @Override
    public void start(Stage stage){
        stage.setTitle("MealPicker");
        stage.setWidth(this.sizes.width);
        stage.setHeight(this.sizes.height);

        MainLayout mainLayout = new MainLayout();
        Scene scene = new Scene(mainLayout);
        Settings settings = Settings.getSettings();
        settings.setScene(scene);
        settings.setTheme("Dark");
        stage.setScene(scene);


        stage.show();
    }
}
