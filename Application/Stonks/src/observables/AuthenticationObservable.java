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
    
    /*Methods*/
    public boolean hasMaxProfiles(){
        return cProfile.hasMaxProfiles();
    }
    
    public <T> VALIDATE verifyData(PROFILE_FIELD field, T value){
        return cProfile.verifyData(field, value);
    }
    
    public boolean createProfile(String firstName, String lastName, String securityQuestion, String securityAnswer, String password, String color){
        boolean resp = cProfile.createProfile(firstName, lastName, securityQuestion, securityAnswer, password, color);
        
        System.out.println(resp);
        
        if(resp){
            firePropertyChange(AUTH_EVENT.CREATE_PROFILE.name(), null, null);
        }
        
        return resp;
    }
    
    public HashMap<Integer, ProfileModel> getListProfiles() {
        return stonksObs.getListProfiles();
    }
}
