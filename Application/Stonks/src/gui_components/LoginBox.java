package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import observables.AuthenticationObservable;
import stonks.Constants;

public class LoginBox implements Constants, PropertyChangeListener{
    private final BorderPane root;
    private final AuthenticationObservable authObs;
    
    //Containers
    private VBox formContainer;
    private BorderPane bpLoginRoot;
    private VBox hbTitle;
    private BorderPane bpOption;

    //Title Labels
    private Label lblTitle;
    private Label lblUserName;
    private Label lblPassword;

    //Label Buttons
    private Label btnForgotPassword;
    private Label btnSignIn;   

    //Text Field
    private TextField txtPassword;

    public LoginBox(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new BorderPane();
        
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        
        setupLoginForm();
        setupPropertyChangeListeners();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_LOGIN.name(), this);
    }

    private void setupLoginForm(){
        bpLoginRoot = new BorderPane();
        bpLoginRoot.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        bpLoginRoot.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_LOGIN_HEIGHT);
        
        lblTitle = new Label("Login as ");
        lblUserName = new Label();
        lblUserName.setWrapText(true);
        hbTitle = new VBox();
        
        formContainer = new VBox();
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        
        btnForgotPassword = new Label("I forgot my password");
        btnForgotPassword.setOnMouseClicked(e -> {
            authObs.recoverPasswordClicked();
        });
        btnSignIn = new Label("Sign in");
        
        bpOption = new BorderPane();
        
        /*Add the title to the title box*/
        hbTitle.getChildren().addAll(lblTitle, lblUserName);
        
        /*Add label and input to the form box*/
        formContainer.getChildren().addAll(lblPassword,txtPassword);      
        
        /*Add recover password label and button to the button box*/
        bpOption.setLeft(btnForgotPassword);
        bpOption.setRight(btnSignIn);
        
        /*Add title on top, formContainer on center, label and sign-in button on bottom*/
        bpLoginRoot.setTop(hbTitle);
        bpLoginRoot.setCenter(formContainer);
        bpLoginRoot.setBottom(bpOption);
        
        /*Set CSS ID's to nodes*/
        bpLoginRoot.setId("loginRoot");
        formContainer.setId("loginVbox");
        
        /*Set CSS Classes to nodes*/
        lblTitle.getStyleClass().add("TitleLabel");
        lblUserName.getStyleClass().add("UsernameLabel");
        lblPassword.getStyleClass().add("FormLabel");
        txtPassword.getStyleClass().add("textFieldInput");
        btnForgotPassword.getStyleClass().add("forgotPassword_btn");
        btnSignIn.getStyleClass().add("signIn_btn");
        btnForgotPassword.getStyleClass().add("forgotPassword_btn");
        btnSignIn.getStyleClass().add("labelButton");
        hbTitle.getStyleClass().add("hbTitle");
        
        /*Add login container into the root pane*/
        root.setCenter(bpLoginRoot);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_LOGIN.name())){
            lblUserName.setText(
                    authObs.getProfile(authObs.getViewSelectedProfileId()).getFirstName() 
                    + " "
                    + authObs.getProfile(authObs.getViewSelectedProfileId()).getLastName());
            resetFields();
        }
    }

    private void resetFields() {
        txtPassword.setText("");
    }
}
