package com.smartfridge.mvp.interactor;

/**
 * Created by zp on 2017/6/15.
 */
public enum InteractorPriority {
    LOW(0),
    MID(50),
    HIGH(100);

    private int value;

    InteractorPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
