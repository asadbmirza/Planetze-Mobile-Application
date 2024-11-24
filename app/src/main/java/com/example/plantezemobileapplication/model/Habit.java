package com.example.plantezemobileapplication.model;

import java.util.ArrayList;

public class Habit {
    private String id;
    private String name;
    private String category;
    private String activity;
    private int impact;

    private String time;
    private ArrayList<Integer> days;


    public Habit() {

    }
    public Habit(String name, String category, String activity, int impact) {
        this.name = name;
        this.category = category;
        this.activity = activity;
        this.impact = impact;
        this.time = "";
        this.days = new ArrayList<>();
    }
    public Habit(String name, String category, String activity, int impact, String time, ArrayList<Integer> days) {
        this.name = name;
        this.category = category;
        this.activity = activity;
        this.impact = impact;
        this.time = time;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImpact() {
        return impact;
    }


    public void setImpact(int impact) {
        this.impact = impact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if (time == null || time.length() != 5 || time.charAt(2) != ':') {
            return;
        }
        try {
            int hour = Integer.parseInt(time.substring(0, 2));
            int min = Integer.parseInt(time.substring(3));
            if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
                this.time = time;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return;
        }
    }


    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        this.days = days;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public int hashCode() {
        return 31*(name.hashCode() + category.hashCode() + impact);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Habit h = (Habit) obj;

        return (h.name.equals(name) && h.category.equals(category) && h.impact == impact);
    }

}
