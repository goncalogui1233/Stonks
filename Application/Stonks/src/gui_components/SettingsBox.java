package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import observables.SettingsObservable;
import stonks.Constants;

public class SettingsBox implements Constants, PropertyChangeListener{
    private final SettingsObservable settObs;
    private final BorderPane root;

    private boolean activeDarkMode;
    
    //Containers
    private VBox titleContainer;
    private VBox settingsContainer;
    private HBox buttonContainer;

    //Input Divs
    private HBox darkModeDiv;
    private VBox toggleDiv;

    //Title Labels
    private Label lblTitle;
    private Label lblDarkMode;

    //Field Inputs
    private Label toggleButton;
    
    private PseudoClass active;

    //Buttons
    private Button btnSave;

    public SettingsBox(SettingsObservable settObs) {
        this.settObs = settObs;
        root = new BorderPane();

        root.setMinWidth(PROFILE_EDIT_VIEW_WIDTH - 6/*CHECK WHY THIS IS NEEDED*/);
        root.setMaxSize(PROFILE_EDIT_VIEW_WIDTH - 6/*CHECK WHY THIS IS NEEDED*/, PROFILE_EDIT_VIEW_HEIGHT);

        setupSettingsBox();
        setupEventListeners();
        setupPropertyChangeListeners();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void setupSettingsBox() {
        lblTitle = new Label("Settings");
        titleContainer = new VBox();

        settingsContainer = new VBox();
        settingsContainer.setMaxWidth(400);
        BorderPane.setAlignment(settingsContainer, Pos.CENTER_LEFT);


        //Toggle Stuff
        active = PseudoClass.getPseudoClass("active");
        
        lblDarkMode = new Label("Dark Mode: ");
        lblDarkMode.setMinHeight(35);
        lblDarkMode.setMaxHeight(35);
        lblDarkMode.setId("toggleLabel");
        
        toggleDiv = new VBox();
        toggleDiv.setMinSize(90, 32);
        toggleDiv.setMaxHeight(32);
        toggleDiv.setPickOnBounds(true);
        toggleDiv.setId("toggleDiv");
        
        toggleButton = new Label();
        toggleButton.setMinSize(50, 32);
        toggleButton.setMaxHeight(32);
        toggleButton.getStyleClass().addAll("button", "btn-default");
        toggleDiv.pseudoClassStateChanged(active, activeDarkMode);
        
        if(activeDarkMode){
            toggleButton.setText("On");
            toggleDiv.setAlignment(Pos.CENTER_RIGHT);
        }else{
            toggleButton.setText("Off");
            toggleDiv.setAlignment(Pos.CENTER_LEFT);
        }
        
        toggleDiv.setOnMouseClicked(e -> {
            activeDarkMode = !activeDarkMode;
            toggleDiv.pseudoClassStateChanged(active, activeDarkMode);
            
            if(activeDarkMode){
                toggleButton.setText("On");
                toggleDiv.setAlignment(Pos.CENTER_RIGHT);
            }else{
                toggleButton.setText("Off");
                toggleDiv.setAlignment(Pos.CENTER_LEFT);
            }
        });
        
        toggleDiv.getChildren().add(toggleButton);

        buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        btnSave = new Button("Save Changes");

        /*Initialize divs*/
        darkModeDiv = new HBox();

        /*Add the title to the title box*/
        titleContainer.getChildren().addAll(lblTitle);

        /*Add all labels and inputs to the form box*/
        darkModeDiv.getChildren().addAll(lblDarkMode, toggleDiv);
        settingsContainer.getChildren().addAll(darkModeDiv);

        /*Add save button to the button container*/
        buttonContainer.getChildren().addAll(btnSave);

        /*Set CSS ID's to nodes*/
        root.setId("settingBox");

        /*Set CSS Classes to nodes*/
        settingsContainer.getStyleClass().add("form");
        titleContainer.getStyleClass().add("titleBox");

        darkModeDiv.getStyleClass().addAll("fieldDiv");

        btnSave.getStyleClass().addAll("button", "btn-default", "btn-form-sharp");

        /*Add title on top, formContainer on center, button on bottom*/
        root.setTop(titleContainer);
        root.setCenter(settingsContainer);
        root.setBottom(buttonContainer);
    }

    private void setupEventListeners() {
        btnSave.setOnMouseClicked(e -> {
            settObs.setDarkMode(activeDarkMode);
        });
    }

    private void setupPropertyChangeListeners() {
        settObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.PROFILE_HAS_BEEN_AUTH.name(), this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(settObs.getStonksObs().getAuthProfile().getSettings().isDarkMode()){
            toggleButton.setText("On");
            toggleDiv.setAlignment(Pos.CENTER_RIGHT);
        }else{
            toggleButton.setText("Off");
            toggleDiv.setAlignment(Pos.CENTER_LEFT);
        }
    }
}
