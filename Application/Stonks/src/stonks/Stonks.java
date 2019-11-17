package stonks;

import controllers.DashboardController;
import controllers.GoalController;
import controllers.ProfileController;
import javafx.application.Application;
import javafx.stage.Stage;
import models.GoalModel;
import models.ProfileModel;
import models.WalletModel;

public class Stonks extends Application implements Constants{
    private Stage window;
    private StonksData data;    
    
    private ProfileController cProfile;
    private DashboardController cDashboard;
    private GoalController cGoal;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        
        setupApp();
        setupWindow();
    }
    
    public void setupApp(){
        data = new StonksData();
        
        cProfile = new ProfileController(data);
        cDashboard = new DashboardController(data);
        cGoal = new GoalController(data);
        
        ProfileModel.setData(data);
        GoalModel.setData(data);
        WalletModel.setData(data);
    }
    
    public void setupWindow(){
        window.setTitle(APP_TITLE);
        window.setResizable(false);
        window.setWidth(APP_WIDTH);
        window.setHeight(APP_HEIGHT);
        
        window.show();
    }
}
