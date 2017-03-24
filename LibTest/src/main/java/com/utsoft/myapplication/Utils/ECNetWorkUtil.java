package com.utsoft.myapplication.Utils;

import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.utsoft.myapplication.Interface.Idoubanuser;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangzhu on 2017/3/9.
 * func:
 */

public class ECNetWorkUtil {
    private static Idoubanuser idoubanuser = null;

    private ECNetWorkUtil() {
    }

    /**
     * 获得接口实例
     *
     * @return
     */
    public static Idoubanuser getInstance() {
        synchronized (Object.class) {
            if (idoubanuser == null) {
                synchronized (Object.class) {
                    newOneInstance();
                }
            }
        }
        return idoubanuser;
    }

    /**
     * 创建新实例
     */
    public static int n = 0;
    private static Retrofit retrofit;
    private static HashSet<String> cookies = new HashSet<>();
    private static OkHttpClient okHttpClient;
    private static HttpLoggingInterceptor interceptor;

    public static class AddCookiesInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
                Log.v("zzz", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
            Log.v("zzz", "Request Header: " + builder.build().headers().toString());
            return chain.proceed(builder.build());
        }
    }

    public static class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                    Log.i("bbb", "header=====" + header);
                }
            }
            Log.v("bbb", "Response Header: " + originalResponse.headers().toString());
            return originalResponse;
        }
    }

    private static void newOneInstance() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
//                Log.d("zzz", "OkHttp====message " + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Idoubanuser.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        idoubanuser = retrofit.create(Idoubanuser.class);
    }

    //使用自定义SSLSocketFactory
    private void onHttps(OkHttpClient.Builder builder) {
        try {
            builder.sslSocketFactory(getSSLSocketFactory()).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    X509TrustManager x509TrustManager;
    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts,
                new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        return sslContext
                .getSocketFactory();
    }


}
