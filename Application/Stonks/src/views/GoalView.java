package views;

import controllers.GoalController;
import gui_components.GoalBox;
import gui_components.GoalForm;
import gui_components.SideMenu;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.GoalModel;
import observables.GoalsObservable;
import stonks.Constants;

public class GoalView implements Constants, PropertyChangeListener {

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

    //Buttons
    private Button btnAdd;
    private ToggleButton tbtnFilter;

    private GoalsObservable goalsObs;
    private final HBox root;
    private GoalForm form;

    public GoalView(GoalsObservable goalsObs) {
        this.goalsObs = goalsObs;

        root = new HBox();
        root.setId("goalView");
        root.setMinSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);
        root.setMaxSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);
        
        form = new GoalForm(goalsObs);

        setupPropertyChangeListeners();
        setupGoalView();
    }

    public HBox getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        goalsObs.addPropertyChangeListener(GOAL_EVENT.CREATE_GOAL.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.DELETE_GOAL.name(), this);
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

        tbtnFilter = new ToggleButton("Show Completed");
        tbtnFilter.getStyleClass().addAll("lala");

        topContainerButtons.getChildren().addAll(tbtnFilter, btnAdd);

        topContainer.setLeft(viewTitle);
        topContainer.setRight(topContainerButtons);

        //Middle container
        middleContainer = new VBox();
        middleContainer.setId("goalsMiddleContainer");

        //No goals message
        lblNoGoalsMsg = new Label("This profile doesn’t have any goals. Add a new goal and let us help you achieve it!");
        lblNoGoalsMsg.setId("noGoalsMsg");

        //Goals container
        goalsContainer = new VBox();
        goalsContainer.setId("goalsContainer");
        goalsContainer.setMinWidth(GOALS_CONTAINER_WIDTH);
        goalsContainer.setMaxWidth(GOALS_CONTAINER_WIDTH);
 

//        this.displayProfileGoals();

        //Scrollpane
        goalsScrollPane = new ScrollPane();
        goalsScrollPane.setStyle("-fx-background-color:transparent;");
        //goalsScrollPane.setMinWidth(GOAL_BOX_WIDTH);
        goalsScrollPane.setMinHeight(500);
        goalsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        goalsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        goalsScrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>(){
            @Override
            public void handle(ScrollEvent t) {
                if(t.getDeltaX() != 0){
                    t.consume();
                }
            }
            
        });

        goalsScrollPane.setContent(goalsContainer);

        //middleContainer.getChildren().addAll(lblNoGoalsMsg);
        //View container
        viewContent = new VBox();
        viewContent.setMinSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);
        viewContent.setMaxSize(GOAL_VIEW_WIDTH, GOAL_VIEW_HEIGHT);
        viewContent.getChildren().addAll(topContainer, middleContainer);
        viewContent.setId("goalViewContent");

        root.getChildren().addAll(new SideMenu().getRoot(), viewContent);

        btnAdd.setOnAction(e -> {
            form.display(0);
        });
    }

    public void displayProfileGoals() {
        try{
            goalsContainer.getChildren().removeAll(goalsContainer.getChildren());
            middleContainer.getChildren().removeAll(middleContainer.getChildren());

        goalsContainer.getChildren().removeAll(goalsContainer.getChildren());
        middleContainer.getChildren().removeAll(middleContainer.getChildren());

        if (goalsObs.getAuthProfile().getGoals().size() < 1) {
            middleContainer.getChildren().add(lblNoGoalsMsg);
        } else {
            middleContainer.getChildren().add(goalsScrollPane);
            for (GoalModel goal : goalsObs.getAuthProfile().getGoals().values()) {
                Label divider = new Label();
                divider.getStyleClass().addAll("divider");
                goalsContainer.getChildren().addAll(new GoalBox(goal, goalsObs).getRoot(), divider);
            }
        }catch(NullPointerException ex){ }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(GOAL_EVENT.CREATE_GOAL.name())) {
            displayProfileGoals();
        }
        if (evt.getPropertyName().equals(GOAL_EVENT.DELETE_GOAL.name())) {
            displayProfileGoals();
        }
    }
}
