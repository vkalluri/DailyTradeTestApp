package com.dailyTrade;

import com.dailyTrade.model.BuySell;
import com.dailyTrade.tradingException.TradingException;
import com.dailyTrade.util.Calculation;
import com.dailyTrade.util.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;




/**
 *
 * @author venkatakalluri
 */
public class DailyTradeApp {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws TradingException, IOException, ParseException {
        DailyTradeApp buySellJavaApplication = new DailyTradeApp();
        
        ArrayList<BuySell> arrayList = new ArrayList<>();
        buySellJavaApplication.readDataFromFile(arrayList);        
        buySellJavaApplication.calcIncoming(arrayList);
        buySellJavaApplication.calcOutgoings(arrayList);
    }
    
    /**
     *  This file is used to read the data from the input file
     * @param aList
     * @return
     * @throws IOException
     * @throws ParseException 
     */
    private ArrayList readDataFromFile(ArrayList aList) throws IOException, ParseException {
        BufferedReader in = new BufferedReader(new FileReader("C:\\Projects\\NetBeansProjects\\DailyTradeApp\\src\\com\\dailyTrade\\testData\\buysellsampledata.txt"));
        String str;  
        
        while((str = in.readLine()) != null){
            String[] array = str.split(Constants.STRSPLIT);
            aList.add(createObject(array));
        }        
        return aList;        
    }   
    
    private void calcIncoming(ArrayList aList) {
        TreeMap treeMap = new TreeMap();
        HashMap hashMap = new HashMap();
        
        for (int i=0; i<aList.size(); i++) {
            BuySell buySell = (BuySell) aList.get(i);            
            if (buySell.getBuyOrSell().equalsIgnoreCase(Constants.SELL)) {                
                processIncomingData(treeMap, buySell);
                processOutgoingData(hashMap, buySell);
            } 
        }        
        printSummaryDetails(treeMap, hashMap, "SETTLED INCOMINGS DATA", "RANKING OF INCOMING DATA DETAILS");
    }
    
    private void calcOutgoings(ArrayList aList) {
        TreeMap treeMap = new TreeMap();
        HashMap hashMap = new HashMap();
        
        for (int i=0; i<aList.size(); i++) {
            BuySell buySell = (BuySell) aList.get(i);            
            if (buySell.getBuyOrSell().equalsIgnoreCase("B")) {                
                processIncomingData(treeMap, buySell);
                processOutgoingData(hashMap, buySell);
            } 
        }        
        printSummaryDetails(treeMap, hashMap, "SETTLED OUTGOING DATA", "RANKING OF OUTGOING DATA DETAILS");
    }
    
    private BuySell createObject(String[] strArray) throws ParseException {
        String expectedDatePattern = Constants.DATEPATTERN;
        DateFormat format = new SimpleDateFormat(expectedDatePattern);
        
        String entity = strArray[0];
        String buyOrSell = strArray[1];
        float agreedFx = Float.parseFloat(strArray[2]);
        String currency = strArray[3];
        Date instructionDate = format.parse(strArray[4]);
        Date settlementDate = format.parse(strArray[5]);
        int units = Integer.parseInt(strArray[6]);
        float ppu = Float.parseFloat(strArray[7]);   

       BuySell buySell = new BuySell();
       buySell.setEntity(entity);
       buySell.setBuyOrSell(buyOrSell);
       buySell.setAgreedFx(agreedFx);
       buySell.setCurrency(currency);
       buySell.setInstructionDate(instructionDate);
       buySell.setSettlementDate(settlementDate);
       buySell.setUnits(units);
       buySell.setPpu(ppu);
        
        return buySell;
    }
    
    private TreeMap processIncomingData(TreeMap treeMap, BuySell buySell) {
       Calculation cal  = new Calculation();
        if (!treeMap.containsKey(buySell.getSettlementDate())) {
            treeMap.put(cal.calculateSettlementDate(buySell.getSettlementDate(), buySell.getCurrency()), 
                    cal.getUSD(buySell.getUnits(),buySell.getPpu(), buySell.getAgreedFx()));
        } else {
            float value = (float) treeMap.get(buySell.getSettlementDate());
            treeMap.put(cal.calculateSettlementDate(buySell.getSettlementDate(), buySell.getCurrency()), 
                    value + cal.getUSD(buySell.getUnits(),buySell.getPpu(), buySell.getAgreedFx()));
        }
        return treeMap;
    }
    
    private HashMap processOutgoingData(HashMap hashMap, BuySell buySell) {
        Calculation cal  = new Calculation();
        if (!hashMap.containsKey(buySell.getEntity())) {
            hashMap.put(cal.calculateSettlementDate(buySell.getSettlementDate(), buySell.getCurrency()), 
                    cal.getUSD(buySell.getUnits(),buySell.getPpu(), buySell.getAgreedFx()));
        } else {           
            hashMap.put(cal.calculateSettlementDate(buySell.getSettlementDate(), buySell.getCurrency()), 
                    cal.getUSD(buySell.getUnits(),buySell.getPpu(), buySell.getAgreedFx()));
        }
        return hashMap;
    }
    
    private void printSummaryDetails(TreeMap treeMap, HashMap hashMap, String summaryTest, String entityText) {
        System.out.println(summaryTest);
        System.out.println("------------------");
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + Constants.STRSEPERATOR + pairs.getValue() + Constants.USDOLLARS);
        }
        
        System.out.println("");
        System.out.println(entityText);
        System.out.println("----------------------------");
        HashMap sortedHashMap = new LinkedHashMap();
        sortedHashMap = sortMapDataByValues(hashMap);        
        
        Iterator itEntity = sortedHashMap.entrySet().iterator();
        while (itEntity.hasNext()) {
            Map.Entry pairs = (Map.Entry)itEntity.next();
            System.out.println(pairs.getKey() + Constants.STRSEPERATOR + pairs.getValue() + Constants.USDOLLARS);
        }
        System.out.println("");
    }
    
    private HashMap sortMapDataByValues(HashMap map) { 
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, (Object o1, Object o2) -> ((Comparable) ((Map.Entry) (o2)).getValue())
                .compareTo(((Map.Entry) (o1)).getValue()));

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), entry.getValue());
        } 
        return linkedHashMap;
     }
    
}
