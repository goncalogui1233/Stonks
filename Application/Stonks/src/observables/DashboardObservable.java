package observables;

import controllers.DashboardController;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;
import stonks.Constants;


public class DashboardObservable  extends PropertyChangeSupport implements Constants{
    
    private final StonksObservable stonkObs;
    private final DashboardController cDashboard;

    public DashboardObservable(DashboardController cDashboard, StonksObservable stonkobs) {
        super(cDashboard);
        
        this.cDashboard = cDashboard;
        this.stonkObs = stonkobs;
    }
    
    public StonksObservable getStonksObs(){
        return stonkObs;
    }
    
    /*Clicked Methods*/
    public void filterStatisticsChanged(){
        firePropertyChange(DASHBOARD_EVENT.CALCULATE_STATISTICS.name(), null, null);
    }
    
    public Map<String, Integer> dataForPieChart()
    {
        return cDashboard.getListOfUncomplichedGoals();
    }
    
    public  List<String> goalsWithDeadline()
    {
        return cDashboard.getGoalsWithDeadline();
    }
    
    public int moneySaved()
    {
        return cDashboard.getCurrentlySaved();
    }
    
    public Map<Integer, String> goalsFiltered(String year, String month){
        return cDashboard.CalculateGoalsStatistics(year, month);
    }
    
    
}
