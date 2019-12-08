package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.ProfileModel;
import stonks.Constants;
import stonks.StonksData;

public class ProfileController implements Constants {

    private final StonksData data;
    private int viewSelectedProfileId;

    public ProfileController(StonksData data) {
        this.data = data;
    }

    public ProfileModel getProfile(int id) {
        try {
            return data.getListProfiles().get(id);
        } catch (NullPointerException ex) {
            return null;
        }

    }

    public int getViewSelectedProfileId() {
        return viewSelectedProfileId;
    }

    public void setViewSelectedProfileId(int viewSelectedProfileId) {
        this.viewSelectedProfileId = viewSelectedProfileId;
    }

    public boolean createProfile(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, String color) {
        if (hasMaxProfiles()) {
            return false;
        }

        if (verifyData(PROFILE_FIELD.FIRST_NAME, firstName) == VALIDATE.OK
                && verifyData(PROFILE_FIELD.LAST_NAME, lastName) == VALIDATE.OK
                && verifyData(PROFILE_FIELD.SECURITY_QUESTION, securityQuestion) == VALIDATE.OK
                && verifyData(PROFILE_FIELD.SECURITY_ANSWER, securityAnswer) == VALIDATE.OK
                && verifyData(PROFILE_FIELD.COLOR, color) == VALIDATE.OK) {

            ProfileModel newProfile;
            if (!password.isEmpty()) {
                if (verifyData(PROFILE_FIELD.PASSWORD, password) == VALIDATE.OK) {
                    newProfile = new ProfileModel(firstName, lastName, securityQuestion, securityAnswer, password, color);
                } else {
                    return false;
                }
            } else {
                newProfile = new ProfileModel(firstName, lastName, securityQuestion, securityAnswer, color);
            }

            newProfile.setId(this.getNextId());
//            System.out.println(newProfile.toString()); 
            data.getListProfiles().put(newProfile.getId(), newProfile);

            data.updateDatabase();
            return true;
        }

        return false;
    }

    public boolean editProfile(int profileId, String firstName, String lastName, String password, String color) {
        if (!hasAuthProfile() || data.getAuthProfile().getId() != profileId) {
            System.out.println("smth aint right");
            return false;
        }

        if (verifyData(PROFILE_FIELD.FIRST_NAME, firstName) != VALIDATE.OK
                || verifyData(PROFILE_FIELD.LAST_NAME, lastName) != VALIDATE.OK
                || verifyData(PROFILE_FIELD.PASSWORD, password) != VALIDATE.OK
                || verifyData(PROFILE_FIELD.COLOR, color) != VALIDATE.OK) {
            
            return false;
        }
        data.getListProfiles().get(profileId).setFirstName(firstName);
        data.getListProfiles().get(profileId).setLastName(lastName);
        data.getListProfiles().get(profileId).setPassword(password);
        data.getListProfiles().get(profileId).setColor(color);
        
        data.updateDatabase();

        return true;
    }

