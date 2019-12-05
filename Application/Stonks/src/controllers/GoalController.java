package controllers;

import exceptions.AuthenticationException;
import exceptions.EmptyDepositException;
import exceptions.GoalNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import stonks.StonksData;
import models.GoalModel;
import stonks.Constants;

/**
 * The GoalController class is responsible to manage the logic of the goals
 * module.
 */
public class GoalController implements Constants {

    private final StonksData data;

    public GoalController(StonksData data) {
        this.data = data;
    }

    public int getNextId() throws AuthenticationException {

        /*Check if the authenticated profile has goals*/
        if (data.getAuthProfile().hasGoals()) {

            int biggest = 1;

            /*Searches on the list of goals for the biggest goal id*/
            for (Integer id : data.getAuthProfile().getGoals().keySet()) {
                if (id > biggest) {
                    biggest = id;
                }
            }

            /*Returns the bigest goal id + 1*/
            return ++biggest;
        }

        /*If the authenticated profile doesn't have any goals, the id will be 1*/
        return 1;

    }

    public GoalModel getGoal(int id) throws AuthenticationException, GoalNotFoundException {
        /*If the authenticated profile has goals*/
        try {
            if (data.getAuthProfile().hasGoals()) {
                /*Searhes on the goals list for the goal with the corresponding id passed as argument*/
                for (GoalModel goal : data.getAuthProfile().getGoals().values()) {
                    if (goal.getId() == id) {
                        return goal;
                    }
                }

            }

            throw new GoalNotFoundException();

        } catch (NullPointerException ex) {
            throw new AuthenticationException();
        }
    }

    public boolean createGoal(String name, int objective, LocalDate deadline) throws AuthenticationException {

        /*Validates if the inputs recevied by argument are in the correct format*/
        if (verifyData(GOAL_FIELD.NAME, name) == VALIDATE.OK
                && verifyData(GOAL_FIELD.OBJECTIVE, objective) == VALIDATE.OK) {

            /*If the goal will have a deadline, checks if the deadline is in the correct format*/
            if (deadline != null) {
                /*If the deadline isn't in the correct format, the goal isn't created*/
                if (verifyData(GOAL_FIELD.DEADLINE, deadline) != VALIDATE.OK) {
                    return false;
                }
            }

            /*Creates a goal and assign an id*/
            GoalModel newGoal = new GoalModel(getNextId(), name, objective, deadline);

            /*Adds the goal to the current profile goals list*/
            data.getAuthProfile().getGoals().put(newGoal.getId(), newGoal);
            System.out.println(data.getAuthProfile().getFirstName());
            /*UPDATE DATABASE*/
            data.updateDatabase();

            return true;
        }

        return false;
    }

    public boolean editGoal(int id, String name, int objective, LocalDate deadline) throws AuthenticationException, GoalNotFoundException {

        /*Checks if the inputs recevied by argument are in the correct format*/
        if (verifyData(GOAL_FIELD.NAME, name) == VALIDATE.OK
                && verifyData(GOAL_FIELD.OBJECTIVE, objective) == VALIDATE.OK) {

            /*If the goal has deadline, checks if it is in the correct format*/
            if (deadline != null) {
                /*If the deadline isn't in the correct format, the goal isn't edited*/
                if (verifyData(GOAL_FIELD.DEADLINE, deadline) != VALIDATE.OK) {
                    return false;
                }
            }

            /*Edits the goal information*/
            getGoal(id).setName(name);
            getGoal(id).setObjective(objective);
            getGoal(id).setDeadlineDate(deadline);

            /*UPDATE DATABASE*/
            data.updateDatabase();
            return true;
        }

        return false;
    }

    public boolean removeGoal(int id) throws AuthenticationException, GoalNotFoundException {

        data.getAuthProfile().getGoals().remove(getGoal(id).getId());

        /*UPDATE DATABASE*/
        data.updateDatabase();
        return true;

    }

    /*Validates the fields of a goal*/
    public <T> VALIDATE verifyData(GOAL_FIELD field, T value) {
        try {
            switch (field) {
                /*NAME FIELD VALIDATIONS*/
                case NAME:
                    if (((String) value).length() < GOAL_NAME_MINCHARS) {
                        return VALIDATE.MIN_CHAR;
                    }
                    if (((String) value).length() > GOAL_NAME_MAXCHARS) {
                        return VALIDATE.MAX_CHAR;
                    }

                    return VALIDATE.OK;

                /*OBJECTIVE FIELD VALIDATIONS*/
                case OBJECTIVE:
                    if (((Integer) value) < GOAL_OBJECTIVE_MINVALUE) {
                        return VALIDATE.MIN_NUMBER;
                    }
                    if (((Integer) value) > GOAL_OBJECTIVE_MAXVALUE) {
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

    public double getGoalProgress(int id) throws AuthenticationException, GoalNotFoundException {

        double objective = this.getGoal(id).getObjective();
        double currentSavedMoney = getGoal(id).getWallet().getSavedMoney();

        /*Returns the value in percentage (%)*/
        return ((currentSavedMoney * objective) / 1000) * 0.1;

    }

    public boolean manageGoalFunds(int id, int updateValue) throws AuthenticationException, GoalNotFoundException {
        /*Manages the money saved of a goal (add or remove money)*/
        LocalDate date = LocalDate.now();

        GoalModel goal = getGoal(id);

        /*If a deposit was never made*/
        if (goal.getWallet().getFirstDepositDate() == null) {
            /*Updates the first deposit date*/
            goal.getWallet().setFirstDepositDate(date);
            /*And the last deposit date*/
            goal.getWallet().setLastDepositDate(date);

        } else {
            /*If the first deposit was already made, only the updates last deposit date*/
            goal.getWallet().setLastDepositDate(date);

        }

        if (updateValue > 0) {

            if (goal.getWallet().getSavedMoney() + updateValue > goal.getObjective()) {
                return false;
            }

            goal.getWallet().addMoney(updateValue);
        } else {

            if (goal.getWallet().getSavedMoney() - Math.abs(updateValue) < 0) {
                return false;
            }

            goal.getWallet().removeMoney(Math.abs(updateValue));
        }

        return true;

    }

    public LocalDate getEstimatedDate(int id) throws AuthenticationException, GoalNotFoundException, EmptyDepositException {

        GoalModel goal = getGoal(id);

        /*Saving Rate*/
        LocalDate lastDeposit = goal.getWallet().getLastDepositDate();
        LocalDate firstDeposit = goal.getWallet().getFirstDepositDate();
        int savedMoney = goal.getWallet().getSavedMoney();

        long rate = ChronoUnit.DAYS.between(firstDeposit, lastDeposit) / savedMoney;

        /*Date Estimation*/
        int savedIncrement = savedMoney;
        int objective = goal.getObjective();
        long countDays = 0;

        /*Cycle to increment number of days until the value of objective*/
        while (savedIncrement <= objective) {
            savedIncrement += rate;
            countDays++;
        }

        /*Add the estimated days to today's date in order to get the estimated date*/
        LocalDate today = LocalDate.now();

        today.plusDays(countDays);

        return today;

    }
}
