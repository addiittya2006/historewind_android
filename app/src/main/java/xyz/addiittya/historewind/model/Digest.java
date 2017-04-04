package xyz.addiittya.historewind.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by addiittya on 13/03/17.
 */

public class Digest implements Serializable{

    private String date;
    private ArrayList<HistoryEvent> births;
    private ArrayList<HistoryEvent> events;
    private ArrayList<HistoryEvent> deaths;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<HistoryEvent> getBirths() {
        return births;
    }

    public void setBirths(ArrayList<HistoryEvent> births) {
        this.births = births;
    }

    public ArrayList<HistoryEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<HistoryEvent> events) {
        this.events = events;
    }

    public ArrayList<HistoryEvent> getDeaths() {
        return deaths;
    }

    public void setDeaths(ArrayList<HistoryEvent> deaths) {
        this.deaths = deaths;
    }
}
