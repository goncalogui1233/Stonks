package controllers;

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

    public int getNextId() {

        try {
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
        } catch (Exception ex) {
            return -1;
        }

    }

    public GoalModel getGoal(int id) {
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
        } catch (Exception ex) {
            return null;
        }

        /*If the goal wasn't found returns null*/
        return null;
    }

    public boolean createGoal(String name, int objective, LocalDate deadline) {

        /*A profile must be authenticated to create a goal*/
        try {

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
                GoalModel newGoal = new GoalModel(name, objective, deadline);
                newGoal.setId(this.getNextId());

                /*Adds the goal to the current profile goals list*/
                data.getAuthProfile().getGoals().put(newGoal.getId(), newGoal);
                System.out.println(data.getAuthProfile().getFirstName());
                /*UPDATE DATABASE*/
                data.updateDatabase();

                return true;
            }

        } catch (Exception ex) {
            /*If the profile isn't authenticated, it will catch a NullPointerException*/
            return false;
        }

        return false;
    }

    public boolean editGoal(int id, String name, int objective, LocalDate deadline) {

        try {
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
        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    public boolean removeGoal(int id) {
        try {

            data.getAuthProfile().getGoals().remove(id);

            /*UPDATE DATABASE*/
            data.updateDatabase();
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
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

    public int getGoalProgress(int id) {
        try {
            int objective = getGoal(id).getObjective();
            int currentSavedMoney = getGoal(id).getWallet().getSavedMoney();

            /*Returns the value in percentage (%)*/
            return (currentSavedMoney * objective) / 100;

        } catch (NullPointerException ex) {
            /*If the goals doesnt exist*/
            return -1;

        }
    }

    public boolean manageGoalFunds(int id, int updateValue) {
        /*Manages the money saved of a goal (add or remove money)*/
        LocalDate date = LocalDate.now();

        try {
            getGoal(id).getWallet().setSavedMoney(updateValue);
            /*If a deposit was never made*/
            if (getGoal(id).getWallet().getFirstDepositDate() == null) {
                /*Updates the first deposit date*/
                getGoal(id).getWallet().setFirstDepositDate(date);
                /*And the last deposit date*/
                getGoal(id).getWallet().setLastDepositDate(date);

            } else {
                /*If the first deposit was already made, only the updates last deposit date*/
                getGoal(id).getWallet().setLastDepositDate(date);

            }
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public LocalDate getEstimatedDate(int id) {
        try {
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
        } catch (NullPointerException ex) { }

        return null;
    }
}
