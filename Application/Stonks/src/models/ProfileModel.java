package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import stonks.StonksData;

public class ProfileModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;
    private static StonksData data;

    private int id;
    private String firstName;
    private String lastName;
    private final String securityQuestion;
    private final String securityAnswer;
    private String password;
    private String color;
    private HashMap<Integer, GoalModel> goals;

    /*ProfileModel constructor WITH Password*/
    public ProfileModel(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.password = password;
        this.color = color;
        this.goals = new HashMap<>();

        id = idCounter++;
    }

    /*ProfileModel constructor WITHOUT Password*/
    public ProfileModel(String firstName, String lastName, String securityQuestion, String securityAnswer, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.color = color;
        this.goals = new HashMap<>();

        id = idCounter++;
    }

    public static void setData(StonksData data) {
        ProfileModel.data = data;
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
        return goals;
    }

    public void setGoals(HashMap<Integer, GoalModel> goals) {
        this.goals = goals;
    }

    public boolean hasGoals() {
        if (goals != null) {
            if (goals.size() > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean hasPassword(){
        return !(password == null || password.isEmpty());
    }
    
    @Override
    public String toString() {
        return "ProfileModel{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", securityQuestion=" + securityQuestion + ", securityAnswer=" + securityAnswer + ", password=" + password + ", color=" + color + ", goals=" + goals + '}';
    }
}
