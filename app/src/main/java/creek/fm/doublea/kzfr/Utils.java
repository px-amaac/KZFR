package creek.fm.doublea.kzfr;

import android.content.Context;
import android.util.DisplayMetrics;

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
}
