package gui_components;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;
import static stonks.Constants.PROFILE_EDIT_VIEW_HEIGHT;
import static stonks.Constants.PROFILE_EDIT_VIEW_WIDTH;

public class ProfileEditBox {
    private BorderPane root;
    
    //Containers
    private VBox formContainer;
    private HBox hboxButton;
    private BorderPane rightPane;

    //Title Labels
    private Label lblTitle;
    private Label lblFN;
    private Label lblLN;
    private Label lblPassword;
    private Label lblColor;
    //Label Buttons
    private Label btnDeleteProfile;
    private Label btnSave;

    //Text Field
    private TextField txtFN; 
    private TextField txtLN;
    private TextField txtPassword;

    //Colorpicker Field
    private ColorPicker cpPickColor;

    public ProfileEditBox() {
        root = new BorderPane();
        
        root.setMinWidth(PROFILE_EDIT_VIEW_WIDTH);
        root.setMaxSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT);
        
        setupProfileEditBox();
    }
    
    private void setupProfileEditBox(){
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
            DialogBox.display(Constants.DBOX_TYPE.CONFIRM, Constants.DBOX_CONTENT.CONFIRM_DELETE_PROFILE);
        });
        
        /*Save button on click listener*/
        btnSave.setOnMouseClicked((event) -> {
            DialogBox.display(Constants.DBOX_TYPE.SUCCESS, Constants.DBOX_CONTENT.SUCCESS_CREATE_PROFILE);
        });
        
        /*Add the button container to the right of the border pane*/
        rightPane.setRight(hboxButton);
        
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
        
        /*Add title on top, formContainer on center, button on bottom*/
        root.setTop(lblTitle);    
        root.setCenter(formContainer);
        root.setBottom(rightPane); 
    }

    public BorderPane getRoot() {
        return root;
    }
}
