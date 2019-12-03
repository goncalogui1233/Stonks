package views;

import controllers.GoalController;
import gui_components.DashboardBox;
import gui_components.StatisticsBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stonks.Constants;

public class DashboardView implements Constants{
    
    private final TilePane root;
    private DashboardBox dashboardBox;
    private StatisticsBox statisticsBox;
    private GoalController controller;
    
    public DashboardView(GoalController controller) {
        this.controller = controller;
        this.root = new TilePane();
        dashboardBox = new DashboardBox(controller);
        statisticsBox = new StatisticsBox(controller);
        root.setPrefColumns(2);
        root.setPrefTileWidth(DASHBOARD_VIEW_WIDTH/2);
        root.setId("dashboardView");
        root.setMinSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        root.setMaxSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        root.getChildren().addAll(dashboardBox.getRoot(),statisticsBox.getRoot());
    }
}
