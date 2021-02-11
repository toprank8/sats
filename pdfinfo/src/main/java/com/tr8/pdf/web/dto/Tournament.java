package com.tr8.pdf.web.dto;

public class Tournament {

    private String year ;
    private int id ;
    private String event ;

    public Tournament(String year, int id, String event) {
        this.year = year;
        this.id = id;
        this.event = event;
    }

    public Tournament() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
