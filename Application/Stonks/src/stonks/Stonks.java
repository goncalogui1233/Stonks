package stonks;

import com.sun.javafx.css.StyleManager;
import controllers.DashboardController;
import controllers.GoalController;
import controllers.ProfileController;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.GoalModel;
import models.ProfileModel;
import models.WalletModel;
import observables.AuthenticationObservable;
import observables.GoalsObservable;
import observables.StonksObservable;
import views.AuthenticationView;
import views.GoalView;

public class Stonks extends Application implements Constants, PropertyChangeListener {

    private Stage window;
    private StonksData data;

    private StonksObservable stonksObs;
    private AuthenticationObservable authObs;
    private GoalsObservable goalsObs;

    private ProfileController cProfile;
    private DashboardController cDashboard;
    private GoalController cGoal;

    private AuthenticationView authenticationView;
    private GoalView goalView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;

        setupApp();
        setupWindow();
        setupPropertyChangeListeners();

        /*DialogBox test - REMOVE LATER*/
        //DBOX_CONTENT.CONFIRM_DELETE_PROFILE.setSubExtra("User 1");
        //System.out.println("DBOX_RETURN = " + DialogBox.display(DBOX_TYPE.CONFIRM, DBOX_CONTENT.CONFIRM_DELETE_PROFILE));
    }

    public void setupApp() {
        data = new StonksData();
        data = data.loadDatabase();

        cProfile = new ProfileController(data);
        cDashboard = new DashboardController(data);
        cGoal = new GoalController(data);

        ProfileModel.setData(data);
        GoalModel.setData(data);
        WalletModel.setData(data);

        stonksObs = new StonksObservable(data);
        authObs = new AuthenticationObservable(cProfile, stonksObs);
        goalsObs = new GoalsObservable(cGoal, stonksObs);

        authenticationView = new AuthenticationView(authObs);
        goalView = new GoalView(goalsObs);
    }

    public void setupWindow() {
        window.setTitle(APP_TITLE);
        window.setResizable(false);
        window.setWidth(APP_WIDTH);
        window.setHeight(APP_HEIGHT);

        //window.setScene(new Scene(new ProfileView()));
        window.setScene(new Scene(authenticationView.getRoot()));

        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        StyleManager.getInstance().addUserAgentStylesheet("resources/StonksCSS.css");

        window.show();
    }

    public void setupPropertyChangeListeners() {
        stonksObs.addPropertyChangeListener(STONKS_EVENT.GOTO_GOAL_VIEW.name(), this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_GOAL_VIEW.name())) {
            goalView.displayProfileGoals();
            window.setScene(new Scene(goalView.getRoot()));

        }
    }
}
