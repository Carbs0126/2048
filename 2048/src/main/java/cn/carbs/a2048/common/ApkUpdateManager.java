package cn.carbs.a2048.common;

import com.google.gson.Gson;

import java.io.InputStream;

import cn.carbs.a2048.net.LawyerHttp;
import cn.carbs.a2048.net.json.ApkUpdateJson;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * Created by carbs on 2017/4/22.
 */

public class ApkUpdateManager {

    private ApkUpdateManager() {
    }

    private static volatile ApkUpdateManager instance = null;

    public static ApkUpdateManager getInstance() {
        if (instance == null) {
            synchronized (ApkUpdateManager.class) {
                if (instance == null) {
                    instance = new ApkUpdateManager();
                }
            }
        }
        return instance;
    }

    public void requestApkUpdateInfo(ApkInfoUpdateCallback callback) {
        LawyerHttp.getInstance().getApkUpdateInfo(getResponseBodySubscriber(callback));
    }

    public interface ApkInfoUpdateCallback {
        void onInfoSuccess(ApkUpdateJson apkUpdateJson);

        void onInfoFail();
    }

    private Observer<ResponseBody> getResponseBodySubscriber(final ApkInfoUpdateCallback callback) {
        return new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (responseBody == null) {
                    if (callback != null) {
                        callback.onInfoFail();
                    }
                    return;
                }
                InputStream is = responseBody.byteStream();

                String updateInfo = FileUtils.inputStreamToString(is);

                if (updateInfo != null){
                    Gson gson = new Gson();
                    ApkUpdateJson json = gson.fromJson(updateInfo, ApkUpdateJson.class);
                    if (callback != null) {
                        callback.onInfoSuccess(json);
                    }
                }else{
                    if (callback != null) {
                        callback.onInfoFail();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onInfoFail();
                }
            }

            @Override
            public void onComplete() {

            }
        };
    }
}