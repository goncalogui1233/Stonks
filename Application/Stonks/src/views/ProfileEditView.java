package views;

import gui_components.DialogBox;
import gui_components.SideMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class ProfileEditView extends HBox implements Constants{
    
    //Containers
    private BorderPane profileEditContainer;
    private VBox vboxInput;
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
    
    public ProfileEditView(){
        this.getChildren().add(new SideMenu().getRoot());
        
        setupEditForm();
    }

    private void setupEditForm(){
        profileEditContainer = new BorderPane();
        
        profileEditContainer.setMinWidth(PROFILE_EDIT_VIEW_WIDTH);
        profileEditContainer.setMaxSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT);
        
        startUPForm();
        
       /* profileEditContainer.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        profileEditContainer.setMinSize(50, 50);*/
        
        this.getChildren().add(profileEditContainer);
    }
    
    private void startUPForm(){
        lblTitle = new Label("My Profile");
        lblTitle.getStyleClass().add("TitleLabel");
        //add css to label's
        
        vboxInput = new VBox();
        
        lblFN = new Label("First Name");
        txtFN = new TextField();
        lblFN.getStyleClass().add("FormLabel");       
        txtFN.getStyleClass().add("textFieldInput");
        
        
        lblLN = new Label("Last Name");
        txtLN = new TextField();
        lblFN.getStyleClass().add("FormLabel");
        txtLN.getStyleClass().add("textFieldInput");
        
        lblPassword = new Label("Password");
        txtPassword = new TextField();
        lblPassword.getStyleClass().add("FormLabel");
        txtPassword.getStyleClass().add("textFieldInput");
        
        lblColor = new Label("Color");
        lblColor.getStyleClass().add("FormLabel");
        
        hboxButton = new HBox();
        rightPane = new BorderPane();
        btnDeleteProfile = new Label("Delete Profile");
        btnSave = new Label("Save Changes");
        
        btnDeleteProfile.getStyleClass().addAll("labelButton","lbtnDelete");
        btnSave.getStyleClass().addAll("labelButton", "lbtnSaveChange");
        
        profileEditContainer.setTop(lblTitle);        
                
        vboxInput.getChildren().addAll(lblFN,txtFN);
        vboxInput.getChildren().addAll(lblLN,txtLN);
        vboxInput.getChildren().addAll(lblPassword,txtPassword);
        vboxInput.getChildren().addAll(lblColor);
        profileEditContainer.setCenter(vboxInput);
        
        hboxButton.getChildren().addAll(btnDeleteProfile, btnSave);
        
        btnDeleteProfile.setOnMouseClicked((event) ->
        {
            DialogBox.display(DBOX_TYPE.CONFIRM, DBOX_CONTENT.CONFIRM_DELETE_PROFILE);
        });
        
        btnSave.setOnMouseClicked((event) ->
        {
            DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_CREATE_PROFILE);
        });
        
        rightPane.setRight(hboxButton);
        
        profileEditContainer.setBottom(rightPane);        
    }
    
}
