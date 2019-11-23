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
import javafx.scene.layout.VBox;
import stonks.Constants;

/**
 *
 * @author joaom
 */
public class ProfileLoginView extends HBox implements Constants{
    //Containers
    private BorderPane profileLoginContainer;
    private VBox vbForm;
    private BorderPane bpLoginRoot;
    private HBox hbTitle;
    private HBox hbOption;
    private BorderPane bpOption;
    //Title Labels
    private Label lblTitle;
    private Label lblPassword;
    
    //Label Buttons
    private Label btnForgotPassword;
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
        
        bpLoginRoot = new BorderPane();
        bpLoginRoot.setId("loginRoot");
        bpLoginRoot.setMinSize(450,0);
        bpLoginRoot.setMaxSize(450, 400);
        
        lblTitle = new Label("Login as");
        lblTitle.getStyleClass().add("TitleLabel");
        hbTitle = new HBox();
        
        vbForm = new VBox();
        vbForm.setId("loginVbox");
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        lblPassword.getStyleClass().add("FormLabel");
        txtPassword.getStyleClass().add("textFieldInput");
        
        hbOption  = new HBox();
        btnForgotPassword = new Label("I forgot my password");
        btnSignIp = new Label("Sign in");
        
        btnSignIp.getStyleClass().add("signIn_btn");
        btnForgotPassword.getStyleClass().add("forgotPassword_btn");
        btnSignIp.getStyleClass().add("labelButton");
      
        hbTitle.getChildren().add(lblTitle);
        hbTitle.getStyleClass().add("hbTitle");
        
        bpOption = new BorderPane();
        bpOption.setLeft(btnForgotPassword);
        bpOption.setRight(btnSignIp);
        
        vbForm.getChildren().addAll(lblPassword,txtPassword);
      
        
        bpLoginRoot.setTop(hbTitle);
        bpLoginRoot.setCenter(vbForm);
        bpLoginRoot.setBottom(bpOption);
        
        profileLoginContainer.setCenter(bpLoginRoot);
    }    
}
