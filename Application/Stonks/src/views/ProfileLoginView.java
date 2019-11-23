/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import gui_components.SideMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import stonks.Constants;

/**
 *
 * @author joaom
 */
public class ProfileLoginView extends HBox implements Constants{
    //Containers
    private BorderPane profileLoginContainer;
    
    //Title Labels
    private Label lblTitle;
    private Label lblPassword;
    
    //Label Buttons
    private Label lblForgotPassword;
    private Label btnSignIp;   
        
    //Text Field
    private TextField txtPassword;

    public ProfileLoginView()    {
                this.getChildren().add(new SideMenu().getRoot());
        
        setupLoginForm();
    }

    private void setupLoginForm(){
        profileLoginContainer = new BorderPane();
        
        profileLoginContainer.setMinWidth(PROFILE_EDIT_VIEW_WIDTH);
        profileLoginContainer.setMaxSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT);
        
        startUPForm();
        
        this.getChildren().add(profileLoginContainer);
    }
    
    private void startUPForm(){
        lblTitle = new Label("Login as");
        lblTitle.getStyleClass().add("TitleLabel");
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        lblPassword.getStyleClass().add("FormLabel");
        txtPassword.getStyleClass().add("textFieldInput");
        
        lblForgotPassword = new Label("I forgot my password");
        
        btnSignIp = new Label("Sign in");
        btnSignIp.getStyleClass().add("labelButton");
    }
    
}
