package com.example.main.mmkv;

import com.example.application.MApplication;
import com.tencent.mmkv.MMKV;

/**
 * 对mmkv 封装类
 *
 * @author yanxiaofei
 */
public class MMKVWrapper {
    private MMKV mMMKV;

    private static class InnerMMKV {
        private static final MMKVWrapper instance = new MMKVWrapper();
    }

    protected static MMKVWrapper getInstance() {
        return InnerMMKV.instance;
    }

    private MMKVWrapper() {
        mMMKV = MMKV.mmkvWithID(MApplication.getContext().getPackageName() + "_mmkv", MMKV.MULTI_PROCESS_MODE);
    }

    protected MMKV getMMKV() {
        return mMMKV;
    }

}
