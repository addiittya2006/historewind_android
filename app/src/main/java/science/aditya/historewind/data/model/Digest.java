
package science.aditya.historewind.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Digest {

    @SerializedName("births")
    @Expose
    private List<Birth> births = null;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;
    @SerializedName("deaths")
    @Expose
    private List<Death> deaths = null;

    private List<HistoryEvent> complete = null;

    public List<Birth> getBirths() {
        return births;
    }

    public void setBirths(List<Birth> births) {
        this.births = births;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Death> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<Death> deaths) {
        this.deaths = deaths;
    }

}
