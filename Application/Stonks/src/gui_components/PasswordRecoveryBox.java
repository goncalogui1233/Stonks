package gui_components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class PasswordRecoveryBox implements Constants{
    private final BorderPane root;
    
    public PasswordRecoveryBox(){
        root = new BorderPane();
        
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        
        setupRecoverPasswordForm();
    }
    
    private void setupRecoverPasswordForm(){
        //Containers
        VBox formContainer;
        BorderPane bpRecoverRoot;
        VBox vbTitle;
        BorderPane bpOption;

        //Title Labels
        Label lblTitle;
        Label lblUserName;
        Label lblSecurityQuestion;
        Label lvSecurityQuestion;

        //Label Buttons
        Label lblSecurityAnswer;   
        Label btnRecover;

        //Text Field
        TextField txtSecurityAnswer;

        bpRecoverRoot = new BorderPane();
        bpRecoverRoot.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        bpRecoverRoot.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_RECOVER_PASSWORD_HEIGHT);
        
        lblTitle = new Label("Recover Password");
        lblUserName = new Label(/*INSERT PROFILE FIRST AND LAST NAME*/);
        vbTitle = new VBox();
        
        formContainer = new VBox();
        
        lblSecurityQuestion = new Label("Security Question");
        lvSecurityQuestion = new Label(/*INSERT PROFILE SECURITY QUESTION*/);
        
        lblSecurityAnswer = new Label("Security Answer");
        txtSecurityAnswer = new TextField();
        
        btnRecover = new Label("Recover");
        
        bpOption = new BorderPane();
        
        /*Add the title to the title box*/
        vbTitle.getChildren().addAll(lblTitle, lblUserName);
        
        /*Add all labels and inputs to the form box*/
        formContainer.getChildren().addAll(lblSecurityQuestion, lvSecurityQuestion,
                lblSecurityAnswer, txtSecurityAnswer);
        
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
        lblUserName.getStyleClass().add("TitleLabel");
        lblSecurityQuestion.getStyleClass().add("FormLabel");
        lvSecurityQuestion.getStyleClass().add("FormLabel");
        lblSecurityAnswer.getStyleClass().add("FormLabel");
        txtSecurityAnswer.getStyleClass().add("textFieldInput");
        btnRecover.getStyleClass().add("labelButton");
        vbTitle.getStyleClass().add("hbTitle");
        
        /*Add login container into the root pane*/
        root.setCenter(bpRecoverRoot);
    }

    public BorderPane getRoot() {
        return root;
    }
}
