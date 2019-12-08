package views;

import gui_components.LoginBox;
import gui_components.PasswordRecoveryBox;
import gui_components.RegisterBox;
import gui_components.SideProfileBar;
import gui_components.WelcomeBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.scene.layout.HBox;
import observables.AuthenticationObservable;
import stonks.Constants;

public class AuthenticationView implements Constants, PropertyChangeListener{
    private final HBox root;
    private final AuthenticationObservable authObs;
    
    private SideProfileBar sideProfileBarContainer;
    private WelcomeBox welcomeContainer;
    private RegisterBox registerContainer;
    private LoginBox loginContainer;
    private PasswordRecoveryBox recoverPasswordContainer;
    
    public AuthenticationView(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new HBox();
        
        root.setMinSize(APP_WIDTH, APP_HEIGHT);
        root.setMaxSize(APP_WIDTH, APP_HEIGHT);
        
        setupContainers();
        setupPropertyChangeListeners();
    }

    public HBox getRoot() {
        return root;
    }
    
    private void setupContainers(){
        sideProfileBarContainer = new SideProfileBar(authObs);
        welcomeContainer = new WelcomeBox(authObs);
        registerContainer = new RegisterBox(authObs);
        loginContainer = new LoginBox(authObs);
        recoverPasswordContainer = new PasswordRecoveryBox(authObs);
        
        root.getChildren().add(sideProfileBarContainer.getRoot());
        root.getChildren().add(welcomeContainer.getRoot());
    }
    
    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_LOGIN.name(), this);
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_REGISTER.name(), this);
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_RECOVER_PASSWORD.name(), this);
        authObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW.name(), this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        /*Since this method is only called when a GOTO event is thrown, this is allowed*/
        root.getChildren().removeAll(root.getChildren());
        root.getChildren().add(sideProfileBarContainer.getRoot());
        
        if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_LOGIN.name())){
            root.getChildren().add(loginContainer.getRoot());
        }else if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_REGISTER.name())){
            root.getChildren().add(registerContainer.getRoot());
        }else if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_RECOVER_PASSWORD.name())){
            root.getChildren().add(recoverPasswordContainer.getRoot());
        }else if(evt.getPropertyName().equals(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW.name())){
            root.getChildren().add(welcomeContainer.getRoot());
        }
    }
}
