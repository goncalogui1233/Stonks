/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import controllers.GoalController;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stonks.Constants;
import static stonks.Constants.APP_HEIGHT;
import static stonks.Constants.APP_WIDTH;

/**
 *
 * @author Bizarro
 */
public class DashboardBox implements Constants{
    private VBox root;
    private Label lbTitle;
    private Label lbSavings;
    private Label lbDeadlinesTitle;
    private PieChart pieChart;
    private final Label lbDeadlines;
    
    
    public DashboardBox(GoalController controller) {
        root = new VBox();
        root.setPrefHeight(DASHBOARD_VIEW_WIDTH/2);
        root.setPrefHeight(DASHBOARD_VIEW_HEIGHT);
        
        lbTitle = new Label("Active Goals Statistics");
        lbSavings = new Label("Savings");
        lbDeadlinesTitle = new Label("Deadlines");
    
        lbDeadlines = new Label();
        //lbDeadlines = generateDeadlinesLabel(controller.getGoalsWithDeadline()); //from controller
        
        pieChart = new PieChart();
        //pieChart = generatePieChart(controller.getListOfUncomplichedGoals()); //from controller
        
        root.getChildren().addAll(lbTitle,lbSavings,pieChart,lbDeadlinesTitle,lbDeadlines);
    }
    
    private PieChart generatePieChart(Map<String,Integer> goals){
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String,Integer> entry : goals.entrySet()) {
            PieChart.Data p = new PieChart.Data(entry.getKey(),entry.getValue());
            data.add(p);
        }
        return new PieChart(data);
    }
    
    private Label generateDeadlinesLabel(Map<String,String> goals){
        String str = "";
        for (Map.Entry<String,String> entry : goals.entrySet()) {
            str += entry.getKey() + " " +  entry.getValue() + "\n";
        }
        return new Label(str);
    }

    public VBox getRoot() {
        return root;
    }
    
    
}
