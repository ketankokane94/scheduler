package service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import models.Project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static service.ConnectionService.APPLICATION_NAME;
import static service.ConnectionService.JSON_FACTORY;
import static service.ConnectionService.TOKENS_DIRECTORY_PATH;

public class GetProjectService {
    private static final List<String> SCOPES = Collections.singletonList(TasksScopes.TASKS);
    private static final String CREDENTIALS_FILE_PATH = "/google_task_credential.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GetProjectService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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
        Tasks service = new Tasks.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();


        String ID = getIDOfTheListWithTitle(service);
        final com.google.api.services.tasks.model.Tasks projects = service.tasks().list(ID).execute();
        List<com.google.api.services.tasks.model.Task> projectsItems = projects.getItems();

        if (projectsItems == null || projectsItems.isEmpty()) {
            System.out.println("No task lists found.");
        } else {
            for (Task task : projectsItems) {
                System.out.println(task.getTitle());
            }
        }
    }

    private static String getIDOfTheListWithTitle(Tasks service) throws IOException {
        TaskLists taskList = service.tasklists().list().execute();
        final List<TaskList> items = taskList.getItems();
        String ID = "";
        for (TaskList item : items){
           System.out.println(item.getId() + "     @@@@     " + item.getTitle());
             if(item.getTitle().equals("Projects")){
                 ID = item.getId();
                 break;
             }
        }
        return ID;
    }

    public List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Learn arrays", 0, 3*60));
        result.add(new Project("Leet code", 0, 3*60));
        result.add(new Project("Call HDFC", 0, 1*60));
        return result;
    }
}
