package controllers;

import java.time.LocalDate;
import stonks.StonksData;
import models.GoalModel;
import stonks.Constants;

public class GoalController implements Constants {
    private final StonksData data;

    public GoalController(StonksData data) {
        this.data = data;
    }

    private GoalModel getGoal(int id) { //Search for a goal with the id passed as argument
        for (GoalModel goal : data.getCurrentProfile().getGoals()) {
            if (goal.getId() == id) {
                return goal;
            }
        }

        return null;
    }
    
    public boolean createGoal(String name, int objective, LocalDate deadline) {
        if (verifyData(GOAL_FIELD.NAME, name)
                && verifyData(GOAL_FIELD.OBJECTIVE, Integer.toString(objective))) {
            data.getCurrentProfile().getGoals().add(new GoalModel(name, objective, deadline));
            return true;
        }

        return false;
    }

    public boolean editGoal(int id, String name, int objective, LocalDate deadline) {
        if (verifyData(GOAL_FIELD.NAME, name)
                && verifyData(GOAL_FIELD.OBJECTIVE, Integer.toString(objective))) {
            getGoal(id).setName(name);
            getGoal(id).setObjective(objective);
            getGoal(id).setDeadlineDate(deadline);
            return true;
        }

        return false;
    }

    public boolean removeGoal(int id) {
        try {
            data.getCurrentProfile().getGoals().remove(getGoal(id));
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    private <T> boolean verifyData(GOAL_FIELD field, T value) { //verify the data in goal name and objective
        switch (field) {
            /*NAME FIELD VALIDATIONS*/
            case NAME:
                if (((String) value).isEmpty() //name is empty
                        || ((String) value).length() > 50) //name is longer than expected
                {
                    return false;
                }
                break;

            /*OBJECTIVE FIELD VALIDATIONS*/
            case OBJECTIVE:
                if (((Integer) value) <= 0 //objective is 0 or less
                        || ((Integer) value) > 999999999) //objective is too big
                {
                    return false;
                }
                break;

            /*DEADLINE FIELD VALIDATIONS*/
            case DEADLINE:
                break;
        }

        return true;
    }

    public int getGoalProgress(int id) {
        try {
            int objective = getGoal(id).getObjective();
            int actual = getGoal(id).getWallet().getSavedMoney();

            return (actual / objective) / 100; //returns percentage value to PBar
        } catch (NullPointerException ex) {
            return -1; //if not exists, return -1;
        }
    }

    /*public Date showEstimateDateOfFinish(int id){
        int pos;
        if((pos = existeGoal(id)) > -1 ){
            //Saving Rate
            Date lastDeposit = get().getGoals().get(pos).getWallet().getLastDepositDate();
            Date firstDeposit = get().getGoals().get(pos).getWallet().getFirstDepositDate();
            int savedMoney = get().getGoals().get(pos).getWallet().getSavedMoney();
            
            long rate = (lastDeposit.getTime() - firstDeposit.getTime()) / savedMoney;
            
            //Date Estimation
            int moneyNeeded = get().getGoals().get(pos).getObjective() - savedMoney; //diference between objective money and actual money
            long estimate = rate/moneyNeeded; //
            
            
        }
        
        return null;
    }*/
    
    public boolean manageGoalFunds(int id, int updateValue) { //manages the money saved of a goal 
        LocalDate date = LocalDate.now();

        try {
            getGoal(id).getWallet().setSavedMoney(updateValue);
            if (getGoal(id).getWallet().getFirstDepositDate() == null) { //if never made a deposit,
                getGoal(id).getWallet().setFirstDepositDate(date);     //updates first deposit date
                getGoal(id).getWallet().setLastDepositDate(date);      //and last deposit date
            } else {
                getGoal(id).getWallet().setLastDepositDate(date); //updates last deposit date
            }
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }
}
