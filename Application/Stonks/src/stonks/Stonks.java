package stonks;

import javafx.application.Application;
import javafx.stage.Stage;
import models.ProfileModel;

public class Stonks extends Application implements Constants{
    private Stage window;
        
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        
        setupWindow();
    }
    
    public void setupWindow(){
        window.setTitle(APP_TITLE);
        window.setResizable(false);
        window.setWidth(APP_WIDTH);
        window.setHeight(APP_HEIGHT);
        
        window.show();
    }
}
