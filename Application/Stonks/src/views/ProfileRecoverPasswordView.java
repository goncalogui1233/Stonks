/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import gui_components.SideMenu;
import gui_components.SideProfileBar;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;
import static stonks.Constants.SECURITY_QUESTIONS_LIST;

/**
 *
 * @author joaom
 */
public class ProfileRecoverPasswordView extends HBox implements Constants{
         //Containers
    private BorderPane profileRecoverPasswordContainer;
    private VBox vbForm;
    private BorderPane bpRecoverRoot;
    private VBox vbTitle;
    private BorderPane bpOption;
    
    //Title Labels
    private Label lblTitle;
    private Label lblUserName;
    private Label lblSecurtyQuestion;
    
    //Label Buttons
    private Label lblSecurtyAnswer;   
    //Label Buttons
    private Label btnRecover;
        
    //Text Field
    private TextField txtSecurtyAnswer;
    
    //Choice Field
    private ChoiceBox cbSecurityQuestion;

    public ProfileRecoverPasswordView(){
        this.getChildren().add(new SideProfileBar().getRoot());
        
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
        bpRecoverRoot = new BorderPane();
        bpRecoverRoot.setId("recoverRoot");
        bpRecoverRoot.setMinSize(450,0);
        bpRecoverRoot.setMaxSize(450, 400);
        
        lblTitle = new Label("Recover Password");
        lblTitle.getStyleClass().add("TitleLabel");
        lblUserName = new Label("User 1");
        lblUserName.getStyleClass().add("TitleLabel");
        vbTitle = new VBox();
        
        vbForm = new VBox();
        vbForm.setId("recoverVbox");
        
        lblSecurtyQuestion = new Label("Security Question");
        lblSecurtyQuestion.getStyleClass().add("FormLabel");
        cbSecurityQuestion = new ChoiceBox( 
                FXCollections.observableArrayList(SECURITY_QUESTIONS_LIST) );
        cbSecurityQuestion.setValue(SECURITY_QUESTIONS_LIST[0]);
        
        
        lblSecurtyAnswer = new Label("Security Answer");
        txtSecurtyAnswer = new TextField();
        lblSecurtyAnswer.getStyleClass().add("FormLabel");
        txtSecurtyAnswer.getStyleClass().add("textFieldInput");
        
        
        btnRecover = new Label("Recover");
        btnRecover.getStyleClass().add("labelButton");
        
        vbTitle.getChildren().addAll(lblTitle, lblUserName);
        vbTitle.getStyleClass().add("hbTitle");
        
        bpOption = new BorderPane();
        bpOption.setRight(btnRecover);
        
        vbForm.getChildren().addAll(lblSecurtyQuestion, cbSecurityQuestion);
        vbForm.getChildren().addAll(lblSecurtyAnswer, txtSecurtyAnswer);
        
        bpRecoverRoot.setTop(vbTitle);
        bpRecoverRoot.setCenter(vbForm);
        bpRecoverRoot.setBottom(bpOption);
        
        profileRecoverPasswordContainer.setCenter(bpRecoverRoot);
        
    }
     
     
    
}
