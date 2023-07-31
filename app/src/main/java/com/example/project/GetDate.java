package com.example.project;

import java.util.Calendar;

public class GetDate {
    private String day;
    private String month;
    private String year;

    public GetDate() {
        Calendar c = Calendar.getInstance();

        int d = c.get(Calendar.DAY_OF_MONTH);
        if (d < 10) {
            day = "0"+d;
        }
        else {
            day = Integer.toString(d);
        }

        int m= c.get(Calendar.MONTH)+1;
        if (m < 10) {
            month = "0"+m;
        }
        else {
            month = Integer.toString(m);
        }


        year = Integer.toString(c.get(Calendar.YEAR));
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
