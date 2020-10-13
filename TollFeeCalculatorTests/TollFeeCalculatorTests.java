package kyh.labs.lab4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TollFeeCalculatorTests {


    @Test
    @DisplayName("Testing Maximal Cost per day")
    void maxcost() {
        LocalDateTime[] testDates = new LocalDateTime[6];
        testDates[0] = LocalDateTime.parse("2020-06-30 06:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 8
        testDates[1] = LocalDateTime.parse("2020-06-30 06:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        testDates[2] = LocalDateTime.parse("2020-06-30 07:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18
        testDates[3] = LocalDateTime.parse("2020-06-30 07:18", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 18
        testDates[4] = LocalDateTime.parse("2020-06-30 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // 13
        testDates[5] = LocalDateTime.parse("2020-06-30 15:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); //8


        assertEquals(60, TollFeeCalculator.getTotalFeeCost(testDates));//
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
    @DisplayName("Test if error prints")
    void Testprint() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        assertEquals("Utskrift skickat till System.err", errContent.toString().trim());

    }


    @Test
    @DisplayName("test if file was empty")
    void TestExpections() {
        try {
            new TollFeeCalculator("Data/Emptyfile.txt");
        } catch (DateTimeParseException e) {
            System.err.println(e);
            assertNull(e);
        }
    }

    @Test
    @DisplayName("Test first passing")
    void TestFirstPassing() throws DateTimeParseException {
        LocalDateTime[] date = new LocalDateTime[2];
        date[0] = LocalDateTime.parse("2020-06-01 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-06-01 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        assertEquals(16, TollFeeCalculator.getTotalFeeCost(date));

    }


    @Test
    void getTotalFeeCostOver60() {
        LocalDateTime[] date = new LocalDateTime[6];
        date[0] = LocalDateTime.parse("2020-06-01 06:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[1] = LocalDateTime.parse("2020-06-01 08:52", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[2] = LocalDateTime.parse("2020-06-01 10:13", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[3] = LocalDateTime.parse("2020-06-01 14:34", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        date[4] = LocalDateTime.parse("2020-06-01 16:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    }

    @Test
    @DisplayName("Checkingarraylist")
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
