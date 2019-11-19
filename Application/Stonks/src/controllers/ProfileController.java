package controllers;

import models.ProfileModel;
import stonks.StonksData;

public class ProfileController {

    private final StonksData data;

    public ProfileController(StonksData data) {
        this.data = data;
    }

    public boolean isFirstNameValid(String value) {
        return false;
    }

    public boolean isLastNameValid(String value) {
        return false;
    }

    public boolean isSecurityQuestionValid(String value) {
        return false;
    }

    public boolean isSecurityAnswerValid(String value) {
        return false;
    }

    public boolean isPasswordValid(String value) {
        return false;
    }

    public boolean isColorValid(String value) {
        return false;
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

            data.getListProfiles().put(newProfile.getId(), newProfile);

            return 1;

        }

        return 0;
    }
}
