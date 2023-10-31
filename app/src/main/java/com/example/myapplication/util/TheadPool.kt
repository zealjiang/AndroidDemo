package com.example.myapplication.util

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class TheadPool {

    fun createThreadPool(): ThreadPoolExecutor {
        val corePoolSize = 2  // 核心线程数
        val maximumPoolSize = 4  // 最大线程数
        val keepAliveTime = 10L  // 空闲线程的存活时间
        val unit = TimeUnit.SECONDS  // 存活时间单位
        var workQueue: BlockingQueue<Runnable>  = ArrayBlockingQueue(2)  // 任务队列, 有界队列，容量为2
        val threadFactory: ThreadFactory = Executors.defaultThreadFactory()  // 线程工厂
        val handler: RejectedExecutionHandler = ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        val executor: ThreadPoolExecutor = ThreadPoolExecutor(
            corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler)

        return executor
    }
}