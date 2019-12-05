package observables;

import controllers.ProfileController;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import models.ProfileModel;
import stonks.Constants;
import stonks.StonksData;

public class StonksObservable extends PropertyChangeSupport implements Constants{
    
    private final ProfileController cProfile;
    private final StonksData data;
    
    public StonksObservable(ProfileController cProfile, StonksData data) {
        super(data);
        
        this.cProfile = cProfile;
        this.data = data;
    }
    
    /*Bridge Methods*/
    public HashMap<Integer, ProfileModel> getListProfiles() {
        return data.getListProfiles();
    }
    
    public ProfileModel getAuthProfile(){
        return data.getAuthProfile();
    }
    
    public boolean logout(){
        boolean loggedOut = cProfile.logoutProfile();
        
        if(loggedOut){
            firePropertyChange(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW);
        }
        
        return loggedOut;
    }
    
    public void firePropertyChange(STONKS_EVENT event){
        firePropertyChange(event.name(), null, null);
    }
}
