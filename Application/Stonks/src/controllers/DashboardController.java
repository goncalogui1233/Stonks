package controllers;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import models.GoalModel;
import stonks.StonksData;

public class DashboardController {
    private final StonksData data;
    
    public DashboardController(StonksData data) {
        this.data = data;
    }
    
    public int getCurrentySaved()
    {
        int sum = 0;
        
        for (GoalModel obj : data.getAuthProfile().getGoals())
        {
            sum += obj.getWallet().getSavedMoney();
        }
        
        return sum;
    }
    
    public HashMap<String, String> getGoalsWithDeadline()
    {
        boolean created = false;
        HashMap<String, String> returnData = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (GoalModel obj : data.getAuthProfile().getGoals())
        {
            if (obj.hasDeadline())
            {
                if (!created)
                {
                    returnData = new HashMap<>();
                }
                returnData.put(obj.getName(), obj.getDeadlineDate().format(formatter));
            }
        }
        
        return returnData;
    }
    
    public HashMap<Integer, String> getListOfUncomplichedGoals()
    {
        HashMap<Integer, String> returnData = null;
        boolean created = false;
        
        for (GoalModel obj : data.getAuthProfile().getGoals())
        {
            if (obj.getWallet().getSavedMoney() >= obj.getObjective())
            {
                if (!created)
                {
                    returnData = new HashMap<>();
                }
                
                String data = Integer.toString(obj.getObjective())+ "%-" + obj.getName();
                returnData.put(obj.getGoalProgress(), data);
            }
        }
        return null;    
    }
}
