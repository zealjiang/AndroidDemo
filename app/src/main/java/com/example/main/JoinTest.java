package com.example.main;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class JoinTest {
    public static void main(String[] args) {
        JoinTest waitTest = new JoinTest();
        waitTest.joinTest();
    }

    private void joinTest(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        final Object obj = new Object();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int G = 0;
                Random random = new Random();
                while (true){
                    G++;
                    System.out.println(df.format(new Date())+"  "+"thread = "+Thread.currentThread().getName()+"  G = "+G);
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(G > 3)break;

                    //int n = random.nextInt(9999999);
                    //System.out.println(df.format(new Date())+"  "+" thread = "+Thread.currentThread().getName()+"random :" + n +"  G = "+G+"  isInterrupted = "+Thread.currentThread().isInterrupted());

/*                    synchronized (obj){
                        try {
                            System.out.println(df.format(new Date())+"  "+"thread = "+Thread.currentThread().getName()+" sleep() 200秒");
                            //obj.wait();
                            Thread.currentThread().join(200 * 1000);
                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                            System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+" InterruptedException isInterrupted = "+Thread.currentThread().isInterrupted());
                        }
                    }*/

/*                    if(G > 6)break;
                    while (true) {
                        n--;
                        if(n == 0) {
                            System.out.println(df.format(new Date())+"  "+"G :" + G);
                            break;
                        }
                    }*/

/*                    System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+"*********break 跳出循环 退出线程**********");
                    break;*/


                }
                System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+" out while end run***");
            }
        });

        //System.out.println(df.format(new Date())+"  "+thread1.getName()+" start()  isInterrupted() = "+thread1.isInterrupted());
        System.out.println(df.format(new Date())+"  "+thread1.getName()+" start()  ");
        thread1.start();
/*        try {
            System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+" thread sleep() 2秒  ");
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //System.out.println(df.format(new Date())+"  "+"after "+Thread.currentThread().getName()+" sleep()  "+thread1.getName()+" join() ");
        try {
            System.out.println(df.format(new Date())+"  "+thread1.getName()+" join()  ");
            thread1.join();//线程会收到InterruptedException异常 并 从wait的阻塞中退出，所以可以不用调用notify来唤醒线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+" thread  after "+thread1.getName()+" join() ");
    }

    public void canDo(){
        InnerClass innerClass = new InnerClass();
        innerClass.add();
    }
    private class InnerClass{
        private void add(){
            int a,b;
            a = 2;
            b = 3;

            //hook


            Log.d("mtest","result="+(a+b));
        }
    }
}
