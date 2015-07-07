package creek.fm.doublea.kzfr.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import creek.fm.doublea.kzfr.models.Airtime;

/**
 * Created by Aaron on 6/30/2015.
 */
public class Utils {

    public static String parseDay(String weekday) {
        int day = 0;
        String[] mWeekday = { "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday" };
        try {
            day = Integer.parseInt(weekday);
        } catch (NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        return mWeekday[day - 1];
    }
    /*
        https://gist.github.com/laaptu/7867851
     */
    public static int convertDpToPx(Context context, int dp){
        return Math.round(dp*(context.getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));

    }

    public static String getFriendlyAirtimes(List<Airtime> airtimes) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Airtime airtime : airtimes) {
            stringBuilder.append(getFriendlyAirTime(airtime))
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    public static String getFriendlyAirTime(Airtime airTime) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(parseDay(airTime.getWeekday()))
                .append(" ")
                .append(parseTime(airTime.getStartF()))
                .append(" - ")
                .append(parseTime(airTime.getEndF()));

        return stringBuilder.toString();
    }

    private static String parseTime(String time) {
        String mTime = null;
        TimeZone currentTimeZone = TimeZone.getDefault();
        TimeZone KZFRTimeZone = TimeZone.getTimeZone("America/Los_Angeles");
        SimpleDateFormat simpleDateFormatKZFRTimeZone = new SimpleDateFormat("HH:mm:ss", Locale.US);
        SimpleDateFormat simpleDateFormatCurrentTimeZone = new SimpleDateFormat("hh:mm a",
                Locale.getDefault());

        Date date = null;
        simpleDateFormatCurrentTimeZone.setTimeZone(currentTimeZone);
        simpleDateFormatKZFRTimeZone.setTimeZone(KZFRTimeZone);
        try {
            date = simpleDateFormatKZFRTimeZone.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mTime = simpleDateFormatCurrentTimeZone.format(date.getTime());

        // bruteforce the result to be formatted correctly.
        String result = "";
        if (mTime.charAt(0) == '0')
            result = mTime.substring(1);
        else
            result = mTime;
        return result;
    }
}
