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

    public int getNextId() {
        if (data.getAuthProfile().hasGoals()) {
            int biggest = 1;

            for (Integer id : data.getAuthProfile().getGoalsIds()) {
                if (id > biggest) {
                    biggest = id;
                }
            }

            return ++biggest;
        }

        return 1;
    }

    public GoalModel getGoal(int id) { //Search for a goal with the id passed as argument
        for (GoalModel goal : data.getAuthProfile().getGoals()) {
            if (goal.getId() == id) {
                return goal;
            }
        }

        return null;
    }

    public boolean createGoal(String name, int objective, LocalDate deadline) {

        if (data.getAuthProfile() != null) {
            if (verifyData(GOAL_FIELD.NAME, name) == VALIDATE.OK
                    && verifyData(GOAL_FIELD.OBJECTIVE, objective) == VALIDATE.OK) {

                if (deadline != null) {
                    if (verifyData(GOAL_FIELD.DEADLINE, deadline) != VALIDATE.OK) {
                        return false;
                    }
                }

                GoalModel newGoal = new GoalModel(name, objective, deadline);
                newGoal.setId(this.getNextId());

                data.getAuthProfile().getGoalsMap().put(newGoal.getId(), newGoal);

                /*UPDATE DATABASE*/
                return true;
            }
        }

        return false;
    }

    public boolean editGoal(int id, String name, int objective, LocalDate deadline) {
        if (verifyData(GOAL_FIELD.NAME, name) == VALIDATE.OK
                && verifyData(GOAL_FIELD.OBJECTIVE, objective) == VALIDATE.OK) {
            getGoal(id).setName(name);
            getGoal(id).setObjective(objective);
            getGoal(id).setDeadlineDate(deadline);

            /*UPDATE DATABASE*/
            return true;
        }

        return false;
    }

    public boolean removeGoal(int id) {
        try {
            data.getAuthProfile().getGoalsMap().remove(id);

            /*UPDATE DATABASE*/
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public <T> VALIDATE verifyData(GOAL_FIELD field, T value) { //verify the data in goal name and objective
        try {
            switch (field) {
                /*NAME FIELD VALIDATIONS*/
                case NAME:
                    if (((String) value).length() < 1/*CONSTANT*/) {
                        return VALIDATE.MIN_CHAR;
                    }
                    if (((String) value).length() > 50/*CONSTANT*/) {
                        return VALIDATE.MAX_CHAR;
                    }

                    return VALIDATE.OK;

                /*OBJECTIVE FIELD VALIDATIONS*/
                case OBJECTIVE:
                    if (((Integer) value) <= 0/*CONSTANT*/) {
                        return VALIDATE.MIN_NUMBER;
                    }
                    if (((Integer) value) > 999999999/*CONSTANT*/) {
                        return VALIDATE.MAX_NUMBER;
                    }

                    return VALIDATE.OK;
                /*DEADLINE FIELD VALIDATIONS*/
                case DEADLINE:
                    if (value == null) {
                        return VALIDATE.EMPTY;
                    }
                    if (((LocalDate) value).compareTo(LocalDate.now()) <= 0) {
                        return VALIDATE.MIN_DATE;
                    }

                    return VALIDATE.OK;

                default:
                    return VALIDATE.UNDEFINED;
            }
        } catch (ClassCastException ex) {
            return VALIDATE.UNDEFINED;
        } catch (NullPointerException ex) {
            return VALIDATE.EMPTY;
        } catch (Exception ex) {
            return VALIDATE.UNDEFINED;
        }
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
