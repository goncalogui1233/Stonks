package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import observables.AuthenticationObservable;
import stonks.Constants;

public class WelcomeBox implements Constants, PropertyChangeListener {

    private final BorderPane root;
    private final AuthenticationObservable authObs;

    //Containers
    private BorderPane authBox;
    private VBox messageDiv;

    //Title Labels
    private Label lblWelcome;
    private Label lblSubMessage;

    public WelcomeBox(AuthenticationObservable authObs) {
        this.authObs = authObs;

        root = new BorderPane();

        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);

        setupWelcomeBox();
        setupPropertyChangeListeners();
        updateMessage();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupWelcomeBox() {
        authBox = new BorderPane();
        authBox.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        authBox.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_LOGIN_HEIGHT);

        messageDiv = new VBox();

        lblWelcome = new Label(AUTH_WELCOME_MESSAGE);
        lblWelcome.setWrapText(true);

        lblSubMessage = new Label();
        lblSubMessage.setWrapText(true);

        /*Add both message labels into their div*/
        messageDiv.getChildren().addAll(lblWelcome, lblSubMessage);

        /*Add message on center*/
        authBox.setCenter(messageDiv);

        /*Set CSS ID's to nodes*/
        authBox.setId("authBox");
        messageDiv.setId("messageDiv");
        lblWelcome.setId("welcomeMessage");
        lblSubMessage.setId("subMessage");

        /*Set CSS Classes to nodes*/
        authBox.getStyleClass().add("stonks-box");

        /*Add login container into the root pane*/
        root.setCenter(authBox);
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.CREATE_PROFILE.name(), this);
        authObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW.name(), this);
    }

    private void updateMessage(){
        if(authObs.hasNoProfiles()){
            lblSubMessage.setText(AUTH_WELCOME_SUBMESSAGE_WITHOUT_PROFILES);
        }else{
            lblSubMessage.setText(AUTH_WELCOME_SUBMESSAGE_WITH_PROFILES);
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        updateMessage();
    }
}
