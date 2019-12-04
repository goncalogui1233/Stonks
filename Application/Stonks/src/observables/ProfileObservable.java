package observables;

import controllers.ProfileController;
import java.beans.PropertyChangeSupport;
import models.ProfileModel;
import stonks.Constants;

public class ProfileObservable extends PropertyChangeSupport implements Constants{
    private final ProfileController cProfile;
    private final StonksObservable stonksObs;
    
    public ProfileObservable(ProfileController cProfile, StonksObservable stonksObs) {
        super(cProfile);
        
        this.cProfile = cProfile;
        this.stonksObs = stonksObs;
    }
    
    /*Bridge Methods*/
    public StonksObservable getStonksObs(){
        return stonksObs;
    }
    
    public <T> VALIDATE verifyData(PROFILE_FIELD field, T value){
        return cProfile.verifyData(field, value);
    }
    
    public ProfileModel getAuthProfile(){
        return stonksObs.getAuthProfile();
    }
    
    public boolean editProfile(String firstName, String lastName, String password, String color){
        boolean edited = cProfile.editProfile(getAuthProfile().getId(), firstName, lastName, password, color);
        
        System.out.println(edited);
        
        if(edited){
            /*CAN BE CHANGED INTO ANOTHER EVENT IF DECIDED BETTER 
            In order to update the view*/
            stonksObs.firePropertyChange(STONKS_EVENT.PROFILE_HAS_BEEN_EDITED);
        }
        
        return edited;
    }
    
    public boolean removeProfile(){
        boolean removed = cProfile.removeProfile(getAuthProfile().getId());
        
        if(removed){
            stonksObs.firePropertyChange(STONKS_EVENT.GOTO_AUTHENTICATION_VIEW);
        }
        
        return removed;
    }
}
