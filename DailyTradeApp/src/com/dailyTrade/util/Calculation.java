package com.dailyTrade.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author venkatakalluri
 * This is a Utility class for Trading application where in we calculate details
 */
public class Calculation {
    
    public Date calculateSettlementDate(Date settlementDate, String currency){
      Calendar cal =  Calendar.getInstance();  
      cal.setTime(settlementDate);
      int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
      if(currency.equalsIgnoreCase(Constants.SINGCURRENCY) || currency.equalsIgnoreCase(Constants.AUSDOLLARS)){
          if(dayOfWeek == 5){
              cal.add(Calendar.DATE, 2);
          }else{
              cal.add(Calendar.DATE, 1);
          }
      }else{
          if(dayOfWeek == 6){
              cal.add(Calendar.DATE, 2);
          }else if(dayOfWeek == 0){
              cal.add(Calendar.DATE, 1);
          }
      }
      return cal.getTime();
    }
    
    public float getUSD(int units, float ppu, float agreedFx){
        return (units*ppu) *agreedFx;
    }
    
}
