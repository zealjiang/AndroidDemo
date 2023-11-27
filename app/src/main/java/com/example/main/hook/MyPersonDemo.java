package com.example.main.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyPersonDemo {

    public static void main(String[] args) {
        MyPersonDemo demo = new MyPersonDemo();
        demo.aaa();
    }

    public void aaa(){
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        final Person leo = new Monkey();
        Person monkeyProxy = (Person)Proxy.newProxyInstance(leo.getClass().getClassLoader(), leo.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName()+",这个家伙进来了");

                if("setAge".equals(method.getName())){
                    System.out.println("神仙神仙，求求你把我变年轻");
                    args[0] = 50;
                }

                return method.invoke(leo,args);
            }
        });

        System.out.println("1 通过monkey对象调用print对象");
        leo.getAge();
        System.out.println("2 假设系统调用");
        leo.setAge(18);
        System.out.println("3 请问我现在多大了");
        leo.getAge();
        System.out.println("-----------------");
        System.out.println("4 替换了系统的调用");
        monkeyProxy.setAge(16);
        System.out.println("5 我再次变年轻了");
        monkeyProxy.getAge();
    }

    interface Person{
        void setAge(int age);
        int getAge();
    }

    class Monkey implements Person{
        int age;
        @Override
        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public int getAge() {
            System.out.println("我的年龄是 "+age);
            return age;
        }
    }
}
