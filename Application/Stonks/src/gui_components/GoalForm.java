/* 
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates 
 * and open the template in the editor. 
 */
package gui_components;

import exceptions.AuthenticationException;
import exceptions.GoalNotFoundException;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.GoalModel;
import observables.GoalsObservable;
import stonks.Constants;

/**
 *
 * @author Tiago
 */
public class GoalForm extends PropertyChangeSupport implements Constants {

    private int goalId = 0;
    private final GoalsObservable goalsObs;
    private Stage goalForm;
    private HashMap<Integer, GoalBox> goalsList;

    //Containers 
    private BorderPane rootLayout;
    private BorderPane topLayout;
    private VBox titleContainer;
    private Pane imageContainer;
    private ImageView closeBtn;
    private VBox form;
    private VBox nameDiv;
    private VBox objectiveDiv;
    private VBox deadlineDiv;
    private HBox deadlineInlineInput;
    private BorderPane bottomLayout;

    //Labels 
    private Label title;
    private Label subtitle;
    private Label lblName;
    private Label errorName;
    private Label lblObjective;
    private Label errorObjective;
    private Label lblDeadline;
    private Label errorDeadline;

    //Inputs 
    private TextField txfName;
    private TextField txfObjective;
    private CheckBox hasDeadline;
    private DatePicker dpDeadline;

    //Buttons 
    private Button btnSubmit;

    public GoalForm(GoalsObservable goalsObs, HashMap<Integer, GoalBox> goalsList) {
        super(goalsObs);
        this.goalsObs = goalsObs;
        this.goalsList = goalsList;
    }

    public boolean display(int goalId) {
        this.goalId = goalId;
        goalForm = new Stage();

        goalForm.initStyle(StageStyle.TRANSPARENT);
        /*Remove window default border and buttons (minimize, close, etc...)*/
        goalForm.initModality(Modality.APPLICATION_MODAL);
        /*Unables clicks outside of this window*/
        goalForm.setWidth(300);
        goalForm.setHeight(425);
        goalForm.setAlwaysOnTop(true);
        /*Cant be onfocused (application wise)*/

        if (!setupForm(goalId)) {
            return false;
        }

        setupEventListeners();

        goalForm.showAndWait();

        return true;

    }

