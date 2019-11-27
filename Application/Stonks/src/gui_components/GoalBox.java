/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.GoalModel;
import stonks.Constants;

/**
 *
 * @author Utilizador
 */
public class GoalBox implements Constants {

    GoalModel goal;

    //Containers
    private VBox root;
    private BorderPane topContainer;

    //First row container
    private BorderPane firstRow;
    private HBox datesContainer;
    private HBox buttonsContainer;

    //Second row container
    private BorderPane secondRow;
    private HBox moneyContainer;
    private HBox fundsContainer;

    //Title Labels
    private Label nameTitle;
    private Label objectiveTitle;
    private Label accomplishedTitle;
    private Label deadlineTitle;
    private Label estimationTitle;
    private Label createdTitle;

    //Value labels
    private Label name;
    private Label objective;
    private Label accomplished;
    private Label deadline;
    private Label estimation;
    private Label created;

    //Buttons
    private Button btnDelete;
    private Button btnEdit;
    private Button btnFunds;

    public GoalBox(GoalModel goal) {

        this.goal = goal;

        root = new VBox();
        root.setId("goalBox");

        root.setMinSize(GOAL_BOX_WIDTH, GOAL_BOX_HEIGHT);
        root.setMaxSize(GOAL_BOX_WIDTH, GOAL_BOX_HEIGHT);

        setupGoalBox();
    }

    public void setupGoalBox() {

        this.name = new Label(goal.getName()); //01234567890123456789012345678901234567890123456789
        name.getStyleClass().addAll("title");
        topContainer = new BorderPane();
        topContainer.setId("topContainer");
        //topContainer.setMinSize(GOALBOX_WIDTH, 40);
        // topContainer.setMaxSize(GOALBOX_WIDTH, 40);
        //topContainer.getStyleClass().add("BACKGROUND_RED");
        topContainer.setLeft(name);
        topContainer.setRight(buttonsContainer);

        //First Row
        firstRow = new BorderPane();
        firstRow.setId("firstRow");
        firstRow.getStyleClass().add("row");

        //First row dates
        estimationTitle = new Label("ESTIMATION: ");
        estimation = new Label("22/11/2019");
        estimation.getStyleClass().addAll("lblValue");

        createdTitle = new Label("CREATED: ");
        created = new Label(goal.getCreationDate().getDayOfMonth() + "/" + goal.getCreationDate().getDayOfMonth() + "/" + goal.getCreationDate().getYear());
        created.getStyleClass().addAll("lblValue");

        if (goal.hasDeadline()) {
            deadlineTitle = new Label("DEADLINE: ");
            deadline = new Label(goal.getDeadlineDate().getDayOfMonth() + "/" + goal.getDeadlineDate().getDayOfMonth() + "/" + goal.getDeadlineDate().getYear());
            deadline.getStyleClass().addAll("lblValue");
        }

        datesContainer = new HBox();
        datesContainer.getChildren().addAll(createdTitle, created, estimationTitle, estimation);

        if (goal.hasDeadline()) {
            datesContainer.getChildren().addAll(deadlineTitle, deadline);
        }

        //First row buttons
        btnDelete = new Button("DELETE");
        btnEdit = new Button("EDIT");

        //Delete behaviour
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                DialogBox.display(DBOX_TYPE.CONFIRM, DBOX_CONTENT.CONFIRM_DELETE_GOAL);
            }
        });

        //Edit behaviour
        btnDelete.getStyleClass().addAll("btn", "btn-danger");
        btnEdit.getStyleClass().addAll("btn", "btn-primary");

        buttonsContainer = new HBox();
        buttonsContainer.getChildren().addAll(btnDelete, btnEdit);
        buttonsContainer.setId("rowBtn");

        firstRow.setRight(buttonsContainer);
        firstRow.setLeft(datesContainer);

        //Second Row
        secondRow = new BorderPane();
        secondRow.setId("secondRow");
        secondRow.getStyleClass().add("row");

        //Second row money
        objectiveTitle = new Label("Goal: ");
        objective = new Label(goal.getObjective() + "€");
        objective.getStyleClass().addAll("lblValue");

        accomplishedTitle = new Label("Accomplished: ");
        accomplished = new Label(goal.getWallet().getSavedMoney() + "€");
        accomplished.getStyleClass().addAll("lblValue");

        moneyContainer = new HBox();
        moneyContainer.getChildren().addAll(objectiveTitle, objective, accomplishedTitle, accomplished);

        secondRow.setLeft(moneyContainer);

        //Second row button
        btnFunds = new Button("MANAGE FUNDS");
        btnFunds.getStyleClass().addAll("btn", "btn-success");

        secondRow.setRight(btnFunds);

        root.getChildren().addAll(topContainer, firstRow, secondRow);
    }

    public VBox getRoot() {
        return root;
    }
}
