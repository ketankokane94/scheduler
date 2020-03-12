package models;
import org.junit.Assert;
import org.junit.Test;


public class ProjectTest {

    @Test
    public void getCompletedTime() {
        Project project = new Project("test Proj", 8, 14);
        Assert.assertEquals(8, project.getCompletedTime());
    }

    @Test
    public void setCompletedTime() {
        Project project = new Project("test Proj", 80, 14);
        project.setCompletedTime(8);
        Assert.assertEquals(8, project.getCompletedTime());
    }

    @Test
    public void getRequired_minutes() {
        Project project = new Project("test Proj", 8, 14);
        Assert.assertEquals(14, project.getRequired_minutes());
    }

    @Test
    public void setRequired_minutes() {
    }

    @Test
    public void getName() {
        Project project = new Project("test Proj", 8, 14);
        Assert.assertEquals("test Proj", project.getName());
    }

    @Test
    public void setName() {
        Project project = new Project("test Proj1", 8, 14);
        project.setName("test Proj");
        Assert.assertEquals("test Proj", project.getName());
    }
}