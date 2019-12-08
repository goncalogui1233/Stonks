/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Map;
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
    private VBox vbLabels;
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
        vbLabels = new VBox();
        root.setMinWidth(DASHBOARD_VIEW_WIDTH / 2);
        root.setMaxWidth(DASHBOARD_VIEW_WIDTH / 2);

        lbTitle = new Label("All Time Statistics");
        lbTitle.getStyleClass().addAll("dashboardTitle", "dashboard");

        lbGoalStatisticsTitle = new Label("Goals Statistics");
        lbGoalStatisticsTitle.getStyleClass().addAll("dashboardTitle", "dashboard");

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

        hbChoice.getChildren().addAll(cbYear, cbMonth);
        hbChoice.setPadding(new Insets(0, 0, 0, 20));

        root.getChildren().add(lbTitle);
        root.getChildren().add(hbChoice);
        root.getChildren().add(vbLabels);

        setupPropertyChangeListeners();
        setupEventListeners();
        populateLabels();
    }

    private void populateLabels() {
        Map<Integer, String> values = dashObs.goalsFiltered(cbYear.getValue().toString(), cbMonth.getValue().toString());
        // Switch 0's to values from controller
        lbTotalGoals = generateTotalGoalsLabel(
                values.get(DASHBOARD_STATISTICS_TOTAL_GOALS));
        lbTotalGoals.getStyleClass().addAll("dashboardLable");
        
        lbCompletedGoals = generateCompleteGoalsLabel(
                values.get(DASHBOARD_STATISTICS_GOALS_COMPLETE));        
        lbCompletedGoals.getStyleClass().addAll("dashboardLable");
        
        lbIncompletedGoals = generateIncompleteGoalsLabel(
                values.get(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE));        
        lbIncompletedGoals.getStyleClass().addAll("dashboardLable");
        
        lbSavedMoney = generateSavedMoneyLabel(
                values.get(DASHBOARD_STATISTICS_SAVED_MONEY));        
        lbSavedMoney.getStyleClass().addAll("dashboardLable");
        
        lbTotalObjective = generateTotalObjectiveLabel(
                values.get(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE));        
        lbTotalObjective.getStyleClass().addAll("dashboardLable");
        
        vbLabels.getChildren().clear();
        vbLabels.getChildren().addAll(lbGoalStatisticsTitle, lbTotalGoals, lbCompletedGoals, lbIncompletedGoals, lbSavedMoney, lbTotalObjective);
    }

    private Label generateTotalGoalsLabel(String totalGoals) {
        return new Label("Total Goals: " + totalGoals);
    }

    private Label generateCompleteGoalsLabel(String goalsComplete) {
        return new Label("Complete Goals: " + goalsComplete);
    }

    private Label generateIncompleteGoalsLabel(String goalsComplete) {
        return new Label("Incomplete goals: " + goalsComplete);
    }

    private Label generateSavedMoneyLabel(String savedMoney) {
        return new Label("Money saved: " + savedMoney);
    }

    private Label generateTotalObjectiveLabel(String totalObjective) {
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
                    populateLabels();
                });

        cbYear.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    populateLabels();
                });
    }

}
