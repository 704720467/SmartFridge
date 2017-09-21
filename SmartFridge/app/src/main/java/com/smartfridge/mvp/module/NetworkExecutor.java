package com.smartfridge.mvp.module;

import android.content.Context;

import com.smartfridge.BuildConfig;
import com.smartfridge.mvp.network.HttpRequestInterceptor;
import com.smartfridge.mvp.network.HttpService;
import com.smartfridge.mvp.network.HttpsRequestInterceptor;
import com.smartfridge.mvp.network.HttpsService;
import com.smartfridge.mvp.network.RetroritErrorHandler;
import com.smartfridge.util.Constants;
import com.smartfridge.util.TrustAllHostNameVerifier;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by zp on 2017/6/15.
 */

public class NetworkExecutor {
    public static final String apiUrl = Constants.HTTP_URL;
    public static final String userUrl = Constants.HTTPS_URL;
    public HttpService httpService;
    public HttpsService httpsService;
    public RetroritErrorHandler retroritErrorHandler;

    public NetworkExecutor(Context context) {
        init(context);
    }

    public void init(Context context) {

        retroritErrorHandler = new RetroritErrorHandler();

        ArrayList<InputStream> certificates = new ArrayList<InputStream>();
        //InputStream serverIS = context.getResources().openRawResource(R.raw.server);
        //  InputStream caIS = context.getResources().openRawResource(R.raw.ca);
        //  certificates.add(serverIS);
        // certificates.add(caIS);

        Cache responseCache = null;
        try {
            responseCache = new Cache(context.getCacheDir(), 10 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(responseCache);
        // // 设置连接超时时间
        // okHttpClient.setConnectTimeout(0, TimeUnit.MILLISECONDS);
        // // 读取超时时间
        // okHttpClient.setReadTimeout(0, TimeUnit.MILLISECONDS);
        okHttpClient.setSslSocketFactory(getSSLContext().getSocketFactory());

        // 证书认证，两个证书;
        //		okHttpClient.setSslSocketFactory(OkHttpClientManager.getSslSocketFactory(certificates));

        okHttpClient.setHostnameVerifier(new TrustAllHostNameVerifier());


        okHttpClient.setAuthenticator(new Authenticator() {

            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                return null;
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });

        RestAdapter.LogLevel level = null;
        if (BuildConfig.DEBUG) {
            level = RestAdapter.LogLevel.FULL;
        } else {
            level = RestAdapter.LogLevel.NONE;
        }

        RestAdapter restAdapter = new RestAdapter.Builder()//
                .setEndpoint(apiUrl)//
                .setRequestInterceptor(new HttpRequestInterceptor())//
                .setLogLevel(level)//
                .setClient(new OkClient(okHttpClient))//
                .setErrorHandler(retroritErrorHandler)//
                .build();
        httpService = restAdapter.create(HttpService.class);

        RestAdapter userAdapter = new RestAdapter.Builder()//
                .setEndpoint(userUrl)//
                .setRequestInterceptor(new HttpsRequestInterceptor())//
                .setLogLevel(level)//
                .setClient(new OkClient(okHttpClient))//
                .setErrorHandler(retroritErrorHandler)//
                .build();
        httpsService = userAdapter.create(HttpsService.class);
    }

    private SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }
}