/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import models.GoalModel;
import models.ProfileModel;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static stonks.Constants.DASHBOARD_STATISTICS_GOALS_COMPLETE;
import static stonks.Constants.DASHBOARD_STATISTICS_SAVED_MONEY;
import static stonks.Constants.DASHBOARD_STATISTICS_TOTAL_GOALS;
import static stonks.Constants.DASHBOARD_STATISTICS_TOTAL_INCOMPLETE;
import static stonks.Constants.DASHBOARD_STATISTICS_TOTAL_OBJECTIVE;
import stonks.StonksData;

/**
 *
 * @author joaom
 */
public class DashboardControllerTest {

    StonksData data;
    DashboardController dashboardController;
    GoalController goals;

    public DashboardControllerTest() {

    }

    @Before
    public void setUp() {
        data = new StonksData();
        data.setCurrentProfile(new ProfileModel("João", "Paulo", "securityQuestion", "securityAnswer", "#123456"));
        goals = new GoalController(data);
        dashboardController = new DashboardController(data);
    }

    @After
    public void tearDown() {
        data.getAuthProfile().getGoals().clear();
    }

    @Test
    public void testGetCurrentySaved() {

        //Close deadline
        GoalModel model1 = new GoalModel("Telemovel", 100, LocalDate.of(2019, Month.DECEMBER, 1));
        //without deadline
        GoalModel model2 = new GoalModel("Trotineta", 200, null);
        GoalModel model3 = new GoalModel("Carro", 100, null);
        // valid date
        GoalModel model4 = new GoalModel("Viagem", 100, LocalDate.of(2020, Month.MARCH, 1));
        GoalModel model5 = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        //goal not accomplished and deadline passed
        GoalModel model6 = new GoalModel("PC", 100, LocalDate.of(2010, Month.MARCH, 1));

        data.getAuthProfile().getGoals().put(model1.getId(), model1);
        data.getAuthProfile().getGoals().put(model2.getId(), model2);
        data.getAuthProfile().getGoals().put(model3.getId(), model3);
        data.getAuthProfile().getGoals().put(model4.getId(), model4);
        data.getAuthProfile().getGoals().put(model5.getId(), model5);
        data.getAuthProfile().getGoals().put(model6.getId(), model6);

        //Goal already achived
        model1.getWallet().setSavedMoney(100);
        //Goal almost achived
        model2.getWallet().setSavedMoney(190);
        model3.getWallet().setSavedMoney(20);
        model5.getWallet().setSavedMoney(10);
        //Goal with negative value
        model4.getWallet().setSavedMoney(-100);
        //goal not accomplished and deadline passed
        model6.getWallet().setSavedMoney(10);

        //Expected Result = 190 + 20 + 10  = 220
        assertEquals(220, dashboardController.getCurrentySaved());

    }

