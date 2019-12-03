package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import stonks.StonksData;

public class WalletModel implements Serializable{
    private static int idCounter = 0;
    private static StonksData data;
    
    private final int id;
    private int savedMoney;
    private LocalDate firstDepositDate;
    private LocalDate lastDepositDate;

    public WalletModel() {
        id = idCounter++;
        savedMoney = 0;
        firstDepositDate = null;
        lastDepositDate = null;
    }

    public static void setData(StonksData data){
        WalletModel.data = data;
    }
    
    public int getId() {
        return id;
    }

    public int getSavedMoney() {
        return savedMoney;
    }

    public boolean setSavedMoney(int savedMoney) {
        if(firstDepositDate == null)
            firstDepositDate = LocalDate.now();
        else{ 
            lastDepositDate = firstDepositDate;
            firstDepositDate = LocalDate.now();
        }
        System.out.println("l: " + this.savedMoney);
        this.savedMoney += savedMoney;
        System.out.println("A: " + this.savedMoney);
        
        return true;
    }

    public LocalDate getFirstDepositDate() {
        return firstDepositDate;
    }

    public void setFirstDepositDate(LocalDate firstDepositDate) {
        this.firstDepositDate = firstDepositDate;
    }

    public LocalDate getLastDepositDate() {
        return lastDepositDate;
    }

    public void setLastDepositDate(LocalDate lastDepositDate) {
        this.lastDepositDate = lastDepositDate;
    }
    
}
