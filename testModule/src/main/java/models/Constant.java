package models;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
    public  static String WAKE_UP_AT = "09:30 AM";
    public  static String SLEEP_AT = "11:59 PM";

    public static DateTime convertToDateTime(EventDateTime eventDateTime){
      return  eventDateTime.getDate();
    }
}
