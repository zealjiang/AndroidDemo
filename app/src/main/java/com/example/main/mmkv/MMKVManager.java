package com.example.main.mmkv;

public class MMKVManager {

    //1像素activity任务栈id
    private static final String ONE_PX_ACTIVITY_TASK_ID = "one_px_activity_task_id";
    private static final String ONE_PX_ACTIVITY_TASK_ID_2 = "one_px_activity_task_id_2";

    private static final Singleton<MMKVManager> sInstance = new Singleton<MMKVManager>() {
        @Override
        protected MMKVManager create() {
            return new MMKVManager();
        }
    };

    public static MMKVManager getInstance() {
        return sInstance.get();
    }

    private MMKVManager() {
    }


    //1像素activity任务栈id
    public int getOnePxActivityTaskId() {
        return getIntValue(ONE_PX_ACTIVITY_TASK_ID, 0);
    }

    public void setOnePxActivityTaskId(int taskId) {
        setIntValue(ONE_PX_ACTIVITY_TASK_ID, taskId);
    }

    //1像素activity任务栈id
    public int getOnePxActivityTaskId2() {
        return getIntValue(ONE_PX_ACTIVITY_TASK_ID_2, 0);
    }

    public void setOnePxActivityTaskId2(int taskId) {
        setIntValue(ONE_PX_ACTIVITY_TASK_ID_2, taskId);
    }

    public final int getIntValue(String key, int defValue) {
        return MMKVWrapper.getInstance().getMMKV().getInt(key, defValue);
    }

    public final void setIntValue(String key, int value) {
        MMKVWrapper.getInstance().getMMKV().putInt(key, value);
    }
}
