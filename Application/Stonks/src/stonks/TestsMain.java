/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stonks;

import controllers.ProfileController;
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
        
        //ProfileController pc = new ProfileController(data);
        //pc.registerProfile("Maria", "Leal", "question", "answer", null, "#f1f1f1");
        //data.updateDatabase();
        if(data.getCurrentProfile() != null){
            System.out.println(data.getCurrentProfile().getFirstName());
        }
        if (data.getListProfiles() != null) {
            if (data.getListProfiles().size() > 0) {
                System.out.println(data.getListProfiles().get(0).getFirstName());
            } else {
                System.out.println("No profiles created");
            }
        }
    }
}
