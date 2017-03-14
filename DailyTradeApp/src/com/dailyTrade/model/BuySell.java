package com.dailyTrade.model;

import java.util.Date;

/**
 *
 * @author venkatakalluri
 * 
 * This is a model class for trading Application
 */
public class BuySell {
   private String entity;
   private String buyOrSell;
   private float agreedFx;
   private String currency;
   private Date instructionDate;
   private Date settlementDate;
   private int units;
   private float ppu;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(String buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public float getAgreedFx() {
        return agreedFx;
    }

    public void setAgreedFx(float agreedFx) {
        this.agreedFx = agreedFx;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public float getPpu() {
        return ppu;
    }

    public void setPpu(float ppu) {
        this.ppu = ppu;
    }  
    
    
}
