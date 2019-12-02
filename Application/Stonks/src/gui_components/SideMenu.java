package gui_components;

import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import stonks.Constants;

public class SideMenu implements Constants{
    VBox rootDiv;
    
    HBox profileDiv;
    VBox linkDiv;
    VBox goalDiv;
    
    public SideMenu() {
        rootDiv = new VBox();
        
        rootDiv.setMinSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT);
        rootDiv.setMaxSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT);
        
        rootDiv.setId("sideMenu");
        
        setupSideMenu();
    }
    
    private void setupSideMenu(){
        setupProfileDiv();
        setupLinkDiv();
        setupGoalDiv();
    }
    
    private void setupProfileDiv(){
        profileDiv = new HBox();
        
        profileDiv.setMinSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.15);
        profileDiv.setMaxSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.15);
        profileDiv.setId("profileDiv");
        
        Label profileIcon = new Label("U1"/*ADD PROFILE INITIALS*/);
        
        profileIcon.setMinSize(75, 75);
        profileIcon.setId("profileIcon");
        
        Color profileColor = Color.DARKBLUE /*ADD PROFILE COLOR*/;
        
        Color textColor;
        if(((profileColor.getRed() * 0.333) + 
                (profileColor.getGreen() * 0.333) + 
                (profileColor.getBlue() * 0.333)) > 0.3){
            textColor = Color.valueOf("#000"); 
        }else{
            textColor = Color.valueOf("#FFF"); 
        }
        profileIcon.setTextFill(textColor);
        profileIcon.setBackground(new Background(new BackgroundFill(profileColor, new CornerRadii(100), new Insets(3))));
        profileIcon.setBorder(new Border(new BorderStroke(Color.valueOf("#111"), BorderStrokeStyle.SOLID, new CornerRadii(100), new BorderWidths(5))));
        
        VBox profileLinkDiv = new VBox();
        profileLinkDiv.setId("profileLinkDiv");
        
        Label profileLink = new Label("Profile");
        Label logoutLink = new Label("Logout");
        profileLinkDiv.getChildren().addAll(profileLink, logoutLink);
        profileDiv.getChildren().addAll(profileIcon, profileLinkDiv);
        
        rootDiv.getChildren().add(profileDiv);
    }
    
    private void setupLinkDiv(){
        linkDiv = new VBox();
        
        linkDiv.setMinSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.10);
        linkDiv.setMaxSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.50);
        linkDiv.setId("linkDiv");
        
        Label dashboardLink = new Label("Dashboard");
        dashboardLink.setMinWidth(SIDEMENU_WIDTH-2);
        dashboardLink.setMinHeight(SIDEMENU_HEIGHT * 0.07);
        
        PseudoClass last = PseudoClass.getPseudoClass("last");
        Label goalLink = new Label("Goals");
        goalLink.setMinWidth(SIDEMENU_WIDTH-2);
        goalLink.setMinHeight(SIDEMENU_HEIGHT * 0.07);
        goalLink.pseudoClassStateChanged(last, true);
        
        linkDiv.getChildren().addAll(dashboardLink, goalLink);
        
        rootDiv.getChildren().add(linkDiv);
    }

    private void setupGoalDiv() {
        goalDiv = new VBox();
        
        /*ToDo*/
        
        rootDiv.getChildren().add(goalDiv);
    }
    
    public Pane getRoot(){
        return rootDiv;
    }
}
