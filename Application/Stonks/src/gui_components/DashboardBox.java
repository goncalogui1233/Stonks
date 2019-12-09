package gui_components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
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

    //ScrollPane
    private final ScrollPane spDeadLines;

    //Labels
    private Label lbTitle;
    private Label lbSavings;
    private Label lbDeadlinesTitle;
    private PieChart pieChart;
    private Label lbDeadlines;

    public DashboardBox(DashboardObservable dashObs) {
        this.dashObs = dashObs;

        root = new VBox();
        root.setStyle("-fx-border-width: 0 1 0 0; -fx-border-color:#bdbdbd;");
        vbDeadLines = new VBox();
        vbDeadLines.setPadding(new Insets(0, 0, 0, 20));
        hbPieChartContainer = new HBox();

        vbPieChartLabels = new VBox();
        //vbPieChartLabels.setMinWidth(200);
        //vbPieChartLabels.autosize();
        //vbPieChartLabels.setMinWidth(150);

        root.setMinWidth(DASHBOARD_VIEW_WIDTH / 2);
        root.setMaxWidth(DASHBOARD_VIEW_WIDTH / 2);

        setupLabels();
        vbDeadLines.getChildren().add(lbDeadlines);

        //ScrollPane
        spDeadLines = new ScrollPane();
        //goalsScrollPane.setMinWidth(GOAL_BOX_WIDTH); 
        spDeadLines.setMaxHeight(100);
        spDeadLines.setMaxWidth(450);
        spDeadLines.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        spDeadLines.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        spDeadLines.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent t) {
                if (t.getDeltaX() != 0) {
                    t.consume();
                }
            }

        });

        spDeadLines.setContent(vbDeadLines);

        root.getChildren().addAll(lbTitle, lbSavings, hbPieChartContainer, lbDeadlinesTitle, spDeadLines);

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
        List<String> goalsWithDeadline = dashObs.goalsWithDeadline();
        System.out.println(goalsWithDeadline.size());
        Map<String, Integer> goalsUncomplished = dashObs.dataForPieChart();

        if (goalsWithDeadline != null) {
            generateDeadlinesLabel();
        } else {
        }    //Não existe deadlines

        if (goalsUncomplished != null) {
            pieChart = new PieChart();

            //pieChart.paddingProperty().set(Insets.EMPTY);
            pieChart = generatePieChart(goalsUncomplished);
            pieChart.setLabelsVisible(false);

            //pieChart.setLegendSide(Side.RIGHT);
            root.getChildren().add(pieChart);
        } else {
            //Label lbNoData = new Label("No Data Avaiable");
            //root.getChildren().add(lbNoData);
        }//No data avaiabel for user        

        hbPieChartContainer.getChildren().clear();
        hbPieChartContainer.getChildren().addAll(pieChart, vbPieChartLabels);
    }

    private PieChart generatePieChart(Map<String, Integer> goals) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : goals.entrySet()) {
            int intAux = new Double(entry.getValue() * 100.0).intValue();
            PieChart.Data p
                    = new PieChart.Data(entry.getKey(),
                            new Double(intAux));
            data.add(p);
        }

        return new PieChart(data);
    }

    private void generateDeadlinesLabel() {
        List<String> goals = dashObs.goalsWithDeadline();
        Label lblGoal;
        HBox line;

        if (!goals.isEmpty()) {
            vbDeadLines.getChildren().clear();
            for (String entry : goals) {
                lblGoal = new Label(entry);
                line = new HBox();
                line.getChildren().addAll(lblGoal);

                vbDeadLines.getChildren().addAll(line);
            }
        } else {
            lblGoal = new Label("No Data Available");
            vbDeadLines.getChildren().clear();
            vbDeadLines.getChildren().add(lblGoal);
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
