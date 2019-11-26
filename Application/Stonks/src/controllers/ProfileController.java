package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.ProfileModel;
import stonks.Constants;
import stonks.StonksData;

public class ProfileController implements Constants {

    private StonksData data;

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

    public boolean createProfile(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, String color) {
        if (hasMaxProfiles())
            return false;

        if (verifyData(PROFILE_FIELD.FIRST_NAME, firstName)
                && verifyData(PROFILE_FIELD.LAST_NAME, lastName)
                && verifyData(PROFILE_FIELD.SECURITY_ANSWER, securityQuestion)
                && verifyData(PROFILE_FIELD.SECURITY_ANSWER, securityAnswer)
                && verifyData(PROFILE_FIELD.COLOR, color)) {

            ProfileModel newProfile;
            if (password != null) {
                if (verifyData(PROFILE_FIELD.PASSWORD, password)) {
                    newProfile = new ProfileModel(firstName, lastName, securityQuestion, securityAnswer, password, color);
                } else {
                    return false;
                }
            } else {
                newProfile = new ProfileModel(firstName, lastName, securityQuestion, securityAnswer, color);
            }

//            newProfile.setId(this.getNextId());
            data.getListProfiles().put(newProfile.getId(), newProfile);

            /*UPDATE DATABASE*/
            return true;
        }

        return false;
    }

    public boolean editProfile(int profileId, HashMap<String, String> fields) {
        if (fields == null || fields.size() < 1)
            return false;

        if (hasAuthProfile()) {
            if (data.getAuthProfile().getId() == profileId) {
                String field;
                String newValue;

                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    field = entry.getKey();
                    newValue = entry.getValue();

                    if (field.equalsIgnoreCase("firstname")) {
                        if (!verifyData(PROFILE_FIELD.FIRST_NAME, newValue)) {
                            return false;
                        }
                    }else if (field.equalsIgnoreCase("lastname")) {
                        if (!verifyData(PROFILE_FIELD.LAST_NAME, newValue)) {
                            return false;
                        }
                    }else if (field.equalsIgnoreCase("password")) {
                        if (!verifyData(PROFILE_FIELD.PASSWORD, newValue)) {
                            return false;
                        }
                    }else if (field.equalsIgnoreCase("color")) {
                        if (!verifyData(PROFILE_FIELD.COLOR, newValue)) {
                            return false;
                        }
                    }
                }

                for (Map.Entry<String, String> entry : fields.entrySet()) {
                    field = entry.getKey();
                    newValue = entry.getValue();
                    if (field.equalsIgnoreCase("firstname")) {
                            data.getListProfiles().get(profileId).setFirstName(newValue);
                    }else if (field.equalsIgnoreCase("lastname")) {
                            data.getListProfiles().get(profileId).setLastName(newValue);
                    }else if (field.equalsIgnoreCase("password")) {
                            data.getListProfiles().get(profileId).setPassword(newValue);
                    }else if (field.equalsIgnoreCase("color")) {
                            data.getListProfiles().get(profileId).setColor(newValue);
                    }
                }
                
                /*UPDATE DATABASE*/
                return true;
            }
        }

        return false;
    }
    
    public boolean removeProfile(int profileId){
        try {
            data.getListProfiles().remove(profileId);

            /*UPDATE DATABASE*/
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

//    public int getNextId() {
//        if (!hasNoProfiles()) {
//            int biggest = 1;
//
//            for (Integer id : data.getListProfiles().keySet()) {
//                if (id > biggest) {
//                    biggest = id;
//                }
//            }
//
//            return ++biggest;
//        }
//
//        return 1;
//    }
    //Inputs validation
    private <T> boolean verifyData(PROFILE_FIELD field, T value) { //verify the data in goal name and objective
        try {
            switch (field) {
                /*FIRST_NAME FIELD VALIDATIONS*/
                case FIRST_NAME:
                    return !(((String) value).length() < 1/*CONSTANT*/
                            || ((String) value).length() > 50/*CONSTANT*/);

                /*LAST_NAME FIELD VALIDATIONS*/
                case LAST_NAME:
                    return !(((String) value).length() < 1/*CONSTANT*/
                            || ((String) value).length() > 50/*CONSTANT*/);

                /*SECURITY_QUESTION FIELD VALIDATIONS*/
                case SECURITY_QUESTION:
                    /*TODO VALIDATION*/
                    return false;

                /*SECURITY_ANSWER FIELD VALIDATIONS*/
                case SECURITY_ANSWER:
                    return !(((String) value).length() < 1/*CONSTANT*/
                            || ((String) value).length() > 50/*CONSTANT*/);

                /*PASSWORD FIELD VALIDATIONS*/
                case PASSWORD:
                    return !(((String) value).length() < 6/*CONSTANT*/
                            || ((String) value).length() > 50/*CONSTANT*/);

                /*COLOR FIELD VALIDATIONS*/
                case COLOR:
                    Pattern pattern = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
                    Matcher matcher = pattern.matcher(((String) value));

                    return matcher.matches();

                default:
                    return false;
            }
        } catch (ClassCastException ex) {
            return false;
        } catch (Exception ex) {
            System.out.println(ex);

            return false;
        }
    }

    public boolean loginProfile(int id, String password) {
        //Profile can only login if there isnt any other loged profile
        if (!hasAuthProfile()) {
            ProfileModel profile = this.getProfile(id);

            if (profile != null) {

                //If the profile doesnt have a password, logs in the profile.
                if (password == null && profile.getPassword() == null) {
                    data.setCurrentProfile(profile);
                    return true;
                }

                //If the password input is correct
                if (verifyData(PROFILE_FIELD.PASSWORD, password)) {
                    //If the password input matches the profile password, logs in the profile
                    if (profile.getPassword().equals(password)) {
                        data.setCurrentProfile(profile);
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean logoutProfile() {
        if (hasAuthProfile()) {
            data.setCurrentProfile(null);
            return true;
        }

        return false;
    }

    public String recoverPassword(int id, String securityAnswer) {
        //There can't be any loged in profile to recover a password
        if (hasAuthProfile())
            return null;
        
        ProfileModel profile = getProfile(id);

        try{
            //Checks if the security asnwer input is valid
            if (verifyData(PROFILE_FIELD.SECURITY_ANSWER, securityAnswer)) {
                //If the security answer input matches the profile security answer, returns the profile password
                if (securityAnswer.equals(profile.getSecurityAnswer())) {
                    return profile.getPassword();
                }
            }
        }catch(NullPointerException ex){
            return null;
        }
        
        return null;
    }
}
