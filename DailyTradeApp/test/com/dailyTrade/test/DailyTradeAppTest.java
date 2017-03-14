package com.dailyTrade.test;

import com.dailyTrade.model.BuySell;
import com.dailyTrade.util.Calculation;
import com.dailyTrade.util.Constants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author venkatakalluri
 */
public class DailyTradeAppTest {
    BuySell buySell1;
    BuySell buySell2;
    Calculation cal1;
    Calculation cal2;
    
    String expectedDatePattern = Constants.DATEPATTERN;
    DateFormat format = new SimpleDateFormat(expectedDatePattern);          
    
    public DailyTradeAppTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception{    
        cal1 = new Calculation();
        cal2 = new Calculation();
        
        buySell1 = new BuySell();
        buySell1.setAgreedFx(0.50f);
        buySell1.setBuyOrSell(Constants.BUY);
        buySell1.setCurrency(Constants.SINGCURRENCY);
        buySell1.setEntity("foo");
        buySell1.setInstructionDate(format.parse("01 Jan 2016"));
        buySell1.setSettlementDate(format.parse("02 Jan 2016"));
        buySell1.setUnits(200);
        buySell1.setPpu(100.25f);
        
        buySell2 = new BuySell();
        buySell2.setAgreedFx(0.22f);
        buySell2.setBuyOrSell(Constants.SELL);
        buySell2.setCurrency(Constants.AUSDOLLARS);
        buySell2.setEntity("bar");
        buySell2.setInstructionDate(format.parse("05 Jan 2016"));
        buySell2.setSettlementDate(format.parse("07 Jan 2016"));
        buySell2.setUnits(450);
        buySell2.setPpu(150.5f);
             
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCalculateUSD() {
        assertEquals(10025.0, cal1.getUSD(buySell1.getUnits(), buySell1.getPpu(), buySell1.getAgreedFx()), 0);
        assertEquals(14899.5, cal2.getUSD(buySell2.getUnits(), buySell2.getPpu(), buySell2.getAgreedFx()), 0);
    }
    
    @Test
    public void testSettlementDate() throws ParseException {
        assertEquals(format.parse("03 Jan 2016"), cal1.calculateSettlementDate(buySell1.getSettlementDate(), buySell1.getCurrency()));
    }
}
