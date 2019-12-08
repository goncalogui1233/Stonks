/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import observables.DashboardObservable;
import stonks.Constants;

/**
 *
 * @author Bizarro
 */
public class StatisticsBox implements Constants, PropertyChangeListener {

    private final DashboardObservable dashObs;

    private VBox root;
    private Label lbTitle;
    private final ChoiceBox cbYear;
    private final ChoiceBox cbMonth;
    private Label lbGoalStatisticsTitle;
    private Label lbTotalGoals;
    private Label lbCompletedGoals;
    private Label lbIncompletedGoals;
    private Label lbSavedMoney;
    private Label lbTotalObjective;

    public StatisticsBox(DashboardObservable dashboardobservable) {
        this.dashObs = dashboardobservable;

        root = new VBox();

        lbTitle = new Label("All Time Statistics");
        lbTitle.getStyleClass().addAll("dashboardTitle","dashboard");
        
        lbGoalStatisticsTitle = new Label("Goals Statistics");
        lbGoalStatisticsTitle.getStyleClass().addAll("dashboardTitle","dashboard");
        
        HBox hbChoice = new HBox();
        
        cbYear = new ChoiceBox();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        cbYear.getItems().add("Year");
        
        for (int i = 0; i < 5; i++) { // actual year and 5 years before it
            cbYear.getItems().add(currentYear - i);
        }
        cbYear.setValue("Year");

        cbMonth = new ChoiceBox();
        for (String month : DASHBOARD_STATISTICS_MONTH) {
            cbMonth.getItems().add(month);
        }        
        cbMonth.setValue(DASHBOARD_STATISTICS_MONTH[0]);
        
        hbChoice.getChildren().addAll(cbYear,cbMonth);
        hbChoice.setPadding(new Insets(0,0,0,20));
        
        
        // Switch 0's to values from controller
        lbTotalGoals = generateTotalGoalsLabel(0);
        lbTotalGoals.getStyleClass().addAll("dashboardLable");
        lbCompletedGoals = generateCompleteGoalsLabel(0);
        lbCompletedGoals.getStyleClass().addAll("dashboardLable");
        lbIncompletedGoals = generateIncompleteGoalsLabel(0);
        lbIncompletedGoals.getStyleClass().addAll("dashboardLable");
        lbSavedMoney = generateSavedMoneyLabel(0);
        lbSavedMoney.getStyleClass().addAll("dashboardLable");
        lbTotalObjective = generateTotalObjectiveLabel(0);
        lbTotalObjective.getStyleClass().addAll("dashboardLable");

        root.getChildren().add(lbTitle);
        root.getChildren().add(hbChoice);
        root.getChildren().addAll(lbGoalStatisticsTitle, lbTotalGoals, lbCompletedGoals, lbIncompletedGoals, lbSavedMoney, lbTotalObjective);
        
        setupPropertyChangeListeners();
        setupEventListeners();
    }

    private Label generateTotalGoalsLabel(int totalGoals) {
        return new Label("Total Goals: " + totalGoals);
    }

    private Label generateCompleteGoalsLabel(int goalsComplete) {
        return new Label("Complete Goals: " + goalsComplete);
    }

    private Label generateIncompleteGoalsLabel(int goalsComplete) {
        return new Label("Incomplete goals: " + goalsComplete);
    }

    private Label generateSavedMoneyLabel(int savedMoney) {
        return new Label("Money saved: " + savedMoney);
    }

    private Label generateTotalObjectiveLabel(int totalObjective) {
        return new Label("Total objective: " + totalObjective);
    }

    public VBox getRoot() {
        return root;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName()
                .equals(DASHBOARD_EVENT.CALCULATE_STATISTICS.CALCULATE_STATISTICS.name())) {
            
        }
    }

    private void setupPropertyChangeListeners() {
        dashObs.addPropertyChangeListener(
                DASHBOARD_EVENT.CALCULATE_STATISTICS.name(), this);
    }

    private void setupEventListeners() {
        cbMonth.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                System.out.println("Mes:" + newValue);
                dashObs.goalsFiltered(cbYear.getValue().toString(), newValue.toString());
                
                });
        
        cbYear.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                dashObs.goalsFiltered(newValue.toString(), cbMonth.getValue().toString());
                System.out.println("Year:" + newValue);
                });
    }

}
