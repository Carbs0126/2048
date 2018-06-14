package cn.carbs.a2048.net.json;

import java.util.List;

/**
 * Created by carbs on 2017/12/15.
 */
public class ApkUpdateJson {

    public AndroidInfo android;

    public static class AndroidInfo {
        public LawyerInfo lawyer;

        public static class LawyerInfo {
            public String current_version_code;
            public String current_version_name;
            public String force_update;
            public String file_length;
            public List<String> update_info;
        }
    }
}