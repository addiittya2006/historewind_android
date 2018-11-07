package science.aditya.historewind.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Birth implements HistoryEvent {

    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("desc")
    @Expose
    private String desc;
    private int eventType;

    public int getEventType() {
        return 1;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
