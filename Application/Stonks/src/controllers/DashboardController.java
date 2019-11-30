package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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

    public int getCurrentySaved() {
        int sum = 0;

        for (GoalModel obj : data.getAuthProfile().getGoals()) {
            if (obj.getGoalProgress() < 100 //dont count progress of goals achived
                    && obj.getWallet().getSavedMoney() >= 0) // dont add negative values
            {
                if (obj.hasDeadline()) { //verify if deadline already passed
                    if (LocalDate.now().compareTo(obj.getDeadlineDate()) < 0) {
                        sum += obj.getWallet().getSavedMoney();
                    }
                } else {
                    sum += obj.getWallet().getSavedMoney();
                }
            }
        }

        return sum;
    }

    public Map<String, String> getGoalsWithDeadline() {
        Map<String, String> returnData = new HashMap<>();;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (GoalModel obj : data.getAuthProfile().getGoals()) {
            if (obj.hasDeadline()
                    && obj.getGoalProgress() < 100) //dont count goals already accomplished
            { //goals 
                returnData.put(obj.getName(), obj.getDeadlineDate().format(formatter));
            }
        }

        return returnData.isEmpty() ? null : returnData;
    }

    //Know issue when the NAME OF A GOAL AND TTHE GOAL PROGRESS IS DUPLICATED
    //THE LAST ONE WILL OVERRIDE DE LASTONE
    public Map<String, Integer> getListOfUncomplichedGoals() {
        Map<String, Integer> allData = new TreeMap<>(Collections.reverseOrder());
        
        for (GoalModel obj : data.getAuthProfile().getGoals()) {
            if (obj.getGoalProgress() < 100) {
                String text = Integer.toString(obj.getGoalProgress()) + "% - " + obj.getName();
                allData.put( text, obj.getGoalProgress());
            }
        }

        if (!allData.isEmpty()) {
            return putFirstEntries(4, allData);
        }

        return null;
    }

    private static Map<String, Integer> putFirstEntries(int max, Map<String, Integer> source) {
        int count = 0;
        Map<String, Integer> target = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : source.entrySet()) {
            if (count >= max) {
                break;
            }

            target.put(entry.getKey(), entry.getValue());
            count++;
        }
        return target;
    }
}
