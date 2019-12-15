package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        return (Double.valueOf(wallet.getSavedMoney()) / Double.valueOf(objective));
    }
  
    public boolean isCompleted() {
        if (this.objective == this.wallet.getSavedMoney()) {
            return true;
        }

        return false;
    }
    
    public static void orderListByProgress(List<GoalModel> list){
        for(int i = 0; i < list.size(); i++){
            for(int j = i+1; j < list.size(); j++){
                if(list.get(i).getProgress() < list.get(j).getProgress()){
                    Collections.swap(list, i, j);
                }
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.objective;
        hash = 23 * hash + Objects.hashCode(this.deadlineDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GoalModel other = (GoalModel) obj;
        if (this.objective != other.objective) {
            return false;
        }
        if (!this.name.equals( other.name)) {
            return false;
        }
        if (!this.creationDate.equals(other.creationDate)) {
            System.out.println("erro");
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "GoalModel{" + "id=" + id + ", creationDate=" + creationDate + ", achievementDate=" + achievementDate + ", name=" + name + ", objective=" + objective + ", deadlineDate=" + deadlineDate + ", wallet=" + wallet + '}';
    }
    
    
}
