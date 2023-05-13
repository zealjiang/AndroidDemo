package com.example.myapplication;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final String TAG = "mtest";
    private static final int MESSAGE_ID = 100;
    private static final int JOB_ID = 191;

    private Handler mJobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "handle message");
            //请注意，我们手动调用了jobFinished方法。
            //当onStartJob返回true的时候，我们必须在合适时机手动调用jobFinished方法
            //否则该应用中的其他job将不会被执行
            //jobFinished((JobParameters) msg.obj, false);
            //jobScheduler.cancel(JOB_ID);//cancel的jobId必须与此JobInfo.Builder中的JobId一致

            //第一个参数JobParameter来自于onStartJob(JobParameters params)中的params，
            // 这也说明了如果我们想要在onStartJob中执行异步操作，必须要保存下来这个JobParameter。
            return true;
        }
    });

    /**
     * 启动任务之后，会调用onStartJob方法，因为JobService运行在主线程，所以如果在任务开始时，如果要执行耗时的操作，就需要创建一个线程去做。
     *
     * 如果onStartJob执行的是不耗时的任务，就可以返回false，表示任务执行结束。
     *
     * 如果onStartJob起了一个线程执行耗时任务，就要返回true，表示任务还在执行，需要等任务真正结束后手动调用JobFinished()方法告诉系统任务已经结束。
      */
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob");
        // 注意到我们在使用Hanlder的时候把传进来的JobParameters保存下来了
        mJobHandler.sendMessage(Message.obtain(mJobHandler, MESSAGE_ID, params));

        // 返回false说明job已经完成  不是个耗时的任务
        // 返回true说明job在异步执行  需要手动调用jobFinished告诉系统job完成
        // 这里我们返回了true,因为我们要做耗时操作。
        // 返回true意味着耗时操作花费的事件比onStartJob执行的事件更长
        // 并且意味着我们会手动的调用jobFinished方法
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob");
        mJobHandler.removeMessages(MESSAGE_ID);

        // 当系统收到一个cancel job的请求时，并且这个job仍然在执行(onStartJob返回true)，系统就会调用onStopJob方法。
        // 但不管是否调用onStopJob，系统只要收到取消请求，都会取消该job

        // true 需要重试
        // false 不再重试 丢弃job
        return false;
    }

    private static JobScheduler jobScheduler;
    public static void startJobScheduler(Context context){
        Log.d(TAG,"startJobScheduler");
        //Builder构造方法接收两个参数，第一个参数是jobId，每个app或者说uid下不同的Job,它的jobId必须是不同的
        //第二个参数是我们自定义的JobService,系统会回调我们自定义的JobService中的onStartJob和onStopJob方法
        JobInfo.Builder builder = new JobInfo.Builder(991,
                new ComponentName(context, JobSchedulerService.class));
        builder.setMinimumLatency(2000) // 最小延时 2秒
                .setOverrideDeadline(5000) // 最大延迟时间
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)//任意网络
                /**
                 设置重试/退避策略，当一个任务调度失败的时候执行什么样的测量采取重试。
                 initialBackoffMillis:第一次尝试重试的等待时间间隔ms
                 *backoffPolicy:对应的退避策略。比如等待的间隔呈指数增长。
                 */
                .setBackoffCriteria(2000,JobInfo.BACKOFF_POLICY_LINEAR)
                .setPersisted(false)//设置设备重启后，这个任务是否还要保留。需要权限：  RECEIVE_BOOT_COMPLETED
                .setRequiresCharging(false)//是否需要充电
                .setRequiresDeviceIdle(false);//是否需要等设备出于空闲状态的时候

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //指定启动Job是否为低电源
            builder.setRequiresBatteryNotLow(false);

            //指定启动Job是否为低存储
            builder.setRequiresStorageNotLow(false);
        }
        JobInfo jobInfo = builder.build();

        jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int jobId = jobScheduler.schedule(jobInfo);
        if (jobId == JobScheduler.RESULT_FAILURE) {
            Log.d(TAG, "jobId is " + jobId + " Schedule failed");
            // 取消Job
            jobScheduler.cancel(jobId);
        }else{
            Log.d(TAG, "shcedule job suc jobId=" + jobId +"=="+JobScheduler.RESULT_SUCCESS+" jobScheduler="+jobScheduler);
        }
    }
}
