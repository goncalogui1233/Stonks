package controllers;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
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
    
    public Map<String, String> getGoalsWithDeadline()
    {
        boolean created = false;
        Map<String, String> returnData = null;
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
    
    public Map<Integer, String> getListOfUncomplichedGoals()
    {
        Map<Integer, String> allData = null;
        Map<Integer, String> returnData = null;
        Map<Integer, String>  orderData= null;
        boolean created = false;
        
        for (GoalModel obj : data.getAuthProfile().getGoals())
        {
            if (obj.getWallet().getSavedMoney() >= obj.getObjective())
            {
                if (!created)
                {
                    allData = new HashMap<>();
                }
                
                String data = Integer.toString(obj.getObjective())+ "%-" + obj.getName();
                allData.put(obj.getGoalProgress(), data);
            }
        }
        
        if (allData != null)
        {
            orderData = new TreeMap<>(returnData);
            return putFirstEntries(4, orderData);
        }
        
        return null;    
    }
    
    private static Map<Integer,String> putFirstEntries(int max, Map<Integer,String> source) {
        int count = 0;
        Map<Integer,String> target =  new HashMap<>();;
        for (Map.Entry<Integer,String> entry : source.entrySet()) {
            if (count >= max) break;

        target.put(entry.getKey(), entry.getValue());
        count++;
        }
        return target;
    }
}
