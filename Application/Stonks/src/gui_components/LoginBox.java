package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import observables.AuthenticationObservable;
import stonks.Constants;

public class LoginBox implements Constants, PropertyChangeListener{
    private final BorderPane root;
    private final AuthenticationObservable authObs;
    
    //Containers
    private BorderPane authBox;
    private VBox hbTitle;
    private VBox formContainer;
    private BorderPane bpOption;
    
    //Input Divs
    private VBox passwordDiv;

    //Title Labels
    private Label lblTitle;
    private Label lblUserName;
    private Label lblPassword;

    //Field Inputs
    private TextField txtPassword;
    
    //Error Labels
    private Label errorPassword;

    //Buttons
    private Button btnSignIn;   
    
    //Links
    private Label linkForgotPassword;

    public LoginBox(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new BorderPane();
        
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        
        setupLoginForm();
        setupPropertyChangeListeners();
        setupEventListeners();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_LOGIN.name(), this);
    }
    
    private int validateInputs(){
        int errorCounter = 0;

        switch(authObs.verifyData(PROFILE_FIELD.PASSWORD, txtPassword.getText())){
            case EMPTY:
                errorPassword.setText("Password cannot be empty");
                errorPassword.setVisible(true);
                errorCounter++;
                break;
            default:
                errorPassword.setVisible(false);
                break;
        }
        
        return errorCounter;
    }
    
    private void setupEventListeners(){
        btnSignIn.setOnMouseClicked(e -> {
            if(validateInputs() == 0){
                if(!authObs.loginProfile(authObs.getViewSelectedProfileId(), txtPassword.getText())){
                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_LOGIN);
                }
            }
        });
    }

    private void setupLoginForm(){
        authBox = new BorderPane();
        authBox.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        authBox.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_LOGIN_HEIGHT);
        
        lblTitle = new Label("Login as ");
        lblUserName = new Label();
        lblUserName.setWrapText(true);
        lblUserName.setAlignment(Pos.CENTER);//CHECK
        hbTitle = new VBox();
        
        formContainer = new VBox();
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        
        linkForgotPassword = new Label("I forgot my password");
        linkForgotPassword.setOnMouseClicked(e -> {
            authObs.recoverPasswordClicked();
        });
        btnSignIn = new Button("Sign in");
        bpOption = new BorderPane();

        /*Initialize error labels*/
        errorPassword = new Label("errorPassword");
        errorPassword.setVisible(false);
        
        /*Initialize divs*/
        passwordDiv = new VBox();
        
        /*Add the title to the title box*/
        hbTitle.getChildren().addAll(lblTitle, lblUserName);
        
        /*Add all labels and inputs to the form box*/
        passwordDiv.getChildren().addAll(lblPassword, txtPassword, errorPassword);
        formContainer.getChildren().addAll(passwordDiv);      
        
        /*Add recover password label and button to the button box*/
        bpOption.setLeft(linkForgotPassword);
        bpOption.setRight(btnSignIn);
        
        /*Add title on top, formContainer on center, label and sign-in button on bottom*/
        authBox.setTop(hbTitle);
        authBox.setCenter(formContainer);
        authBox.setBottom(bpOption);
        
        /*Set CSS ID's to nodes*/
        authBox.setId("authBox");
        lblUserName.setId("titleProfile");
        
        /*Set CSS Classes to nodes*/
        authBox.getStyleClass().add("box");
        formContainer.getStyleClass().add("form");
        hbTitle.getStyleClass().add("titleBox");
        
        passwordDiv.getStyleClass().addAll("fieldDiv");
        lblPassword.getStyleClass().add("fieldTitle");
        txtPassword.getStyleClass().add("fieldInput");
        errorPassword.getStyleClass().addAll("fieldError");
        
        linkForgotPassword.getStyleClass().add("link");
        btnSignIn.getStyleClass().addAll("button", "btn-default", "btn-form");
        
        /*Add login container into the root pane*/
        root.setCenter(authBox);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_LOGIN.name())){lblUserName.setText(
                    authObs.getProfile(authObs.getViewSelectedProfileId()).getFirstName() 
                    + " "
                    + authObs.getProfile(authObs.getViewSelectedProfileId()).getLastName());
            resetFields();
        }
    }

    private void resetFields() {
        txtPassword.setText("");
        errorPassword.setVisible(false);
    }
}
