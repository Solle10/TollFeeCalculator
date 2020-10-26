package kyh.labs.lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TollFeeCalculator {


    public TollFeeCalculator(String inputFile) {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            String[] dateStrings = sc.nextLine().split(", ");
            LocalDateTime[] dates = new LocalDateTime[dateStrings.length]; //deleted -length
            for (int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                int dayOne = dates[0].getDayOfYear();
                if (dates[i].getDayOfYear() != dayOne) {
                }
            }
            sc.close();
            System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
        } catch (DateTimeParseException e) {
            System.err.println("Wrong dates  " + e + " Please try again!");
        } catch (IOException e) {
            System.err.println("File not found " + inputFile);

        } finally {
            System.out.println("Closed");
        }
    }


    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        int feePerHour = 0;
        LocalDateTime intervalStart = dates[0];
        for (LocalDateTime date : dates) {
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            int fee = 0;
            if (diffInMinutes >= 60) {
                feePerHour = 0;
                intervalStart = date;
                totalFee += getTollFeePerPassing(date);
            } else {
                feePerHour = Math.max(getTollFeePerPassing(date), feePerHour);
                totalFee += fee;
            }
            System.out.println(date.toString() + "\n" + "Fee: " + getTollFeePerPassing(date) + "\n" + "---------");
        }

        return Math.min(totalFee + feePerHour, 60);//changed to Math.min
    }


    public static int getTollFeePerPassing(LocalDateTime date) {
        if (isTollFreeDate(date)) return 0;
        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6 && minute <= 29) return 8;
        else if (hour == 6) return 13;
        else if (hour == 7) return 18;
        else if (hour == 8 && minute <= 29) return 13; //bug
        else if (hour >= 8 && hour < 15) return 8;
        else if (hour == 15 && minute <= 29) return 13;//bug
        else if (hour == 15 || hour == 16) return 18;
        else if (hour == 17) return 13;
        else if (hour == 18 && minute <= 29) return 8;
        else return 0;
    }

    public static boolean isTollFreeDate(LocalDateTime date) {
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }

    public static void main(String[] args) {
        new TollFeeCalculator("Data/Lab4.txt");


    }
}
