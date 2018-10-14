package science.aditya.historewind.data.model;

public class DateDigest {

    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    private String date;
    private int tod;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTod() {
        return tod;
    }

    public void setTod(int tod) {
        this.tod = tod;
    }
}
