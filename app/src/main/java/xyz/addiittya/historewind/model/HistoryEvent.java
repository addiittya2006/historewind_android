package xyz.addiittya.historewind.model;

import java.io.Serializable;

/**
 * Created by addiittya on 12/03/17.
 */

public class HistoryEvent implements Serializable{

    private String mYear;
    private String mInfo;

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        this.mYear = year;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        this.mInfo = info;
    }
}
