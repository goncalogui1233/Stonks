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
import observables.AuthenticationObservable;
import observables.GoalsObservable;
import observables.ProfileObservable;
import observables.StonksObservable;
import views.AuthenticationView;
import views.GoalView;
import views.ProfileView;

public class Stonks extends Application implements Constants, PropertyChangeListener {
    private Stage window;
    private StonksData data;

    /*Scenes*/
    private Scene authScene;
    private Scene profileScene;
    private Scene goalScene;

    /*Observables*/
    private StonksObservable stonksObs;
    private AuthenticationObservable authObs;
    private ProfileObservable profileObs;
    private GoalsObservable goalsObs;

    /*Controllers*/
    private ProfileController cProfile;
    private DashboardController cDashboard;
    private GoalController cGoal;

    /*Views*/
    private AuthenticationView authenticationView;
    private ProfileView profileView;
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

//        populateApp(); /*Remove later*/
        //ProfileModel.setData(data);
        //WalletModel.setData(data);
        stonksObs = new StonksObservable(cProfile, data);
        authObs = new AuthenticationObservable(cProfile, stonksObs);
        profileObs = new ProfileObservable(cProfile, stonksObs);
        goalsObs = new GoalsObservable(cGoal, stonksObs);

        authenticationView = new AuthenticationView(authObs);
        profileView = new ProfileView(profileObs);
        goalView = new GoalView(goalsObs);

        authScene = new Scene(authenticationView.getRoot());
        profileScene = new Scene(profileView.getRoot());
        goalScene = new Scene(goalView.getRoot());

    }

    public void setupWindow() {
        window.setTitle(APP_TITLE);
        window.setResizable(false);
        window.setWidth(APP_WIDTH);
        window.setHeight(APP_HEIGHT);

        //window.setScene(new Scene(new ProfileView()));
        window.setScene(authScene);

        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
        StyleManager.getInstance().addUserAgentStylesheet("resources/StonksCSS.css");

        window.show();
    }

    public void setupPropertyChangeListeners() {
        stonksObs.addPropertyChangeListener(STONKS_EVENT.GOTO_GOAL_VIEW.name(), this);
        stonksObs.addPropertyChangeListener(STONKS_EVENT.GOTO_PROFILE_VIEW.name(), this);
        stonksObs.addPropertyChangeListener(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW.name(), this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_GOAL_VIEW.name())) {
            //goalView.displayIncompleteProfileGoals();/*CHECK LATER - GOAL VIEW CAN BE NOTIFIED BY STONKS OBS*/
            window.setScene(goalScene);
        } else if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_PROFILE_VIEW.name())) {
            window.setScene(profileScene);
        }else if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW.name())) {
            window.setScene(authScene);
        }
    }

    /*Remove later*/
    private void populateApp() {
        cProfile.createProfile("Ricardo",
                "Pereira",
                SECURITY_QUESTIONS.CHILDHOOD_NICKNAME.getQuestion(),
                "kekkek",
                "kekkek",
                "#d3d9f1");
    }
}
