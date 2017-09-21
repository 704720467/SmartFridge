package com.smartfridge.mvp.network;

import retrofit.mime.TypedString;

/**
 * Created by zp on 2017/6/15.
 */
public class TypedJsonString extends TypedString{

    public TypedJsonString(String string) {
        super(string);
    }

    @Override
    public String mimeType() {
        return "application/json";
    }
}
