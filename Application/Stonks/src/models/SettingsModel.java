package models;

import java.io.Serializable;

public class SettingsModel implements Serializable {
    private boolean darkMode;

    public SettingsModel() {
        this.darkMode = false;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
