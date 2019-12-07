/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_components;

import controllers.GoalController;
import java.util.Calendar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Bizarro
 */
public class StatisticsBox {
    private GoalController controller;
    private VBox root;
    private Label lbTitle;
    private ComboBox cbYear;
    private ComboBox cbMonth;
    private Label lbGoalStatisticsTitle;
    private Label lbTotalGoals;
    private Label lbCompletedGoals;
    private Label lbIncompletedGoals;
    private Label lbSavedMoney;
    private Label lbTotalObjective;
    
    
    public StatisticsBox(GoalController controller) {
        this.controller = controller;
        root = new VBox();
        
        lbTitle = new Label("All Time Statistics");
     
        lbGoalStatisticsTitle = new Label("Goals Statistics");
        
        cbYear = new ComboBox();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 20; i++) { // actual year and 20 years before it
            cbYear.getItems().add(currentYear-i);
        }
        
        cbMonth = new ComboBox();
        cbMonth.getItems().addAll("January","February","March","April","May",
                "June","July","August","September","October","November","December");
        
        // Switch 0's to values from controller
        lbTotalGoals = generateTotalGoalsLabel(0);
        lbCompletedGoals = generateCompleteGoalsLabel(0);
        lbIncompletedGoals = generateIncompleteGoalsLabel(0);
        lbSavedMoney = generateSavedMoneyLabel(0);
        lbTotalObjective = generateTotalObjectiveLabel(0);
        
        root.getChildren().add(lbTitle);
        root.getChildren().add(new HBox(cbYear,cbMonth));
        root.getChildren().addAll(lbGoalStatisticsTitle,lbTotalGoals,lbCompletedGoals,lbIncompletedGoals,lbSavedMoney,lbTotalObjective);
    }
    
    private Label generateTotalGoalsLabel(int totalGoals){
        return new Label("Total Goals: " + totalGoals);
    }
    
    private Label generateCompleteGoalsLabel(int goalsComplete){
        return new Label("Complete Goals: " + goalsComplete);
    }
    
    private Label generateIncompleteGoalsLabel(int goalsComplete){
        return new Label("Incomplete goals: " + goalsComplete);
    }
    
    private Label generateSavedMoneyLabel(int savedMoney){
        return new Label("Money saved: " + savedMoney);
    }
    
    private Label generateTotalObjectiveLabel(int totalObjective){
        return new Label("Total objective: " + totalObjective);
    }

    public VBox getRoot() {
        return root;
    }
    
    
}
