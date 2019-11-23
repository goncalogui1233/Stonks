package gui_components;

import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.ProfileModel;
import stonks.Constants;

public class SideProfileBar implements Constants{
    VBox root;

    /*REMOVE LATER*/
    public SideProfileBar(){
        this(null);
    }
    
    public SideProfileBar(HashMap<Integer, ProfileModel> listProfiles) {
        /*REMOVE LATER*/
        if(listProfiles == null){
            listProfiles = new HashMap();
            ProfileModel tempProfile;
            
            for(int i = 1; i <= 6; i++){
                String hex = String.format( "#%02X%02X%02X",
                (int)( Color.rgb(20*i, 20*i, 20*i).getRed() * 255 ),
                (int)( Color.rgb(20*i, 20*i, 20*i).getGreen() * 255 ),
                (int)( Color.rgb(20*i, 20*i, 20*i).getBlue() * 255 ) );
                tempProfile = new ProfileModel("U", ""+i, SECURITY_QUESTIONS_LIST[0], "a", "123", hex);
                listProfiles.put(tempProfile.getId(), tempProfile);
            }
        }
        
        root = new VBox();
        root.setMinSize(SIDEPROFILEBAR_WIDTH, SIDEPROFILEBAR_HEIGHT);
        root.setMaxSize(SIDEPROFILEBAR_WIDTH, SIDEPROFILEBAR_HEIGHT);
        root.setId("sideProfileBar");

        setupProfileIcons(listProfiles);
        setupRegisterButton();
    }

    private void setupProfileIcons(HashMap<Integer, ProfileModel> listProfiles){
        Label profileIcon;
        String profileInitials;
        Color textColor;
        Color profileColor;
        Label divider;
        
        for(ProfileModel profile:listProfiles.values()){
            profileInitials = "" 
                    + profile.getFirstName().charAt(0)
                    + profile.getLastName().charAt(0);
            
            profileIcon = new Label(profileInitials);
            profileIcon.setMinSize(60, 60);
            profileIcon.getStyleClass().add("BACKGROUND_RED");

            profileColor = Color.valueOf(profile.getColor());

            /*If background is too dark, text is white or black if too bright*/
            if(((profileColor.getRed() * 0.333) + 
                    (profileColor.getGreen() * 0.333) + 
                    (profileColor.getBlue() * 0.333)) > 0.3){
                textColor = Color.valueOf("#000"); 
            }else{
                textColor = Color.valueOf("#FFF"); 
            }
            
            /*Set node properties*/
            profileIcon.setTextFill(textColor);
            profileIcon.setBackground(new Background(new BackgroundFill(profileColor, new CornerRadii(100), new Insets(-5))));
            
            if(profile.equals(listProfiles.get(0)))
                profileIcon.setBorder(new Border(new BorderStroke(textColor, BorderStrokeStyle.SOLID, new CornerRadii(100), new BorderWidths(5))));
        
            /*Set CSS ID's*/
            profileIcon.setId("profileIcon");
            
            /*Add a 10px padding to the root*/
            divider = new Label();
            divider.setId("divider-18");
            root.getChildren().add(divider);
            
            /*Add node to the root*/
            root.getChildren().add(profileIcon);
        }
    }

    private void setupRegisterButton() {
        Label registerButton = new Label("+");
        registerButton.setMinSize(70, 70);

        /*Set node properties*/
        registerButton.setTextFill(Color.valueOf("#333"));
        registerButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#bbb"), new CornerRadii(100), new Insets(0))));
        
        /*Set CSS ID's*/
        registerButton.setId("profileIcon");
        
        /*Add a 10px padding to the root*/
        Label divider = new Label();
        divider.setId("divider-18");
        root.getChildren().add(divider);
        
        root.getChildren().add(registerButton);
    }
    
    public VBox getRoot() {
        return root;
    }
}
