package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import observables.AuthenticationObservable;
import stonks.Constants;

public class RegisterBox implements Constants, PropertyChangeListener{
    private final BorderPane root;
    private final AuthenticationObservable authObs;

    //Containers
    private BorderPane authBox;
    private HBox hbTitle;
    private VBox formContainer;
    private BorderPane hbSignUp;

    //Input Divs
    private VBox firstNameDiv;
    private VBox lastNameDiv;      
    private VBox passwordDiv;
    private VBox securityQuestionDiv;
    private VBox securityAnswerDiv;
    private VBox colorDiv;
    
    //Title Labels
    private Label lblTitle;
    private Label lblFirstName;
    private Label lblLastName;
    private Label lblPassword;
    private Label lblSecurtyQuestion;
    private Label lblSecurtyAnswer;
    private Label lblColor;

    //Field Inputs
    private TextField txfFirstName;
    private TextField txfLastName;
    private TextField txfPassword;
    private TextField txfSecurityAnswer;
    private ChoiceBox cbSecurityQuestion;
    private ColorPicker cpPickColor;
    
    //Error Labels
    private Label errorFirstName;
    private Label errorLastName;
    private Label errorPassword;
    private Label errorSecurityQuestion;
    private Label errorSecurityAnswer;
    private Label errorColor;

    //Buttons
    private Button btnSignUp;

    public RegisterBox(AuthenticationObservable authObs) {
        this.authObs = authObs;

        root = new BorderPane();
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);

