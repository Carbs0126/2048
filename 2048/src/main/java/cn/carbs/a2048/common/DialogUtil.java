package cn.carbs.a2048.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by carbs on 2017/4/23.
 */

public class DialogUtil {

    public static void showMDDialog(Context context, String title, String message,
                                    String posTitle, String negTitle,
                                    DialogInterface.OnClickListener posListener,
                                    DialogInterface.OnClickListener negListener) {
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(negTitle, negListener);
        builder.setPositiveButton(posTitle, posListener);
        builder.show();
    }

    public static void showMDDialog(Context context,
                                    String title,
                                    String message,
                                    String posTitle,
                                    DialogInterface.OnClickListener posListener) {
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posTitle, posListener);
        builder.show();
    }

}
