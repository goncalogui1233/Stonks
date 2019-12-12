package models;

import exceptions.EmptyDepositException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.savedMoney;
        hash = 47 * hash + Objects.hashCode(this.firstDepositDate);
        hash = 47 * hash + Objects.hashCode(this.lastDepositDate);
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
        final WalletModel other = (WalletModel) obj;
        return true;
    }

}
