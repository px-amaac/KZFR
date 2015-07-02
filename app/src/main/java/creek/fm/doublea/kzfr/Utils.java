package creek.fm.doublea.kzfr;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import creek.fm.doublea.kzfr.models.Airtime;

/**
 * Created by Aaron on 6/30/2015.
 */
public class Utils {
    /*
        https://gist.github.com/laaptu/7867851
     */
    public static int convertDpToPx(Context context, int dp){
        return Math.round(dp*(context.getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));

    }

    public static String getFriendlyAirTime(Airtime airTime) {
        String friendlyAirtime;

        friendlyAirtime = parseTime(airTime.getStartF()) + " - "
                + parseTime(airTime.getEndF());
        return friendlyAirtime;
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
