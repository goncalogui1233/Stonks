package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.ProfileModel;
import stonks.StonksData;

public class ProfileController {

    private StonksData data;

    public ProfileController(StonksData data) {
        this.data = data;
    }

    public boolean stonksHasProfiles() {

        if (data != null) {
            if (data.getListProfiles() != null) {
                if (data.getListProfiles().size() > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean profileIsLogedIn() {

        if (stonksHasProfiles()) {
            if (data.getCurrentProfile() == null) {
                return false;
            }
        }

        return true;
    }

    public int getNextId() {

        if (stonksHasProfiles()) {
            int biggest = 1;

            for (Integer id : data.getListProfiles().keySet()) {
                if (id > biggest) {
                    biggest = id;
                }
            }

            return ++biggest;
        }

        return 1;
    }

    public ProfileModel getProfileById(int id) {

        if (stonksHasProfiles()) {
            return data.getListProfiles().get(id);
        }

        return null;
    }

    //Inputs validation
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

        if (data == null || data.getListProfiles() == null) {
            return 0;
        }

        if (data.getListProfiles() != null) {
            if (data.getListProfiles().size() == 6) { //Constante
                return 0;
            }
        }

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

            newProfile.setId(this.getNextId());
            data.getListProfiles().put(newProfile.getId(), newProfile);

            return 1;

        }

        return 0;
    }

    public int loginProfile(int id, String password) {

        //Profile can only login if there isnt any other loged profile
        if (!this.profileIsLogedIn()) {

            ProfileModel profile = this.getProfileById(id);

            if (profile != null) {

                //If the profile doesnt have a password, logs in the profile.
                if (password == null && profile.getPassword() == null) {
                    data.setCurrentProfile(profile);
                    return 1;
                }

                //If the password input is correct
                if (this.isPasswordValid(password)) {
                    //If the password input matches the profile password, logs in the profile
                    if (profile.getPassword().equals(password)) {
                        data.setCurrentProfile(profile);
                        return 1;
                    }
                }

            }
        }
        return 0;
    }

    public int logoutProfile() {
        if (this.profileIsLogedIn()) {
            data.setCurrentProfile(null);
            return 1;
        }

        return 0;
    }

    public String recoverPassword(int id, String securityAnswer) {

        //There can't be any loged in profile to recover a password
        if (!this.profileIsLogedIn()) {
            ProfileModel profile = getProfileById(id);

            if (profile != null) {
                //The profile must be associated with a password
                if (profile.getPassword() != null) {
                    //Checks if the security asnwer input is valid
                    if (this.isSecurityAnswerValid(securityAnswer)) {
                        //If the security answer input matches the profile security answer, returns the profile password
                        if (securityAnswer.equals(profile.getSecurityAnswer())) {
                            return profile.getPassword();
                        }
                    }
                }
            }
        }

        return null;
    }

    public int editProfile(int profileId, HashMap<String, String> fields) {

        if (fields == null || fields.size() < 1) {
            return 0;
        }

        if (this.stonksHasProfiles()) {
            if (this.profileIsLogedIn()) {
                if (data.getCurrentProfile().getId() == profileId) {

                    String field;
                    String newValue;

                    for (Map.Entry<String, String> entry : fields.entrySet()) {
                        field = entry.getKey();
                        newValue = entry.getValue();

                        if (field.equalsIgnoreCase("firstname")) {
                            if (this.isFirstNameValid(newValue)) {
                                data.getListProfiles().get(profileId).setFirstName(newValue);
                                data.getCurrentProfile().setFirstName(newValue);
                            } else {
                                return 0;
                            }
                        }

                        if (field.equalsIgnoreCase("lastname")) {
                            if (this.isLastNameValid(newValue)) {
                                data.getListProfiles().get(profileId).setLastName(newValue);
                                data.getCurrentProfile().setLastName(newValue);
                            } else {
                                return 0;
                            }
                        }

                        if (field.equalsIgnoreCase("password")) {
                            if (this.isPasswordValid(newValue)) {
                                data.getListProfiles().get(profileId).setPassword(newValue);
                                data.getCurrentProfile().setPassword(newValue);
                            } else {
                                return 0;
                            }
                        }

                        if (field.equalsIgnoreCase("color")) {
                            if (this.isColorValid(newValue)) {
                                data.getListProfiles().get(profileId).setColor(newValue);
                                data.getCurrentProfile().setColor(newValue);
                            } else {
                                return 0;
                            }
                        }
                    }

                    return 1;
                }
            }
        }

        return 0;
    }
}
