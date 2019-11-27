package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import observables.AuthenticationObservable;
import stonks.Constants;

public class RegisterBox implements Constants, PropertyChangeListener{

    private final BorderPane root;
    private final AuthenticationObservable authObs;

    //Containers
    private BorderPane registerRoot;
    private VBox formContainer;
    private HBox hbSignUp;
    private HBox hbTitle;

    //Title Labels
    private Label lblTitle;
    private Label lblFN;
    private Label lblLN;
    private Label lblPassword;
    private Label lblSecurtyQuestion;
    private Label lblSecurtyAnswer;
    private Label lblColor;

    //Label Buttons
    private Label btnSignUp;

    //Text Field
    private TextField txfFirstName;
    private TextField txfLastName;
    private TextField txfPassword;
    private TextField txfSecurityAnswer;

    //Choice Field
    private ChoiceBox cbSecurityQuestion;

    //Colorpicker Field
    private ColorPicker cpPickColor;

    public RegisterBox(AuthenticationObservable authObs) {
        this.authObs = authObs;

        root = new BorderPane();

        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);

        setupRegisterForm();
        setupEventListeners();
        setupPropertyChangeListeners();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_REGISTER.name(), this);
    }

    private void setupRegisterForm() {
        registerRoot = new BorderPane();
        registerRoot.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        registerRoot.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_REGISTER_HEIGHT);

        lblTitle = new Label("Register");
        hbTitle = new HBox();

        formContainer = new VBox();

        lblFN = new Label("First Name");
        txfFirstName = new TextField();

        lblLN = new Label("Last Name");
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

        lblSecurtyAnswer = new Label("Security Answer");
        txfSecurityAnswer = new TextField();

        lblColor = new Label("Color");
        cpPickColor = new ColorPicker();

        hbSignUp = new HBox();
        btnSignUp = new Label("Sign Up");

        /*Add the title to the title box*/
        hbTitle.getChildren().add(lblTitle);

        /*Add all labels and inputs to the form box*/
        formContainer.getChildren().addAll(lblFN, txfFirstName,
                lblLN, txfLastName,
                lblPassword, txfPassword,
                lblSecurtyQuestion, cbSecurityQuestion,
                lblSecurtyAnswer, txfSecurityAnswer,
                lblColor, cpPickColor);

        /*Add the button to the button box*/
        hbSignUp.getChildren().add(btnSignUp);

        /*Add title on top, formContainer on center, sign-up button on bottom*/
        registerRoot.setTop(hbTitle);
        registerRoot.setCenter(formContainer);
        registerRoot.setBottom(hbSignUp);

        /*Set CSS ID's to nodes*/
        registerRoot.setId("registerRoot");
        formContainer.setId("registerVbox");
        cpPickColor.setId("colorPickerLogin");

        /*Set CSS Classes to nodes*/
        lblTitle.getStyleClass().add("TitleLabel");
        lblFN.getStyleClass().add("FormLabel");
        txfFirstName.getStyleClass().add("textFieldInput");
        lblLN.getStyleClass().add("FormLabel");
        txfLastName.getStyleClass().add("textFieldInput");
        lblPassword.getStyleClass().add("FormLabel");
        txfPassword.getStyleClass().add("textFieldInput");
        lblSecurtyQuestion.getStyleClass().add("FormLabel");
        lblSecurtyAnswer.getStyleClass().add("FormLabel");
        txfSecurityAnswer.getStyleClass().add("textFieldInput");
        lblColor.getStyleClass().add("FormLabel");
        btnSignUp.getStyleClass().add("labelButton");
        hbSignUp.getStyleClass().add("signUp_btn");
        hbTitle.getStyleClass().add("hbTitle");

        /*Add register container into the root pane*/
        root.setCenter(registerRoot);
    }

    private void setupEventListeners() {
        btnSignUp.setOnMouseClicked(e -> {
            if (authObs.hasMaxProfiles()) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_PROFILE_LIMIT);
                return;
            }

            int errorCounter = 0;

            switch (authObs.verifyData(PROFILE_FIELD.FIRST_NAME, txfFirstName.getText())) {
                case MIN_CHAR:
                    System.out.println("FIRST_NAME - MIN_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
                case MAX_CHAR:
                    System.out.println("FIRST_NAME - MAX_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
            }

            switch (authObs.verifyData(PROFILE_FIELD.LAST_NAME, txfLastName.getText())) {
                case MIN_CHAR:
                    System.out.println("LAST_NAME - MIN_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
                case MAX_CHAR:
                    System.out.println("LAST_NAME - MAX_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
            }

            switch (authObs.verifyData(PROFILE_FIELD.SECURITY_QUESTION, cbSecurityQuestion.getValue().toString())) {
                case INVALID_QUESTION:
                    System.out.println("SECURITY_QUESTION - INVALID_QUESTION");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
            }

            switch (authObs.verifyData(PROFILE_FIELD.SECURITY_ANSWER, txfSecurityAnswer.getText())) {
                case MIN_CHAR:
                    System.out.println("SECURITY_ANSWER - MIN_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
                case MAX_CHAR:
                    System.out.println("SECURITY_ANSWER - MAX_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
            }

            switch (authObs.verifyData(PROFILE_FIELD.PASSWORD, txfPassword.getText())) {
                case MIN_CHAR:
                    System.out.println("PASSWORD - MIN_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
                case MAX_CHAR:
                    System.out.println("PASSWORD - MAX_CHAR");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
            }

            switch (authObs.verifyData(PROFILE_FIELD.COLOR, String.format("#%02X%02X%02X", (int) (cpPickColor.getValue().getRed() * 255), (int) (cpPickColor.getValue().getGreen() * 255), (int) (cpPickColor.getValue().getBlue() * 255)))) {
                case FORMAT:
                    System.out.println("COLOR - FORMAT");/*ERROR LABEL CODE HERE*/
                    errorCounter++;
                    break;
            }

            if (errorCounter == 0) {
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
    }
}
