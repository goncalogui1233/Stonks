/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.HashMap;
import java.util.Map;
import models.ProfileModel;
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
        //
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
    }


    @Test
    public void testHasAuthProfile() {
    }

    @Test
    public void testGetNextId() {
        assertEquals(profileController.getNextId(), 1);
        data.getListProfiles().put(1, new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul"));
        assertEquals(profileController.getNextId(), 2);
    }

    @Test
    public void testLoginProfile() {
        
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

    @Test (expected = Exception.class)
    public void testRecoverPassword() {
        ProfileModel user;
        //No User Auth
        try {
            //assertEquals(profileController.recoverPassword(0, ""),null);
        } catch (Exception e) {
        }

        //profileController.createProfile("João", "Batatas", "securityQuestion", "securityAnswer", "password123", "#123456");
        //user = profileController.getProfile(1);
        //data.setAuthProfile(user);
        //User Auth
      //  assertNull(profileController.recoverPassword(1, ""));
      //  assertEquals(profileController.recoverPassword(1, "securityAnswer"), "password123");
    }

}
