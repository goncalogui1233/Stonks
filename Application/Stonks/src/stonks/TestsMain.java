package stonks;

import controllers.ProfileController;
import java.util.Map;
import models.ProfileModel;

public class TestsMain {

    public static void main(String args[]) {
        System.out.println("Tests\n");
        StonksData data = new StonksData();
        data = data.loadDatabase();
        
        ProfileController pc = new ProfileController(data);
        pc.createProfile("Maria", "Leal", "question", "answer", "Kappa123", "#f1f1f1");
        data.updateDatabase();
        
        pc.loginProfile(6, "Kappa123");
        pc.logoutProfile();
        System.out.println(pc.recoverPassword(6, "answer"));
        
        if (data.getAuthProfile() != null) {
            System.out.println("Loged in: " + data.getAuthProfile().getFirstName() + " " + data.getAuthProfile().getLastName());
        }
        if (data.getListProfiles() != null) {
            if (data.getListProfiles().size() > 0) {
                System.out.println("All profiles: ");
                for (Map.Entry<Integer, ProfileModel> entry : data.getListProfiles().entrySet()) {
                    System.out.println(entry.getKey() + " - " + entry.getValue().getFirstName());
                }
            } else {
                System.out.println("No profiles created");
            }
        }
    }
}
