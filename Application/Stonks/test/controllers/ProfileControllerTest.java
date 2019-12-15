/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.HashMap;
import java.util.Map;
import models.ProfileModel;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import stonks.StonksData;

/**
 *
 * @author joaom
 */
public class ProfileControllerTest {

    StonksData data;
    ProfileController profileController;

    public ProfileControllerTest() {
    }

    @Before
    public void setUp() {
        data = new StonksData();
        profileController = new ProfileController(data);
    }

    @Test
    public void testGetProfile() {
        assertEquals(profileController.getProfile(0), null);
        profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#123456");
        assertTrue(profileController.getProfile(1) != null);
    }

    @Test
    public void testCreateProfile() {
        //6 Users created 
        profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#223456");
        profileController.createProfile("Andre", "Batatas", "securityQuestion", "securityAnswer", "password123", "#323456");
        profileController.createProfile("Rui", "Batatas", "securityQuestion", "securityAnswer", "password123", "#423456");
        profileController.createProfile("Luis", "Batatas", "securityQuestion", "securityAnswer", "password123", "#623456");
        profileController.createProfile("Ana", "Batatas", "securityQuestion", "securityAnswer", "password123", "#783456");
        profileController.createProfile("Ana", "Batatas", "securityQuestion", "securityAnswer", "password123", "#783456");
        //Max users
        assertEquals(profileController.createProfile("Joana", "Batatas", "securityQuestion", "securityAnswer", "password123", "#783456"), false);

        //Remove 1 User
        data.setAuthProfile(profileController.getProfile(1));
        profileController.removeProfile(1);
        
        assertEquals(profileController.createProfile("Joana", "Batatas", "securityQuestion", "securityAnswer", "password123", "#783456"), true);
    }

    /**
     * Test of editProfile method, of class ProfileController.
     */
    @Test
    public void testEditProfile() {
        ProfileModel user;
        //No User Auth
        assertEquals(profileController.editProfile(0, null, null, null, null), false);
        assertEquals(profileController.editProfile(1, null, null, null, null), false);

        assertEquals(profileController.editProfile(1, "João", "Robalo",
                "password123", "#123456"), false);
        assertEquals(profileController.editProfile(1, "", "Robalo",
                "password123", "#123456"), false);

        //User Auth
        profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#123456");
        user = profileController.getProfile(1);
        data.setAuthProfile(user);
        assertEquals(profileController.editProfile(user.getId(), null, null, null, null), false);

        assertEquals(profileController.editProfile(user.getId(), "João", "Robalo",
                "password123", "#123456"), true);
        assertEquals(profileController.editProfile(user.getId(), "", "Robalo",
                "password123", "#123456"), false);
    }

    @Test
    public void testRemoveProfile() {
        ProfileModel user;
        //not auth
        assertEquals(profileController.removeProfile(1), false);
        //User Auth
        profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#123456");
        user = profileController.getProfile(1);
        data.setAuthProfile(user);
        //Try delete another user
        assertEquals(profileController.removeProfile(user.getId()), true);

    }

    @Test
    public void testGetNextId() {
        assertEquals(profileController.getNextId(), 1);
        data.getListProfiles().put(1, new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul"));
        assertEquals(profileController.getNextId(), 2);
    }

    @Test(expected = Exception.class)
    public void testLoginProfile() {
        assertEquals(profileController.loginProfile(0, ""), false);
        ProfileModel user;
        profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#123456");
        profileController.createProfile("Alfredo", "Batatas", "securityQuestion", "securityAnswer", "", "#123456");
        
        assertEquals(profileController.loginProfile(1, ""), false); //user without password
        assertEquals(profileController.loginProfile(2, ""), false); //user with password
        assertEquals(profileController.loginProfile(2, "password13"), true);
        
        //With user logged
        user = profileController.getProfile(2);
        data.setAuthProfile(user);

        assertEquals(profileController.loginProfile(1, ""), false); //user without password
        assertEquals(profileController.loginProfile(2, ""), false); //user with password
        assertEquals(profileController.loginProfile(2, "password13"), false);
    }

    @Test
    public void testLogoutProfile() {
        ProfileModel user;
        assertEquals(profileController.logoutProfile(), false);
        profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#123456");
        user = profileController.getProfile(1);
        data.setAuthProfile(user);
        assertEquals(profileController.logoutProfile(), true);
        assertEquals(profileController.logoutProfile(), false);
    }

    @Test
    public void testRecoverPassword() {
        ProfileModel user;
        //No User Auth
        //assertNull(profileController.recoverPassword(0, ""));


        profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#123456");
        user = profileController.getProfile(1);
        data.setAuthProfile(user);
        //User Auth
          assertNull(profileController.recoverPassword(1, ""));
          assertEquals(profileController.recoverPassword(1, "securityAnswer"), "password123");
    }

}
