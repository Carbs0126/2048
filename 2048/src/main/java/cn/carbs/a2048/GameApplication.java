package cn.carbs.a2048;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class GameApplication extends Application {

    public static GameApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        initBugly();
        application = this;
    }

    private void initBugly(){
        CrashReport.initCrashReport(getApplicationContext(), "fc745e57ea", true);
    }

    public static GameApplication getApplication(){
        return application;
    }
}