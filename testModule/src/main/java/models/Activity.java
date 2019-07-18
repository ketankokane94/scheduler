package models;

/**
 * {"name":"Sleep","from":"2230","to":"0530","how_long":7}
 */
public class Activity extends Task {

    public Activity(String name, String from, String to) {
        super(name, from, to);
    }
}
