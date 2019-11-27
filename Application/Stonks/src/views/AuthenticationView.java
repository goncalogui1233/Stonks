package views;

import gui_components.LoginBox;
import gui_components.PasswordRecoveryBox;
import gui_components.RegisterBox;
import gui_components.SideProfileBar;
import javafx.scene.layout.HBox;
import observables.AuthenticationObservable;
import stonks.Constants;

public class AuthenticationView implements Constants{
    private HBox root;
    private AuthenticationObservable authObs;
    
    private final RegisterBox registerContainer;
    private final LoginBox loginContainer;
    private final PasswordRecoveryBox recoverPasswordContainer;
    
    public AuthenticationView(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new HBox();
        
        root.setMinSize(APP_WIDTH, APP_HEIGHT);
        root.setMaxSize(APP_WIDTH, APP_HEIGHT);
        
        registerContainer = new RegisterBox(authObs);
        loginContainer = new LoginBox(authObs);
        recoverPasswordContainer = new PasswordRecoveryBox(authObs);
        
        root.getChildren().add(new SideProfileBar(authObs).getRoot());
        root.getChildren().add(registerContainer.getRoot());
    }

    public HBox getRoot() {
        return root;
    }
}
