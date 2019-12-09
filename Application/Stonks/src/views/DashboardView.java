package views;

import controllers.GoalController;
import gui_components.DashboardBox;
import gui_components.SideMenu;
import gui_components.StatisticsBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import observables.DashboardObservable;
import stonks.Constants;

public class DashboardView implements Constants, PropertyChangeListener {

    private final DashboardObservable dashObs;

    private DashboardBox dashboardBox;
    private StatisticsBox statisticsBox;

    //Containers
    private final HBox root;

    public DashboardView(DashboardObservable dashObs) {
        this.dashObs = dashObs;

        this.root = new HBox();
        this.root.setId("dashboardView");
        root.setMinSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);
        root.setMaxSize(DASHBOARD_VIEW_WIDTH, DASHBOARD_VIEW_HEIGHT);

        setupContainers();
        setupPropertyChangeListeners();
    }

    private void setupPropertyChangeListeners() {
        dashObs.getStonksObs().addPropertyChangeListener(STONKS_EVENT.GOTO_DASHBOARD_VIEW.name(), this);
    }

    private void setupContainers() {
        dashboardBox = new DashboardBox(dashObs);
        statisticsBox = new StatisticsBox(dashObs);

        root.getChildren().addAll(new SideMenu(dashObs.getStonksObs()).getRoot(),
                dashboardBox.getRoot(), statisticsBox.getRoot());
    }

    public HBox getRoot() {
        return root;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(STONKS_EVENT.GOTO_DASHBOARD_VIEW.name())) {
           statisticsBox.populateLabels();
           System.out.println("er");
        }
    }
}
