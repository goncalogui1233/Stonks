package observables;

import controllers.ProfileController;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import models.ProfileModel;
import stonks.Constants;

public class AuthenticationObservable extends PropertyChangeSupport implements Constants{
    private final ProfileController cProfile;
    private final StonksObservable stonksObs;

    public AuthenticationObservable(ProfileController cProfile, StonksObservable stonksObs) {
        super(cProfile);
        
        this.cProfile = cProfile;
        this.stonksObs = stonksObs;
    }
    
    /*Bridge Methods*/
    public HashMap<Integer, ProfileModel> getListProfiles() {
        return stonksObs.getListProfiles();
    }
    
    public ProfileModel getProfile(int id) {
        return cProfile.getProfile(id);
    }
    
    public int getViewSelectedProfileId(){
        return cProfile.getViewSelectedProfileId();
    }
    
    public boolean hasMaxProfiles(){
        return cProfile.hasMaxProfiles();
    }
    
    public <T> VALIDATE verifyData(PROFILE_FIELD field, T value){
        return cProfile.verifyData(field, value);
    }
    
    public boolean createProfile(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, String color){
        boolean resp = cProfile.createProfile(firstName, lastName, securityQuestion, securityAnswer, password, color);
        
        if(resp){
            firePropertyChange(AUTH_EVENT.CREATE_PROFILE.name(), null, null);
        }
        
        return resp;
    }
    
    public String recoverPassword(String securityAnswer){
        return cProfile.recoverPassword(getViewSelectedProfileId(), securityAnswer);
    }
    
    public boolean loginProfile(int id, String password){
        boolean resp = cProfile.loginProfile(id, password);
        
        if(resp){
            stonksObs.firePropertyChange(STONKS_EVENT.GOTO_GOAL_VIEW);
        }
        
        return resp;
    }
    
    /*Clicked Methods*/
    public void profileClicked(int id){
        cProfile.setViewSelectedProfileId(id);
        
        if(cProfile.getProfile(id).hasPassword()){
            firePropertyChange(AUTH_EVENT.UPDATE_SELECTION.name(), null, null);
            firePropertyChange(AUTH_EVENT.GOTO_LOGIN.name(), null, null);
        }else{
            loginProfile(id, null);
        }
    }
    
    public void recoverPasswordClicked(){
        if(cProfile.getProfile(cProfile.getViewSelectedProfileId()).hasPassword()){
            firePropertyChange(AUTH_EVENT.GOTO_RECOVER_PASSWORD.name(), null, null);
        }
    }
    
    public void addProfileClicked(){
        cProfile.setViewSelectedProfileId(-1);
        
        firePropertyChange(AUTH_EVENT.UPDATE_SELECTION.name(), null, null);
        firePropertyChange(AUTH_EVENT.GOTO_REGISTER.name(), null, null);
    }
}
