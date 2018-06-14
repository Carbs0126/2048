package cn.carbs.a2048.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by carbs on 2017/4/23.
 */

public class ToastUtil {

    public static void showUnfinishedToast(Context context){
        Toast.makeText(context.getApplicationContext(), "施工中...", Toast.LENGTH_SHORT).show();
    }

    public static void showLogoutToast(Context context){
        Toast.makeText(context.getApplicationContext(), "退出成功", Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastL(Context context, String message){
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
