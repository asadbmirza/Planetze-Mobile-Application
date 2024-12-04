package com.example.plantezemobileapplication.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Habit implements Parcelable {
    private String id;
    private String name;
    private String category;
    private String activity;
    private int impact;
    private String time;
    private ArrayList<Integer> days;
    private int timesCompleted;

    public Habit() {
        // Default constructor
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

    protected Habit(Parcel in) {
        id = in.readString();
        name = in.readString();
        category = in.readString();
        activity = in.readString();
        impact = in.readInt();
        time = in.readString();
        days = in.readArrayList(Integer.class.getClassLoader());
        timesCompleted = in.readInt();
    }

    public static final Creator<Habit> CREATOR = new Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel in) {
            return new Habit(in);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };

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

    public int getTimesCompleted() {
        return timesCompleted;
    }

    public void setTimesCompleted(int timesCompleted) {
        this.timesCompleted = timesCompleted;
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
        return 31 * (name.hashCode() + category.hashCode() + impact);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(activity);
        dest.writeInt(impact);
        dest.writeString(time);
        dest.writeList(days);
        dest.writeInt(timesCompleted);
    }
}
