package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.ProfileModel;
import observables.ProfileObservable;
import stonks.Constants;

public class ProfileEditBox implements Constants, PropertyChangeListener {

    private final ProfileObservable profileObs;
    private final BorderPane root;

    //Containers
    private VBox titleContainer;
    private VBox formContainer;
    private HBox buttonContainer;

    //Input Divs
    private VBox firstNameDiv;
    private VBox lastNameDiv;
    private VBox passwordDiv;
    private VBox colorDiv;

    //Title Labels
    private Label lblTitle;
    private Label lblUserName;
    private Label lblFirstName;
    private Label lblLastName;
    private Label lblPassword;
    private Label lblColor;

    //Field Inputs
    private TextField txfFirstName;
    private TextField txfLastName;
    private TextField txfPassword;
    private ColorPicker cpPickColor;

    //Error Labels
    private Label errorFirstName;
    private Label errorLastName;
    private Label errorPassword;
    private Label errorColor;

    //Buttons
    private Button btnDeleteProfile;
    private Button btnSave;

    public ProfileEditBox(ProfileObservable profileObs) {
        this.profileObs = profileObs;
        root = new BorderPane();

        root.setMinWidth(PROFILE_EDIT_VIEW_WIDTH - 6/*CHECK WHY THIS IS NEEDED*/);
        root.setMaxSize(PROFILE_EDIT_VIEW_WIDTH - 6/*CHECK WHY THIS IS NEEDED*/, PROFILE_EDIT_VIEW_HEIGHT);

        setupProfileEditBox();
        setupEventListeners();
        setupPropertyChangeListeners();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        profileObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOTO_PROFILE_VIEW.name(), this);
        profileObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.PROFILE_HAS_BEEN_EDITED.name(), this);
    }

    private void setupProfileEditBox() {
        lblTitle = new Label("Edit Profile");
        lblUserName = new Label();
        titleContainer = new VBox();

        formContainer = new VBox();
        formContainer.setMaxWidth(400);
        BorderPane.setAlignment(formContainer, Pos.CENTER_LEFT);

        lblFirstName = new Label("First Name");
        txfFirstName = new TextField();

        lblLastName = new Label("Last Name");
        txfLastName = new TextField();

        lblPassword = new Label("Password");
        txfPassword = new TextField();

        lblColor = new Label("Color");
        cpPickColor = new ColorPicker();
        cpPickColor.setMinWidth(400);
        cpPickColor.setMaxWidth(400);

        buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        btnDeleteProfile = new Button("Delete Profile");
        btnSave = new Button("Save Changes");

        /*Initialize error labels*/
        errorFirstName = new Label("errorFirstName");
        errorFirstName.setVisible(false);
        errorLastName = new Label("errorLastName");
        errorLastName.setVisible(false);
        errorPassword = new Label("errorPassword");
        errorPassword.setVisible(false);
        errorColor = new Label("errorColor");
        errorColor.setVisible(false);

        /*Initialize divs*/
        firstNameDiv = new VBox();
        lastNameDiv = new VBox();
        passwordDiv = new VBox();
        colorDiv = new VBox();

        /*Add the title to the title box*/
        titleContainer.getChildren().addAll(lblTitle, lblUserName);

        /*Add all labels and inputs to the form box*/
        firstNameDiv.getChildren().addAll(lblFirstName, txfFirstName, errorFirstName);
        lastNameDiv.getChildren().addAll(lblLastName, txfLastName, errorLastName);
        passwordDiv.getChildren().addAll(lblPassword, txfPassword, errorPassword);
        colorDiv.getChildren().addAll(lblColor, cpPickColor, errorColor);
        formContainer.getChildren().addAll(firstNameDiv, lastNameDiv, passwordDiv, colorDiv);

        /*Add delete and save buttons to the button container*/
        buttonContainer.getChildren().addAll(btnDeleteProfile, btnSave);

        /*Set CSS ID's to nodes*/
        root.setId("profileBox");
        cpPickColor.setId("colorPicker");
        lblUserName.setId("titleProfile");

        /*Set CSS Classes to nodes*/
        formContainer.getStyleClass().add("form");
        titleContainer.getStyleClass().add("titleBox");

        firstNameDiv.getStyleClass().addAll("fieldDiv");
        lastNameDiv.getStyleClass().addAll("fieldDiv");
        passwordDiv.getStyleClass().addAll("fieldDiv");
        colorDiv.getStyleClass().addAll("fieldDiv");

        lblFirstName.getStyleClass().add("fieldTitle");
        lblLastName.getStyleClass().add("fieldTitle");
        lblPassword.getStyleClass().add("fieldTitle");
        lblColor.getStyleClass().add("fieldTitle");

        txfFirstName.getStyleClass().add("fieldInput");
        txfLastName.getStyleClass().add("fieldInput");
        txfPassword.getStyleClass().add("fieldInput");
        cpPickColor.getStyleClass().add("fieldInput");

        errorFirstName.getStyleClass().addAll("fieldError");
        errorLastName.getStyleClass().addAll("fieldError");
        errorPassword.getStyleClass().addAll("fieldError");
        errorColor.getStyleClass().addAll("fieldError");

        btnDeleteProfile.getStyleClass().addAll("button", "btn-danger", "btn-form-sharp");
        btnSave.getStyleClass().addAll("button", "btn-default", "btn-form-sharp");

        lblFirstName.getStyleClass().add("FormLabel");
        txfFirstName.getStyleClass().add("textFieldInput");
        lblLastName.getStyleClass().add("FormLabel");
        txfLastName.getStyleClass().add("textFieldInput");
        lblPassword.getStyleClass().add("FormLabel");
        txfPassword.getStyleClass().add("textFieldInput");
        lblColor.getStyleClass().add("FormLabel");

        /*Add title on top, formContainer on center, button on bottom*/
        root.setTop(titleContainer);
        root.setCenter(formContainer);
        root.setBottom(buttonContainer);
    }

    private int validateInputs() {
        int errorCounter = 0;

        switch (profileObs.verifyData(PROFILE_FIELD.FIRST_NAME, txfFirstName.getText())) {
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

        switch (profileObs.verifyData(PROFILE_FIELD.LAST_NAME, txfLastName.getText())) {
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

        switch (profileObs.verifyData(PROFILE_FIELD.PASSWORD, txfPassword.getText())) {
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

        switch (profileObs.verifyData(PROFILE_FIELD.COLOR, String.format("#%02X%02X%02X", (int) (cpPickColor.getValue().getRed() * 255), (int) (cpPickColor.getValue().getGreen() * 255), (int) (cpPickColor.getValue().getBlue() * 255)))) {
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
        btnDeleteProfile.setOnMouseClicked(e -> {
            DBOX_CONTENT.CONFIRM_DELETE_PROFILE.setSubExtra(profileObs.getAuthProfile().getFirstName() + " " + profileObs.getAuthProfile().getLastName());
            if (DialogBox.display(DBOX_TYPE.CONFIRM, DBOX_CONTENT.CONFIRM_DELETE_PROFILE) == DBOX_RETURN.YES) {
                profileObs.removeProfile();
            }
        });

        btnSave.setOnMouseClicked(e -> {
            if (validateInputs() == 0) {
                if (profileObs.editProfile(txfFirstName.getText(), txfLastName.getText(), txfPassword.getText(), String.format("#%02X%02X%02X", (int) (cpPickColor.getValue().getRed() * 255), (int) (cpPickColor.getValue().getGreen() * 255), (int) (cpPickColor.getValue().getBlue() * 255)))) {
                    DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_EDIT_PROFILE);
                }
            }
        });
    }

    private void setTextFieldValues(ProfileModel authProfile) {
        try {
            lblUserName.setText(authProfile.getFirstName()
                    + " "
                    + authProfile.getLastName());
            txfFirstName.setText(authProfile.getFirstName());
            txfLastName.setText(authProfile.getLastName());
            txfPassword.setText(authProfile.getPassword());
            cpPickColor.setValue(Color.valueOf(authProfile.getColor()));
        } catch (NullPointerException ex) {
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_PROFILE_VIEW.name())) {
            setTextFieldValues(profileObs.getAuthProfile());
        } else if (evt.getPropertyName().equals(STONKS_EVENT.PROFILE_HAS_BEEN_EDITED.name())) {
            setTextFieldValues(profileObs.getAuthProfile());
        }
    }
}
