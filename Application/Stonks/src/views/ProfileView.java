package views;

import gui_components.ProfileEditBox;
import gui_components.SideMenu;
import javafx.scene.layout.HBox;
import stonks.Constants;

public class ProfileView implements Constants{
    private final HBox root;
    
    public ProfileView(){
        root = new HBox();
        root.getChildren().add(new SideMenu().getRoot());
        root.getChildren().add(new ProfileEditBox().getRoot());
    }

    public HBox getRoot() {
        return root;
    }
}
