package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import observables.DashboardObservable;
import stonks.Constants;

public class DashboardBox implements Constants, PropertyChangeListener {

    private final VBox root;
    private final DashboardObservable dashObs;

    //Containers
    private final VBox vbDeadLines;
    private final HBox hbPieChartContainer;
    private final VBox vbPieChartLabels;

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
        setupPropertyChangeListeners();
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
        Map<String, Double> goalsUncomplished = dashObs.dataForPieChart();

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
            pieChart.setLabelsVisible(false);
            pieChart.setLegendSide(Side.RIGHT);
            root.getChildren().add(pieChart);
        } else {
            //Label lbNoData = new Label("No Data Avaiable");
            //root.getChildren().add(lbNoData);
        }//No data avaiabel for user        
        
        hbPieChartContainer.getChildren().clear();
        hbPieChartContainer.getChildren().addAll(pieChart,vbPieChartLabels);
    }

    private PieChart generatePieChart(Map<String, Double >goals) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        
        for (Map.Entry<String, Double> entry : goals.entrySet()) {
            int intAux = new Double(entry.getValue()* 100.0).intValue();
            PieChart.Data p
                    = new PieChart.Data(entry.getKey(), 
                            new Double(intAux));
            data.add(p);
        }
        
        return new PieChart(data);
    }

    private void generateDeadlinesLabel() {
        Map<String, String> goals = dashObs.goalsWithDeadline();
        Label lbNameGoal, lbDate;
        HBox line;

        if (!goals.isEmpty()) {
            for (Map.Entry<String, String> entry : goals.entrySet()) {
                lbNameGoal = new Label(entry.getKey());
                lbDate = new Label(entry.getValue());
                line = new HBox();
                line.getChildren().addAll(lbNameGoal, lbDate);

                vbDeadLines.getChildren().clear();
                vbDeadLines.getChildren().addAll(line);
            }
        }
        else
        {
            lbNameGoal = new Label("No Data Avaible");
            vbDeadLines.getChildren().clear();
            vbDeadLines.getChildren().add(lbNameGoal);
        }

    }

    public VBox getRoot() {
        return root;
    }

    private void setupPropertyChangeListeners() {
        dashObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.PROFILE_HAS_BEEN_AUTH.name(), this);
        dashObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOAL_STATE_CHANGED.name(), this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        populateGoals();
        generateDeadlinesLabel();
//        if(evt.getPropertyName().equals(STONKS_EVENT.PROFILE_HAS_BEEN_AUTH)){
//            
//        }else if(evt.getPropertyName().equals(STONKS_EVENT.PROFILE_HAS_BEEN_AUTH)){
//            
//        }
    }
}