    @Test
    public void testGetGoalsWithDeadline() {

        //without deadline
        GoalModel model2 = new GoalModel("Trotineta", 200, null);
        GoalModel model3 = new GoalModel("Carro", 100, null);
        // valid date
        // goal already acomplished
        GoalModel model1 = new GoalModel("Telemovel", 100, LocalDate.of(2019, Month.DECEMBER, 1));
        //goal not acomplished
        GoalModel model4 = new GoalModel("Viagem", 100, LocalDate.of(2020, Month.MARCH, 2));
        GoalModel model5 = new GoalModel("Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        //goal deadline already pass
        GoalModel model6 = new GoalModel("Bike-2", 100, LocalDate.of(2019, Month.MARCH, 1));

        data.getAuthProfile().getGoals().put(model1.getId(), model1);
        data.getAuthProfile().getGoals().put(model2.getId(), model2);
        data.getAuthProfile().getGoals().put(model3.getId(), model3);
        data.getAuthProfile().getGoals().put(model4.getId(), model4);
        data.getAuthProfile().getGoals().put(model5.getId(), model5);
        data.getAuthProfile().getGoals().put(model6.getId(), model6);

        //Goal already achived
        model1.getWallet().setSavedMoney(200);
        //Goal almost achived
        model2.getWallet().setSavedMoney(190);
        model3.getWallet().setSavedMoney(20);
        model5.getWallet().setSavedMoney(10);
        model4.getWallet().setSavedMoney(90);

        Map<String, String> dataExpected = new HashMap<>();

        dataExpected.put("Bike", "01/03/2020");
        dataExpected.put("Viagem", "02/03/2020");
        dataExpected.put("Bike-2", "01/03/2019");
        assertEquals(dataExpected, dashboardController.getGoalsWithDeadline());
        //Without any goal with deadline 

        goals.removeGoal(model5.getId());
        goals.removeGoal(model4.getId());
        goals.removeGoal(model6.getId());
        assertEquals(null, dashboardController.getGoalsWithDeadline());
    }

    @Test
    public void testGetListOfUncomplichedGoals() {
        // goal already acomplished
        GoalModel model1 = new GoalModel("Telemovel", 100, LocalDate.of(2019, Month.DECEMBER, 1));
        //Goals not acomplished
        GoalModel model2 = new GoalModel("Trotineta", 200, null);
        GoalModel model3 = new GoalModel("Trotineta-2", 200, null);
        GoalModel model4 = new GoalModel("Carro", 100, LocalDate.now());
        GoalModel model5 = new GoalModel("Carro-2", 200, null);
        GoalModel model6 = new GoalModel("Carro-3", 200, null);

        data.getAuthProfile().getGoals().put(model1.getId(),model1);
        data.getAuthProfile().getGoals().put(model2.getId(),model2);
        data.getAuthProfile().getGoals().put(model3.getId(),model3);
        data.getAuthProfile().getGoals().put(model4.getId(),model4);
        data.getAuthProfile().getGoals().put(model5.getId(),model5);
        data.getAuthProfile().getGoals().put(model6.getId(),model6);

        model1.getWallet().setSavedMoney(200);
        //Goal almost achived
        model2.getWallet().setSavedMoney(190); //1
        model3.getWallet().setSavedMoney(5); //5
        model4.getWallet().setSavedMoney(30); //3
        model5.getWallet().setSavedMoney(15); //4
        model6.getWallet().setSavedMoney(90); //2

        //More that 4 Goals
        Map<String, Integer> test1 = new TreeMap<>(Collections.reverseOrder());
        test1.put("95% - Trotineta", 95);
        test1.put("45% - Carro-3", 45);
        test1.put("7% - Carro-2", 7);
        test1.put("30% - Carro", 30);

        assertEquals(test1, dashboardController.getListOfUncomplichedGoals());

        //Left only 3 goals
        goals.removeGoal(model2.getId());
        goals.removeGoal(model4.getId());

        //More that 4 Goals
        Map<String, Integer> test2 = new TreeMap<>(Collections.reverseOrder());
        test2.put("45% - Carro-3", 45);
        test2.put("7% - Carro-2", 7);
        test2.put("2% - Trotineta-2", 2);

        assertEquals(test2, dashboardController.getListOfUncomplichedGoals());

        //Remove all goals
        goals.removeGoal(model1.getId());
        goals.removeGoal(model3.getId());
        goals.removeGoal(model5.getId());
        goals.removeGoal(model6.getId());

        assertEquals(null, dashboardController.getListOfUncomplichedGoals());
    }

    @Test
    public void testCalculateGoalsStatistics() {
        GoalModel model1 = new GoalModel("Trotineta", 200, null);
        GoalModel model2 = new GoalModel("Trotineta-2", 200, null);
        GoalModel model3 = new GoalModel("Carro-2", 200, null);
        GoalModel model4 = new GoalModel("Carro-3", 200, null);

        data.getAuthProfile().getGoals().put(model1.getId(),model1);
        data.getAuthProfile().getGoals().put(model2.getId(),model2);
        data.getAuthProfile().getGoals().put(model3.getId(),model3);
        data.getAuthProfile().getGoals().put(model4.getId(),model4);

        model1.getWallet().setSavedMoney(200); //Achived
        model2.getWallet().setSavedMoney(190); //Not Achived
        model3.getWallet().setSavedMoney(5); //5
        model4.getWallet().setSavedMoney(30); //3

        Map<Integer, String> filtersNotSelected = new HashMap<>();

        filtersNotSelected.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
        filtersNotSelected.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
        filtersNotSelected.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
        filtersNotSelected.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0€");
        filtersNotSelected.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0€");

        //No filter select
        assertEquals(filtersNotSelected,
                dashboardController.CalculateGoalsStatistics("Year", "Month"));

        //Filter year 
        //Fiilter with previous(2018) year
        Map<Integer, String> filtersPreviousYear = new HashMap<>();

        filtersPreviousYear.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
        filtersPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
        filtersPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
        filtersPreviousYear.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0€");
        filtersPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0€");
        assertEquals(filtersPreviousYear,
                dashboardController.CalculateGoalsStatistics("2018", "Month"));
        //Fiilter with actual year
        Map<Integer, String> filtersActualYear = new HashMap<>();

        filtersActualYear.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "1");
        filtersActualYear.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "3");
        filtersActualYear.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "4");
        filtersActualYear.put(DASHBOARD_STATISTICS_SAVED_MONEY, "425€");
        filtersActualYear.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "800€");
        assertEquals(filtersActualYear,
                dashboardController.CalculateGoalsStatistics("2019", "Month"));

        //Filter Month
        //Fiilter with previous month
        Map<Integer, String> filtersPreviousMonth = new HashMap<>();

        filtersPreviousMonth.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
        filtersPreviousMonth.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
        filtersPreviousMonth.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
        filtersPreviousMonth.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0€");
        filtersPreviousMonth.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0€");
        assertEquals(filtersPreviousMonth,
                dashboardController.CalculateGoalsStatistics("Year", "March"));
        //Fiilter with actual month
        Map<Integer, String> filtersActualMonth = new HashMap<>();

        filtersActualMonth.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "1");
        filtersActualMonth.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "3");
        filtersActualMonth.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "4");
        filtersActualMonth.put(DASHBOARD_STATISTICS_SAVED_MONEY, "425€");
        filtersActualMonth.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "800€");
        assertEquals(filtersActualMonth,
                dashboardController.CalculateGoalsStatistics("Year", "December"));

        //Filter Year and Month
        //Fiilter with previous month And actual year
        Map<Integer, String> filtersPreviousMonthActualYear = new HashMap<>();

        filtersPreviousMonthActualYear.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
        filtersPreviousMonthActualYear.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
        filtersPreviousMonthActualYear.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
        filtersPreviousMonthActualYear.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0€");
        filtersPreviousMonthActualYear.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0€");
        assertEquals(filtersPreviousMonthActualYear,
                dashboardController.CalculateGoalsStatistics("2019", "March"));

        //Fiilter with actual month  and actual year
        Map<Integer, String> filtersActualMonthActualYear = new HashMap<>();

        filtersActualMonthActualYear.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "1");
        filtersActualMonthActualYear.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "3");
        filtersActualMonthActualYear.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "4");
        filtersActualMonthActualYear.put(DASHBOARD_STATISTICS_SAVED_MONEY, "425€");
        filtersActualMonthActualYear.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "800€");
        assertEquals(filtersActualMonthActualYear,
                dashboardController.CalculateGoalsStatistics("2019", "December"));

        //Fiilter with previous month And previous year
        Map<Integer, String> filtersPreviousMonthPreviousYear = new HashMap<>();

        filtersPreviousMonthPreviousYear.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
        filtersPreviousMonthPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
        filtersPreviousMonthPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
        filtersPreviousMonthPreviousYear.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0€");
        filtersPreviousMonthPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0€");
        assertEquals(filtersPreviousMonthPreviousYear,
                dashboardController.CalculateGoalsStatistics("2018", "March"));

        //Fiilter with actual month  and previous year
        Map<Integer, String> filtersActualMonthPreviousYear = new HashMap<>();

        filtersActualMonthPreviousYear.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "0");
        filtersActualMonthPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "0");
        filtersActualMonthPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "0");
        filtersActualMonthPreviousYear.put(DASHBOARD_STATISTICS_SAVED_MONEY, "0€");
        filtersActualMonthPreviousYear.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "0€");

        assertEquals(filtersActualMonthPreviousYear,
                dashboardController.CalculateGoalsStatistics("2018", "December"));

    }

}
