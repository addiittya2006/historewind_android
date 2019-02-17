package science.aditya.historewind.data.model;

import java.util.List;

public class Digest {

    private List<Birth> births = null;
    private List<Event> events = null;
    private List<Death> deaths = null;

    public List<Birth> getBirths() {
        return births;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Death> getDeaths() {
        return deaths;
    }

}
