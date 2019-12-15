package views;

import gui_components.SettingsBox;
import gui_components.SideMenu;
import javafx.scene.layout.HBox;
import observables.SettingsObservable;
import stonks.Constants;

public class SettingView implements Constants{
    private final SettingsObservable settObs;
    private final HBox root;
    
    public SettingView(SettingsObservable settObs){
        this.settObs = settObs;
        root = new HBox();
        root.getChildren().add(new SideMenu(settObs.getStonksObs()).getRoot());
        root.getChildren().add(new SettingsBox(settObs).getRoot());
    }

    public HBox getRoot() {
        return root;
    }
}
