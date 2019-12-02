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
    public <T> VALIDATE verifyData(PROFILE_FIELD field, T value){
        return cProfile.verifyData(field, value);
    }
    
    public ProfileModel getProfile(int id) {
        return cProfile.getProfile(id);
    }
    
    public int getViewSelectedProfileId(){
        return cProfile.getViewSelectedProfileId();
    }
}
