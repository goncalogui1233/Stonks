/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stonks;

import controllers.ProfileController;
import java.util.HashMap;
import java.util.Map;
import models.ProfileModel;

/**
 *
 * @author Tiago
 */
public class TestsMain {

    public static void main(String args[]) {
        System.out.println("Tests\n");
        StonksData data = new StonksData();
        data = data.loadDatabase();
        //data.setCurrentProfile(new ProfileModel("firstName", "lastName", "question", "answer", "password", "#ffffff"));
        //data.updateDatabase();

        ProfileController pc = new ProfileController(data);
        pc.registerProfile("Maria", "Leal", "question", "answer", null, "#f1f1f1");
        data.updateDatabase();
        
        pc.loginProfile(6, null);
        
        if (data.getCurrentProfile() != null) {
            System.out.println("Loged in: " + data.getCurrentProfile().getFirstName() + " " + data.getCurrentProfile().getLastName());
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
