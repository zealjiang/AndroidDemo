package com.example.libjava;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    public static void main(String[] args) {
        ReentrantLockTest test = new ReentrantLockTest();
        test.testReentrantLock();
    }

    void testReentrantLock() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        lock.lock(); // 获取锁 目的是为了让主线程先获取到锁

        MyThread thread = new MyThread(lock, condition);
        thread.start();

        try {
            System.out.println("主线程等待");
            condition.await(); // 等待条件满足 会释放锁 一旦调用了 condition.await() 方法，当前线程就会释放锁，并且进入到与该条件相关的等待队列中，直到被其他线程显式地唤醒为止

            System.out.println("主线程继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // 释放锁
        }
    }


    class MyThread extends Thread {
        private final ReentrantLock lock;
        private final Condition condition;

        public MyThread(ReentrantLock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            try {
                lock.lock(); // 获取锁

                System.out.println("线程开始执行");
                Thread.sleep(2000); // 模拟耗时操作

                condition.signal(); // 通知等待的线程 在调用 condition.signal() 之后，当前线程并不会立即释放锁，而是会继续执行剩余的代码。直到当前线程显式地释放锁或执行完毕后，才会将锁释放给其他等待的线程

                System.out.println("线程执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock(); // 释放锁
            }
        }
    }
}
