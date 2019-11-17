package models;

import java.util.Date;
import stonks.StonksData;

public class GoalModel {
    private static int idCounter = 0;
    private static StonksData data;
    
    private final int id;
    private final Date creationDate;
    private Date achievementDate;
    private String name;
    private int objective;
    private Date deadlineDate;
    
    private final WalletModel wallet;

    public GoalModel(String name, int objective) {
        this.name = name;
        this.objective = objective;
        
        id = idCounter++;
        creationDate = new Date();
        
        wallet = new WalletModel();
    }
    
    public static void setData(StonksData data){
        GoalModel.data = data;
    }
    
    public int getId(){
        return id;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public Date getAchievementDate() {
        return achievementDate;
    }

    public void setAchievementDate(Date achievementDate) {
        this.achievementDate = achievementDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjective() {
        return objective;
    }

    public void setObjective(int objective) {
        this.objective = objective;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
    
    public WalletModel getWallet(){
        return wallet;
    }
}
