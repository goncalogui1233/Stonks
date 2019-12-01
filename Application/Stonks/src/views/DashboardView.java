package views;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import stonks.Constants;

public class DashboardView implements Constants{
    
    private final HBox root;
    private VBox activeGoalsVBox;
    private VBox allTimeStatsVBox;
    private Label activeGoalsTitleLabel;
    private Label allTimeStatsTitleLabel;
    private Label savingsLabel;
    private Label deadlinesLabel;
    private Label goalStatisticsLabel;
    
    
    public DashboardView() {
        this.root = new HBox();
        root.setId("dashboardView");
        root.setMinSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        root.setMaxSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
    }
    
    public void setupDashboardView(){
        activeGoalsTitleLabel = new Label("Active Goals Statistics");
        allTimeStatsTitleLabel = new Label("All Time Statistics");
        savingsLabel = new Label("Savings");
        deadlinesLabel = new Label("Deadlines");
        goalStatisticsLabel = new Label("Goal Statistiscs");
        
        
        activeGoalsVBox = new VBox();
        allTimeStatsVBox = new VBox();
        root.getChildren().addAll(activeGoalsVBox,allTimeStatsVBox);
    }
    
    
    
    
    
}
