/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.time.LocalDate;
import java.time.Month;
import models.ProfileModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import stonks.StonksData;

/**
 *
 * @author Bizarro
 */
public class GoalControllerTest {
    StonksData data;
    GoalController goalController;
    public GoalControllerTest() {
    }
    
    @Before
    public void setUp() {
        data = new StonksData();
        data.setCurrentProfile(new ProfileModel("João", "Paulo", "securityQuestion", "securityAnswer", "#123456"));
        goalController=new GoalController(data);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createGoal method, of class GoalController.
     */
    @Test
    public void testCreateGoal() {
        // everything null
        assertEquals(goalController.createGoal("", 0, null), 0); 
        // empty name
        assertEquals(goalController.createGoal("", 100, LocalDate.now()), 0);
        // empty objective
        assertEquals(goalController.createGoal("Bike", 0, LocalDate.now()), 0);
        // empty date
        assertEquals(goalController.createGoal("Bike", 100, null), 0);
        // invalid date
        assertEquals(goalController.createGoal("Bike", 100, LocalDate.of(1900, Month.MARCH, 1)), 0);
        // valid
        assertEquals(goalController.createGoal("Bike", 100, LocalDate.of(2020, Month.MARCH, 1)), 1);
    }

    /**
     * Test of editGoal method, of class GoalController.
     */
    @Test
    public void testEditGoal() {
        // everything null
        assertEquals(goalController.createGoal("", 0, null), 0); 
        // empty name
        assertEquals(goalController.createGoal("", 100, LocalDate.now()), 0);
        // empty objective
        assertEquals(goalController.createGoal("Bike", 0, LocalDate.now()), 0);
        // empty date
        assertEquals(goalController.createGoal("Bike", 100, null), 0);
        // invalid date
        assertEquals(goalController.createGoal("Bike", 100, LocalDate.of(1900, Month.MARCH, 1)), 0);
        // valid
        assertEquals(goalController.createGoal("Bike", 100, LocalDate.of(2020, Month.MARCH, 1)), 1);
    }

    /**
     * Test of removeGoal method, of class GoalController.
     */
    @Test
    public void testRemoveGoal() {
    }

    /**
     * Test of showNameGoal method, of class GoalController.
     */
    @Test
    public void testShowNameGoal() {
    }

    /**
     * Test of progressBarCompleted method, of class GoalController.
     */
    @Test
    public void testProgressBarCompleted() {
    }

    /**
     * Test of showActualMoney method, of class GoalController.
     */
    @Test
    public void testShowActualMoney() {
    }

    /**
     * Test of showObjectiveMoney method, of class GoalController.
     */
    @Test
    public void testShowObjectiveMoney() {
    }

    /**
     * Test of showDaysGoalDeadline method, of class GoalController.
     */
    @Test
    public void testShowDaysGoalDeadline() {
    }

    /**
     * Test of getDeadlineDate method, of class GoalController.
     */
    @Test
    public void testGetDeadlineDate() {
    }

    /**
     * Test of manageFundsWallet method, of class GoalController.
     */
    @Test
    public void testManageFundsWallet() {
    }
    
}
