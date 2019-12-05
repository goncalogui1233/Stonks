package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.ProfileModel;
import observables.StonksObservable;
import stonks.Constants;

public class SideMenu implements Constants, PropertyChangeListener {

    private final StonksObservable stonksObs;
    private final VBox rootDiv;

    private HBox profileDiv;
    private VBox linkDiv;
    private VBox goalDiv;
    private Label profileIcon;
    private Color profileColor;
    private Color textColor;
    private BorderPane profileLinkDiv;
    private Label profileLink;
    private Label logoutLink;
    private Label dashboardLink;
    private Label goalLink;
    private PseudoClass last;
    private PseudoClass active;

    public SideMenu(StonksObservable stonksObs) {
        this.stonksObs = stonksObs;
        rootDiv = new VBox();

        rootDiv.setMinSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT);
        rootDiv.setMaxSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT);

        rootDiv.setId("sideMenu");

        setupProfileDiv();
        setupLinkDiv();
        setupGoalDiv();
        setupEventListeners();
        setupPropertyChangeListeners();
    }

    private void setupPropertyChangeListeners() {
        stonksObs.addPropertyChangeListener(STONKS_EVENT.GOTO_PROFILE_VIEW.name(), this);
        stonksObs.addPropertyChangeListener(STONKS_EVENT.GOTO_GOAL_VIEW.name(), this);
        stonksObs.addPropertyChangeListener(STONKS_EVENT.GOTO_DASHBOARD_VIEW.name(), this);
        stonksObs.addPropertyChangeListener(STONKS_EVENT.PROFILE_HAS_BEEN_AUTH.name(), this);
        stonksObs.addPropertyChangeListener(STONKS_EVENT.PROFILE_HAS_BEEN_EDITED.name(), this);
    }

    private void setupProfileDiv() {
        profileDiv = new HBox();

        profileDiv.setMinSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.15);
        profileDiv.setMaxSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.15);
        profileDiv.setId("profileDiv");

        profileIcon = new Label();
        profileIcon.setMinSize(75, 75);
        profileIcon.setId("profileIcon");

        profileLinkDiv = new BorderPane();
        profileLinkDiv.setId("profileLinkDiv");

        logoutLink = new Label("Logout");
        logoutLink.getStyleClass().addAll("link");
        profileLinkDiv.setCenter(logoutLink);
        profileDiv.getChildren().addAll(profileIcon, profileLinkDiv);

        rootDiv.getChildren().add(profileDiv);
    }

    private void setupLinkDiv() {
        linkDiv = new VBox();

        linkDiv.setMinSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.10);
        linkDiv.setMaxSize(SIDEMENU_WIDTH, SIDEMENU_HEIGHT * 0.50);
        linkDiv.setId("linkDiv");

        last = PseudoClass.getPseudoClass("last");
        active = PseudoClass.getPseudoClass("active");

        profileLink = new Label("Profile");
        profileLink.setMinWidth(SIDEMENU_WIDTH);
        profileLink.setMinHeight(SIDEMENU_HEIGHT * 0.07);

        dashboardLink = new Label("Dashboard");
        dashboardLink.setMinWidth(SIDEMENU_WIDTH);
        dashboardLink.setMinHeight(SIDEMENU_HEIGHT * 0.07);

        goalLink = new Label("Goals");
        goalLink.setMinWidth(SIDEMENU_WIDTH);
        goalLink.setMinHeight(SIDEMENU_HEIGHT * 0.07);
        goalLink.pseudoClassStateChanged(last, true);

        linkDiv.getChildren().addAll(profileLink, dashboardLink, goalLink);

        rootDiv.getChildren().add(linkDiv);
    }

    private void setupGoalDiv() {
        goalDiv = new VBox();

        /*ToDo*/
        rootDiv.getChildren().add(goalDiv);
    }

    public Pane getRoot() {
        return rootDiv;
    }

    private void setupEventListeners() {
        logoutLink.setOnMouseClicked(e -> {
            stonksObs.logout();
        });
        profileLink.setOnMouseClicked(e -> {
            stonksObs.firePropertyChange(STONKS_EVENT.GOTO_PROFILE_VIEW);
        });
        dashboardLink.setOnMouseClicked(e -> {
            stonksObs.firePropertyChange(STONKS_EVENT.GOTO_DASHBOARD_VIEW);
        });
        goalLink.setOnMouseClicked(e -> {
            stonksObs.firePropertyChange(STONKS_EVENT.GOTO_GOAL_VIEW);
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        profileLink.pseudoClassStateChanged(active, false);
        goalLink.pseudoClassStateChanged(active, false);
        dashboardLink.pseudoClassStateChanged(active, false);

        if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_PROFILE_VIEW.name())) {
            profileLink.pseudoClassStateChanged(active, true);
        } else if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_GOAL_VIEW.name())) {
            goalLink.pseudoClassStateChanged(active, true);
        } else if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_DASHBOARD_VIEW.name())) {
            dashboardLink.pseudoClassStateChanged(active, true);
        } else if (evt.getPropertyName().equals(STONKS_EVENT.PROFILE_HAS_BEEN_AUTH.name())) {
            updateSideMenu();
        } else if (evt.getPropertyName().equals(STONKS_EVENT.PROFILE_HAS_BEEN_EDITED.name())) {
            updateSideMenu();
        }
    }

    private void updateSideMenu() {
        ProfileModel authProfile = stonksObs.getAuthProfile();

        profileIcon.setText(""
                + authProfile.getFirstName().charAt(0)
                + authProfile.getLastName().charAt(0));

        profileColor = Color.valueOf(authProfile.getColor());

        if (((profileColor.getRed() * 0.333)
                + (profileColor.getGreen() * 0.333)
                + (profileColor.getBlue() * 0.333)) > 0.3) {
            textColor = Color.valueOf("#000");
        } else {
            textColor = Color.valueOf("#FFF");
        }
        profileIcon.setTextFill(textColor);
        profileIcon.setBackground(new Background(new BackgroundFill(profileColor, new CornerRadii(100), new Insets(3))));
        profileIcon.setBorder(new Border(new BorderStroke(Color.valueOf("#111"), BorderStrokeStyle.SOLID, new CornerRadii(100), new BorderWidths(5))));
    }
}
