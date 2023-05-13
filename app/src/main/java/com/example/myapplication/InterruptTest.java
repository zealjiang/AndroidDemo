package com.example.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class InterruptTest {
    public static void main(String[] args) {
        InterruptTest waitTest = new InterruptTest();
        waitTest.interruptTest();
    }

    private void interruptTest(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        final Object obj = new Object();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int G = 0;
                Random random = new Random();
                while (true){
                    G++;
                    //System.out.println(df.format(new Date())+"  "+"thread = "+Thread.currentThread().getName()+"  G = "+G+"  isInterrupted = "+Thread.currentThread().isInterrupted());

                    int n = random.nextInt(9999999);
                    System.out.println(df.format(new Date())+"  "+" thread = "+Thread.currentThread().getName()+"random :" + n +"  G = "+G+"  isInterrupted = "+Thread.currentThread().isInterrupted());

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

                    if(G > 80)break;
                    while (true) {
                        n--;
                        if(n == 0) {
                            System.out.println(df.format(new Date())+"  "+"G :" + G);
                            break;
                        }
                    }

/*                    System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+"*********break 跳出循环 退出线程**********");
                    break;*/


                }
                System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+" out while end run***");
            }
        });

        System.out.println(df.format(new Date())+"  "+thread1.getName()+" start()  isInterrupted() = "+thread1.isInterrupted());
        thread1.start();
        try {
            System.out.println(df.format(new Date())+"  "+Thread.currentThread().getName()+" thread sleep() 2秒  ");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(df.format(new Date())+"  "+"after "+Thread.currentThread().getName()+" sleep()  "+thread1.getName()+" interrupt() ");
        thread1.interrupt();//线程会收到InterruptedException异常 并 从wait的阻塞中退出，所以可以不用调用notify来唤醒线程
        System.out.println(df.format(new Date())+"  "+"after "+thread1.getName()+" interrupt()  isInterrupted() = "+thread1.isInterrupted());

/*        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
/*        synchronized (obj){
            System.out.println(df.format(new Date())+"  "+"notify thread = "+thread1.getName());
            obj.notify();
        }*/
    }
}