    public boolean removeProfile(int profileId) {
        try {
            if (data.getAuthProfile().getId() == profileId) {
                logoutProfile();
            }
            data.getListProfiles().remove(profileId);

            data.updateDatabase();
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public int getListProfilesSize() {
        try {
            return data.getListProfiles().size();
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    public boolean hasMaxProfiles() {
        try {
            return (data.getListProfiles().size() == MAX_PROFILES);
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public boolean hasNoProfiles() {
        try {
            return data.getListProfiles().isEmpty();
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public boolean hasAuthProfile() {
        if (!hasNoProfiles()) {
            if (data.getAuthProfile() != null) {
                return true;
            }
        }
        return false;
    }

    public int getNextId() {
        if (!hasNoProfiles()) {
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

    //Inputs validation
    public <T> VALIDATE verifyData(PROFILE_FIELD field, T value) { //verify the data in goal name and objective
        try {
            switch (field) {
                /*FIRST_NAME FIELD VALIDATIONS*/
                case FIRST_NAME:
                    if ((((String) value).isEmpty()))/*CONSTANT*/ {
                        return VALIDATE.EMPTY;
                    }
                    if ((((String) value).length() > 50))/*CONSTANT*/ {
                        return VALIDATE.MAX_CHAR;
                    }

                    return VALIDATE.OK;

                /*LAST_NAME FIELD VALIDATIONS*/
                case LAST_NAME:
                    if ((((String) value).isEmpty()))/*CONSTANT*/ {
                        return VALIDATE.EMPTY;
                    }
                    if ((((String) value).length() > 50))/*CONSTANT*/ {
                        return VALIDATE.MAX_CHAR;
                    }

                    return VALIDATE.OK;

                /*SECURITY_QUESTION FIELD VALIDATIONS*/
                case SECURITY_QUESTION:
                    if (((String) value).equals(SECURITY_QUESTIONS.PROTOTYPE.getQuestion())) {
                        return VALIDATE.INVALID_QUESTION;
                    }
                    return VALIDATE.OK;

                /*SECURITY_ANSWER FIELD VALIDATIONS*/
                case SECURITY_ANSWER:
                    if ((((String) value).isEmpty()))/*CONSTANT*/ {
                        return VALIDATE.EMPTY;
                    }
                    if ((((String) value).length() > 50))/*CONSTANT*/ {
                        return VALIDATE.MAX_CHAR;
                    }

                    return VALIDATE.OK;

                /*PASSWORD FIELD VALIDATIONS*/
                case PASSWORD:
                    if ((value == null || ((String) value).isEmpty()))/*CONSTANT*/ {
                        return VALIDATE.OK;
                    }
                    if ((((String) value).length() < 6))/*CONSTANT*/ {
                        return VALIDATE.MIN_CHAR;
                    }
                    if ((((String) value).length() > 50))/*CONSTANT*/ {
                        return VALIDATE.MAX_CHAR;
                    }

                    return VALIDATE.OK;

                /*COLOR FIELD VALIDATIONS*/
                case COLOR:
                    Pattern pattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
                    Matcher matcher = pattern.matcher(((String) value));

                    if (!matcher.matches()) {
                        return VALIDATE.FORMAT;
                    }

                    return VALIDATE.OK;

                default:
                    return VALIDATE.UNDEFINED;
            }
        } catch (ClassCastException ex) {
            return VALIDATE.UNDEFINED;
        } catch (Exception ex) {
            System.out.println(field.name() + ex);

            return VALIDATE.UNDEFINED;
        }
    }

    public boolean loginProfile(int id, String password) {
        //Profile can only login if there isnt any other loged profile 
        if (!hasAuthProfile()) {
            ProfileModel profile = this.getProfile(id);

            if (profile != null) {

                //If the profile doesnt have a password, logs in the profile. 
                if (password == null && (profile.getPassword() == null || profile.getPassword().isEmpty())) {
                    data.setAuthProfile(profile);
                    return true;
                }

                //If the password input is correct 
                if (verifyData(PROFILE_FIELD.PASSWORD, password) == VALIDATE.OK) {
                    //If the password input matches the profile password, logs in the profile 
                    if (profile.getPassword().equals(password)) {
                        data.setAuthProfile(profile);
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean logoutProfile() {
        if (hasAuthProfile()) {
            data.setAuthProfile(null);
            return true;
        }

        return false;
    }

    public String recoverPassword(int id, String securityAnswer) {
        //There can't be any loged in profile to recover a password 
        if (hasAuthProfile()) {
            return null;
        }

        ProfileModel profile = getProfile(id);

        try {
            //Checks if the security asnwer input is valid 
            if (verifyData(PROFILE_FIELD.SECURITY_ANSWER, securityAnswer) == VALIDATE.OK) {
                //If the security answer input matches the profile security answer, returns the profile password 
                if (securityAnswer.equals(profile.getSecurityAnswer())) {
                    return profile.getPassword();
                }
            }
        } catch (NullPointerException ex) {
            return null;
        }

        return null;
    }
}
