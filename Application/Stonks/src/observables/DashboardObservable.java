/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observables;

import controllers.DashboardController;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;
import models.GoalModel;
import stonks.Constants;


public class DashboardObservable  extends PropertyChangeSupport implements Constants{
    
    private final StonksObservable stonkObs;
    private final DashboardController cDashboard;

    public DashboardObservable(DashboardController cDashboard, StonksObservable stonkobs) {
        super(cDashboard);
        
        this.cDashboard = cDashboard;
        this.stonkObs = stonkobs;
    }
    
    /*Clicked Methods*/
    public void filterStatisticsChanged(){
        firePropertyChange(DASHBOARD_EVENT.CALCULATE_STATISTICS.name(), null, null);
    }
    
    public Map<String, Integer> dataForPieChart()
    {
        return cDashboard.getListOfUncomplichedGoals();
    }
    
    public  Map<String, String> goalsWithDeadline()
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
