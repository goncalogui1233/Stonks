package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProfileModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;

    private int id;
    private String firstName;
    private String lastName;
    private final String securityQuestion;
    private final String securityAnswer;
    private String password;
    private String color;
    private HashMap<Integer, GoalModel> goalList;

    /*ProfileModel constructor WITH Password*/
    public ProfileModel(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.password = password;
        this.color = color;
        this.goalList = new HashMap<>();

        id = idCounter++;
    }

    /*ProfileModel constructor WITHOUT Password*/
    public ProfileModel(String firstName, String lastName, String securityQuestion, String securityAnswer, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.color = color;
        this.goalList = new HashMap<>();

        id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HashMap<Integer, GoalModel> getGoals() {
        return goalList;
    }

    public void setGoals(HashMap<Integer, GoalModel> goals) {
        this.goalList = goals;
    }

    public boolean hasGoals() {
        if (goalList != null) {
            if (goalList.size() > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean hasCompletedGoals() {

        try {
            for (GoalModel goal : this.goalList.values()) {
                if (goal.isCompleted()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    public boolean hasIncompleteGoals() {

        try {
            for (GoalModel goal : this.goalList.values()) {
                if (!goal.isCompleted()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        }

        return false;
    }

    public boolean hasPassword() {
        return !(password == null || password.isEmpty());
    }

    @Override
    public String toString() {
        return "ProfileModel{" + "id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", securityQuestion=" + securityQuestion
                + ", securityAnswer=" + securityAnswer
                + ", password=" + password
                + ", color=" + color
                + ", goals=" + goalList + '}';
    }

    public List<GoalModel> getTopGoals(int quant) {
        if (quant <= 0) {
            return null;
        }

        List<GoalModel> topGoals = new ArrayList();

        for (GoalModel newGoal : goalList.values()) {
            /*Doesn't show complete goals*/
            if (newGoal.isCompleted()) {
                continue; //Goes to the next iteration
            }

            /*While list has less items than desired quantity (quant)*/
            if (topGoals.size() < quant) {
                topGoals.add(newGoal);
                continue; //Goes to the next iteration
            }

            GoalModel smallestIn = topGoals.get(0);
            for(GoalModel insertedGoal : topGoals){
                if(insertedGoal.getProgress() < smallestIn.getProgress()){
                    smallestIn = insertedGoal;
                }
            }
            
            if(newGoal.getProgress() > smallestIn.getProgress()){
                topGoals.remove(smallestIn);
                topGoals.add(newGoal);
            }
        }
        GoalModel.orderListByProgress(topGoals);
        return topGoals;
    }
}
