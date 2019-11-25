package views;

import gui_components.DialogBox;
import gui_components.SideMenu;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class ProfileEditView implements Constants{
    private final HBox root;
    
    
    public ProfileEditView(){
        root = new HBox();
        root.getChildren().add(new SideMenu().getRoot());
        
        setupEditForm();
    }

    private void setupEditForm(){
        //Containers
        BorderPane profileEditContainer;
        VBox formContainer;
        HBox hboxButton;
        BorderPane rightPane;

        //Title Labels
        Label lblTitle;
        Label lblFN;
        Label lblLN;
        Label lblPassword;
        Label lblColor;
        //Label Buttons
        Label btnDeleteProfile;
        Label btnSave;

        //Text Field
        TextField txtFN; 
        TextField txtLN;
        TextField txtPassword;

        //Colorpicker Field
        ColorPicker cpPickColor;
        
        profileEditContainer = new BorderPane();
        profileEditContainer.setMinWidth(PROFILE_EDIT_VIEW_WIDTH);
        profileEditContainer.setMaxSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT);
        
        lblTitle = new Label("My Profile");
        
        formContainer = new VBox();
        
        lblFN = new Label("First Name");
        txtFN = new TextField();
        
        lblLN = new Label("Last Name");
        txtLN = new TextField();
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        
        lblColor = new Label("Color");
        cpPickColor = new ColorPicker();
        
        hboxButton = new HBox();
        hboxButton.setId("editHbox");
        rightPane = new BorderPane();
        btnDeleteProfile = new Label("Delete Profile");
        btnSave = new Label("Save Changes");
        
        /*Add all labels and inputs to the form box*/
        formContainer.getChildren().addAll(lblFN, txtFN,
                lblLN, txtLN,
                lblPassword, txtPassword,
                lblColor, cpPickColor);
        
        /*Add delete and save buttons to the button container*/
        hboxButton.getChildren().addAll(btnDeleteProfile, btnSave);
        
        /*Delete button on click listener*/
        btnDeleteProfile.setOnMouseClicked((event) -> {
            DialogBox.display(DBOX_TYPE.CONFIRM, DBOX_CONTENT.CONFIRM_DELETE_PROFILE);
        });
        
        /*Save button on click listener*/
        btnSave.setOnMouseClicked((event) -> {
            DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_CREATE_PROFILE);
        });
        
        /*Add the button container to the right of the border pane*/
        rightPane.setRight(hboxButton);
        
        /*Add title on top, formContainer on center, button on bottom*/
        profileEditContainer.setTop(lblTitle);    
        profileEditContainer.setCenter(formContainer);
        profileEditContainer.setBottom(rightPane); 
        
        /*Set CSS ID's to nodes*/
        formContainer.setId("editVbox");
        cpPickColor.setId("colorPickerEdit");
        
        /*Set CSS Classes to nodes*/
        lblTitle.getStyleClass().addAll("TitleLabel", "editTitleLabel");
        lblFN.getStyleClass().add("FormLabel");       
        txtFN.getStyleClass().add("textFieldInput");
        lblLN.getStyleClass().add("FormLabel");
        txtLN.getStyleClass().add("textFieldInput");
        lblPassword.getStyleClass().add("FormLabel");
        txtPassword.getStyleClass().add("textFieldInput");
        lblColor.getStyleClass().add("FormLabel");
        btnDeleteProfile.getStyleClass().addAll("labelButton","lbtnDelete");
        btnSave.getStyleClass().addAll("labelButton", "lbtnSaveChange");
        
        /*Add profile edit container into the root pane*/
        root.getChildren().add(profileEditContainer);
    }

    public HBox getRoot() {
        return root;
    }
}
