package cn.carbs.a2048.common;

/**
 * Created by Rick.Wang on 2016/10/21.
 */
public class Constant {

    public static boolean PE = true;
    public static String getBaseUrl() {
        if (PE) {
            return "https://39.107.108.37:8443";
        } else {
            return "https://39.107.105.116:8443";
        }
    }

    public static String getBaseUrlHeader() {
        if (PE) {
            return "https://39.107.108.37:8443/atLawyer";
        } else {
            return "https://39.107.105.116:8443/atLawyer";
        }
    }

    //TODO
    public static class ApkUpdateInfo{
        public static final String url_version_info = "http://47.94.196.243:53100/atLawyer/PhoneUpFile/apk/version_info.json";
        public static final String url_apk = "http://47.94.196.243:53100/atLawyer/PhoneUpFile/apk/android-lawyer.apk";
    }
}