package com.example.myapplication.util.threadpool

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch

class AsyncTaskTest {
    val TAG = "AsyncTaskTest"
    fun moreSyncTaskDoThen() {
        if (true) {
            volatileTest()
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "main")
            coroutineScope {
                val deferreds = listOf(
                    async { asyncTask(1, 30) },
                    async { asyncTask(2, 50) },
                    async { asyncTask(3, 20) }
                )
                deferreds.awaitAll()

                //或
/*                val deferred1 = async { asyncTask(1, 10) }
                val deferred2 = async { asyncTask(2, 20) }
                val deferred3 = async { asyncTask(3, 30) }

                deferred1.await()
                deferred2.await()
                deferred3.await()*/
            }
            Log.d(TAG, "after all async task")
        }
    }

    fun oneThreadDo() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "oneThreadDo")

/*            coroutineScope {
                val deferreds = listOf(
                    async { asyncTask(1, 30) },
                    async { asyncTask(2, 50) },
                    async { asyncTask(3, 20) }
                )
                deferreds.awaitAll()
            }*/

            coroutineScope {
                asyncTask(1, 30)
                asyncTask(2, 50)
                asyncTask(3, 20)
            }

            Log.d(TAG, "after all async task")
        }
    }

    private suspend fun asyncTask(id: Int,sleepTime: Long) {
        withContext(Dispatchers.IO) {
            //异步耗时代码 比如请求网络
            Log.d(TAG, "id $id thread =${Thread.currentThread()}")
            Thread.sleep(sleepTime)
            Log.d(TAG, "task $id done")
        }
    }

    //---------------------------------
    fun countDownlatchTest() {
        Log.d(TAG, "countDownlatchTest")
        val latch = CountDownLatch(3)
        asyncTask2(latch, 1, 30)
        asyncTask2(latch, 2, 50)
        asyncTask2(latch, 3, 20)

        latch.await()
        Log.d(TAG, "after all async task")
    }

    private fun asyncTask2(latch: CountDownLatch,id: Int,sleepTime: Long) {
        Thread {
            //异步耗时代码 比如请求网络
            Log.d(TAG, "id $id thread =${Thread.currentThread()}")
            Thread.sleep(sleepTime)
            Log.d(TAG, "task $id done")
            latch.countDown()
        }.start()
    }

    //---------------------------------
    fun threadJoinTest() {
        Log.d(TAG, "threadJoinTest")
        val t1= asyncTask3(1, 30)
        val t2= asyncTask3(2, 50)
        val t3= asyncTask3(3, 20)

        t1.join()//t1 线程加入主线程中，t1结束后，主线程再执行
        t2.join()//t2 线程加入主线程中，t1结束后，主线程再执行
        t3.join()//t3 线程加入主线程中，t1结束后，主线程再执行
        Log.d(TAG, "after all async task")
    }

    private fun asyncTask3(id: Int,sleepTime: Long): Thread{
        val t = Thread {
            //异步耗时代码 比如请求网络
            Log.d(TAG, "id $id thread =${Thread.currentThread()}")
            Thread.sleep(sleepTime)
            Log.d(TAG, "task $id done")
        }
        t.start()
        return t
    }

    //---------------------------------
    @Volatile
    var count = 3
    val lock = Object()
    fun volatileTest() {
        Log.d(TAG, "volatileTest")
        val t1= asyncTask4(1, 30)
        val t2= asyncTask4(2, 50)
        val t3= asyncTask4(3, 20)

        t1.start()
        t2.start()
        t3.start()

        synchronized(lock) {
            lock.wait()
        }

        Log.d(TAG, "after all async task")
    }

    private fun asyncTask4(id: Int,sleepTime: Long): Thread{
        val t = Thread {
            //异步耗时代码 比如请求网络
            Log.d(TAG, "id $id thread =${Thread.currentThread()}")
            Thread.sleep(sleepTime)
            Log.d(TAG, "task $id done")
            count--

            if (count == 0) {
                synchronized(lock) {
                    lock.notifyAll()
                }
            }
        }
        return t
    }

}