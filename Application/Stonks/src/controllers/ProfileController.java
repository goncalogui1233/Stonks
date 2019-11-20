package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.ProfileModel;
import stonks.StonksData;

public class ProfileController {

    private StonksData data;

    public ProfileController(StonksData data) {
        this.data = data;
    }

    public boolean isFirstNameValid(String value) {
        if (value == null) {
            return false;
        }

        return !(value.length() < 1 || value.length() > 50); //Constant
    }

    public boolean isLastNameValid(String value) {
        if (value == null) {
            return false;
        }

        return !(value.length() < 1 || value.length() > 50); //Constant
    }

    public boolean isSecurityQuestionValid(String value) {
        return true;
    }

    public boolean isSecurityAnswerValid(String value) {
        if (value == null) {
            return false;
        }

        return !(value.length() < 1 || value.length() > 50); //Constant
    }

    public boolean isPasswordValid(String value) {
        if (value == null) {
            return false;
        }

        return !(value.length() < 6 || value.length() > 50); //Constant
    }

    public boolean isColorValid(String value) {
        if (value == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
        Matcher matcher = pattern.matcher(value);
        
        return matcher.matches();
    }

    public int registerProfile(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, String color) {

        if (isFirstNameValid(firstName) && isLastNameValid(lastName) && isSecurityQuestionValid(securityQuestion)
                && isSecurityAnswerValid(securityAnswer) && isColorValid(color)) {

            ProfileModel newProfile;

            if (password != null) {
                if (isPasswordValid(password)) {
                    newProfile = new ProfileModel(firstName, lastName, securityQuestion, securityAnswer, password, color);
                } else {
                    return 0;
                }

            } else {
                newProfile = new ProfileModel(firstName, lastName, securityQuestion, securityAnswer, color);
            }

            System.out.println(newProfile.getId());
            
            data.getListProfiles().put(newProfile.getId(), newProfile);

            return 1;

        }

        return 0;
    }
}
