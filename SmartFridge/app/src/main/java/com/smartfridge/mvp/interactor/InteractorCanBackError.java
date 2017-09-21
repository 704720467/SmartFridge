package com.smartfridge.mvp.interactor;

/**
 * 添加可返回错误对象的拦截器
 * Created by zp on 2017/7/21.
 */
public abstract class InteractorCanBackError implements Interactor {
    public Object errorEvent;

    public Object getErrorEvent() {
        return errorEvent;
    }

    /**
     * 设置错误时返回对象
     *
     * @param errorEvent
     */
    public void setErrorEvent(Object errorEvent) {
        this.errorEvent = errorEvent;
    }
}
