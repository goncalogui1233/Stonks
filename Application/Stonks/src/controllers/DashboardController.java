package controllers;

import models.GoalModel;
import stonks.Constants;
import stonks.StonksData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DashboardController implements Constants {

    private final StonksData data;

    public DashboardController(StonksData data) {
        this.data = data;
    }

    public int getCurrentlySaved() {
        int sum = 0;

        try {
            for (GoalModel obj : data.getAuthProfile().getGoals().values()) {
                if (obj.getProgress() < 1 //dont count progress of goals achived
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

        } catch (Exception e) {
        }

        return sum;
    }

    public Map<String, String> getGoalsWithDeadline() {
        Map<String, String> returnData = new HashMap<>();;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            for (GoalModel obj : data.getAuthProfile().getGoals().values()) {
                if (obj.hasDeadline()
                        && obj.getProgress() < 1) //dont count goals already accomplished
                { //goals 
                    returnData.put(obj.getName(), obj.getDeadlineDate().format(formatter));
                }
            }
        } catch (Exception e) {
        }

        return returnData;
    }

    //Know issue when the NAME OF A GOAL AND TTHE GOAL PROGRESS IS DUPLICATED
    //THE LAST ONE WILL OVERRIDE DE LASTONE
    public Map<String, Double> getListOfUncomplichedGoals() {
        Map<String, Double> allData = new TreeMap<>(Collections.reverseOrder());

        try {
            for (GoalModel obj : data.getAuthProfile().getGoals().values()) {
                if (obj.getProgress() < 1) {
                    int intAux = new Double(obj.getProgress()* 100.0).intValue();
                    String text = Double.toString(intAux) + "% - " + obj.getName();
                    allData.put(text, obj.getProgress());
                }
            }
        } catch (Exception e) {
        }

        if (!allData.isEmpty()) {
            return putFirstEntries(4, allData);
        }

        return allData;
    }

    private static Map<String, Double> putFirstEntries(int max, Map<String, Double> source) {
        int count = 0;
        Map<String, Double> target = new TreeMap<>();
        for (Map.Entry<String, Double> entry : source.entrySet()) {
            if (count >= max) {
                break;
            }

            target.put(entry.getKey(), entry.getValue());
            count++;
        }
        return target;
    }

    //-------------Statistics-------------
    public Map<Integer, String> CalculateGoalsStatistics(String year, String month) {
        Map<Integer, String> returnData = new HashMap<>();
        List<GoalModel> listOfAllGoals;
        int intYear = 0;
        String monthUpperCase;

        try {
            if (!year.equals("Year")) {
                intYear = Integer.parseInt(year);
            }
            monthUpperCase = month.toUpperCase();

        } catch (Exception e) {
            returnData.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
            returnData.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
            returnData.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
            returnData.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0â‚¬");
            returnData.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0â‚¬");
            return returnData;
        }

        //The Filters was the default value
        if (year.equals("Year") && month.equals("Month")) {
            returnData.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
            returnData.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
            returnData.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
            returnData.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0â‚¬");
            returnData.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0â‚¬");
        } else {
            //Check if both filters are selected
            if (!year.equals("Year") && !month.equals("Month")) {
                listOfAllGoals = getListOfGoalsByYearAndMonth(
                        intYear, monthUpperCase);
            } else {
                //The filter year is selected
                if (!year.equals("Year")) {
                    listOfAllGoals = getListOfGoalsByYear(intYear);
                } else//The filter month is selected
                {
                    listOfAllGoals = getListOfGoalsByMonth(monthUpperCase);
                }
            }
            //goals statics
            goalStatistics(listOfAllGoals, returnData);
        }

        return returnData;
    }

    private void goalStatistics(List<GoalModel> filterGoals, Map<Integer, String> returnData) {
        int totalGoals = 0;
        int GoalsComplete = 0;
        int GoalsIncomplete = 0;
        int savedMoney = 0;
        int totalObjective = 0;

        try {
            for (GoalModel obj : filterGoals) {
                totalGoals++;
                totalObjective += obj.getObjective();
                savedMoney += obj.getWallet().getSavedMoney();

                if (obj.getProgress() >= 1) { //Goal completed
                    GoalsComplete++;
                } else {
                    GoalsIncomplete++;
                }
            }
        } catch (Exception e) {
        }

        returnData.put(DASHBOARD_STATISTICS_GOALS_COMPLETE,
                Integer.toString(GoalsComplete));

        returnData.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE,
                Integer.toString(GoalsIncomplete));

        returnData.put(DASHBOARD_STATISTICS_TOTAL_GOALS,
                Integer.toString(totalGoals));

        returnData.put(DASHBOARD_STATISTICS_SAVED_MONEY,
                Integer.toString(savedMoney) + "â‚¬");

        returnData.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE,
                Integer.toString(totalObjective) + "â‚¬");

    }

    private List<GoalModel> getListOfGoalsByYearAndMonth(int year, String month) {
        List<GoalModel> listOfAllGoals = new ArrayList<>();

        try {
            for (GoalModel obj : data.getAuthProfile().getGoals().values()) {
                if (obj.getCreationDate()
                        .getMonth().toString().equals(month) //Same Month and Year
                        && obj.getCreationDate()
                                .getYear() == year) {
                    listOfAllGoals.add(obj);
                }
            }
        } catch (Exception e) {
        }

        return listOfAllGoals;
    }

    private List<GoalModel> getListOfGoalsByYear(int year) {
        List<GoalModel> listOfAllGoals = new ArrayList<>();

        try {
            for (GoalModel obj : data.getAuthProfile().getGoals().values()) {
                if (obj.getCreationDate().getYear() == year) {
                    listOfAllGoals.add(obj);
                }
            }
        } catch (Exception e) {
        }

        return listOfAllGoals;
    }

    private List<GoalModel> getListOfGoalsByMonth(String month) {
        List<GoalModel> listOfAllGoals = new ArrayList<>();

        try {
            for (GoalModel obj : data.getAuthProfile().getGoals().values()) {
                if (obj.getCreationDate()
                        .getMonth().toString().equals(month)) {
                    listOfAllGoals.add(obj);
                }
            }
        } catch (Exception e) {
        }

        return listOfAllGoals;
    }
}
