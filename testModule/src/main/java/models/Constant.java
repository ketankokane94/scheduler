package models;

import com.google.api.services.calendar.model.EventDateTime;

import org.joda.time.DateTime;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
    public  static String WAKE_UP_AT = "09:30 AM";
    public  static String SLEEP_AT = "11:59 PM";
    public  static int min_interval = 55;
    public  static int max_interval = 120;


    public static DateTime convertToDateTime(EventDateTime eventDateTime){
        return new DateTime(eventDateTime.getDate());
    }

}
