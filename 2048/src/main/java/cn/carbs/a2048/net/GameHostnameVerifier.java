package cn.carbs.a2048.net;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by carbs on 2018/1/24.
 */

public class GameHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }

}
