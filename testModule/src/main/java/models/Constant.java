package models;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
    public  static String WAKE_UP_AT = "09:30 AM";
    public  static String SLEEP_AT = "11:59 PM";
}
