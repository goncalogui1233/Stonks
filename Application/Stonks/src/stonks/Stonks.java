package stonks;

import com.sun.javafx.css.StyleManager;
import controllers.DashboardController;
import controllers.GoalController;
import controllers.ProfileController;
import gui_components.DialogBox;
import gui_components.SideMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.GoalModel;
import models.ProfileModel;
import models.WalletModel;
import views.ProfileEditView;
import views.ProfileLoginView;
import views.ProfileRecoverPasswordView;
import views.ProfileRegisterView;

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
        
        /*DialogBox test - REMOVE LATER*/
        DBOX_CONTENT.CONFIRM_DELETE_PROFILE.setExtra("User 1");
        
        //window.setScene(new Scene(new ProfileEditView()));
        window.setScene(new Scene(new ProfileRegisterView()));
        
        //System.out.println("DBOX_RETURN = " + DialogBox.display(DBOX_TYPE.CONFIRM, DBOX_CONTENT.CONFIRM_DELETE_PROFILE));
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
        
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        StyleManager.getInstance().addUserAgentStylesheet("resources/StonksCSS.css");
        
        window.show();
    }
}
