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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import stonks.StonksData;

/**
 *
 * @author joaom
 */
public class GoalControllerTest {

    StonksData data;
    GoalController goalController;

    public GoalControllerTest() {
    }

    @Before
    public void setUp() {
        data = new StonksData();
        data.setAuthProfile(new ProfileModel("João", "Paulo", "securityQuestion", "securityAnswer", "#123456"));
        goalController = new GoalController(data);
    }

    @After
    public void tearDown() {
        data.getAuthProfile().getGoals().clear();
    }

    /**
     * Test of createGoal method, of class GoalController.
     */
    @Test
    public void testCreateGoal() throws Exception {
        assertEquals(goalController.createGoal("", 0, null), null);
        // empty name
        assertEquals(goalController.createGoal("", 100, LocalDate.now()), null);
        // empty objective
        assertEquals(goalController.createGoal("Bike", 0, LocalDate.now()), null);
        // empty date
        assertThat(goalController.createGoal("Bike", 100, null), is(new GoalModel(3, "Bike", 100, null)));
        // invalid date
        assertEquals(goalController.createGoal("Bike", 100, LocalDate.of(1900, Month.MARCH, 1)), null);
//        // valid || but compara inst working
        assertThat(goalController.createGoal("Bike", 100, LocalDate.of(2020, Month.MARCH, 1)),
                is(new GoalModel(3, "Bike", 100, LocalDate.of(2020, Month.MARCH, 1))));
    }

    /**
     * Test of editGoal method, of class GoalController.
     */
    @Test(expected = Exception.class)
    public void testEditGoal() throws Exception {
        GoalModel model = new GoalModel(1, "Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        data.getAuthProfile().getGoals().put(2, model);

        // everything null
        assertEquals(goalController.editGoal(model.getId(), "", 0, null), null);
        // empty name
        assertEquals(goalController.editGoal(model.getId(), "", 100, LocalDate.now()), null);
        // empty objective
        assertEquals(goalController.editGoal(model.getId(), "Bike", 0, LocalDate.now()), null);
        // empty date
        assertEquals(goalController.editGoal(model.getId(), "Bike", 100, null), model);
        // invalid date
        assertEquals(goalController.editGoal(model.getId(), "Bike", 100, LocalDate.of(1900, Month.MARCH, 1)), null);
        // valid
        assertEquals(goalController.editGoal(model.getId(), "Bike", 100, LocalDate.of(2020, Month.MARCH, 1)),
                model);
        // Goal that doesn't exist
        assertEquals(goalController.editGoal(1000000, "Bike", 100, LocalDate.of(2020, Month.MARCH, 1)), null);
    }

    /**
     * Test of removeGoal method, of class GoalController.
     */
    @Test(expected = Exception.class)
    public void testRemoveGoal() throws Exception {
        GoalModel model = new GoalModel(1, "Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        data.getAuthProfile().getGoals().put(2, model);
        // doesn't exist
        assertEquals(goalController.removeGoal(1000000), true);
        // exists
        assertEquals(goalController.removeGoal(model.getId()), true);
    }

    @Test(expected = Exception.class)
    public void testManageGoalFunds() throws Exception {
        GoalModel model = new GoalModel(1, "Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        model.getWallet().setSavedMoney(100);
        data.getAuthProfile().getGoals().put(1, model);
        assertEquals(goalController.manageGoalFunds(100000000, 10), 0);
        assertEquals(goalController.manageGoalFunds(model.getId(), 10), 1);
    }

    @Test(expected = Exception.class)
    public void testGetEstimatedDate() throws Exception {
        GoalModel model = new GoalModel(1, "Car", 3000, LocalDate.of(2020, Month.MARCH, 1));
        data.getAuthProfile().getGoals().put(1, model);
        goalController.manageGoalFunds(model.getId(), 100);
                                                                    //Will Fail erro because is dynamic
        assertTrue(goalController.getEstimatedDate(model.getId()).toEpochDay() == LocalDate.of(2020, Month.JANUARY, 11).toEpochDay());
        assertEquals(goalController.getEstimatedDate(1010100).toString(), null);
        //assertEquals(, data);
        
        
    }

}
