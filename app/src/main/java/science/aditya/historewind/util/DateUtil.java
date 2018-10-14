package science.aditya.historewind.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class DateUtil {

    Calendar cal = new GregorianCalendar();

    public int getTod() {
        return cal.get(Calendar.AM_PM);
    }

    public String getMonth() {
        return setMonth(cal.get(Calendar.MONTH));
    }

    public int getDate() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }


    public static String setMonth(int month) {
        HashMap<String, String> map = new HashMap<>();

        map.put("0", "January");
        map.put("1", "February");
        map.put("2", "March");
        map.put("3", "April");
        map.put("4", "May");
        map.put("5", "June");
        map.put("6", "July");
        map.put("7", "August");
        map.put("8", "September");
        map.put("9", "October");
        map.put("10", "November");
        map.put("11", "December");

        return map.get(Integer.toString(month));
    }

}