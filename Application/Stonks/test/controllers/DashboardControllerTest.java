/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import exceptions.AuthenticationException;
import exceptions.GoalNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        data.setAuthProfile(new ProfileModel("João", "Paulo", "securityQuestion", "securityAnswer", "#123456"));
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
        GoalModel model1 = new GoalModel(1, "Telemovel", 100, LocalDate.of(2019, Month.DECEMBER, 1));
        //without deadline
        GoalModel model2 = new GoalModel(2, "Trotineta", 200, null);
        GoalModel model3 = new GoalModel(3, "Carro", 100, null);
        // valid date
        GoalModel model4 = new GoalModel(4, "Viagem", 100, LocalDate.of(2020, Month.MARCH, 1));
        GoalModel model5 = new GoalModel(5, "Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        //goal not accomplished and deadline passed
        GoalModel model6 = new GoalModel(6, "PC", 100, LocalDate.of(2010, Month.MARCH, 1));

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
        assertEquals(220, dashboardController.getCurrentlySaved());

    }

    @Test
    public void testGetGoalsWithDeadline() throws AuthenticationException, GoalNotFoundException {

        //without deadline
        GoalModel model2 = new GoalModel(1, "Trotineta", 200, null);
        GoalModel model3 = new GoalModel(2, "Carro", 100, null);
        // valid date
        // goal already acomplished
        GoalModel model1 = new GoalModel(3, "Telemovel", 100, LocalDate.of(2019, Month.DECEMBER, 1));
        //goal not acomplished
        GoalModel model4 = new GoalModel(4, "Viagem", 100, LocalDate.of(2020, Month.MARCH, 2));
        GoalModel model5 = new GoalModel(5, "Bike", 100, LocalDate.of(2020, Month.MARCH, 1));
        //goal deadline already pass
        GoalModel model6 = new GoalModel(6, "Bike-2", 100, LocalDate.of(2019, Month.MARCH, 1));

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

        List<String> dataExpected = new ArrayList<>();

        dataExpected.add("02/03/2020 - Viagem");
        dataExpected.add("01/03/2020 - Bike");
        dataExpected.add("01/03/2019 - Bike-2");
        assertEquals(dataExpected, dashboardController.getGoalsWithDeadline());
        //Without any goal with deadline 

        goals.removeGoal(model5.getId());
        goals.removeGoal(model4.getId());
        goals.removeGoal(model6.getId());
        dataExpected.remove(2);
        dataExpected.remove(1);
        dataExpected.remove(0);

        assertEquals(dataExpected.isEmpty(), dashboardController.getGoalsWithDeadline().isEmpty());
    }

    @Test
    public void testGetListOfUncomplichedGoals() throws AuthenticationException, GoalNotFoundException {
        // goal already acomplished
        GoalModel model1 = new GoalModel(1, "Telemovel", 100, LocalDate.of(2019, Month.DECEMBER, 1));
        //Goals not acomplished
        GoalModel model2 = new GoalModel(2, "Trotineta", 200, null);
        GoalModel model3 = new GoalModel(3, "Trotineta-2", 200, null);
        GoalModel model4 = new GoalModel(4, "Carro", 100, LocalDate.now());
        GoalModel model5 = new GoalModel(5, "Carro-2", 200, null);
        GoalModel model6 = new GoalModel(6, "Carro-3", 200, null);
        GoalModel model7 = new GoalModel(7, "PC", 2000, null);

        data.getAuthProfile().getGoals().put(model1.getId(), model1);
        data.getAuthProfile().getGoals().put(model2.getId(), model2);
        data.getAuthProfile().getGoals().put(model3.getId(), model3);
        data.getAuthProfile().getGoals().put(model4.getId(), model4);
        data.getAuthProfile().getGoals().put(model5.getId(), model5);
        data.getAuthProfile().getGoals().put(model6.getId(), model6);
        data.getAuthProfile().getGoals().put(model7.getId(), model7);

        model1.getWallet().setSavedMoney(200);
        //Goal almost achived
        model2.getWallet().setSavedMoney(190); //1
        model3.getWallet().setSavedMoney(5); //5
        model4.getWallet().setSavedMoney(30); //3
        model5.getWallet().setSavedMoney(15); //4
        model6.getWallet().setSavedMoney(90); //2
        model7.getWallet().setSavedMoney(900); //2

        //More that 4 Goals
        Map<String, Integer> test1 = new TreeMap<>();
        test1.put("900.0€ - PC", 900);
        test1.put("190.0€ - Trotineta", 190);
        test1.put("90.0€ - Carro-3", 90);
        test1.put("30.0€ - Carro", 30);
        test1.put("15.0€ - Carro-2", 15);

        assertEquals(test1, dashboardController.getListOfUncomplichedGoals());

        //Left only 3 goals
        goals.removeGoal(model2.getId());
        goals.removeGoal(model4.getId());

        //More that 4 Goals
        Map<String, Integer> test2 = new TreeMap<>();
        test2.put("900.0€ - PC", 900);
        test2.put("90.0€ - Carro-3", 90);
        test2.put("15.0€ - Carro-2", 15);
        test2.put("5.0€ - Trotineta-2", 5);

        assertEquals(test2, dashboardController.getListOfUncomplichedGoals());

        //Remove all goals
        goals.removeGoal(model1.getId());
        goals.removeGoal(model3.getId());
        goals.removeGoal(model5.getId());
        goals.removeGoal(model6.getId());
        goals.removeGoal(model7.getId());
        Map<String, Integer> test3 = new TreeMap<>();

        assertEquals(test3.isEmpty(), dashboardController.getListOfUncomplichedGoals().isEmpty());
    }

    @Test
    public void testCalculateGoalsStatistics() throws AuthenticationException, GoalNotFoundException {
        GoalModel model1 = new GoalModel(1, "Trotineta", 200, null);
        GoalModel model2 = new GoalModel(2, "Trotineta-2", 200, null);
        GoalModel model3 = new GoalModel(3, "Carro-2", 200, null);
        GoalModel model4 = new GoalModel(4, "Carro-3", 200, null);

        data.getAuthProfile().getGoals().put(model1.getId(), model1);
        data.getAuthProfile().getGoals().put(model2.getId(), model2);
        data.getAuthProfile().getGoals().put(model3.getId(), model3);
        data.getAuthProfile().getGoals().put(model4.getId(), model4);

        model1.getWallet().setSavedMoney(200); //Achived
        model2.getWallet().setSavedMoney(190); //Not Achived
        model3.getWallet().setSavedMoney(5); //5
        model4.getWallet().setSavedMoney(30); //3

        Map<Integer, String> filtersNotSelected = new HashMap<>();

        filtersNotSelected.put(DASHBOARD_STATISTICS_GOALS_COMPLETE, "1");
        filtersNotSelected.put(DASHBOARD_STATISTICS_TOTAL_INCOMPLETE, "3");
        filtersNotSelected.put(DASHBOARD_STATISTICS_TOTAL_GOALS, "4");
        filtersNotSelected.put(DASHBOARD_STATISTICS_SAVED_MONEY, "425€");
        filtersNotSelected.put(DASHBOARD_STATISTICS_TOTAL_OBJECTIVE, "800€");

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
