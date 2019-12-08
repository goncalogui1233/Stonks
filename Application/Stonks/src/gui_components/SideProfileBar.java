package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.ProfileModel;
import observables.AuthenticationObservable;
import stonks.Constants;

public class SideProfileBar implements Constants, PropertyChangeListener {

    private final VBox root;
    private final AuthenticationObservable authObs;

    private Label profileIcon;
    private String profileInitials;
    private Color textColor;
    private Color profileColor;
    private Label divider;
    private Label registerButton;

    private final PseudoClass selected_black;
    private final PseudoClass selected_white;

    public SideProfileBar(AuthenticationObservable authObs) {
        this.authObs = authObs;

        root = new VBox();
        root.setMinSize(SIDEPROFILEBAR_WIDTH, SIDEPROFILEBAR_HEIGHT);
        root.setMaxSize(SIDEPROFILEBAR_WIDTH, SIDEPROFILEBAR_HEIGHT);
        root.setId("sideProfileBar");

        selected_black = PseudoClass.getPseudoClass("selected_black");
        selected_white = PseudoClass.getPseudoClass("selected_white");

        setupPropertyChangeListeners();
        setupProfileIcons(authObs.getListProfiles());
    }

    public VBox getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        authObs.addPropertyChangeListener(AUTH_EVENT.CREATE_PROFILE.name(), this);
        authObs.addPropertyChangeListener(AUTH_EVENT.UPDATE_SELECTION.name(), this);
        authObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW.name(), this);
    }

    private void setupProfileIcons(HashMap<Integer, ProfileModel> listProfiles) {
        root.getChildren().clear();

        for (ProfileModel profile : listProfiles.values()) {
            profileInitials = ""
                    + profile.getFirstName().charAt(0)
                    + profile.getLastName().charAt(0);

            profileIcon = new Label(profileInitials);
            profileIcon.setMinSize(60, 60);
            profileIcon.setPickOnBounds(false);

            profileColor = Color.valueOf(profile.getColor());

            /*If background is too dark, text is white or black if too bright*/
            if (((profileColor.getRed() * 0.333)
                    + (profileColor.getGreen() * 0.333)
                    + (profileColor.getBlue() * 0.333)) > 0.3) {
                profileIcon.getProperties().put("blackBorder", true);
                textColor = Color.valueOf("#111");
            } else {
                profileIcon.getProperties().put("blackBorder", false);
                textColor = Color.valueOf("#eee");
            }

            /*Set node properties*/
            profileIcon.setTextFill(textColor);
            profileIcon.setBackground(new Background(new BackgroundFill(profileColor, new CornerRadii(100), new Insets(-5))));

            /*Set CSS ID's*/
            profileIcon.setId("profileIcon");
            profileIcon.getProperties().put("profileId", profile.getId());

            /*Add a 10px padding to the root*/
            divider = new Label();
            divider.setId("divider-18");
            root.getChildren().add(divider);

            /*Add onClick event*/
            profileIcon.setOnMouseClicked(e -> {
                authObs.profileClicked(profile.getId());
            });

            /*Add node to the root*/
            root.getChildren().add(profileIcon);
        }

        setupRegisterButton();
    }

    private void setupRegisterButton() {
        registerButton = new Label("+");
        registerButton.setMinSize(70, 70);
        registerButton.setPickOnBounds(false);

        /*Set node properties*/
        registerButton.setTextFill(Color.valueOf("#333"));
        registerButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#bbb"), new CornerRadii(100), new Insets(0))));

        /*Add onClick event*/
        registerButton.setOnMouseClicked(e -> {
            authObs.addProfileClicked();
        });

        /*Set CSS ID's*/
        registerButton.setId("profileIcon");

        /*Add a 10px padding to the root*/
        divider = new Label();
        divider.setId("divider-18");
        root.getChildren().add(divider);

        root.getChildren().add(registerButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(AUTH_EVENT.CREATE_PROFILE.name())) {
            setupProfileIcons(authObs.getListProfiles());
        } else if(evt.getPropertyName().equals(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW.name())){
            setupProfileIcons(authObs.getListProfiles());
        }else if (evt.getPropertyName().equals(AUTH_EVENT.UPDATE_SELECTION.name())) {
            boolean blackBorder;
            for (Node node : root.getChildren()) {
                node.pseudoClassStateChanged(selected_black, false);
                node.pseudoClassStateChanged(selected_white, false);

                try {
                    blackBorder = (Boolean) node.getProperties().get("blackBorder");

                    if ((Integer) node.getProperties().get("profileId")
                            == authObs.getViewSelectedProfileId()) {
                        if (blackBorder) {
                            node.pseudoClassStateChanged(selected_black, true);
                        } else {
                            node.pseudoClassStateChanged(selected_white, true);
                        }
                    }
                } catch (NullPointerException ex) {
                } catch (ClassCastException ex) {
                }
            }
        }
    }
}
