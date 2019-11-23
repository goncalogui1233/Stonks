/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import gui_components.SideMenu;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import stonks.Constants;
import static stonks.Constants.SECURITY_QUESTIONS_LIST;

/**
 *
 * @author joaom
 */
public class ProfileRecoverPasswordView extends HBox implements Constants{
         //Containers
    private BorderPane profileRecoverPasswordContainer;
    
    //Title Labels
    private Label lblTitle;
    private Label lblUserName;
    private Label lblSecurtyQuestion;
    
    //Label Buttons
    private Label lblSecurtyAnswer;   
    //Label Buttons
    private Label btnSignUp;
        
    //Text Field
    private TextField txtSecurtyAnswer;
    
    //Choice Field
    private ChoiceBox cbSecurityQuestion;

    public ProfileRecoverPasswordView(){
        this.getChildren().add(new SideMenu().getRoot());
        
        setupRecoverPasswordForm();
    }

    private void setupRecoverPasswordForm(){
        profileRecoverPasswordContainer = new BorderPane();
        
        profileRecoverPasswordContainer.setMinWidth(PROFILE_EDIT_VIEW_WIDTH);
        profileRecoverPasswordContainer.setMaxSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT);
        
        startRecoverPasswordForm();
        
        this.getChildren().add(profileRecoverPasswordContainer);
    }
    
    private void startRecoverPasswordForm(){
        
        lblTitle = new Label("Recover Password");
        lblTitle.getStyleClass().add("TitleLabel");
        
        lblUserName = new Label("User 1");
        lblUserName.getStyleClass().add("TitleLabel");
        
        
        lblSecurtyQuestion = new Label("Security Question");
        lblSecurtyQuestion.getStyleClass().add("FormLabel");
        cbSecurityQuestion = new ChoiceBox( 
                FXCollections.observableArrayList(SECURITY_QUESTIONS_LIST) );
        cbSecurityQuestion.setValue("Select One");
        
        
        lblSecurtyAnswer = new Label("Security Answer");
        txtSecurtyAnswer = new TextField();
        lblSecurtyAnswer.getStyleClass().add("FormLabel");
        txtSecurtyAnswer.getStyleClass().add("textFieldInput");
        
        
        btnSignUp = new Label("Recover");
        btnSignUp.getStyleClass().add("labelButton");
    }
     
     
    
}
