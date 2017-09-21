package com.smartfridge.mvp.network;

import com.smartfridge.application.MyApplication;
import com.smartfridge.util.Base64;
import com.smartfridge.util.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit.RequestInterceptor;

public class SpecialRequestInterceptor implements RequestInterceptor {

    @Override
    public void intercept(RequestInterceptor.RequestFacade request) {
        long time = System.currentTimeMillis() / 1000;
        request.addHeader("version", Constants.NETWORK_REQUEST_VERSION);
        request.addHeader("osVersion", MyApplication.getAppContext().getVersion());
        request.addHeader("User-Agent", Constants.USER_AGENT);
        request.addHeader("accessKey", String.valueOf(time));
        request.addHeader("sign", sign(time));
    }

    public static String sign(long time) {
        try {
            return Base64.encode(md5(time + "ZhongShenGuiWei2015"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] md5(String plainText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plainText.getBytes("UTF-8"));
        return md.digest();
    }
}