package science.aditya.historewind.data.model;


public interface HistoryEvent {

    int getEventType();

    String getYear();

    void setYear(String mYear);

    String getThumb();

    void setThumb(String mThumb);

    String getDesc();

    void setDesc(String mDesc);
}
