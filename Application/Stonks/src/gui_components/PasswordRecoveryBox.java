package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import observables.AuthenticationObservable;
import stonks.Constants;

public class PasswordRecoveryBox implements Constants, PropertyChangeListener{
    private final BorderPane root;
    private final AuthenticationObservable authObs;
    
    //Containers
    private BorderPane authBox;
    private VBox titleContainer;
    private VBox formContainer;
    private BorderPane optionContainer;

    //Input Divs
    private VBox securityDiv;
    
    //Title Labels
    private Label lblTitle;
    private Label lblUserName;
    private Label lblSecurityQuestion;
    
    //Field Inputs
    private TextField txtSecurityAnswer;

    //Error Labels
    private Label errorSecurityAnswer;
    
    //Buttons
    private Button btnRecover;

    
    public PasswordRecoveryBox(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new BorderPane();
        
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        
        setupRecoverPasswordForm();
        setupEventListeners();
        setupPropertyChangeListeners();
        setupInputFieldsListeners();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupInputFieldsListeners(){
        txtSecurityAnswer.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtSecurityAnswer.setText(txtSecurityAnswer.getText().replaceAll("\\s+", " ").trim());
                }
            }
        });
    }
    
    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_RECOVER_PASSWORD.name(), this);
    }
    
    private int validateInputs(){
        int errorCounter = 0;

        switch(authObs.verifyData(PROFILE_FIELD.SECURITY_ANSWER, txtSecurityAnswer.getText())){
            case EMPTY:
                errorSecurityAnswer.setText("Security answer cannot be empty");
                errorSecurityAnswer.setVisible(true);
                errorCounter++;
                break;
            case MAX_CHAR:
                errorSecurityAnswer.setText("Security answer has a maximum of 50 characters");
                errorSecurityAnswer.setVisible(true);
                errorCounter++;
                break;
            default:
                errorSecurityAnswer.setVisible(false);
                break;
        }
        
        return errorCounter;
    }
    
    private void setupEventListeners(){
        btnRecover.setOnMouseClicked(e -> {
            if(validateInputs() == 0){
                String password = authObs.recoverPassword(txtSecurityAnswer.getText());

                if(password != null){
                    DBOX_CONTENT.SUCCESS_RECOVER_PASSWORD.setTextExtra(password);
                    DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_RECOVER_PASSWORD);
                }else{
                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_RECOVER_PASSWORD);
                }
            }
        });
    }
    
    private void setupRecoverPasswordForm(){
        authBox = new BorderPane();
        authBox.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        authBox.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_RECOVER_PASSWORD_HEIGHT);
        
        lblTitle = new Label("Recover Password");
        lblUserName = new Label();
        lblUserName.setWrapText(true);
        lblUserName.setAlignment(Pos.CENTER);//CHECK
        titleContainer = new VBox();
        
        formContainer = new VBox();
        
        lblSecurityQuestion = new Label();
        lblSecurityQuestion.setWrapText(true);
        lblSecurityQuestion.setTextAlignment(TextAlignment.JUSTIFY);
        
        txtSecurityAnswer = new TextField();
        
        btnRecover = new Button("Recover");
        
        optionContainer = new BorderPane();
        
        /*Initialize error labels*/
        errorSecurityAnswer = new Label("errorSecurityAnswer");
        errorSecurityAnswer.setVisible(false);
        
        /*Initialize divs*/
        securityDiv = new VBox();
        
        /*Add the title to the title box*/
        titleContainer.getChildren().addAll(lblTitle, lblUserName);
        
        /*Add all labels and inputs to the form box*/
        securityDiv.getChildren().addAll(lblSecurityQuestion, txtSecurityAnswer, errorSecurityAnswer);
        formContainer.getChildren().addAll(securityDiv);
        
        /*Add the button to the button box*/
        optionContainer.setRight(btnRecover);
        
        /*Add title on top, formContainer on center, button on bottom*/
        authBox.setTop(titleContainer);
        authBox.setCenter(formContainer);
        authBox.setBottom(optionContainer);
        
        /*Set CSS ID's to nodes*/
        authBox.setId("authBox");
        lblUserName.setId("titleProfile");
        
        /*Set CSS Classes to nodes*/
        authBox.getStyleClass().add("stonks-box");
        formContainer.getStyleClass().add("form");
        titleContainer.getStyleClass().add("titleBox");
        
        securityDiv.getStyleClass().addAll("fieldDiv");
        lblSecurityQuestion.getStyleClass().add("fieldTitle");
        txtSecurityAnswer.getStyleClass().add("fieldInput");
        errorSecurityAnswer.getStyleClass().addAll("fieldError");
        
        btnRecover.getStyleClass().addAll("button", "btn-default", "btn-form");
        
        /*Add login container into the root pane*/
        root.setCenter(authBox);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_RECOVER_PASSWORD.name())){
            lblUserName.setText(
                    authObs.getProfile(authObs.getViewSelectedProfileId()).getFirstName() 
                    + " "
                    + authObs.getProfile(authObs.getViewSelectedProfileId()).getLastName());
            lblSecurityQuestion.setText(authObs.getProfile(authObs.getViewSelectedProfileId()).getSecurityQuestion());
            resetFields();
        }
    }

    private void resetFields() {
        txtSecurityAnswer.setText("");
        errorSecurityAnswer.setVisible(false);
    }
}
