package gui_components;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class RegisterBox implements Constants{
    private final BorderPane root;
    
    public RegisterBox(){
        root = new BorderPane();
                
        root.setMinSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        root.setMaxSize(PROFILE_AUTH_WIDTH, PROFILE_AUTH_HEIGHT);
        
        setupRegisterForm();
    }
    
    private void setupRegisterForm(){
        //Containers
        BorderPane registerRoot;
        VBox formContainer;
        HBox hbSignUp;
        HBox hbTitle;

        //Title Labels
        Label lblTitle;
        Label lblFN;
        Label lblLN;
        Label lblPassword;
        Label lblSecurtyQuestion;
        Label lblSecurtyAnswer;
        Label lblColor;

        //Label Buttons
        Label btnSignUp;

        //Text Field
        TextField txtFN; 
        TextField txtLN;
        TextField txtPassword;
        TextField txtSecurtyAnswer;

        //Choice Field
        ChoiceBox cbSecurityQuestion;
        
        //Colorpicker Field
        ColorPicker cpPickColor;
        
        registerRoot = new BorderPane();
        registerRoot.setMinWidth(PROFILE_AUTH_BOX_WIDTH);
        registerRoot.setMaxSize(PROFILE_AUTH_BOX_WIDTH, PROFILE_AUTH_BOX_REGISTER_HEIGHT);
        
        lblTitle = new Label("Register");
        hbTitle = new HBox();
        
        formContainer = new VBox();
        
        lblFN = new Label("First Name");
        txtFN = new TextField();
        
        lblLN = new Label("Last Name");
        txtLN = new TextField();
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
                
        lblSecurtyQuestion = new Label("Security Question");
        cbSecurityQuestion = new ChoiceBox(FXCollections.observableArrayList(SECURITY_QUESTIONS.values()));
        cbSecurityQuestion.setValue("Select One");
        
        lblSecurtyAnswer = new Label("Security Answer");
        txtSecurtyAnswer = new TextField();
        
        lblColor = new Label("Color");
        cpPickColor = new ColorPicker();
        
        hbSignUp = new HBox();
        btnSignUp = new Label("Sign Up");
        
        /*Add the title to the title box*/
        hbTitle.getChildren().add(lblTitle);
                        
        /*Add all labels and inputs to the form box*/
        formContainer.getChildren().addAll(lblFN, txtFN, 
                lblLN, txtLN, 
                lblPassword, txtPassword,
                lblSecurtyQuestion, cbSecurityQuestion,
                lblSecurtyAnswer, txtSecurtyAnswer, 
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
        txtFN.getStyleClass().add("textFieldInput");    
        lblLN.getStyleClass().add("FormLabel");
        txtLN.getStyleClass().add("textFieldInput");   
        lblPassword.getStyleClass().add("FormLabel");
        txtPassword.getStyleClass().add("textFieldInput");
        lblSecurtyQuestion.getStyleClass().add("FormLabel");
        lblSecurtyAnswer.getStyleClass().add("FormLabel");
        txtSecurtyAnswer.getStyleClass().add("textFieldInput");
        lblColor.getStyleClass().add("FormLabel");
        btnSignUp.getStyleClass().add("labelButton");
        hbSignUp.getStyleClass().add("signUp_btn");
        hbTitle.getStyleClass().add("hbTitle");
        
        /*Add register container into the root pane*/
        root.setCenter(registerRoot);   
    }

    public BorderPane getRoot() {
        return root;
    }
}
