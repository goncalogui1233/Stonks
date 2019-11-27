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
import stonks.Constants;

/**
 *
 * @author Tiago
 */
public class GoalForm implements Constants {

    private Stage goalForm;
    private GoalController controller;

    public GoalForm(GoalController controller) {
        this.controller = controller;
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

        goalForm.showAndWait();
    }

    public void setupForm(int goalId) {

        VBox rootLayout = new VBox();
        rootLayout.setId("goalForm");
        goalForm.setScene(new Scene(rootLayout));

        //Top layout
        BorderPane topLayout = new BorderPane();
        topLayout.getStyleClass().addAll("topLayout");

        //Title
        Label title = new Label("Adding Goal");
        title.getStyleClass().addAll("title");

        //Close button
        Pane imageContainer = new Pane();
        ImageView closeBtn = new ImageView(new Image("resources/DialogBox_Close.png"));
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
        VBox form = new VBox();
        form.getStyleClass().addAll("form");

        //Name
        VBox nameDiv = new VBox();
        nameDiv.getStyleClass().addAll("fieldDiv");

        Label lblName = new Label("Name");
        lblName.getStyleClass().addAll("fieldTitle");

        TextField txfName = new TextField();

        Label errorName = new Label("Name has a maximum of 50 characters");
        errorName.getStyleClass().addAll("fieldError");
        errorName.setVisible(false);

        nameDiv.getChildren().addAll(lblName, txfName, errorName);

        //Objective
        VBox objectiveDiv = new VBox();
        objectiveDiv.getStyleClass().addAll("fieldDiv");

        Label lblObjective = new Label("Objective");
        lblObjective.getStyleClass().addAll("fieldTitle");

        HBox objectiveInlineInput = new HBox();
        TextField txfObjective = new TextField();
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

        Label lblCurrency = new Label("€");
        lblCurrency.getStyleClass().addAll("currency");
        objectiveInlineInput.getChildren().addAll(txfObjective, lblCurrency);

        Label errorObjective = new Label("Objective value cannot be negative");
        errorObjective.getStyleClass().addAll("fieldError");
        errorObjective.setVisible(false);

        objectiveDiv.getChildren().addAll(lblObjective, objectiveInlineInput, errorObjective);

        //Deadline
        VBox deadlineDiv = new VBox();
        deadlineDiv.getStyleClass().addAll("fieldDiv");

        HBox deadlineInlineInput = new HBox();

        Label lblDeadline = new Label("Deadline");
        lblDeadline.setPadding(new Insets(0, 10, 10, 0));
        lblDeadline.getStyleClass().addAll("fieldTitle");

        CheckBox hasDeadline = new CheckBox();
        hasDeadline.setIndeterminate(false);

        deadlineInlineInput.getChildren().addAll(lblDeadline, hasDeadline);

        DatePicker dpDeadline = new DatePicker();
        dpDeadline.setVisible(false);

        Label errorDeadline = new Label("Deadline cannot be empty");
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
        BorderPane bottomLayout = new BorderPane();
        Button btnSubmit = new Button("Save");
        btnSubmit.getStyleClass().addAll("btn", "btn-default", "submit");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                int errors = 0;
                try {
                    //Validate name
                    if (controller.verifyData(GOAL_FIELD.NAME, txfName.getText()) == VALIDATE.MIN_CHAR) {
                        errorName.setText("Name canot be empty");
                        errorName.setVisible(true);
                        errors++;
                    } else if (txfName.getLength() > 50) {
                        errorName.setText("Name has a maximum of 50 characters");
                        errorName.setVisible(true);
                        errors++;
                    } else {
                        errors--;
                        errorName.setVisible(false);
                    }

                    //Validate Objective
                    if (txfObjective.getLength() < 1) {
                        errorObjective.setText("Objective cannot be empty");
                        errorObjective.setVisible(true);
                        errors++;
                    } else if (controller.verifyData(GOAL_FIELD.OBJECTIVE, Integer.parseInt(txfObjective.getText())) == VALIDATE.MIN_NUMBER) {
                        errorObjective.setText("Objective value cannot be negative");
                        errorObjective.setVisible(true);
                        errors++;
                    } else if (controller.verifyData(GOAL_FIELD.OBJECTIVE, Integer.parseInt(txfObjective.getText())) == VALIDATE.MAX_NUMBER) {
                        errorObjective.setText("Objective has a maximum value 999999999");
                        errorObjective.setVisible(true);
                        errors++;
                    } else {
                        errors--;
                        errorObjective.setVisible(false);
                    }
                } catch (Exception ex) {

                }

                //Validate deadline
                if (hasDeadline.isSelected()) {
                    if (controller.verifyData(GOAL_FIELD.DEADLINE, dpDeadline.getValue()) == VALIDATE.EMPTY) {
                        errorDeadline.setText("Deadline cannot be empty");
                        errorDeadline.setVisible(true);
                        errors++;
                    } else if (controller.verifyData(GOAL_FIELD.DEADLINE, dpDeadline.getValue()) == VALIDATE.MIN_DATE) {
                        errorDeadline.setText("Deadline has to be later then today");
                        errorDeadline.setVisible(true);
                        errors++;
                    } else {
                        errors--;
                        errorDeadline.setVisible(false);
                    }
                } else {
                    errors--;
                    errorDeadline.setVisible(false);
                }

                if (errors < 0) {
                    if (hasDeadline.isSelected()) {
                        System.out.println(controller.createGoal(txfName.getText(), Integer.parseInt(txfObjective.getText()), dpDeadline.getValue()));
                    } else {
                        System.out.println(controller.createGoal(txfName.getText(), Integer.parseInt(txfObjective.getText()), null));
                    }

                }

            }
        });

        bottomLayout.setRight(btnSubmit);

        rootLayout.getChildren().addAll(topLayout, form, bottomLayout);
    }
}
