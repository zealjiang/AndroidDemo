package com.example.libjava;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class JavaReferenceTest {

    public static void main(String[] args) {
        JavaReferenceTest bean = new JavaReferenceTest();
        bean.weakReferenceTest();
    }
    private static String TAG = "JavaReferenceTest";
    public void weakReferenceTest() {
        A a = new A();
        ReferenceQueue referenceQueue = new ReferenceQueue<A>();
        WeakReference weakReference = new WeakReference<A>(a, referenceQueue);

        System.gc();
        try {
            Thread.currentThread().sleep(1);
        } catch (Exception e) {

        }

        Object referenceThread = weakReference.get();
        System.out.println(TAG+ "1 referenceQueue = "+referenceQueue.poll() +
                " a ="+ a +
                " referenceThread ="+referenceThread  +
                " weakReference =" +weakReference);

        a = null;
        referenceThread = null;
        System.gc();
        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        referenceThread = weakReference.get();
        System.out.println(TAG+ "2 referenceQueue = "+referenceQueue.poll() +
                " a ="+ a +
                " referenceThread ="+referenceThread  +
                " weakReference =" +weakReference);



/*        A a = new A();

        ReferenceQueue<A> rq = new ReferenceQueue<A>();
        WeakReference<A> wrA = new WeakReference<A>(a, rq);

        System.out.println("引用对象:" + wrA);

        a = null;

        if (wrA.get() == null) {
            System.out.println("a对象进入垃圾回收流程");
        } else {
            System.out.println("a对象尚未进入垃圾回收流程" + wrA.get());
        }

        // 通知系统进行垃圾回收
        System.gc();

        try {
            Thread.currentThread().sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (wrA.get() == null) {
            System.out.println("a对象进入垃圾回收流程");
        } else {
            System.out.println("a对象尚未进入垃圾回收流程" + wrA.get());
        }

        System.out.println("引用对象:" + rq.poll());*/


    }


    static class A {

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("in A finalize");
        }

    }
}
