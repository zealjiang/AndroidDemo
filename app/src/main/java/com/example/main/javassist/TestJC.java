package com.example.main.javassist;

import javassist.ClassPool;
import javassist.CtClass;

public class TestJC {

    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.example.main.javassist.Student");
        cc.setSuperclass(pool.get("com.example.main.javassist.TestJC"));
        cc.writeFile();
        cc.toBytecode();
    }
}
