/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import observables.DashboardObservable;
import stonks.Constants;

/**
 *
 * @author Bizarro
 */
public class DashboardBox implements Constants {

    private final VBox root;
    private final DashboardObservable dashObs;

    //Containers
    private VBox vbDeadLines;
    private HBox hbPieChartContainer;
    private VBox vbPieChartLabels;

    //Labels
    private Label lbTitle;
    private Label lbSavings;
    private Label lbDeadlinesTitle;
    private PieChart pieChart;
    private Label lbDeadlines;

    public DashboardBox(DashboardObservable dashObs) {
        this.dashObs = dashObs;

        root = new VBox();
        vbDeadLines = new VBox();
        hbPieChartContainer = new HBox();
        vbPieChartLabels = new VBox();
        
        root.setMinWidth(DASHBOARD_VIEW_WIDTH / 2);
        root.setMaxWidth(DASHBOARD_VIEW_WIDTH / 2);

        setupLabels();
        vbDeadLines.getChildren().add(lbDeadlines);

        root.getChildren().addAll(lbTitle, lbSavings, hbPieChartContainer,lbDeadlinesTitle, vbDeadLines);
        
        populateGoals();
        generateDeadlinesLabel();
        hbPieChartContainer.getChildren().addAll(pieChart,vbPieChartLabels);
    }

    private void setupLabels() {
        lbTitle = new Label("Active Goals Statistics");
        lbTitle.getStyleClass().addAll("dashboardTitle", "dashboard");
        lbSavings = new Label("Savings");
        lbSavings.getStyleClass().addAll("dashboardSubTitle", "dashboard");
        lbDeadlinesTitle = new Label("Deadlines");
        lbDeadlinesTitle.getStyleClass().addAll("dashboardSubTitle", "dashboard");

        lbDeadlines = new Label();
        lbDeadlines.getStyleClass().addAll("dashboardLabel");
    }

    private void populateGoals() {
        //pieChart = generatePieChart(dashObs.dataForPieChart());
        Map<String, String> goalsWithDeadline = dashObs.goalsWithDeadline();
        Map<String, Integer> goalsUncomplished = dashObs.dataForPieChart();
        goalsUncomplished.put("aaa", 23);
        goalsUncomplished.put("Bbb", 23);

        Label lbDeadlineGoal, lbDeadlineDate;
        if (goalsWithDeadline != null) {
            for (Map.Entry<String, String> entry : goalsWithDeadline.entrySet()) {
                lbDeadlineGoal = new Label(entry.getKey());
                lbDeadlineDate = new Label(entry.getValue());
                root.getChildren().addAll(lbDeadlineGoal, lbDeadlineDate);
            }
        } else {
        }    //Não existe deadlines

        if (goalsUncomplished != null) {
            pieChart = generatePieChart(goalsUncomplished);
            root.getChildren().add(pieChart);
        } else {
            //Label lbNoData = new Label("No Data Avaiable");
            //root.getChildren().add(lbNoData);
        }//No data avaiabel for user        
    }

    private PieChart generatePieChart(Map<String, Integer> goals) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : goals.entrySet()) {
            PieChart.Data p
                    = new PieChart.Data(entry.getKey(), entry.getValue());
            data.add(p);
        }
        return new PieChart(data);
    }

    private void generateDeadlinesLabel() {
        Map<String, String> goals = dashObs.goalsWithDeadline();
        goals.put("gola1", "20/12/12");
        goals.put("gola13", "20/12/12");
        Label lbNameGoal, lbDate;
        HBox line;

        if (!goals.isEmpty()) {
            for (Map.Entry<String, String> entry : goals.entrySet()) {
                lbNameGoal = new Label(entry.getKey());
                lbDate = new Label(entry.getValue());
                line = new HBox();
                line.getChildren().addAll(lbNameGoal, lbDate);

                vbDeadLines.getChildren().addAll(line);
            }
        }
        else
        {
            lbNameGoal = new Label("No Data Avaible");
            vbDeadLines.getChildren().add(lbNameGoal);
        }

    }

    public VBox getRoot() {
        return root;
    }
}
