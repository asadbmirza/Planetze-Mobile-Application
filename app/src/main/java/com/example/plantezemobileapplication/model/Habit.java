package com.example.plantezemobileapplication.model;

public class Habit {
    String name;
    String category;
    int impact;

    public Habit() {
    }
    public Habit(String name, String category, int impact) {
        this.name = name;
        this.category = category;
        this.impact = impact;
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
