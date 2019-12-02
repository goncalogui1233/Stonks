/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observables;

import controllers.GoalController;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.HashMap;
import models.GoalModel;
import models.ProfileModel;
import stonks.Constants;

/**
 *
 * @author Tiago
 */
public class GoalsObservable extends PropertyChangeSupport implements Constants {

    private final StonksObservable stonksObs;
    private final GoalController cGoal;

    public GoalsObservable(GoalController cGoal, StonksObservable stonksObs) {
        super(cGoal);

        this.cGoal = cGoal;
        this.stonksObs = stonksObs;
    }

    /*Methods*/
    public StonksObservable getStonksObs(){
        return stonksObs;
    }
    
    public <T> VALIDATE verifyData(GOAL_FIELD field, T value) {
        return cGoal.verifyData(field, value);
    }

    public boolean createGoal(String name, int objective, LocalDate deadline) {

        boolean ans = cGoal.createGoal(name, objective, deadline);

        System.out.println(ans);

        if (ans) {
            firePropertyChange(GOAL_EVENT.CREATE_GOAL.name(), null, null);
        }

        return ans;
    }

    public boolean removeGoal(int id) {
        boolean ans = cGoal.removeGoal(id);

        if (ans) {
            firePropertyChange(GOAL_EVENT.DELETE_GOAL.name(), null, null);
        }

        return ans;
    }

    /*Getters*/
    public ProfileModel getAuthProfile() {
        return stonksObs.getAuthProfile();
    }

    public GoalModel getGoal(int id) {
        return cGoal.getGoal(id);
    }

}
