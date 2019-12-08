/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.time.LocalDate;
import java.time.Month;
import models.GoalModel;
import models.ProfileModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import stonks.StonksData;
import static java.time.temporal.ChronoUnit.DAYS;
import javax.annotation.PreDestroy;

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
//        data.getCurrentProfile().getGoals().clear();
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
        
        GoalModel model =  new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
//        data.getCurrentProfile().getGoals().add(model);

        // everything null
        assertEquals(goalController.editGoal(model.getId(), "", 0, null), 0); 
        // empty name
        assertEquals(goalController.editGoal(model.getId(),"", 100, LocalDate.now()), 0);
        // empty objective
        assertEquals(goalController.editGoal(model.getId(),"Bike", 0, LocalDate.now()), 0);
        // empty date
        assertEquals(goalController.editGoal(model.getId(),"Bike", 100, null), 0);
        // invalid date
        assertEquals(goalController.editGoal(model.getId(),"Bike", 100, LocalDate.of(1900, Month.MARCH, 1)), 0);
        // valid
        assertEquals(goalController.editGoal(model.getId(),"Bike", 100, LocalDate.of(2020, Month.MARCH, 1)), 1);
        // Goal that doesn't exist
        assertEquals(goalController.editGoal(1000000,"Bike", 100, LocalDate.of(2020, Month.MARCH, 1)), 0); 

    }

    /**
     * Test of removeGoal method, of class GoalController.
     */
    @Test
    public void testRemoveGoal() {
        GoalModel model =  new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
//        data.getCurrentProfile().getGoals().add(model);
        // doesn't exist
        assertEquals(goalController.removeGoal(1000000), 0);
        // exists
        assertEquals(goalController.removeGoal(model.getId()), 1);
    }

    /**
     * Test of showNameGoal method, of class GoalController.
     */
    @Test
    public void testShowNameGoal() {
        GoalModel model =  new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
//        data.getCurrentProfile().getGoals().add(model);
//
//        assertEquals(goalController.showNameGoal(100), "");
//        assertEquals(goalController.showNameGoal(model.getId()), "Bike");
    }

    /**
     * Test of progressBarCompleted method, of class GoalController.
     */
    @Test
    public void testProgressBarCompleted() {
        GoalModel model = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        model.getWallet().setSavedMoney(100);
//        data.getCurrentProfile().getGoals().add(model);
//        assertEquals(goalController.progressBarCompleted(100000), -1);
//        assertEquals(goalController.progressBarCompleted(model.getId()), 100);
        
    }

    /**
     * Test of showActualMoney method, of class GoalController.
     */
    @Test
    public void testShowActualMoney() {
        GoalModel model = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        model.getWallet().setSavedMoney(100);
//        data.getCurrentProfile().getGoals().add(model);
//        assertEquals(goalController.showActualMoney(1000000000), -1);
//        assertEquals(goalController.showActualMoney(model.getId()), 100);
    }

    /**
     * Test of showObjectiveMoney method, of class GoalController.
     */
    @Test
    public void testShowObjectiveMoney() {
        GoalModel model = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        model.getWallet().setSavedMoney(100);
//        data.getCurrentProfile().getGoals().add(model);
//        assertEquals(goalController.showObjectiveMoney(100000000), -1);
//        assertEquals(goalController.showObjectiveMoney(model.getId()), 100);
    }

    /**
     * Test of showDaysGoalDeadline method, of class GoalController.
     */
    @Test
    public void testShowDaysGoalDeadline() {
        GoalModel model = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        model.getWallet().setSavedMoney(100);
//        data.getCurrentProfile().getGoals().add(model);
//        assertEquals(goalController.showDaysGoalDeadline(1000000000), -1);
//        assertEquals(goalController.showDaysGoalDeadline(model.getId()), DAYS.between(model.getDeadlineDate(),LocalDate.now()));
    }

    /**
     * Test of getDeadlineDate method, of class GoalController.
     */
    @Test
    public void testGetDeadlineDate() {
        GoalModel model = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        model.getWallet().setSavedMoney(100);
//        data.getCurrentProfile().getGoals().add(model);
//        assertEquals(goalController.getDeadlineDate(1000000000), null);
//        assertEquals(goalController.getDeadlineDate(model.getId()), model.getDeadlineDate());
    }

    /**
     * Test of manageFundsWallet method, of class GoalController.
     */
    @Test
    public void testManageFundsWallet() {
        GoalModel model = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        model.getWallet().setSavedMoney(100);
//        data.getCurrentProfile().getGoals().add(model);
//        assertEquals(goalController.manageFundsWallet(100000000, 10), 0);
//        assertEquals(goalController.manageFundsWallet(model.getId(), 10), 1);
    }
    
}
