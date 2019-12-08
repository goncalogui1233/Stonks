package observables;

import controllers.GoalController;
import exceptions.AuthenticationException;
import exceptions.EmptyDepositException;
import exceptions.GoalNotFoundException;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.GoalModel;
import models.ProfileModel;
import stonks.Constants;

public class GoalsObservable extends PropertyChangeSupport implements Constants {

    private final StonksObservable stonksObs;
    private final GoalController cGoal;

    public GoalsObservable(GoalController cGoal, StonksObservable stonksObs) {
        super(cGoal);

        this.cGoal = cGoal;
        this.stonksObs = stonksObs;
    }

    /*Methods*/
    public StonksObservable getStonksObs() {
        return stonksObs;
    }

    public <T> VALIDATE verifyData(GOAL_FIELD field, T value) {
        return cGoal.verifyData(field, value);
    }

    public GoalModel createGoal(String name, int objective, LocalDate deadline) throws AuthenticationException {

        GoalModel newGoal = cGoal.createGoal(name, objective, deadline);

        if (newGoal != null) {
            firePropertyChange(GOAL_EVENT.CREATE_GOAL.name(), null, null);
            stonksObs.firePropertyChange(STONKS_EVENT.GOAL_STATE_CHANGED.name(), null, null);
        }

        return newGoal;
    }

    public boolean removeGoal(int id) throws AuthenticationException, GoalNotFoundException {
        boolean ans = cGoal.removeGoal(id);

        if (ans) {
            firePropertyChange(GOAL_EVENT.DELETE_GOAL.name(), null, null);
            stonksObs.firePropertyChange(STONKS_EVENT.GOAL_STATE_CHANGED.name(), null, null);
        }

        return ans;
    }

    public GoalModel editGoal(int id, String name, int objective, LocalDate deadline) throws AuthenticationException, GoalNotFoundException {

        GoalModel newGoal = cGoal.editGoal(id, name, objective, deadline);

        if (newGoal != null) {
            firePropertyChange(GOAL_EVENT.EDIT_GOAL.name(), null, null);
            stonksObs.firePropertyChange(STONKS_EVENT.GOAL_STATE_CHANGED.name(), null, null);
        }

        return newGoal;
    }

    public boolean updateWallet(int id, int value) {
        boolean ans = false;
        try {
            ans = cGoal.manageGoalFunds(id, value);
            if (ans) {
                stonksObs.firePropertyChange(STONKS_EVENT.GOAL_STATE_CHANGED.name(), null, null);

                if (getGoal(id).isCompleted()) {
                    firePropertyChange(GOAL_EVENT.GOAL_COMPLETED.name(), null, null);
                }

            }
        } catch (AuthenticationException ex) {
            Logger.getLogger(GoalsObservable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GoalNotFoundException ex) {
            Logger.getLogger(GoalsObservable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ans;
    }

    /*Getters*/
    public ProfileModel getAuthProfile() {
        return stonksObs.getAuthProfile();
    }

    public GoalModel getGoal(int id) throws AuthenticationException, GoalNotFoundException {
        return cGoal.getGoal(id);
    }

    public double getGoalProgress(int id) throws AuthenticationException, GoalNotFoundException {
        return cGoal.getGoal(id).getProgress();
    }

    public LocalDate getEstimatedDate(int id) throws AuthenticationException, GoalNotFoundException, EmptyDepositException {
        return cGoal.getEstimatedDate(id);
    }

}
