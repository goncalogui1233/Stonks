///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package controllers;
//
//import java.util.HashMap;
//import java.util.Map;
//import models.ProfileModel;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import stonks.StonksData;
//
///**
// *
// * @author Bizarro
// */
//public class ProfileControllerTest {
//    StonksData data;
//    ProfileController profileController;
//    
//    public ProfileControllerTest() {
//    }
//    
//    @Before
//    public void setUp(){
//        data = new StonksData();
//        profileController = new ProfileController(data);
//    }
//
//    /**
//     * Test of stonksHasProfiles method, of class ProfileController.
//     */
//    @Test
//    public void testStonksHasProfiles() {
//        assertEquals(profileController.stonksHasProfiles(), false);
//        data.getListProfiles().put(100, new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul"));
//        assertEquals(profileController.stonksHasProfiles(), true);
//    }
//
//    /**
//     * Test of profileIsLogedIn method, of class ProfileController.
//     */
//    @Test
//    public void testProfileIsLogedIn() {
//        // No accounts created should return false
//        assertEquals(profileController.profileIsLogedIn(), false);
//        data.setCurrentProfile(new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul"));
//        assertEquals(profileController.profileIsLogedIn(), true);
//    }
//
//    /**
//     * Test of getNextId method, of class ProfileController.
//     */
//    @Test
//    public void testGetNextId() {
//        assertEquals(profileController.getNextId(), 1);
//        data.getListProfiles().put(1, new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul"));
//        assertEquals(profileController.getNextId(), 2);
//    }
//
//    /**
//     * Test of getProfileById method, of class ProfileController.
//     */
//    @Test
//    public void testGetProfileById() {
//        ProfileModel p = new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul");
//        data.getListProfiles().put(1, p);
//        assertEquals(p, profileController.getProfileById(1));
//    }
//
//    /**
//     * Test of isFirstNameValid method, of class ProfileController.
//     */
//    @Test
//    public void testIsFirstNameValid() {
//        assertFalse(profileController.isFirstNameValid(null));
//        assertFalse(profileController.isFirstNameValid(""));
//        assertFalse(profileController.isFirstNameValid("afhdsafhsadfjasbcjsabcjdhsacgsavbhdsfbcbdsacnkndsckancjndscanksckdsancjsdncjsndkcns"));
//        assertTrue(profileController.isFirstNameValid("João"));
//    }
//
//    /**
//     * Test of isLastNameValid method, of class ProfileController.
//     */
//    @Test
//    public void testIsLastNameValid() {
//        assertFalse(profileController.isLastNameValid(null));
//        assertFalse(profileController.isLastNameValid(""));
//        assertFalse(profileController.isLastNameValid("afhdsafhsadfjasbcjsabcjdhsacgsavbhdsfbcbdsacnkndsckancjndscanksckdsancjsdncjsndkcns"));
//        assertTrue(profileController.isLastNameValid("Lopes"));
//    }
//
//    /**
//     * Test of isSecurityQuestionValid method, of class ProfileController.
//     */
//    @Test
//    public void testIsSecurityQuestionValid() {    }
//
//    /**
//     * Test of isSecurityAnswerValid method, of class ProfileController.
//     */
//    @Test
//    public void testIsSecurityAnswerValid() {
//        assertFalse(profileController.isSecurityAnswerValid(null));
//        assertFalse(profileController.isSecurityAnswerValid(""));
//        assertFalse(profileController.isSecurityAnswerValid("afhdsafhsadfjasbcjsabcjdhsacgsavbhdsfbcbdsacnkndsckancjndscanksckdsancjsdncjsndkcns"));
//        assertTrue(profileController.isSecurityAnswerValid("João Lopes"));
//    }
//
//    /**
//     * Test of isPasswordValid method, of class ProfileController.
//     */
//    @Test
//    public void testIsPasswordValid() {
//        assertFalse(profileController.isPasswordValid(null));
//        assertFalse(profileController.isPasswordValid(""));
//        assertFalse(profileController.isPasswordValid("abc"));
//        assertFalse(profileController.isPasswordValid("afhdsafhsadfjasbcjsabcjdhsacgsavbhdsfbcbdsacnkndsckancjndscanksckdsancjsdncjsndkcns"));
//        assertTrue(profileController.isPasswordValid("password123"));
//    }
//
//    /**
//     * Test of isColorValid method, of class ProfileController.
//     */
//    @Test
//    public void testIsColorValid() {
//        assertFalse(profileController.isColorValid(null));
//        assertFalse(profileController.isColorValid(""));
//        assertFalse(profileController.isColorValid("abc"));
//        assertFalse(profileController.isColorValid("#H1"));
//        assertTrue(profileController.isColorValid("#FFFFFF"));
//    }
//
//    /**
//     * Test of registerProfile method, of class ProfileController.
//     */
//    @Test
//    public void testRegisterProfile() {
//        assertEquals(profileController.registerProfile("João", "Batatas", "Pergunta de segurança", "Resposta", "password123", "#123456"), 1);
//        assertEquals(profileController.registerProfile("", "Batatas", "Pergunta de segurança", "Resposta", "password123", "#123456"), 0);
//        assertEquals(profileController.registerProfile("João", "", "Pergunta de segurança", "Resposta", "password123", "#123456"), 0);
//        assertEquals(profileController.registerProfile("João", "Batatas", "", "Resposta", "password123", "#123456"), 0);
//        assertEquals(profileController.registerProfile("João", "Batatas", "Pergunta de segurança", "", "password123", "#123456"), 0);
//        assertEquals(profileController.registerProfile("João", "Batatas", "Pergunta de segurança", "Resposta", "", "#123456"), 0);
//        assertEquals(profileController.registerProfile("João", "Batatas", "Pergunta de segurança", "Resposta", "password123", ""), 0);
//    }
//
//    /**
//     * Test of loginProfile method, of class ProfileController.
//     */
//    @Test
//    public void testLoginProfile() {
//        assertEquals(profileController.loginProfile(0, ""), 0);
//        ProfileModel sp = new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul");
//        ProfileModel p = new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta","password123", "Azul");
//        data.getListProfiles().put(1, sp);
//        data.getListProfiles().put(2, p);
//        assertEquals(profileController.loginProfile(1, ""),1); //user without password
//        assertEquals(profileController.loginProfile(2, ""),0); //user with password
//        assertEquals(profileController.loginProfile(2, "password123"),1);
//    }
//
//    /**
//     * Test of logoutProfile method, of class ProfileController.
//     */
//    @Test
//    public void testLogoutProfile() {
//        assertEquals(profileController.logoutProfile(), 0);
//        data.setCurrentProfile(new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta", "Azul"));
//        assertEquals(profileController.logoutProfile(), 1);
//        assertEquals(profileController.logoutProfile(), 0);
//    }
//
//    /**
//     * Test of recoverPassword method, of class ProfileController.
//     */
//    @Test
//    public void testRecoverPassword() {
//        assertNull(profileController.recoverPassword(0, ""));
//        data.getListProfiles().put(1, new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta","password123", "Azul"));
//        assertNull(profileController.recoverPassword(1, ""));
//        assertEquals(profileController.recoverPassword(1, "Resposta"),"password123");
//    }
//
//    /**
//     * Test of editProfile method, of class ProfileController.
//     */
//    @Test
//    public void testEditProfile() {
//        assertEquals(profileController.editProfile(0, null), 0);
//        data.getListProfiles().put(1, new ProfileModel("João", "Batatas", "Pergunta de segurança", "Resposta","password123", "Azul"));
//        assertEquals(profileController.editProfile(1, null), 0);
//        HashMap<String,String> map = new HashMap<>();
//        map.put("firstname", "João");
//        map.put("lastname", "Robalo");
//        map.put("password", "password123");
//        map.put("color", "#123456");
//        assertEquals(profileController.editProfile(1, map), 1);
//        map.replace("firsname", "");
//        assertEquals(profileController.editProfile(1, map), 0);
//    }
//
//}
