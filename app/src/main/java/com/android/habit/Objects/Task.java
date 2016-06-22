package com.android.habit.Objects;

/**
 * Created by Oscar_Local on 6/14/2016.
 */
public class Task {
    String description;
    String name;
    int points;
    int id;
    String dateString;

    /*
    --------------->Constructor
     */
    /**
     * Default constructor
     */
    public Task() {
        this("N/A", "n/a", 0, -1, "");
    }

    /**
     * Construct a specific task
     * @param name name of Task
     * @param description description of Task
     * @param taskPoints task points, used to prioritize Tasks and calculate progress
     */
    public Task(String name, String description, int taskPoints, int id, String dateString) {
        this.name = name;
        this.description = description;
        this.points = taskPoints;
        this.id = id;
        this.dateString = dateString;
    }

    public Task(String name, String description, int taskPoints, String dateString) {
        this.name = name;
        this.description = description;
        this.points = taskPoints;
        this.dateString = dateString;
    }


    /*
    --------------->Privates
     */



    /*
    --------------->Getters
     */
    public int getTaskPoints() {return points;}
    public String getTaskPointsAsString() {return String.valueOf(getTaskPoints());}
    public String getDescription() {return description;}
    public String getName() {return name;}
    public int getId() {return id;}
    public String getDateString() {return dateString;}
    /*
    --------------->Setters
     */
    public void setTaskPoints(int taskPoints) {this.points = taskPoints;}
    public void setDescription(String description) {this.description = description;}
    public void setName(String name) {this.name = name;}
    public void setId(int id) {this.id = id;}
    public void setDateString(String dateString) {this.dateString = dateString;}

}
