package views;

import gui_components.SideMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class ProfileEditView extends HBox implements Constants{
    BorderPane profileEditContainer;
    
    public ProfileEditView(){
        this.getChildren().add(new SideMenu().getRoot());
        
        setupEditForm();
    }

    private void setupEditForm(){
        profileEditContainer = new BorderPane();
        
        profileEditContainer.setMinSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT-100);
        profileEditContainer.setMaxSize(PROFILE_EDIT_VIEW_WIDTH, PROFILE_EDIT_VIEW_HEIGHT);
        
        startUPForm();
        
       /* profileEditContainer.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        profileEditContainer.setMinSize(50, 50);*/
        
        this.getChildren().add(profileEditContainer);
    }
    
    private void startUPForm(){
        Label lblTitle = new Label("My Profile");
        lblTitle.setId("TitleLabel");
        //add css to label's
        
        VBox vboxInput = new VBox();
        
        Label lblFN = new Label("First Name");
        lblFN.setId("FormLabel");       
        TextField txtFN = new TextField();
        txtFN.setId("textFieldInput");
        
        
        Label lblLN = new Label("Last Name");
        lblLN.setId("FormLabel");
        TextField txtLN = new TextField();
        txtLN.setId("textFieldInput");
        
        Label lblPassword = new Label("Password");
        TextField txtPassword = new TextField();
        lblPassword.setId("FormLabel");
        txtPassword.setId("textFieldInput");
        
        Label lblColor = new Label("Color");
        lblColor.setId("FormLabel");
        
        HBox hboxButton = new HBox();
        BorderPane rightPane = new BorderPane();
        Label btnDeleteProfile = new Label("Delete Profile");
        Label btnSave = new Label("Save Changes");
        
        profileEditContainer.setTop(lblTitle);        
                
        vboxInput.getChildren().addAll(lblFN,txtFN);
        vboxInput.getChildren().addAll(lblLN,txtLN);
        vboxInput.getChildren().addAll(lblPassword,txtPassword);
        vboxInput.getChildren().addAll(lblColor);
        profileEditContainer.setCenter(vboxInput);
        
        hboxButton.getChildren().addAll(btnDeleteProfile, btnSave);
        
        rightPane.setRight(hboxButton);
        
        profileEditContainer.setBottom(rightPane);        
    }
    
}
