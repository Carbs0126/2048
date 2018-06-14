package cn.carbs.a2048.net;

import java.util.concurrent.TimeUnit;

import cn.carbs.a2048.common.Constant;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rick.Wang on 2016/10/22.
 */
public class LawyerHttp {

    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit mRetrofit;
    private LawyerApi mLinApi;

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static LawyerHttp instance;

    private LawyerHttp() {

        OkHttpClient client = getOkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.getBaseUrl())
                .build();

        mLinApi = mRetrofit.create(LawyerApi.class);
    }


    public static OkHttpClient getOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        return client;
    }

    public static LawyerHttp getInstance() {
        if (instance == null) {
            synchronized (LawyerHttp.class) {
                if (instance == null) {
                    instance = new LawyerHttp();
                }
            }
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public static RequestBody toTextRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), value);
        return requestBody;
    }

    public void getApkUpdateInfo(Observer<ResponseBody> subscriber) {
        mLinApi.getApkUpdateInfo(Constant.ApkUpdateInfo.url_version_info)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}