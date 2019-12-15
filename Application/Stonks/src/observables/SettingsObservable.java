package observables;

import java.beans.PropertyChangeSupport;
import stonks.Constants;

public class SettingsObservable extends PropertyChangeSupport implements Constants {

    private final StonksObservable stonksObs;

    public SettingsObservable(StonksObservable stonksObs) {
        super(stonksObs);

        this.stonksObs = stonksObs;
    }

    public StonksObservable getStonksObs() {
        return stonksObs;
    }

    public boolean setDarkMode(boolean darkMode) {
        stonksObs.getAuthProfile().getSettings().setDarkMode(darkMode);
        stonksObs.updateDatabase();

        if (darkMode) {
            stonksObs.firePropertyChange(STONKS_EVENT.DARKMODE_ACTIVE.name(), null, null);
        } else {
            stonksObs.firePropertyChange(STONKS_EVENT.DARKMODE_INACTIVE.name(), null, null);
        }

        return darkMode;
    }
}
