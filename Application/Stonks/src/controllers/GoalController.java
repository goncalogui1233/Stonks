package controllers;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import stonks.StonksData;
import models.GoalModel;
import models.ProfileModel;

public class GoalController {
    private final StonksData data;
    
    public GoalController(StonksData data) {
        this.data = data;
    }
    
    //Gonçalo Guilherme
    public int createGoal(String name, int objective, LocalDate deadline){
        boolean verify = verifyInsertData(name, objective);
        
        if(verify){
            data.getCurrentProfile().getGoals().add(new GoalModel(name, objective, deadline));
            return 1;//goal created
        }
        else
            return 0; //goal not created
        
    }
    
    public int editGoal(int id, String name, int objective, LocalDate deadline){
        boolean verify = verifyInsertData(name, objective);
        
        if(verify){
            get().getGoals().get(id).setName(name);
            get().getGoals().get(id).setObjective(objective);
            get().getGoals().get(id).setDeadlineDate(deadline);
            return 1;
        }
        
        return 0;
    } 
    
    public int removeGoal(int id){
        int pos;
        if((pos = existeGoal(id)) > -1){
            get().getGoals().remove(pos);
            return 1; //removed succesfully
        }
        
        return 0; //goal not found
    }
    
     
    private boolean verifyInsertData(String name, int objective){ //verify the data in goal name and objective
        if(name.isEmpty() || objective < 0)
            return false; //one of them is empty
        
        if(name.length() > 50){
            return false; //length of name is longer than expected
        }
        
        if(objective > 999999999)
            return false; //number is too big
        
        return true;
    }
    
    private ProfileModel get(){     //function to not keep calling everytime data.getCurrentProfile();
        return data.getCurrentProfile();
    }
    
    private int existeGoal(int id){ //Search for a goal with the id passed as argument
        for(int i = 0; i < data.getCurrentProfile().getGoals().size(); i++)
            if(get().getGoals().get(i).getId() == id)
                return i; //return position of the goal in the array
        
        return -1;
    }
    
    public String showNameGoal(int id){ //receive goal ID
        int pos;
        if((pos = existeGoal(id)) > -1 )
            return get().getGoals().get(pos).getName();
        
        return "";
    }
    
    public int progressBarCompleted(int id){
        int pos;
        if((pos = existeGoal(id)) > -1 ){ //if goal finded
            int objective = get().getGoals().get(pos).getObjective();
            int actual = get().getGoals().get(pos).getWallet().getSavedMoney();
            
            return (actual / objective) / 100; //returns percentage value to PBar
        }
        
        return -1; //if not exists, return -1;
    }
    
    public int showActualMoney(int id){
        int pos;
        if((pos = existeGoal(id)) > -1 )
            return get().getGoals().get(pos).getWallet().getSavedMoney(); //returns saved money
        
        return -1; //if not exist goal, return -1
    }
    
    public int showObjectiveMoney(int id){
        int pos;
        if((pos = existeGoal(id)) > -1 )
            return get().getGoals().get(pos).getObjective(); //returns objective goal
        
        return -1; //if not exist goal, return -1
    }
    
    public long showDaysGoalDeadline(int id){ //returns, i think, the days between 2 dates
        int pos;
        if((pos = existeGoal(id)) > -1 ){
            LocalDate deadline = get().getGoals().get(pos).getDeadlineDate();
            LocalDate now = LocalDate.now();
            return DAYS.between(deadline, now);
        }
        
        return -1;
    }
    
    public LocalDate getDeadlineDate(int id){ //return the deadline date
        int pos;
        if((pos = existeGoal(id)) > -1 )
            return get().getGoals().get(pos).getDeadlineDate();
        
        return null;
    }
    
    /*public Date showEstimateDateOfFinish(int id){
        int pos;
        if((pos = existeGoal(id)) > -1 ){
            //Saving Rate
            Date lastDeposit = get().getGoals().get(pos).getWallet().getLastDepositDate();
            Date firstDeposit = get().getGoals().get(pos).getWallet().getFirstDepositDate();
            int savedMoney = get().getGoals().get(pos).getWallet().getSavedMoney();
            
            long rate = (lastDeposit.getTime() - firstDeposit.getTime()) / savedMoney;
            
            //Date Estimation
            int moneyNeeded = get().getGoals().get(pos).getObjective() - savedMoney; //diference between objective money and actual money
            long estimate = rate/moneyNeeded; //
            
            
        }
        
        return null;
    }*/

    
    public int manageFundsWallet(int id, int updateValue){ //manages the money saved of a goal 
        int pos;
        LocalDate date = LocalDate.now();
        if((pos = existeGoal(id)) > -1 ){
            get().getGoals().get(pos).getWallet().setSavedMoney(updateValue);
            if(get().getGoals().get(pos).getWallet().getFirstDepositDate() == null){ //if never made a deposit,
                get().getGoals().get(pos).getWallet().setFirstDepositDate(date);     //updates first deposit date
                get().getGoals().get(pos).getWallet().setLastDepositDate(date);      //and last deposit date
            }
            else
                get().getGoals().get(pos).getWallet().setLastDepositDate(date); //updates last deposit date
            
            return 1;
        }
        
        return 0;
    }
    
    
    
}
