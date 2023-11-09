package com.example.libjava;

public class InterruptExample implements Runnable {
   public void run() {
        try {
            System.out.println("线程开始执行");
            int i = 0;
            for(;;) {
                //这个过程中不能使用Thread.sleep,因为此方法会在收到中断后抛出异常
                System.out.println("执行第 " + i + " 次任务 isInterrupted ="+ Thread.currentThread().isInterrupted());
                i++;
                boolean isterrupted = Thread.currentThread().isInterrupted();//Thread.interrupted();//查看当前线程的中断标志位, 并清除中断标志位(interrupted status 置为false)
                if (isterrupted) {
                    System.out.println("1线程收到中断信号 isterrupted ="+isterrupted);
                    System.out.println("2线程收到中断信号 isterrupted ="+Thread.currentThread().isInterrupted());
                    throw new InterruptedException();
                }
            }
        } catch (Exception e) {
            System.out.println("线程被中断 "+Thread.currentThread().isInterrupted());
        } finally {
            System.out.println("线程结束执行");
        }
    }
 
    public static void main(String[] args) {
        Thread thread = new Thread(new InterruptExample());
        thread.start();
        try {
            Thread.sleep(1);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}