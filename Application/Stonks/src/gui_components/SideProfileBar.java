package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import observables.AuthenticationObservable;
import stonks.Constants;

public class SideProfileBar implements Constants, PropertyChangeListener{
    private final VBox root;
    private final AuthenticationObservable authObs;
    
    private Label profileIcon;
    private String profileInitials;
    private Color textColor;
    private Color profileColor;
    private Label divider;
    
    private Label registerButton;
    
    public SideProfileBar(AuthenticationObservable authObs) {
        this.authObs = authObs;
        
        root = new VBox();
        root.setMinSize(SIDEPROFILEBAR_WIDTH, SIDEPROFILEBAR_HEIGHT);
        root.setMaxSize(SIDEPROFILEBAR_WIDTH, SIDEPROFILEBAR_HEIGHT);
        root.setId("sideProfileBar");
        
        setupPropertyChangeListeners();
        setupProfileIcons(authObs.getListProfiles());
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.CREATE_PROFILE.name(), this);
    }

    private void setupProfileIcons(HashMap<Integer, ProfileModel> listProfiles){
        root.getChildren().removeAll(root.getChildren());
        
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
            
//            if(profile.equals(listProfiles.get(0)))
//                profileIcon.setBorder(new Border(new BorderStroke(textColor, BorderStrokeStyle.SOLID, new CornerRadii(100), new BorderWidths(5))));
        
            /*Set CSS ID's*/
            profileIcon.setId("profileIcon");
            
            /*Add a 10px padding to the root*/
            divider = new Label();
            divider.setId("divider-18");
            root.getChildren().add(divider);
            
            /*Add node to the root*/
            root.getChildren().add(profileIcon);
        }
        
        setupRegisterButton();
    }

    private void setupRegisterButton() {
        registerButton = new Label("+");
        registerButton.setMinSize(70, 70);

        /*Set node properties*/
        registerButton.setTextFill(Color.valueOf("#333"));
        registerButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#bbb"), new CornerRadii(100), new Insets(0))));
        
        /*Set CSS ID's*/
        registerButton.setId("profileIcon");
        
        /*Add a 10px padding to the root*/
        divider = new Label();
        divider.setId("divider-18");
        root.getChildren().add(divider);
        
        root.getChildren().add(registerButton);
    }
    
    public VBox getRoot() {
        return root;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(AUTH_EVENT.CREATE_PROFILE.name())){
            System.out.println("[SideProfileBar] - propertyChange");
            System.out.println(authObs.getListProfiles().size());
            setupProfileIcons(authObs.getListProfiles());
        }
    }
}
