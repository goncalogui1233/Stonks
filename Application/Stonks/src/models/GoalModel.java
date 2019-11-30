package models;

import java.time.LocalDate;
import java.util.Date;
import stonks.StonksData;

public class GoalModel {

    private static int idCounter = 0;
    private static StonksData data;

    private final int id;
    private final LocalDate creationDate;
    private LocalDate achievementDate;
    private String name;
    private int objective;
    private LocalDate deadlineDate;

    private final WalletModel wallet;

    public GoalModel(String name, int objective, LocalDate deadline) {
        this.name = name;
        this.objective = objective;
        if (deadline != null) {
            this.deadlineDate = deadline;
        }

        id = idCounter++;
        creationDate = LocalDate.now();

        wallet = new WalletModel();
    }

    public static void setData(StonksData data) {
        GoalModel.data = data;
    }

    public int getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getAchievementDate() {
        return achievementDate;
    }

    public void setAchievementDate(LocalDate achievementDate) {
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

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public WalletModel getWallet() {
        return wallet;
    }

    public boolean hasDeadline() {
        if (this.deadlineDate != null && !deadlineDate.toString().isEmpty()) {
            return true;
        }

        return false;
    }
    
    public int getGoalProgress()
    {
        if (getObjective() <= 0)
        {
            return 0;
        }
        //To remove Trunc of integer
       return (int) ((getWallet().getSavedMoney() * 1.0 / getObjective())*100); 
    }
    
}