        setupRegisterForm();
        setupEventListeners();
        setupPropertyChangeListeners();
    }

    public Pane getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_REGISTER.name(), this);
    }

    private void setupRegisterForm() {
        authBox = new BorderPane();
        authBox.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        authBox.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_REGISTER_HEIGHT);

        lblTitle = new Label("Register");
        hbTitle = new HBox();

        formContainer = new VBox();

        lblFirstName = new Label("First Name");
        txfFirstName = new TextField();

        lblLastName = new Label("Last Name");
        txfLastName = new TextField();

        lblPassword = new Label("Password");
        txfPassword = new TextField();

        lblSecurtyQuestion = new Label("Security Question");

        ObservableList<String> questions = FXCollections.observableArrayList();

        for (SECURITY_QUESTIONS quest : SECURITY_QUESTIONS.values()) {
            questions.add(quest.getQuestion());
        }

        cbSecurityQuestion = new ChoiceBox(questions);
        cbSecurityQuestion.setValue(SECURITY_QUESTIONS.PROTOTYPE.getQuestion());
        cbSecurityQuestion.setMinWidth(PROFILE_AUTH_BOX_WIDTH - 40);
        cbSecurityQuestion.setMaxWidth(PROFILE_AUTH_BOX_WIDTH - 40);

        lblSecurtyAnswer = new Label("Security Answer");
        txfSecurityAnswer = new TextField();

        lblColor = new Label("Color");
        cpPickColor = new ColorPicker();
        cpPickColor.setMinWidth(PROFILE_AUTH_BOX_WIDTH - 40);
        cpPickColor.setMaxWidth(PROFILE_AUTH_BOX_WIDTH - 40);

        hbSignUp = new BorderPane();
        btnSignUp = new Button("Sign Up");

        /*Initialize error labels*/
        errorFirstName = new Label("errorFirstName");
        errorFirstName.setVisible(false);
        errorLastName = new Label("errorLastName");
        errorLastName.setVisible(false);
        errorPassword = new Label("errorPassword");
        errorPassword.setVisible(false);
        errorSecurityQuestion = new Label("errorSecurityQuestion");
        errorSecurityQuestion.setVisible(false);
        errorSecurityAnswer = new Label("errorSecurityAnswer");
        errorSecurityAnswer.setVisible(false);
        errorColor = new Label("errorColor");
        errorColor.setVisible(false);
        
        /*Initialize divs*/
        firstNameDiv = new VBox();
        lastNameDiv = new VBox();
        passwordDiv = new VBox();
        securityQuestionDiv = new VBox();
        securityAnswerDiv = new VBox();
        colorDiv = new VBox();
        
        /*Add the title to the title box*/
        hbTitle.getChildren().add(lblTitle);
        
        /*Add all labels and inputs to the form box*/
        firstNameDiv.getChildren().addAll(lblFirstName, txfFirstName, errorFirstName);
        lastNameDiv.getChildren().addAll(lblLastName, txfLastName, errorLastName);
        passwordDiv.getChildren().addAll(lblPassword, txfPassword, errorPassword);
        securityQuestionDiv.getChildren().addAll(lblSecurtyQuestion, cbSecurityQuestion, errorSecurityQuestion);
        securityAnswerDiv.getChildren().addAll(lblSecurtyAnswer, txfSecurityAnswer, errorSecurityAnswer);
        colorDiv.getChildren().addAll(lblColor, cpPickColor, errorColor);
        formContainer.getChildren().addAll(firstNameDiv, lastNameDiv, passwordDiv, securityQuestionDiv, securityAnswerDiv, colorDiv);
        
        /*Add the button to the button box*/
        hbSignUp.setRight(btnSignUp);

        /*Add title on top, formContainer on center, sign-up button on bottom*/
        authBox.setTop(hbTitle);
        authBox.setCenter(formContainer);
        authBox.setBottom(hbSignUp);

        /*Set CSS ID's to nodes*/
        authBox.setId("authBox");
        cbSecurityQuestion.setId("securityQuestion");
        cpPickColor.setId("colorPicker");

        /*Set CSS Classes to nodes*/
        authBox.getStyleClass().add("box");
        formContainer.getStyleClass().add("form");
        hbTitle.getStyleClass().add("titleBox");
        
        firstNameDiv.getStyleClass().addAll("fieldDiv");
        lastNameDiv.getStyleClass().addAll("fieldDiv");
        passwordDiv.getStyleClass().addAll("fieldDiv");
        securityQuestionDiv.getStyleClass().addAll("fieldDiv");
        securityAnswerDiv.getStyleClass().addAll("fieldDiv");
        colorDiv.getStyleClass().addAll("fieldDiv");
        
        lblFirstName.getStyleClass().add("fieldTitle");
        lblLastName.getStyleClass().add("fieldTitle");
        lblPassword.getStyleClass().add("fieldTitle");
        lblSecurtyQuestion.getStyleClass().add("fieldTitle");
        lblSecurtyAnswer.getStyleClass().add("fieldTitle");
        lblColor.getStyleClass().add("fieldTitle");
        
        txfFirstName.getStyleClass().add("fieldInput");
        txfLastName.getStyleClass().add("fieldInput");
        txfPassword.getStyleClass().add("fieldInput");
        cbSecurityQuestion.getStyleClass().add("fieldInput");
        txfSecurityAnswer.getStyleClass().add("fieldInput");
        cpPickColor.getStyleClass().add("fieldInput");
        
        errorFirstName.getStyleClass().addAll("fieldError");
        errorLastName.getStyleClass().addAll("fieldError");
        errorPassword.getStyleClass().addAll("fieldError");
        errorSecurityQuestion.getStyleClass().addAll("fieldError");
        errorSecurityAnswer.getStyleClass().addAll("fieldError");
        errorColor.getStyleClass().addAll("fieldError");
        
        btnSignUp.getStyleClass().addAll("button", "btn-default", "btn-form");

        /*Add register container into the root pane*/
        root.setCenter(authBox);
    }

    private int validateInputs(){
        int errorCounter = 0;

        switch(authObs.verifyData(PROFILE_FIELD.FIRST_NAME, txfFirstName.getText())){
            case EMPTY:
                errorFirstName.setText("First Name cannot be empty");
                errorFirstName.setVisible(true);
                errorCounter++;
                break;
            case MAX_CHAR:
                errorFirstName.setText("First Name has a maximum of 50 characters");
                errorFirstName.setVisible(true);
                errorCounter++;
                break;
            default:
                errorFirstName.setVisible(false);
                break;
        }

        switch(authObs.verifyData(PROFILE_FIELD.LAST_NAME, txfLastName.getText())){
            case EMPTY:
                errorLastName.setText("Last Name cannot be empty");
                errorLastName.setVisible(true);
                errorCounter++;
                break;
            case MAX_CHAR:
                errorLastName.setText("Last Name has a maximum of 50 characters");
                errorLastName.setVisible(true);
                errorCounter++;
                break;
            default:
                errorLastName.setVisible(false);
                break;
        }

        switch(authObs.verifyData(PROFILE_FIELD.PASSWORD, txfPassword.getText())){
            case MIN_CHAR:
                errorPassword.setText("Password has a minimum of 6 characters");
                errorPassword.setVisible(true);
                errorCounter++;
                break;
            case MAX_CHAR:
                errorPassword.setText("Password has a maximum of 50 characters");
                errorPassword.setVisible(true);
                errorCounter++;
                break;
            default:
                errorPassword.setVisible(false);
                break;
        }

        switch(authObs.verifyData(PROFILE_FIELD.SECURITY_QUESTION, cbSecurityQuestion.getValue().toString())){
            case INVALID_QUESTION:
                errorSecurityQuestion.setText("Select a question");
                errorSecurityQuestion.setVisible(true);
                errorCounter++;
                break;
            default:
                errorSecurityQuestion.setVisible(false);
                break;
        }

        switch(authObs.verifyData(PROFILE_FIELD.SECURITY_ANSWER, txfSecurityAnswer.getText())){
            case EMPTY:
                errorSecurityAnswer.setText("Security Answer cannot be empty");
                errorSecurityAnswer.setVisible(true);
                errorCounter++;
                break;
            case MAX_CHAR:
                errorSecurityAnswer.setText("Security Answer has a maximum of 50 characters");
                errorSecurityAnswer.setVisible(true);
                errorCounter++;
                break;
            default:
                errorSecurityAnswer.setVisible(false);
                break;
        }

        switch (authObs.verifyData(PROFILE_FIELD.COLOR, String.format("#%02X%02X%02X", (int) (cpPickColor.getValue().getRed() * 255), (int) (cpPickColor.getValue().getGreen() * 255), (int) (cpPickColor.getValue().getBlue() * 255)))) {
            case FORMAT:
                errorColor.setText("Select a color");
                errorColor.setVisible(true);
                errorCounter++;
                break;
            default:
                errorColor.setVisible(false);
                break;
        }
        
        return errorCounter;
    }
    
    private void setupEventListeners() {
        btnSignUp.setOnMouseClicked(e -> {
            if (authObs.hasMaxProfiles()) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_PROFILE_LIMIT);
                return;
            }

            if (validateInputs() == 0) {
                if (authObs.createProfile(txfFirstName.getText(),
                        txfLastName.getText(),
                        cbSecurityQuestion.getValue().toString(),
                        txfSecurityAnswer.getText(),
                        txfPassword.getText(),
                        String.format("#%02X%02X%02X", (int) (cpPickColor.getValue().getRed() * 255), (int) (cpPickColor.getValue().getGreen() * 255), (int) (cpPickColor.getValue().getBlue() * 255))
                )) {
                    DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_CREATE_PROFILE);
                    resetFields();
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_REGISTER.name())){
            resetFields();
        }
    }

    private void resetFields() {
        txfFirstName.setText("");
        txfLastName.setText("");
        cbSecurityQuestion.setValue(SECURITY_QUESTIONS.PROTOTYPE.getQuestion());
        txfPassword.setText("");
        txfSecurityAnswer.setText("");
        cpPickColor.setValue(Color.WHITE);
        
        errorFirstName.setVisible(false);
        errorLastName.setVisible(false);
        errorPassword.setVisible(false);
        errorSecurityQuestion.setVisible(false);
        errorSecurityAnswer.setVisible(false);
        errorColor.setVisible(false);
    }
}
