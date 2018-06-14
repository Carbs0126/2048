package cn.carbs.a2048.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Rick.Wang on 2016/10/22.
 */
public interface LawyerApi {

    //获取最新apk的版本情况
    @Streaming
    @GET
    Observable<ResponseBody> getApkUpdateInfo(@Url String url);

}
