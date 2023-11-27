package com.example.main.mmkv;

public abstract class Singleton<T> {


    public Singleton() {
    }


    private T mInstance;

    protected abstract T create();

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
            return mInstance;
        }
    }
}
