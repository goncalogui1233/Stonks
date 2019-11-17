package models;

import javafx.scene.paint.Color;
import stonks.StonksData;

public class ProfileModel {
    private static int idCounter = 0;
    private static StonksData data;
    
    private final int id;
    private String firstName;
    private String lastName;
    private final String securityQuestion;
    private final String securityAnswer;
    private String password;
    private Color color;

    /*ProfileModel constructor WITH Password*/
    public ProfileModel(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, Color color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.password = password;
        this.color = color;
        
        id = idCounter++;
    }
    
    /*ProfileModel constructor WITHOUT Password*/
    public ProfileModel(String firstName, String lastName, String securityQuestion, String securityAnswer, Color color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.color = color;
        
        id = idCounter++;
    }
    
    public static void setData(StonksData data){
        ProfileModel.data = data;
    }

    public int getId() {
        return id;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
