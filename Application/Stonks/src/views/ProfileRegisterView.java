/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import gui_components.SideMenu;
import gui_components.SideProfileBar;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import stonks.Constants;

/**
 *
 * @author joaom
 */
public class ProfileRegisterView extends HBox implements Constants
{
    //Containers
    private BorderPane profileRegisterContainer;
    private BorderPane bpContext;
    private VBox vboxForm;
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
    private TextField txtFN; 
    private TextField txtLN;
    private TextField txtPassword;
    private TextField txtSecurtyAnswer;
    
    //Choice Field
    private ChoiceBox cbSecurityQuestion;
    //Colorpicker Field
    private ColorPicker cpPickColor;
    
    public ProfileRegisterView()
    {
        this.getChildren().add(new SideProfileBar().getRoot());
        
        setupRegisterForm();
    }
    
    private void setupRegisterForm(){
        profileRegisterContainer = new BorderPane();
        
        profileRegisterContainer.setMinWidth(PROFILE_EDIT_VIEW_WIDTH);
        profileRegisterContainer.setMaxSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT);
        
        startUPForm();
        
        this.getChildren().add(profileRegisterContainer);
    }
    
    private void startUPForm(){
        
        bpContext = new BorderPane();
        bpContext.setId("registerRoot");
        bpContext.setMinSize(450, 0);
        bpContext.setMaxSize(450, APP_HEIGHT - 120);
        
        lblTitle = new Label("Register");
        lblTitle.getStyleClass().add("TitleLabel");
        hbTitle = new HBox();
        
        vboxForm = new VBox();
        vboxForm.setId("registerVbox");
        
        lblFN = new Label("First Name");
        txtFN = new TextField();
        lblFN.getStyleClass().add("FormLabel");       
        txtFN.getStyleClass().add("textFieldInput");       
        
        lblLN = new Label("Last Name");
        txtLN = new TextField();
        lblLN.getStyleClass().add("FormLabel");
        txtLN.getStyleClass().add("textFieldInput");
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        lblPassword.getStyleClass().add("FormLabel");
        txtPassword.getStyleClass().add("textFieldInput");
                
        lblSecurtyQuestion = new Label("Security Question");
        lblSecurtyQuestion.getStyleClass().add("FormLabel");
        cbSecurityQuestion = new ChoiceBox( 
                FXCollections.observableArrayList(SECURITY_QUESTIONS_LIST) );
        cbSecurityQuestion.setValue("Select One");
        
        
        lblSecurtyAnswer = new Label("Security Answer");
        txtSecurtyAnswer = new TextField();
        lblSecurtyAnswer.getStyleClass().add("FormLabel");
        txtSecurtyAnswer.getStyleClass().add("textFieldInput");
        
        lblColor = new Label("Color");
        lblColor.getStyleClass().add("FormLabel");
        cpPickColor = new ColorPicker();
        cpPickColor.setId("colorPickerLogin");
        
        hbSignUp = new HBox();
        btnSignUp = new Label("Sign Up");
        btnSignUp.getStyleClass().add("labelButton");
        
        hbSignUp.getChildren().add(btnSignUp);
        hbSignUp.getStyleClass().add("signUp_btn");
        
        hbTitle.getChildren().add(lblTitle);
        hbTitle.getStyleClass().add("hbTitle");
                        
        vboxForm.getChildren().addAll(lblFN,txtFN);
        vboxForm.getChildren().addAll(lblLN,txtLN);
        vboxForm.getChildren().addAll(lblPassword,txtPassword);
        vboxForm.getChildren().addAll(lblSecurtyQuestion,cbSecurityQuestion);
        vboxForm.getChildren().addAll(lblSecurtyAnswer,txtSecurtyAnswer);
        vboxForm.getChildren().addAll(lblColor,cpPickColor);
                
        bpContext.setTop(hbTitle);
        bpContext.setCenter(vboxForm);   
        bpContext.setBottom(hbSignUp);  
        
        profileRegisterContainer.setCenter(bpContext);        
    }
}
