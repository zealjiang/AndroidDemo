package com.example.main.javassist;

import android.content.Context;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class JavassistDemo {

    public static void main(String[] args) {
        a();
    }
    public static void a() {
/*
        try {
            ClassPool classPool = ClassPool.getDefault();
            // 必须将class文件放在这个工程编译后的class文件中，路径也对应起来
            CtClass ctClass = classPool.get("com.kwad.sdk.c.kwai.c");
            CtMethod newmethod = CtNewMethod.make("public void insertNew(String message) { if (this.i.e != null) {  } }",ctClass);
            ctClass.addMethod(newmethod);
            ctClass.writeFile();
        }catch (Exception e){
            System.out.println("kwai e ="+e.getMessage());
        }
*/



        try{
            // 类库池, jvm中所加载的class
            ClassPool pool = ClassPool.getDefault();
            // 获取指定的Student类
            CtClass ctClass = pool.get("com.kwad.sdk.c.kwai.c");
            // 获取h方法
            CtMethod ctMethod = ctClass.getDeclaredMethod("h");
            // 在方法的代码后追加 一段代码
            ctMethod.insertBefore("        if(this.i.e.getParent() != null){\n" +
                    "            ((android.view.ViewGroup)this.i.e.getParent()).removeView(this.i.e);\n" +
                    "        }");
            ctClass.writeFile();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("kwai e ="+e.getMessage());
        }

    }
}
