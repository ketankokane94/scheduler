import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import models.Interval;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class CalendarQuickstart {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        //ensure the authenticated user has write access to the calendar with
        // the calendarId you provided (for example by calling calendarList.get()
        // for the calendarId and checking the accessRole).


        final List<Interval> intervals = Scheduler.main();

        for(Interval interval: intervals){
            if(interval.Name.equals("wake up") || interval.Name.equals("Sleep")){
                continue;
            }

            Event event = new Event()
                    .setSummary(interval.Name);
                    //.setLocation("800 Howard St., San Francisco, CA 94103")
                   // .setDescription("A chance to hear more about Google's developer products.");

            LocalDateTime date = LocalDate.now().atTime(interval.from);
            //date.format()

            System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ss") )+"+5:30");

            DateTime startDateTime = new DateTime(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ss"))+"+05:30");


            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime);
            event.setStart(start);


            date = LocalDate.now().atTime(interval.to);
            DateTime endDateTime = new DateTime(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ss"))+"+05:30");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime);

            event.setEnd(end);
            String calendarId = "primary";
            event = service.events().insert(calendarId, event).execute();

            System.out.printf("models.Event created: %s\n", event.getHtmlLink());
        }


    }
}