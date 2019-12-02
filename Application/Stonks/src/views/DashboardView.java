package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stonks.Constants;

public class DashboardView extends Application implements Constants{
    
    private final TilePane root;
    private VBox activeGoalsVBox;
    private VBox allTimeStatsVBox;
    private Label activeGoalsTitleLabel;
    private Label allTimeStatsTitleLabel;
    private Label savingsLabel;
    private Label deadlinesLabel;
    private Label goalStatisticsLabel;
    
    
    public DashboardView() {
        this.root = new TilePane();
        root.setPrefColumns(2);
        root.setPrefTileWidth(DASHBOARD_VIEW_WIDTH/2);
        root.setId("dashboardView");
        root.setMinSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        root.setMaxSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        setupDashboardView();
    }
    
    public void setupDashboardView(){
        activeGoalsTitleLabel = new Label("Active Goals Statistics");
        allTimeStatsTitleLabel = new Label("All Time Statistics");
        savingsLabel = new Label("Savings");
        deadlinesLabel = new Label("Deadlines");
        goalStatisticsLabel = new Label("Goal Statistiscs");
        
        
        activeGoalsVBox = new VBox();
        activeGoalsVBox.getChildren().addAll(activeGoalsTitleLabel,allTimeStatsTitleLabel);
        
        
        allTimeStatsVBox = new VBox();
        allTimeStatsVBox.getChildren().addAll(deadlinesLabel,goalStatisticsLabel);
        
        root.getChildren().addAll(activeGoalsVBox,allTimeStatsVBox);
    }
    
    public static void main(String[] args){
           launch(args);
	
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(root,APP_WIDTH,APP_HEIGHT));
        primaryStage.show();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
