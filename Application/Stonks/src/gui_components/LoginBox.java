package gui_components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import observables.AuthenticationObservable;
import stonks.Constants;

public class LoginBox implements Constants{
    private final BorderPane root;
    private AuthenticationObservable authObs;

    public LoginBox(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new BorderPane();
        
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        
        setupLoginForm();
    }

    private void setupLoginForm(){
        //Containers
        VBox formContainer;
        BorderPane bpLoginRoot;
        HBox hbTitle;
        BorderPane bpOption;
        
        //Title Labels
        Label lblTitle;
        Label lblPassword;

        //Label Buttons
        Label btnForgotPassword;
        Label btnSignIn;   

        //Text Field
        TextField txtPassword;
        
        bpLoginRoot = new BorderPane();
        bpLoginRoot.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        bpLoginRoot.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_LOGIN_HEIGHT);
        
        lblTitle = new Label("Login as");
        hbTitle = new HBox();
        
        formContainer = new VBox();
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        
        btnForgotPassword = new Label("I forgot my password");
        btnSignIn = new Label("Sign in");
        
        bpOption = new BorderPane();
        
        /*Add the title to the title box*/
        hbTitle.getChildren().add(lblTitle);
        
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

    public BorderPane getRoot() {
        return root;
    }
}
