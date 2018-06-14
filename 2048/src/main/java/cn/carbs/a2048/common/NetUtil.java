package cn.carbs.a2048.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by carbs on 2017/4/22.
 */

public class NetUtil {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkNetWithFailMessage(Context context, String message) {
        if (!isNetworkAvailable(context.getApplicationContext())) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean hasWifiConnection(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
