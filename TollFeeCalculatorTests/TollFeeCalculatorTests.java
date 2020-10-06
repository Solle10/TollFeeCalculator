
import kyh.labs.lab4.TollFeeCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TollFeeCalculatorTests {

    @Test
    @DisplayName("Testing Maximal Cost per day")
        //
    void maxcost() {
        LocalDateTime[] date = new LocalDateTime[6];
        date[0] = LocalDateTime.parse("2020-06-30 06:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8
        date[1] = LocalDateTime.parse("2020-06-30 06:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[2] = LocalDateTime.parse("2020-06-30 07:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18
        date[3] = LocalDateTime.parse("2020-06-30 07:18", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18
        date[4] = LocalDateTime.parse("2020-06-30 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[5] = LocalDateTime.parse("2020-06-30 15:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //8


        assertEquals(60, TollFeeCalculator.getTotalFeeCost(date));
    }

    @Test
    @DisplayName("Testing Fee per passing at one time")
    void FeePerPassingDate() {
        LocalDateTime date = LocalDateTime.parse("2020-06-30 09:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(date));
    }

    @Test
    @DisplayName("Test if fee per hour is correct")
    void Feeperhourtest() {
        LocalDateTime[] date = new LocalDateTime[3];


        date[0] = LocalDateTime.parse("2020-06-30 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8
        date[1] = LocalDateTime.parse("2020-06-30 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 0
        date[2] = LocalDateTime.parse("2020-06-30 10:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 0

        assertEquals(8, TollFeeCalculator.getTotalFeeCost(date));
    }

    @Test
    @DisplayName("Test isTollFreeDate")
    void IsTollFreeDate() {
        // Checking if tollfree date in weekdays and july
        LocalDateTime date = LocalDateTime.parse("2020-06-30 00:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(TollFeeCalculator.isTollFreeDate(date));

        LocalDateTime dateWeekDay = LocalDateTime.parse("2020-06-23 10:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(TollFeeCalculator.isTollFreeDate(dateWeekDay));

        LocalDateTime tollFreeJuly = LocalDateTime.parse("2020-07-01 10:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(TollFeeCalculator.isTollFreeDate(tollFreeJuly));
        
                                                                       
@Test                                                          
    @DisplayName("TestExceptions")                         
    void TestExceptions() {                                    
    try {                                                      
        try {                                                  
            new TollFeeCalculator("Data/Wrongdates.txt");      
        } catch (NoSuchElementException e) {                   
            System.err.println(e);                             
            assertNull(e);                                     
                     }                                         
                     new TollFeeCalculator("Data/Emptyfile.txt"
                }catch(DateTimeParseException e) {             
                   System.err.println(e);                      
                             assertNull(e);      }  }}         
    



