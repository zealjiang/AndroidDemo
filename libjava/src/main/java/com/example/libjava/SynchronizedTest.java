package com.example.libjava;

public class SynchronizedTest {

    private String name;
    private int age;

    public void setName(String name) {
        synchronized (SynchronizedTest.class) { //类锁
            System.out.println("setName "+System.currentTimeMillis());
            this.name = name;
        }
    }

    public void setAge(int age) {
        synchronized (SynchronizedTest.class) { //类锁
            System.out.println("setAge  "+System.currentTimeMillis());
            try {
                Thread.sleep(3000);
            } catch (Exception e) {}
            this.age = age;
        }
    }

    public static void main(String[] args) {
        SynchronizedTest bean = new SynchronizedTest();
        Thread a = new Thread() {
            public void run() {
                super.run();
                bean.setAge(11);
            }
        };
        a.start();

        Thread b = new Thread() {
            public void run() {
                super.run();
                bean.setName("a");
            }
        };
        b.start();
    }
}
