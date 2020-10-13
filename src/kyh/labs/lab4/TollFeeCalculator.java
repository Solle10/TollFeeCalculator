package kyh.labs.lab4;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TollFeeCalculator {

    public TollFeeCalculator(String inputFile) {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            try {
                String[] dateStrings = sc.nextLine().split(", ");
                LocalDateTime[] dates = new LocalDateTime[dateStrings.length]; //deleted -1
                for (int i = 0; i < dates.length; i++) {
                    dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                }
                System.out.println("The total fee for the inputfile is" + getTotalFeeCost(dates));
            } finally {
                sc.close(); //Added sc.close();
            }
        } catch (IOException e) {
            System.err.println("Could not read file " + inputFile);
        } catch (NoSuchElementException e) {
            System.err.println("Can't show any data, probably a empty file, Check please");
        } catch (DateTimeParseException e) {
            System.err.println("Dates is incorrect");


        }
    }


    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        LocalDateTime intervalStart = dates[0];
        for (LocalDateTime date : dates) {
            System.out.println(date.toString());
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            if (diffInMinutes > 60) {
                totalFee += getTollFeePerPassing(date);
                intervalStart = date;
            } else {
                totalFee += Math.max(getTollFeePerPassing(date), getTollFeePerPassing(intervalStart));
            }
        }

        return Math.min(totalFee, 60);  //Changing from Math.max to Math.min
    }

    public static int getTollFeePerPassing(LocalDateTime date) {
        if (isTollFreeDate(date)) return 0;
        int hour = date.getHour();
        int minute = date.getMinute();
        if (hour == 6 && minute <= 29) return 8;
        else if (hour == 6 && minute <= 59) return 13;
        else if (hour == 7 && minute <= 59) return 18;
        else if (hour == 8 && minute <= 29) return 13;
        else if (hour >= 8 && hour <= 14 && minute <= 59) return 8; // bug
        else if (hour == 15 && minute <= 29) return 13;
        else if (hour >= 15 && hour <= 16) return 18; // bug
        else if (hour == 17 && minute <= 59) return 13;
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
