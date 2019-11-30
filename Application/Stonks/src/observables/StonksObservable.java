package observables;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import models.ProfileModel;
import stonks.Constants;
import stonks.StonksData;

public class StonksObservable extends PropertyChangeSupport implements Constants{
    
    private final StonksData data;
    
    public StonksObservable(StonksData data) {
        super(data);
        
        this.data = data;
    }
    
    /*Methods*/
    public HashMap<Integer, ProfileModel> getListProfiles() {
        return data.getListProfiles();
    }
    
    public ProfileModel getAuthProfile(){
        return data.getAuthProfile();
    }
  
    public void firePropertyChange(STONKS_EVENT event){
        firePropertyChange(event.name(), null, null);
    }
}
