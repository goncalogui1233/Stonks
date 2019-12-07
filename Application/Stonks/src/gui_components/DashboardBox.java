/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import controllers.GoalController;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import observables.DashboardObservable;
import stonks.Constants;

/**
 *
 * @author Bizarro
 */
public class DashboardBox implements Constants {

    private VBox root;
    private final DashboardObservable dashObs;

    private Label lbTitle;
    private Label lbSavings;
    private Label lbDeadlinesTitle;
    private PieChart pieChart;
    private final Label lbDeadlines;

    public DashboardBox(DashboardObservable dashObs) {
        this.dashObs = dashObs;

        root = new VBox();
        root.setPrefHeight(DASHBOARD_VIEW_WIDTH / 2);
        root.setPrefHeight(DASHBOARD_VIEW_HEIGHT);

        lbTitle = new Label("Active Goals Statistics");
        lbSavings = new Label("Savings");
        lbDeadlinesTitle = new Label("Deadlines");

        lbDeadlines = new Label();
        //lbDeadlines = generateDeadlinesLabel(controller.getGoalsWithDeadline()); //from controller

       populateGoals();
        root.getChildren().addAll(lbTitle, lbSavings, lbDeadlinesTitle, lbDeadlines);
    }

    private void populateGoals(){
        //pieChart = generatePieChart(dashObs.dataForPieChart());
        Map<String, String> goalsWithDeadline = dashObs.goalsWithDeadline();
        Map<String, Integer> goalsUncomplished = dashObs.dataForPieChart();
        
        Label lbDeadlineGoal, lbDeadlineDate;
        if (goalsWithDeadline != null) {
            for (Map.Entry<String, String> entry : goalsWithDeadline.entrySet()) {
                lbDeadlineGoal = new Label(entry.getKey());
                lbDeadlineDate = new Label(entry.getValue());
                root.getChildren().addAll(lbDeadlineGoal,lbDeadlineDate);
            }
        }
        else
        {}    //Não existe deadlines
            
        if (goalsUncomplished != null) {
            pieChart = generatePieChart(goalsUncomplished);
            root.getChildren().add(pieChart);
        }
        else{}//No data avaiabel for user        
    }

    private PieChart generatePieChart(Map<String, Integer> goals) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : goals.entrySet()) {
            PieChart.Data p = new PieChart.Data(entry.getKey(), entry.getValue());
            data.add(p);
        }
        return new PieChart(data);
    }

    private Label generateDeadlinesLabel(Map<String, String> goals) {
        String str = "";
        for (Map.Entry<String, String> entry : goals.entrySet()) {
            str += entry.getKey() + " " + entry.getValue() + "\n";
        }
        return new Label(str);
    }

    public VBox getRoot() {
        return root;
    }
}
