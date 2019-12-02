package views;

import gui_components.ProfileEditBox;
import gui_components.SideMenu;
import javafx.scene.layout.HBox;
import observables.ProfileObservable;
import stonks.Constants;

public class ProfileView implements Constants{
    private final ProfileObservable profileObs;
    private final HBox root;
    
    public ProfileView(ProfileObservable profileObs){
        this.profileObs = profileObs;
        root = new HBox();
        root.getChildren().add(new SideMenu(profileObs.getStonksObs()).getRoot());
        root.getChildren().add(new ProfileEditBox(profileObs).getRoot());
    }

    public HBox getRoot() {
        return root;
    }
}
