package views;

import controllers.GoalController;
import gui_components.GoalBox;
import gui_components.GoalForm;
import gui_components.SideMenu;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class GoalView implements Constants {
    private GoalController controller;
    private final HBox root;

    public GoalView(GoalController controller) {
        this.controller = controller;
        
        root = new HBox();
        root.setId("goalView");
        
        setupGoalView();
    }

    private void setupGoalView() {
        //Containers
        VBox viewContent;
        BorderPane topContainer;
        VBox goalsContainer;

        //Labels
        Label viewTitle;

        //Buttons
        Button btnAdd;
        
        //Top container
        topContainer = new BorderPane();
        topContainer.getStyleClass().addAll("goalsTopContainer");
        topContainer.setMaxSize(APP_WIDTH - SIDEMENU_WIDTH - 420, 100);

        //Title
        viewTitle = new Label("My Goals");
        viewTitle.getStyleClass().addAll("title");

        //Button
        btnAdd = new Button("Add goal");
        btnAdd.getStyleClass().addAll("btn", "btn-default");

        topContainer.setLeft(viewTitle);
        topContainer.setRight(btnAdd);

        //Goals container
        goalsContainer = new VBox();
        goalsContainer.setId("goalsContainer");
        goalsContainer.setMinSize(APP_WIDTH - SIDEMENU_WIDTH, APP_HEIGHT);
        goalsContainer.setMaxSize(APP_WIDTH - SIDEMENU_WIDTH, APP_HEIGHT);
        goalsContainer.getChildren().addAll(new GoalBox().getRoot());

        //View container
        viewContent = new VBox();
        viewContent.getChildren().addAll(topContainer, goalsContainer);
        viewContent.setId("goalViewContent");

        root.getChildren().addAll(new SideMenu().getRoot(), viewContent);

        btnAdd.setOnAction(e -> {
            GoalForm form = new GoalForm(controller);
            form.display(0);
        });
    }

    public HBox getRoot() {
        return root;
    }
}
