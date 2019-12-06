package models;

import java.io.Serializable;
import java.time.LocalDate;

public class GoalModel implements Serializable {

    private int id;
    private final LocalDate creationDate;
    private LocalDate achievementDate;
    private String name;
    private int objective;
    private LocalDate deadlineDate;

    private final WalletModel wallet;

    public GoalModel(int id, String name, int objective, LocalDate deadline) {
        this.id = id;
        this.name = name;
        this.objective = objective;
        if (deadline != null) {
            this.deadlineDate = deadline;
        }

        creationDate = LocalDate.now();

        wallet = new WalletModel();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
  
    public double getProgress() {
        /*Returns the value from a range of 0 to 1*/
        return (wallet.getSavedMoney() / objective);
    }
  
    public boolean isCompleted() {
        if (this.objective == this.wallet.getSavedMoney()) {
            return true;
        }

        return false;
    }
}
