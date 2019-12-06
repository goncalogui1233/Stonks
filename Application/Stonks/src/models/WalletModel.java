package models;

import exceptions.EmptyDepositException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import stonks.StonksData;

public class WalletModel implements Serializable {

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

    public static void setData(StonksData data) {
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

    public LocalDate getFirstDepositDate() {
        return firstDepositDate;
    }

    public void setFirstDepositDate(LocalDate firstDepositDate) {
        this.firstDepositDate = firstDepositDate;
    }

    public LocalDate getLastDepositDate() throws EmptyDepositException {
        if (lastDepositDate == null) {
            throw new EmptyDepositException();
        }

        return lastDepositDate;
    }

    public void setLastDepositDate(LocalDate lastDepositDate) {
        this.lastDepositDate = lastDepositDate;
    }
    
    public void addMoney(int quant){
        this.savedMoney += quant;
    }
    
    public void removeMoney(int quant){
        this.savedMoney -= quant;
    }

}
