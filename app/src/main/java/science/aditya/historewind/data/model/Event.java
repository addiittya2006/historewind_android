package science.aditya.historewind.data.model;

public class Event implements HistoryEvent {

    private String year;
    private String thumb;
    private String desc;

    public int getEventType() {
        return 2;
    }

    public String getYear() {
        return year;
    }

    public String getThumb() {
        return thumb;
    }

    public String getDesc() {
        return desc;
    }

}
