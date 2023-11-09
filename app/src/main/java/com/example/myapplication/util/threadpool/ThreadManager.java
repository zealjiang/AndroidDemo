package com.example.myapplication.util.threadpool;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.example.myapplication.BuildConfig;

import java.security.InvalidParameterException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadManager{

    public static final int THREAD_UI = 0;
    /**
     * 操作Launcher.db中的表请不要使用此线程
     */
    public static final int THREAD_DB = 1;
    /**
     * 数据升级线程
     */
    public static final int THREAD_DATA = 2;
    //only for settings
    public static final int THREAD_SETTINGS = 3;
    public static final int THREAD_LOAD_WEATHER_DATA = 4;
    public static final int THREAD_CHECK_SECURITY = 5;
    /**
     * 后台功能线程
     */
    public static final int THREAD_BACKGROUND = 6;
    public static final int THREAD_CONTENT_OBTAION = 7;
    public static final int THREAD_WORKER = 8;

    public static final int THREAD_SIZE = 9;



    private final int MAX_TASK_SIZE = Integer.MAX_VALUE;
    private final int THREAD_POOL_SIZE = 5;
    private final long KEEP_ALIVE_TIME = 30L;
    private ExecutorService mPoolExecutor;
    private String mThreadName;

    private LinkedBlockingQueue<Runnable> mBlockingQueue = null;

    private static ThreadFactory mThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadManager");
        }
    };

    private static class Holder {
        private static ThreadManager threadManager = new ThreadManager();
    }

    public static ThreadManager getInstance() {
        return Holder.threadManager;
    }

    private void ensureExecutor() {
        if (mPoolExecutor != null) {
            return;
        }
        mBlockingQueue = new LinkedBlockingQueue<>(MAX_TASK_SIZE);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                THREAD_POOL_SIZE, THREAD_POOL_SIZE * 2,
                KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                mBlockingQueue, mThreadFactory);
        executor.allowCoreThreadTimeOut(true);
        mPoolExecutor = executor;
    }

    public synchronized void execute(String threadName, Runnable runnable) {
        ensureExecutor();
        mThreadName = threadName;
        if (runnable == null) {
            return;
        }
        try {
            mPoolExecutor.execute(runnable);
        } catch (Exception e) {
        }
    }

    public synchronized void execute(Runnable runnable) {
        mThreadName = "";
        execute(mThreadName, runnable);
    }

    public synchronized void clearTaskQueue() {
        try {
            if (!mBlockingQueue.isEmpty()) {
                mBlockingQueue.clear();
            }
        } catch (Exception e) {
        }
    }


    // 线程信息数组
    private static final Handler[] HANDLER_LIST = new Handler[THREAD_SIZE];
    private static final String[] THREAD_NAME_LIST = {
            "thread_ui", "thread_db", "thread_data", "thread_settings", "thread_load_weather_data",
            "thread_check_security", "thread_background", "thread_content_obtaion","thread_worker"
    };

    /**
     * 派发任务
     * @param index
     * @param r
     */
    public static void post(int index, Runnable r) {
        postDelayed(index, r, 0);
    }

    public static void postDelayed(int index, Runnable r, long delayMillis) {
        Handler handler = getHandler(index);
        handler.postDelayed(r, delayMillis);
    }

    public static void removeCallbacks(int index, Runnable r) {
        Handler handler = getHandler(index);
        handler.removeCallbacks(r);
    }

    /**
     * 获取线程Handler
     * @param index
     * @return
     */
    public static Handler getHandler(int index) {
        if (index < 0 || index >= THREAD_SIZE) {
            throw new InvalidParameterException();
        }

        if (HANDLER_LIST[index] == null) {
            synchronized (HANDLER_LIST) {
                if (HANDLER_LIST[index] == null) {
                    if (index == THREAD_UI) {
                        HANDLER_LIST[THREAD_UI] = new Handler(Looper.getMainLooper());
                    } else {
                        HandlerThread thread = new HandlerThread(THREAD_NAME_LIST[index]);
                        thread.setPriority(Thread.MIN_PRIORITY);
                        thread.start();
                        Handler handler = new Handler(thread.getLooper());
                        HANDLER_LIST[index] = handler;
                    }
                }
            }
        }
        return HANDLER_LIST[index];
    }

    /**
     * Returns true if the current thread is the given thread.
     */
    public static boolean runningOn(int index) {
        return getHandler(index).getLooper() == Looper.myLooper();
    }

    // 从单独的ThreadChecker挪到这里.
    public static void currentlyOn(int index) {
        if (BuildConfig.DEBUG) {
            if (!runningOn(index))
                throw new AssertionError("Running on incorrect thread!");
        }
    }

    /**
     * 在Ui线程里执行
     * @param runnable 执行的逻辑
     */
    public static void runOnUi(Runnable runnable) {
        if (runnable == null) return;

        if (runningOn(ThreadManager.THREAD_UI)) {
            runnable.run();
        } else {
            post(THREAD_UI, runnable);
        }
    }
}
