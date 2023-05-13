package com.example.myapplication.javassist;

import javassist.ClassPool;
import javassist.CtClass;

public class TestJC {

    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.example.myapplication.javassist.Student");
        cc.setSuperclass(pool.get("com.example.myapplication.javassist.TestJC"));
        cc.writeFile();
        cc.toBytecode();
    }
}
