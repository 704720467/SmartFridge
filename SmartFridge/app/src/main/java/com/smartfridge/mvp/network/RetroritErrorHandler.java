package com.smartfridge.mvp.network;

import android.util.Log;

import com.smartfridge.R;
import com.smartfridge.mvp.AppModule;
import com.smartfridge.util.Const;
import com.smartfridge.util.MyLogUtil;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;


/**
 * Created by zp on 2017/6/15.
 */
public class RetroritErrorHandler implements ErrorHandler {
    public static boolean tokenOverdue = false;//true:过期：false：没有过期

    @Override
    public Throwable handleError(RetrofitError retrofitError) {
        // listener.Error(retrofitError);
        RetrofitError.Kind kind = retrofitError.getKind();
        MyLogUtil.showLogI("===", "error-code-kind=" + kind);
        switch (kind) {
            case NETWORK:
                if (Const.TIMEOUT.equalsIgnoreCase(retrofitError.getMessage())) {
                    // 网络超时，连接超时或者读取超时
                    postTimeOut();
                    // ProgressDialogUtil.cancelWaiteDialog();
                } else {
                    showError(R.string.network_error);
                }
                break;
            case CONVERSION:
                showError(R.string.data_error);
                break;
            case UNEXPECTED:
                // showError(R.string.unknown_error);
                Log.e("RetroritErrorHandler", "未知错误！");
                break;
            case HTTP:
                handleHttpError(retrofitError.getResponse().getStatus());
                break;
        }
        retrofitError.printStackTrace();
        return retrofitError;
    }

    public void handleHttpError(int error) {

        // MyLogUtil.showLogI("===", "error-code="+error);
        //

        switch (error) {
            case 401:
                showError(R.string.sign_error);
                break;
            case 402:// wk 3.0 token过期
            case 403:// wk 3.0 refreshtoken过期
                synchronized (this) {
                    if (!tokenOverdue) {
                        tokenOverdue = true;
                        reLogin(R.string.token_error);
                    }
                }
                break;
            case 503:
                showError(R.string.system_error);
                break;
            default:
                if (error == 404)
                    // 上报404错误
                    Log.e("RetroritErrorHandler", "404错误！");
                break;
        }
    }

    public void showError(int resId) {
        AppModule.getInstance().getBus().showError(resId);
    }

    public void postTimeOut() {
        AppModule.getInstance().getBus().postTimeOut();
    }

    public void reLogin(int resId) {
        AppModule.getInstance().getBus().reLogin(resId);
    }

    // wk 3.0加个错误回调
    public interface httpErrorListener {
        void onHttpError(int error);
        // void Error(RetrofitError retrofitError);
    }

    private httpErrorListener listener;

    public void setOnHttpErrorListener(httpErrorListener listener) {
        this.listener = listener;
    }


}
