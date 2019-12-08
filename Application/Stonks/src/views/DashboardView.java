package views;

import controllers.GoalController;
import gui_components.DashboardBox;
import gui_components.StatisticsBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.scene.layout.TilePane;
import observables.DashboardObservable;
import stonks.Constants;

public class DashboardView implements Constants, PropertyChangeListener{
    
    private final DashboardObservable dashObs;
    
    private final TilePane root;
    private DashboardBox dashboardBox;
    private StatisticsBox statisticsBox;
    
    public DashboardView(DashboardObservable dashboardobservable) {
        this.dashObs = dashboardobservable;
        
        this.root = new TilePane();
        
        setupContainers();
        setupPropertyChangeListeners();
    }
    
    private void setupContainers(){
        dashboardBox = new DashboardBox(dashObs);
        statisticsBox = new StatisticsBox(dashObs);
        //statisticsBox = new StatisticsBox(controller);
        root.setPrefColumns(2);
        root.setPrefTileWidth(DASHBOARD_VIEW_WIDTH/2);
        root.setMinSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        root.setMaxSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        root.getChildren().addAll(dashboardBox.getRoot(),statisticsBox.getRoot());
    }
    
    public TilePane getRoot()
    {
        return root;
    }
    
    private void setupPropertyChangeListeners(){
        dashObs.addPropertyChangeListener(DASHBOARD_EVENT.CALCULATE_STATISTICS.name(),this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
