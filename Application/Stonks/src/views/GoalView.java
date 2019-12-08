package views;

import gui_components.GoalBox;
import gui_components.GoalForm;
import gui_components.SideMenu;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javafx.css.PseudoClass;
import java.util.HashMap;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.GoalModel;
import observables.GoalsObservable;
import stonks.Constants;

public class GoalView implements Constants, PropertyChangeListener {

    private boolean showCompleted;
    private HashMap<Integer, GoalBox> goalsList;

    //Containers 
    private VBox viewContent;
    private BorderPane topContainer;
    private HBox topContainerButtons;
    private VBox goalsContainer;
    private ScrollPane goalsScrollPane;
    private VBox middleContainer;

    //Labels 
    private Label viewTitle;
    private Label lblNoGoalsMsg;
    private Label lblOnlyCompletedGoals;

    //Buttons 
    private Button btnAdd;

    //Checkbox
    private CheckBox cbFilter;

    //Toggle
    private Label toggleLabel;
    private VBox toggleDiv;
    private Label toggleButton;
    
    private PseudoClass show;
    
    private final GoalsObservable goalsObs;
    private final HBox root;
    private final GoalForm form;

    public GoalView(GoalsObservable goalsObs) {
        this.goalsObs = goalsObs;
        showCompleted = false;
        goalsList = new HashMap<>();

        root = new HBox();
        root.setId("goalView");
        root.setMinSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);
        root.setMaxSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);

        form = new GoalForm(goalsObs, goalsList);

        setupPropertyChangeListeners();
        setupGoalView();
    }

    public HBox getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        form.addPropertyChangeListener(GOAL_EVENT.CREATE_GOAL.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.DELETE_GOAL.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.GOAL_COMPLETED.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.EDIT_GOAL.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.GOAL_MANAGE_FUNDS.name(), this);
        goalsObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOTO_GOAL_VIEW.name(), this);
    }

    private void setupGoalView() {

        //Top container 
        topContainer = new BorderPane();
        topContainer.getStyleClass().addAll("goalsTopContainer");
        //topContainer.setMaxSize(APP_WIDTH - SIDEMENU_WIDTH - 420, 100); 

        //Top container Title 
        viewTitle = new Label("My Goals");
        viewTitle.getStyleClass().addAll("title");

        //Top container Buttons 
        topContainerButtons = new HBox(5);
        btnAdd = new Button("Add goal");
        btnAdd.getStyleClass().addAll("btn-default");

        //Toggle Stuff
        show = PseudoClass.getPseudoClass("show");
        
        toggleLabel = new Label("Completed");
        toggleLabel.setMinHeight(35);
        toggleLabel.setMaxHeight(35);
        toggleLabel.setId("toggleLabel");
        
        toggleDiv = new VBox();
        toggleDiv.setMinSize(90, 32);
        toggleDiv.setMaxHeight(32);
        toggleDiv.setPickOnBounds(true);
        toggleDiv.setId("toggleDiv");
        
        toggleButton = new Label();
        toggleButton.setMinSize(50, 32);
        toggleButton.setMaxHeight(32);
        toggleButton.getStyleClass().addAll("button", "btn-default");
        toggleDiv.pseudoClassStateChanged(show, showCompleted);
        
        if(showCompleted){
            toggleButton.setText("Hide");
            toggleDiv.setAlignment(Pos.CENTER_RIGHT);
        }else{
            toggleButton.setText("Show");
            toggleDiv.setAlignment(Pos.CENTER_LEFT);
        }
        
        toggleDiv.setOnMouseClicked(e -> {
            showCompleted = !showCompleted;
            toggleDiv.pseudoClassStateChanged(show, showCompleted);
            
            if(showCompleted){
                toggleButton.setText("Hide");
                toggleDiv.setAlignment(Pos.CENTER_RIGHT);
            }else{
                toggleButton.setText("Show");
                toggleDiv.setAlignment(Pos.CENTER_LEFT);
            }
            
            displayProfileGoals();
        });
        
        toggleDiv.getChildren().add(toggleButton);
        topContainerButtons.getChildren().addAll(toggleLabel, toggleDiv, btnAdd);

        topContainer.setLeft(viewTitle);
        topContainer.setRight(topContainerButtons);

        //Middle container 
        middleContainer = new VBox();
        middleContainer.setId("goalsMiddleContainer");

        //No goals message 
        lblNoGoalsMsg = new Label("This profile doesn't have any goals. Add a new goal and let us help you achieve it!");
        lblNoGoalsMsg.setId("noGoalsMsg");

        //Only has completed goals Mgs
        lblOnlyCompletedGoals = new Label("This profile has achieved all goals!  Add a new goal and let us help you achieve it!");
        lblOnlyCompletedGoals.setId("noGoalsMsg");

        //Goals container 
        goalsContainer = new VBox();
        goalsContainer.setId("goalsContainer");
        goalsContainer.setMinWidth(GOALS_CONTAINER_WIDTH);
        goalsContainer.setMaxWidth(GOALS_CONTAINER_WIDTH);

        //Scrollpane 
        goalsScrollPane = new ScrollPane();
        goalsScrollPane.setStyle("-fx-background-color:transparent;");
        //goalsScrollPane.setMinWidth(GOAL_BOX_WIDTH); 
        goalsScrollPane.setMinHeight(500);
        goalsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        goalsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        goalsScrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent t) {
                if (t.getDeltaX() != 0) {
                    t.consume();
                }
            }

        });

        goalsScrollPane.setContent(goalsContainer);

        //View container 
        viewContent = new VBox();
        viewContent.setMinSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);
        viewContent.setMaxSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);
        viewContent.getChildren().addAll(topContainer, middleContainer);
        viewContent.setId("goalViewContent");

        root.getChildren().addAll(new SideMenu(goalsObs.getStonksObs()).getRoot(), viewContent);

        btnAdd.setOnAction(e -> {
            form.display(0);
        });
    }

    public void displayProfileGoals() {
        try {
            goalsContainer.getChildren().removeAll(goalsContainer.getChildren());
            middleContainer.getChildren().removeAll(middleContainer.getChildren());

            if (goalsObs.getAuthProfile().getGoals().size() < 1) {
                middleContainer.getChildren().add(lblNoGoalsMsg);
            } else if (!goalsObs.getStonksObs().getAuthProfile().hasIncompleteGoals() && !showCompleted) {
                middleContainer.getChildren().add(lblOnlyCompletedGoals);
            } else {
                middleContainer.getChildren().add(goalsScrollPane);
                List<GoalModel> listGoals = new ArrayList<>(goalsObs.getAuthProfile().getGoals().values());
                GoalModel.orderListByProgress(listGoals);
                for (GoalModel goal : listGoals) {
                    Label divider = new Label();
                    divider.getStyleClass().addAll("divider");
                    if (showCompleted) {
                        goalsContainer.getChildren().addAll(goalsList.get(goal.getId()).getRoot(), divider);
                    } else {
                        if (!goal.isCompleted()) {
                            System.out.println(goalsList.size());
                            goalsContainer.getChildren().addAll(goalsList.get(goal.getId()).getRoot(), divider);
                        }

                    }

                }
            }
        } catch (NullPointerException ex) {
        }
    }

    private void setupGoalBoxes() {
        for (GoalModel goal : goalsObs.getAuthProfile().getGoals().values()) {
            goalsList.put(goal.getId(), new GoalBox(goal, goalsObs, goalsList));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_GOAL_VIEW.name())) {
            setupGoalBoxes();
            displayProfileGoals();
        }
        if (evt.getPropertyName().equals(GOAL_EVENT.CREATE_GOAL.name())) {
            displayProfileGoals();
        }
        if (evt.getPropertyName().equals(GOAL_EVENT.DELETE_GOAL.name())) {
            displayProfileGoals();
        }
        if (evt.getPropertyName().equals(GOAL_EVENT.GOAL_COMPLETED.name())) {
            displayProfileGoals();
        }
        if (evt.getPropertyName().equals(GOAL_EVENT.EDIT_GOAL.name())) {
            displayProfileGoals();
        }
        if (evt.getPropertyName().equals(GOAL_EVENT.GOAL_MANAGE_FUNDS.name())) {
            displayProfileGoals();
        }
    }
}
