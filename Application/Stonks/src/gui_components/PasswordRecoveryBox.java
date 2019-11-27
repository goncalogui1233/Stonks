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

public class PasswordRecoveryBox implements Constants, PropertyChangeListener{
    private final BorderPane root;
    private final AuthenticationObservable authObs;
    
    //Containers
    private VBox formContainer;
    private BorderPane bpRecoverRoot;
    private VBox vbTitle;
    private BorderPane bpOption;

    //Title Labels
    private Label lblTitle;
    private Label lblUserName;
    private Label lvSecurityQuestion;

    //Label Buttons
    private Label btnRecover;

    //Text Field
    private TextField txtSecurityAnswer;
    
    public PasswordRecoveryBox(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new BorderPane();
        
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        
        setupRecoverPasswordForm();
        setupPropertyChangeListeners();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.GOTO_RECOVER_PASSWORD.name(), this);
    }
    
    private void setupRecoverPasswordForm(){
        bpRecoverRoot = new BorderPane();
        bpRecoverRoot.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        bpRecoverRoot.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_RECOVER_PASSWORD_HEIGHT);
        
        lblTitle = new Label("Recover Password");
        lblUserName = new Label();
        lblUserName.setWrapText(true);
        vbTitle = new VBox();
        
        formContainer = new VBox();
        
        lvSecurityQuestion = new Label(/*INSERT PROFILE SECURITY QUESTION*/);
        lvSecurityQuestion.setWrapText(true);
        lvSecurityQuestion.setTextAlignment(TextAlignment.JUSTIFY);
        
        txtSecurityAnswer = new TextField();
        
        btnRecover = new Label("Recover");
        
        bpOption = new BorderPane();
        
        /*Add the title to the title box*/
        vbTitle.getChildren().addAll(lblTitle, lblUserName);
        
        /*Add all labels and inputs to the form box*/
        formContainer.getChildren().addAll(lvSecurityQuestion, txtSecurityAnswer);
        
        /*Add the button to the button box*/
        bpOption.setRight(btnRecover);
        
        /*Add title on top, formContainer on center, button on bottom*/
        bpRecoverRoot.setTop(vbTitle);
        bpRecoverRoot.setCenter(formContainer);
        bpRecoverRoot.setBottom(bpOption);
        
        /*Set CSS ID's to nodes*/
        bpRecoverRoot.setId("recoverRoot");
        formContainer.setId("recoverVbox");
        
        /*Set CSS Classes to nodes*/
        lblTitle.getStyleClass().add("TitleLabel");
        lblUserName.getStyleClass().add("UsernameLabel");
        lvSecurityQuestion.getStyleClass().add("FormLabel");
        txtSecurityAnswer.getStyleClass().add("textFieldInput");
        btnRecover.getStyleClass().add("labelButton");
        vbTitle.getStyleClass().add("hbTitle");
        
        /*Add login container into the root pane*/
        root.setCenter(bpRecoverRoot);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(AUTH_EVENT.GOTO_RECOVER_PASSWORD.name())){
            lblUserName.setText(
                    authObs.getProfile(authObs.getViewSelectedProfileId()).getFirstName() 
                    + " "
                    + authObs.getProfile(authObs.getViewSelectedProfileId()).getLastName());
            lvSecurityQuestion.setText(authObs.getProfile(authObs.getViewSelectedProfileId()).getSecurityQuestion());
            resetFields();
        }
    }

    private void resetFields() {
        txtSecurityAnswer.setText("");
    }
}
