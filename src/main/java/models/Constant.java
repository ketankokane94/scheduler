package models;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.model.EventDateTime;

import org.joda.time.DateTime;


public class Constant {

    public static final String CALENDAR_TO_ACCESS_PRIMARY = "primary";
    public static final String START_TIME = "startTime";
    public static final String APPLICATION_NAME = "Schedule Maker by Ketan";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public  static int min_interval = 55;
    public  static int max_interval = 120;
    public  static String FREE_INTERVAL_NAME = "Free Interval";


    // get Tomorrow's date

    public static com.google.api.client.util.DateTime  TOMORROWPLUSONE (){
        return new com.google.api.client.util.DateTime(DateTime.now().withTimeAtStartOfDay().plusDays(2).getMillis());
    }

    public static com.google.api.client.util.DateTime  TOMORROW (){
        return new com.google.api.client.util.DateTime(DateTime.now().withTimeAtStartOfDay().plusDays(1).getMillis());
    }

    public static DateTime convertToDateTime(EventDateTime eventDateTime){
        return new DateTime(eventDateTime.getDateTime().getValue());


    }

    public static com.google.api.client.util.DateTime convertToGoogleDateTime(DateTime dateTime){
        return new com.google.api.client.util.DateTime(dateTime.getMillis());
    }

}
