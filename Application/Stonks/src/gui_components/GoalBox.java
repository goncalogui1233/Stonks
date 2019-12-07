/* 
TODO: Recebe lista de goalBoxes, remove-se da lista, edita as info de si própio, notifica a vista
 */
package gui_components;

import exceptions.AuthenticationException;
import exceptions.EmptyDepositException;
import exceptions.GoalNotFoundException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.GoalModel;
import observables.GoalsObservable;
import stonks.Constants;

/**
 *
 * @author Utilizador
 */
public class GoalBox implements Constants, PropertyChangeListener{

    GoalModel goal;
    GoalsObservable goalsObs;

    //Containers 
    private VBox root;
    private VBox topContainer;

    //Progress bar
    ProgressBar pbGoalProgress;

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
    private Label percentage;
    private Label objective;
    private Label accomplished;
    private Label deadline;
    private Label estimation;
    private Label created;

    //Buttons 
    private Button btnDelete;
    private Button btnEdit;
    private Button btnFunds;

    private ManageFundsForm form;

    public GoalBox(GoalModel goal, GoalsObservable goalsObs) {

        this.goal = goal;
        this.goalsObs = goalsObs;

        root = new VBox();
        root.setId("goalBox");
        root.getStyleClass().add("stonks-box");

        root.setMinSize(GOAL_BOX_WIDTH, GOAL_BOX_HEIGHT);
        root.setMaxSize(GOAL_BOX_WIDTH, GOAL_BOX_HEIGHT);

        setupGoalBox();
    }

    private void setupPropertyChangeListeners() {
        goalsObs.addPropertyChangeListener(GOAL_EVENT.CREATE_GOAL.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.DELETE_GOAL.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.EDIT_GOAL.name(), this);
        goalsObs.addPropertyChangeListener(GOAL_EVENT.UPDATE_WALLET.name(), this);
        goalsObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOTO_GOAL_VIEW.name(), this);
    }

    public void setupGoalBox() {
        this.name = new Label(goal.getName()); //01234567890123456789012345678901234567890123456789 
        name.getStyleClass().addAll("title");
        try {
            DecimalFormat df = new DecimalFormat("#,##0.0#");

            percentage = new Label(df.format(goalsObs.getGoalProgress(goal.getId()) * 100) + "%");
        } catch (AuthenticationException ex) {
            DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
        } catch (GoalNotFoundException ex) {
            DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
        }
        percentage.getStyleClass().add("percentage");
        topContainer = new VBox();
        topContainer.setId("topContainer");
        //topContainer.setMinSize(GOALBOX_WIDTH, 40); 
        // topContainer.setMaxSize(GOALBOX_WIDTH, 40); 
        //topContainer.getStyleClass().add("BACKGROUND_RED"); 

        topContainer.getChildren().addAll(name, percentage);

        //Goal progress bar
        try {
            pbGoalProgress = new ProgressBar(goalsObs.getGoalProgress(goal.getId()));
            pbGoalProgress.setMinSize(GOAL_BOX_WIDTH - 2, 20);
            pbGoalProgress.setMaxSize(GOAL_BOX_WIDTH - 2, 20);
            topContainer.getChildren().add(pbGoalProgress);
        } catch (AuthenticationException ex) {
            DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
        } catch (GoalNotFoundException ex) {
            DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
        }

        //First Row 
        firstRow = new BorderPane();
        firstRow.setId("firstRow");
        firstRow.getStyleClass().add("row");

        //First row - dates 
        createdTitle = new Label("CREATED: ");
        created = new Label(goal.getCreationDate().getDayOfMonth() + "/" + goal.getCreationDate().getMonthValue() + "/" + goal.getCreationDate().getYear());
        created.getStyleClass().addAll("lblValue");

        if (goal.hasDeadline()) {
            deadlineTitle = new Label("DEADLINE: ");
            deadline = new Label(goal.getDeadlineDate().getDayOfMonth() + "/" + goal.getDeadlineDate().getMonthValue() + "/" + goal.getDeadlineDate().getYear());
            deadline.getStyleClass().addAll("lblValue");
        }

        datesContainer = new HBox();
        datesContainer.getChildren().addAll(createdTitle, created);

        if (goal.getWallet().getSavedMoney() > 0) {
            estimationTitle = new Label("ESTIMATION: ");

            try {
                //estimation = new Label("22/11/2019");
                LocalDate estimationDate = goalsObs.getEstimatedDate(goal.getId());
                estimation = new Label(estimationDate.getDayOfMonth() + "/" + estimationDate.getMonthValue() + "/" + estimationDate.getYear());
            } catch (AuthenticationException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
            } catch (GoalNotFoundException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
            } catch (EmptyDepositException ex) {
                System.out.println(ex);
            }
            estimation.getStyleClass().addAll("lblValue");

            datesContainer.getChildren().addAll(estimationTitle, estimation);
        }

        if (goal.hasDeadline()) {
            datesContainer.getChildren().addAll(deadlineTitle, deadline);
        }

        if (goal.hasDeadline()) {
            datesContainer.getChildren().addAll(deadlineTitle, deadline);
        }

        //First row - buttons 
        btnDelete = new Button("DELETE");
        btnEdit = new Button("EDIT");

        btnDelete.getStyleClass().addAll("btn", "btn-danger");
        btnEdit.getStyleClass().addAll("btn", "btn-primary");

        buttonsContainer = new HBox();
        buttonsContainer.getChildren().addAll(btnDelete, btnEdit);
        buttonsContainer.setId("rowBtn");

        firstRow.setRight(buttonsContainer);
        firstRow.setLeft(datesContainer);

        //Second Row - money 
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

        //Second row - buttons
        btnFunds = new Button("MANAGE FUNDS");
        btnFunds.getStyleClass().addAll("btn", "btn-success");

        secondRow.setRight(btnFunds);

        root.getChildren().addAll(topContainer, firstRow, secondRow);

        //Delete behaviour 
        btnDelete.setOnAction(e -> {
            DBOX_CONTENT content;
            content = DBOX_CONTENT.CONFIRM_GOAL_DELETE;
            content.setSubExtra(goal.getName());

            if (DialogBox.display(DBOX_TYPE.CONFIRM, content) == DBOX_RETURN.YES) {
                boolean isGoalDeleted = false;

                try {
                    isGoalDeleted = goalsObs.removeGoal(goal.getId());
                } catch (AuthenticationException ex) {
                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                } catch (GoalNotFoundException ex) {
                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
                }

                if (isGoalDeleted) {
                    DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_GOAL_DELETE);
                } else {
                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_CREATE);
                }
            }
        });

        //Edit behaviour 
        btnEdit.setOnAction(e -> {
            DBOX_CONTENT content;
            content = DBOX_CONTENT.CONFIRM_GOAL_DELETE;
            content.setSubExtra(goal.getName());

            GoalForm form = new GoalForm(goalsObs);
            form.display(goal.getId());
        });

        //Manage Funds of Goal
        btnFunds.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ManageFundsForm f = new ManageFundsForm(goalsObs);
                f.display(goal.getId());
            }
        });
    }

    public VBox getRoot() {
        return root;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
