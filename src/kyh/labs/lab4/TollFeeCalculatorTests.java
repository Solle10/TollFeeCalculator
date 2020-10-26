package kyh.labs.lab4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TollFeeCalculatorTests {


    @Test
    void getMaximalCost() {
        LocalDateTime[] date = new LocalDateTime[6];
        date[0] = LocalDateTime.parse("2020-06-30 06:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8
        date[1] = LocalDateTime.parse("2020-06-30 07:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18
        date[2] = LocalDateTime.parse("2020-06-30 08:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[3] = LocalDateTime.parse("2020-06-30 09:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8
        date[4] = LocalDateTime.parse("2020-06-30 15:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[5] = LocalDateTime.parse("2020-06-30 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18 totalFee = 78

        assertEquals(60, TollFeeCalculator.getTotalFeeCost(date));
    }


    @Test
    @DisplayName("Test Fee per passing at one time")
    void FeePerPassingDate() {
        LocalDateTime date = LocalDateTime.parse("2020-06-30 09:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        assertEquals(8, TollFeeCalculator.getTollFeePerPassing(date));
    }

    @Test
    @DisplayName("Test if fee per hour is correct")
    void Feeperhourtest() {
        LocalDateTime[] date = new LocalDateTime[3];

        date[0] = LocalDateTime.parse("2020-06-30 17:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[1] = LocalDateTime.parse("2020-06-30 17:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        date[2] = LocalDateTime.parse("2020-06-30 17:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13 = Actual 39

        assertEquals(13, TollFeeCalculator.getTotalFeeCost(date));   //Fees returned not correct within one hour
    }

    @Test
    @DisplayName("Test isTollFreeDate")
    void IsTollFreeDate() {
        // Checking if tollfree date in weekdays a
        LocalDateTime date = LocalDateTime.parse("2020-06-30 00:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(TollFeeCalculator.isTollFreeDate(date));

        LocalDateTime dateWeekDay = LocalDateTime.parse("2020-06-23 10:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(TollFeeCalculator.isTollFreeDate(dateWeekDay));

    }

    @Test
    @DisplayName("TollFreeJuly")
        //July free
    void istollfreeJuly() {
        LocalDateTime tollFreeJuly = LocalDateTime.parse("2020-07-01 10:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(TollFeeCalculator.isTollFreeDate(tollFreeJuly));
    }

    @Test
    @DisplayName("Test if error prints if file is wrong")
    void readSystemError() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        TollFeeCalculator TollFeeCalculator = new TollFeeCalculator("Data/Lab5.txt");


        assertEquals("File not found Data/Lab5.txt", errContent.toString().trim());

    }


    @Test
    @DisplayName("Test two passing")
    void TestFirstPassing() {
        LocalDateTime[] date = new LocalDateTime[2];
        date[0] = LocalDateTime.parse("2020-06-01 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-06-01 10:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        assertEquals(8, TollFeeCalculator.getTotalFeeCost(date));

    }

    @Test
    @DisplayName("Testing if Date is tollFree or not")
    void isTollFreeDate2() {
        LocalDateTime date = LocalDateTime.parse("2020-06-01 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(TollFeeCalculator.isTollFreeDate(date));
    }


    @Test
    void checkTotalCostUnderSixty() {
        LocalDateTime[] date = new LocalDateTime[8];

        date[0] = LocalDateTime.parse("2020-06-30 10:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[1] = LocalDateTime.parse("2020-06-30 10:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[2] = LocalDateTime.parse("2020-06-30 14:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[3] = LocalDateTime.parse("2020-06-30 15:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[4] = LocalDateTime.parse("2020-06-30 15:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[5] = LocalDateTime.parse("2020-06-30 15:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[6] = LocalDateTime.parse("2020-06-30 16:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //
        date[7] = LocalDateTime.parse("2020-06-30 17:02", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //

        assertEquals(39, TollFeeCalculator.getTotalFeeCost(date));

    }

    @Test
    @DisplayName("Checkingarraylist when testing length-1")
    void CheckArrayLength() {
        LocalDateTime[] date = new LocalDateTime[6];
        date[0] = LocalDateTime.parse("2020-06-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-06-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[2] = LocalDateTime.parse("2020-06-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[3] = LocalDateTime.parse("2020-06-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[4] = LocalDateTime.parse("2020-06-01 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[5] = LocalDateTime.parse("2020-06-01 17:36", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        assertEquals(13, TollFeeCalculator.getTotalFeeCost(date));

    }
}
