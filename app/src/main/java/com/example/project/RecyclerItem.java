package com.example.project;

public class RecyclerItem {
    String date;
    String value;
    int resourceId;

    public RecyclerItem(int resourceId, String date, String value) {
        this.date = date;
        this.value = value;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}