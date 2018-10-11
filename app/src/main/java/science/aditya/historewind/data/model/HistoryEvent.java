package science.aditya.historewind.data.model;


public interface HistoryEvent {

    String getYear();

    void setYear(String mYear);

    String getThumb();

    void setThumb(String mThumb);

    String getDesc();

    void setDesc(String mDesc);
}
