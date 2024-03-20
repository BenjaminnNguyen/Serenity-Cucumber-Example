package tasks.common;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utility {

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }

    public static String getDateTimeFromToday(String format, int plusDay) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String Date = today.plusDays(plusDay).format(formatter);
        return Date;
    }

    public static String convertFormatDate(String format, String date) {
        String date_ = date;
        if (format != "") {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime time = LocalDateTime.parse(date);
            date_ = time.format(formatter);
        }
        return date_;
    }

    public static String getDateTimeFromToday(String format, int plusDay, String type) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String date = today.plusDays(plusDay).format(formatter);
        if (type.equals("Minus")) {
            date = today.minusDays(plusDay).format(formatter);
        }
        return date;
    }

    public static String getMonthFromToday(String format, int months) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String Date = today.plusMonths(months).format(formatter);
        return Date;
    }

    public static String getTimeNow(String format) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String Date = today.truncatedTo(ChronoUnit.MINUTES).format(formatter);
        return Date;
    }

    public static String getDaysFromAnyDate(String format, String fromDate, String toDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        Date dateTime = formatter.parse(toDate);
        Date dateTime2 = formatter.parse(fromDate);
        long diff = dateTime2.getTime() - dateTime.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long difference = time.convert(diff, TimeUnit.MILLISECONDS);
        return String.valueOf(difference);
    }

    public static int compareDate(String date1, String date2, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date1_ = sdf.parse(date1);
            Date date2_ = sdf.parse(date2);
            int result = date1.compareTo(date2);
            if (result == 0) {
                System.out.println("Date1 is equal to Date2");
            } else if (result > 0) {
                System.out.println("Date1 is after Date2");
            } else if (result < 0) {
                System.out.println("Date1 is before Date2");
            }
            return result;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static double convertPriceToDouble(String price) {
        if (price.length() > 1) {
            System.out.println("The original string is: " + price);
            //Remove "$", "-"
            if (price.contains("-")) price = price.replaceAll("-", "");
            System.out.println("After format string is: " + price.substring(1));
            return Double.parseDouble(price.substring(1));
        }
        if (price.length() < 1) {
            price = "0";
        }
        return Double.parseDouble(price);
    }

    public static String convertDoubleToPrice(double price) {
        String output = "$" + String.format("%.2f", price).replaceAll(",", ".");
        return output;
    }
}
