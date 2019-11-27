/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import controllers.GoalController;
import observables.GoalsObservable;
import stonks.Constants;

/**
 *
 * @author Tiago
 */
public class GoalForm implements Constants {

    private final GoalsObservable goalsObs;
    private Stage goalForm;
    private VBox rootLayout;
    private BorderPane topLayout;
    private Label title;
    private Pane imageContainer;
    private ImageView closeBtn;
    private VBox form;
    private VBox nameDiv;
    private Label lblName;
    private TextField txfName;
    private Label errorName;
    private VBox objectiveDiv;
    private Label lblObjective;
    private HBox objectiveInlineInput;
    private TextField txfObjective;
    private Label lblCurrency;
    private Label errorObjective;
    private VBox deadlineDiv;
    private HBox deadlineInlineInput;
    private Label lblDeadline;
    private CheckBox hasDeadline;
    private BorderPane bottomLayout;
    private Button btnSubmit;
    private DatePicker dpDeadline;
    private Label errorDeadline;

    public GoalForm(GoalsObservable goalsObs) {
        this.goalsObs = goalsObs;
    }

    public void display(int goalId) {
        goalForm = new Stage();

        goalForm.initStyle(StageStyle.UNDECORATED);
        /*Remove window default border and buttons (minimize, close, etc...)*/
        goalForm.initModality(Modality.APPLICATION_MODAL);
        /*Unables clicks outside of this window*/
        goalForm.setWidth(300);
        goalForm.setHeight(370);
        goalForm.setAlwaysOnTop(true);
        /*Cant be onfocused (application wise)*/

        setupForm(goalId);
        setupEventListeners();

        goalForm.showAndWait();
    }

    public void setupForm(int goalId) {

        rootLayout = new VBox();
        rootLayout.setId("goalForm");
        goalForm.setScene(new Scene(rootLayout));

        //Top layout
        topLayout = new BorderPane();
        topLayout.getStyleClass().addAll("topLayout");

        //Title
        title = new Label("Adding Goal");
        title.getStyleClass().addAll("title");

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

        topLayout.setLeft(title);
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

        errorName = new Label("Name has a maximum of 50 characters");
        errorName.getStyleClass().addAll("fieldError");
        errorName.setVisible(false);

        nameDiv.getChildren().addAll(lblName, txfName, errorName);

        //Objective
        objectiveDiv = new VBox();
        objectiveDiv.getStyleClass().addAll("fieldDiv");

        lblObjective = new Label("Objective");
        lblObjective.getStyleClass().addAll("fieldTitle");

        objectiveInlineInput = new HBox();
        txfObjective = new TextField();
        txfObjective.setMinWidth(250);

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

        lblCurrency = new Label("€");
        lblCurrency.getStyleClass().addAll("currency");
        objectiveInlineInput.getChildren().addAll(txfObjective, lblCurrency);

        errorObjective = new Label("Objective value cannot be negative");
        errorObjective.getStyleClass().addAll("fieldError");
        errorObjective.setVisible(false);

        objectiveDiv.getChildren().addAll(lblObjective, objectiveInlineInput, errorObjective);

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
        btnSubmit.getStyleClass().addAll("btn", "btn-default", "submit");

        bottomLayout.setRight(btnSubmit);

        rootLayout.getChildren().addAll(topLayout, form, bottomLayout);
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
                boolean canCreate = false;

                try {
                    if (hasDeadline.isSelected()) {
                        System.out.println(canCreate = goalsObs.createGoal(txfName.getText(), Integer.parseInt(txfObjective.getText()), dpDeadline.getValue()));
                    } else {
                        System.out.println(canCreate = goalsObs.createGoal(txfName.getText(), Integer.parseInt(txfObjective.getText()), null));
                    }
                } catch (Exception ex) {
                    System.out.println("Goal form - " + ex);
                }

                if (canCreate) {
                    DialogBox.display(DBOX_TYPE.SUCCESS, DBOX_CONTENT.SUCCESS_CREATE_GOAL);
                    goalForm.close();
                } else {
                    DialogBox.display(DBOX_TYPE.ERROR, DBOX_CONTENT.ERROR_GOAL_CREATE);
                }
            }
        });
    }

}