    public boolean setupForm(int goalId) {

        rootLayout = new BorderPane();
        rootLayout.setId("goalForm");
        rootLayout.getStyleClass().addAll("stonks-box");
        goalForm.setScene(new Scene(rootLayout));

        //Top layout 
        topLayout = new BorderPane();
        topLayout.getStyleClass().addAll("topLayout");

        //Title 
        titleContainer = new VBox();
        title = new Label("Adding Goal");
        title.getStyleClass().addAll("title");

        if (goalId > 0) {
            try {
                title.setText("Editting");
                subtitle = new Label(goalsObs.getGoal(goalId).getName());
                subtitle.getStyleClass().add("subtitle");
                subtitle.wrapTextProperty().setValue(true);
                subtitle.setMinHeight(50);
                subtitle.setMaxWidth(150);
            } catch (AuthenticationException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                return false;
            } catch (GoalNotFoundException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
                return false;
            }

        }

        titleContainer.getChildren().add(title);
        if (goalId > 0) {
            titleContainer.getChildren().add(subtitle);
        }

        //Close button 
        imageContainer = new Pane();
        closeBtn = new ImageView(new Image("resources/DialogBox_Close.png"));
        closeBtn.setFitWidth(25);
        closeBtn.setFitHeight(25);
        imageContainer.getChildren().add(closeBtn);

        imageContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //answer = Constants.DBOX_RETURN.X_CLOSED; 
            goalForm.close();
        });

        topLayout.setLeft(titleContainer);
        topLayout.setRight(imageContainer);

        //Form 
        form = new VBox();
        form.getStyleClass().addAll("form");

        //Name 
        nameDiv = new VBox();
        nameDiv.getStyleClass().addAll("fieldDiv");

        lblName = new Label("Name");
        lblName.getStyleClass().addAll("fieldTitle");

        txfName = new TextField();
        txfName.getStyleClass().add("fieldInput");

        txfName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txfName.setText(txfName.getText().replaceAll("\\s+", " ").trim());
                }
            }

        });

        if (goalId > 0) {
            try {
                txfName.setText(goalsObs.getGoal(goalId).getName());
            } catch (AuthenticationException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                return false;
            } catch (GoalNotFoundException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
                return false;
            }
        }

        errorName = new Label("Name has a maximum of 50 characters");
        errorName.getStyleClass().addAll("fieldError");
        errorName.setVisible(false);

        nameDiv.getChildren().addAll(lblName, txfName, errorName);

        //Objective 
        objectiveDiv = new VBox();
        objectiveDiv.getStyleClass().addAll("fieldDiv");

        lblObjective = new Label("Objective (€)");
        lblObjective.getStyleClass().addAll("fieldTitle");

        txfObjective = new TextField();
        //txfObjective.setMinWidth(250); 
        txfObjective.getStyleClass().add("fieldInput");

        if (goalId > 0) {
            try {
                txfObjective.setText(Integer.toString(goalsObs.getGoal(goalId).getObjective()));
            } catch (AuthenticationException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                return false;
            } catch (GoalNotFoundException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
                return false;
            }
        }

        //Only accept number 
        txfObjective.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {

                if (!newValue.matches("\\d*")) {
                    txfObjective.setText(newValue.replaceAll("[^\\d]", ""));
                }

                if (txfObjective.getText().length() >= 9) {
                    txfObjective.setText(txfObjective.getText().substring(0, 9));

                }

            }
        });

        errorObjective = new Label("Objective value cannot be negative");
        errorObjective.getStyleClass().addAll("fieldError");
        errorObjective.setVisible(false);

        objectiveDiv.getChildren().addAll(lblObjective, txfObjective, errorObjective);

        //Deadline 
        deadlineDiv = new VBox();
        deadlineDiv.getStyleClass().addAll("fieldDiv");

        deadlineInlineInput = new HBox();

        lblDeadline = new Label("Deadline");
        lblDeadline.setPadding(new Insets(0, 10, 10, 0));
        lblDeadline.getStyleClass().addAll("fieldTitle");

        hasDeadline = new CheckBox();
        hasDeadline.setIndeterminate(false);

        deadlineInlineInput.getChildren().addAll(lblDeadline, hasDeadline);

        dpDeadline = new DatePicker();
        dpDeadline.setVisible(false);
        dpDeadline.getStyleClass().add("fieldInput");
        dpDeadline.setMinWidth(260);

        if (goalId > 0) {
            try {
                if (goalsObs.getGoal(goalId).hasDeadline()) {
                    hasDeadline.setSelected(true);
                    dpDeadline.setValue(goalsObs.getGoal(goalId).getDeadlineDate());
                    dpDeadline.setVisible(true);
                }
            } catch (AuthenticationException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                return false;
            } catch (GoalNotFoundException ex) {
                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
                return false;
            }
        }

        errorDeadline = new Label("Deadline cannot be empty");
        errorDeadline.getStyleClass().addAll("fieldError");
        errorDeadline.setVisible(false);

        hasDeadline.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {

                if (hasDeadline.isSelected()) {
                    dpDeadline.setVisible(true);
                } else {
                    dpDeadline.setVisible(false);
                    errorDeadline.setVisible(false);
                }

            }
        });

        deadlineDiv.getChildren().addAll(deadlineInlineInput, dpDeadline, errorDeadline);

        form.getChildren().addAll(nameDiv, objectiveDiv, deadlineDiv);

        //Submit button 
        bottomLayout = new BorderPane();
        btnSubmit = new Button("Save");
        btnSubmit.getStyleClass().addAll("button", "btn-default", "btn-form");

        bottomLayout.setRight(btnSubmit);

        rootLayout.setTop(topLayout);
        rootLayout.setCenter(form);
        rootLayout.setBottom(bottomLayout);

        return true;
    }

    private void setupEventListeners() {

        btnSubmit.setOnAction((ActionEvent e) -> {

            int errors = 0;

            //Validate name 
            try {
                switch (goalsObs.verifyData(GOAL_FIELD.NAME, txfName.getText())) {
                    case MIN_CHAR:
                        errorName.setText("Name has a minimum of 1 character");
                        errorName.setVisible(true);
                        errors++;
                        break;
                    case MAX_CHAR:
                        errorName.setText("Name has a maximum of 50 characters");
                        errorName.setVisible(true);
                        errors++;
                        break;
                    default:
                        errorName.setVisible(false);
                        break;
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            //Validate Objective 
            try {
                if (!txfObjective.getText().isEmpty()) {
                    switch (goalsObs.verifyData(GOAL_FIELD.OBJECTIVE, Integer.parseInt(txfObjective.getText()))) {
                        case MIN_NUMBER:
                            errorObjective.setText("Objective value cannot be negative");
                            errorObjective.setVisible(true);
                            errors++;
                            break;
                        case MAX_NUMBER:
                            errorObjective.setText("Objective has a maximum value 999999999");
                            errorObjective.setVisible(true);
                            errors++;
                            break;
                        default:

                            errorObjective.setVisible(false);
                            break;
                    }
                } else {
                    errorObjective.setText("Objective cannot be empty");
                    errorObjective.setVisible(true);
                    errors++;
                }

            } catch (NumberFormatException ex) {
                System.out.println(ex);
            }

            //Validate deadline 
            if (hasDeadline.isSelected()) {

                switch (goalsObs.verifyData(GOAL_FIELD.DEADLINE, dpDeadline.getValue())) {
                    case EMPTY:
                        errorDeadline.setText("Deadline cannot be empty");
                        errorDeadline.setVisible(true);
                        errors++;
                        break;
                    case MIN_DATE:
                        errorDeadline.setText("Deadline has to be later then today");
                        errorDeadline.setVisible(true);
                        errors++;
                        break;
                    default:
                        errorDeadline.setVisible(false);
                        break;

                }
            } else {
                errorDeadline.setVisible(false);
            }

            if (errors == 0) {

                GoalModel newGoal;

                try {
                    if (hasDeadline.isSelected()) {
                        if (goalId > 0) {
                            try {
                                if (Integer.parseInt(txfObjective.getText()) < goalsObs.getGoal(goalId).getWallet().getSavedMoney()) {
                                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_EDIT_OBJECTIVE);
                                } else {
                                    DBOX_CONTENT content;
                                    content = DBOX_CONTENT.CONFIRM_GOAL_EDIT;
                                    content.setSubExtra(goalsObs.getGoal(goalId).getName());
                                    if (DialogBox.display(DBOX_TYPE.CONFIRM, content) == DBOX_RETURN.YES) {

                                        newGoal = goalsObs.editGoal(goalId, txfName.getText(), Integer.parseInt(txfObjective.getText()), dpDeadline.getValue());

                                        if (newGoal != null) {
                                            goalsList.get(goalId).setGoal(newGoal);
                                            DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_GOAL_EDIT);
                                            goalForm.close();
                                        }

                                    }
                                }

                            } catch (AuthenticationException ex) {
                                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                            } catch (GoalNotFoundException ex) {
                                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
                            }
                        } else {
                            try {
                                newGoal = goalsObs.createGoal(txfName.getText(), Integer.parseInt(txfObjective.getText()), dpDeadline.getValue());

                                if (newGoal != null) {
                                    goalsList.put(newGoal.getId(), new GoalBox(newGoal, goalsObs, goalsList));
                                    DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_GOAL_CREATE);
                                    goalForm.close();
                                }

                            } catch (AuthenticationException ex) {
                                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                            }
                        }

                    } else {
                        if (goalId > 0) {
                            try {
                                if (Integer.parseInt(txfObjective.getText()) < goalsObs.getGoal(goalId).getWallet().getSavedMoney()) {
                                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_EDIT_OBJECTIVE);
                                } else {
                                    DBOX_CONTENT content;
                                    content = DBOX_CONTENT.CONFIRM_GOAL_EDIT;
                                    content.setSubExtra(goalsObs.getGoal(goalId).getName());
                                    if (DialogBox.display(DBOX_TYPE.CONFIRM, content) == DBOX_RETURN.YES) {
                                        newGoal = goalsObs.editGoal(goalId, txfName.getText(), Integer.parseInt(txfObjective.getText()), null);
                                        if (newGoal != null) {
                                            goalsList.get(goalId).setGoal(newGoal);
                                            DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_GOAL_EDIT);
                                            goalForm.close();
                                        }

                                    }
                                }
                            } catch (AuthenticationException ex) {
                                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                            } catch (GoalNotFoundException ex) {
                                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_NOTFOUND);
                            }
                        } else {
                            try {
                                newGoal = goalsObs.createGoal(txfName.getText(), Integer.parseInt(txfObjective.getText()), null);
                                if (newGoal != null) {
                                    goalsList.put(newGoal.getId(), new GoalBox(newGoal, goalsObs, goalsList));
                                    firePropertyChange(GOAL_EVENT.CREATE_GOAL.name(), null, null);
                                    DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_GOAL_CREATE);
                                    goalForm.close();
                                }
                            } catch (AuthenticationException ex) {
                                DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_AUTH);
                            }
                        }

                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Goal form - " + ex);
                }

                /*if (isGoalCreated) { 
                    if (goalId > 0) { 
                        DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_GOAL_EDIT); 
                    } else { 
                        DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_GOAL_CREATE); 
                    } 
                    goalForm.close(); 
                } else { 
                    if (goalId > 0) { 
                        DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_EDIT); 
                    } else { 
                        DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_CREATE); 
                    } 
                }*/
            }
        });
    }
}
