package models;

import java.util.Date;
import stonks.StonksData;

public class WalletModel {
    private static int idCounter = 0;
    private static StonksData data;
    
    private final int id;
    private int savedMoney;
    private Date firstDepositDate;
    private Date lastDepositDate;

    public WalletModel() {
        id = idCounter++;
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

    public void setSavedMoney(int savedMoney) {
        this.savedMoney = savedMoney;
    }

    public Date getFirstDepositDate() {
        return firstDepositDate;
    }

    public void setFirstDepositDate(Date firstDepositDate) {
        this.firstDepositDate = firstDepositDate;
    }

    public Date getLastDepositDate() {
        return lastDepositDate;
    }

    public void setLastDepositDate(Date lastDepositDate) {
        this.lastDepositDate = lastDepositDate;
    }
    
}
