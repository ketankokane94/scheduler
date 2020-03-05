package java.service;

import test.java.models.models.Project;

import java.util.ArrayList;
import java.util.List;

public class GetProjectService {
    public List<Project> getProjects() {
        List<Project> result = new ArrayList<>();
        result.add(new Project("Learn arrays", 0, 3*60));
        result.add(new Project("Leet code", 0, 3*60));
        result.add(new Project("Call HDFC", 0, 1*60));
        return result;
    }
}
