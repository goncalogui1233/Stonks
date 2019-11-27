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
}
