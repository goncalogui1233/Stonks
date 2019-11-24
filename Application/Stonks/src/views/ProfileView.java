package views;

import gui_components.LoginBox;
import gui_components.PasswordRecoveryBox;
import gui_components.RegisterBox;
import gui_components.SideProfileBar;
import javafx.scene.layout.HBox;
import stonks.Constants;

public class ProfileView implements Constants{
    private HBox root;
    private final RegisterBox registerContainer;
    private final LoginBox loginContainer;
    private final PasswordRecoveryBox recoverPasswordContainer;

    public ProfileView() {
        root = new HBox();
        
        root.setMinSize(APP_WIDTH, APP_HEIGHT);
        root.setMaxSize(APP_WIDTH, APP_HEIGHT);
        
        registerContainer = new RegisterBox();
        loginContainer = new LoginBox();
        recoverPasswordContainer = new PasswordRecoveryBox();
        
        root.getChildren().add(new SideProfileBar().getRoot());
        root.getChildren().add(registerContainer.getRoot());
    }

    public HBox getRoot() {
        return root;
    }
}
