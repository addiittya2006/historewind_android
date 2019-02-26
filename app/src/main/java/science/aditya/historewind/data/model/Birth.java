package science.aditya.historewind.data.model;

public class Birth implements HistoryEvent {

    private String year;
    private String thumb;
    private String desc;

    public int getEventType() {
        return 1;
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
