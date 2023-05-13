package com.example.myapplication.javassist;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.Modifier;

public class JavassistCreateClass {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        // 创建teacher类
        CtClass teacherClass = pool.makeClass("com.example.myapplication.javassist.Teacher");
        // 设置为公有类
        teacherClass.setModifiers(Modifier.PUBLIC);
        // 获取String类型
        CtClass stringClass = pool.get("java.lang.String");
        // 获取list类型
        CtClass listClass = pool.get("java.util.List");
        // 获取学生的类型
        CtClass studentClass = pool.get("com.example.myapplication.javassist.Student");
        // 给teacher添加name属性
        CtField nameField = new CtField(stringClass, "name", teacherClass);
        nameField.setModifiers(Modifier.PUBLIC);
        teacherClass.addField(nameField);
        // 给teacher类添加students属性
        CtField studentList = new CtField(listClass, "students", teacherClass);
        studentList.setModifiers(Modifier.PUBLIC);
        teacherClass.addField(studentList);
        // 给teacher类添加无参构造方法
        CtConstructor ctConstructor = CtNewConstructor.make("public Teacher() { this.name =\"abc\";this.students = new java.util.ArrayList();}", teacherClass);
        teacherClass.addConstructor(ctConstructor);
        // 给teacher类添加addStudent方法
        CtMethod m = new CtMethod(CtClass.voidType, "addStudent", new CtClass[]{studentClass}, teacherClass);
        m.setModifiers(Modifier.PUBLIC);
        // 添加学生对象到students属性中, $1代表参数1
        m.setBody("this.students.add($1);");
        teacherClass.addMethod(m);
        // 给teacher类添加sayHello方法
        m = new CtMethod(CtClass.voidType, "sayHello", new CtClass[]{}, teacherClass);
        m.setModifiers(Modifier.PUBLIC);
        m.setBody("System.out.println(\"Hello, My name is \" + this.name);");
        m.insertAfter("System.out.println(\"I have \" + this.students.size() + \" students\");");
        teacherClass.addMethod(m);
        teacherClass.writeFile();


        // 加载修改后的类
        Class<?> cls = teacherClass.toClass();
        // 实例teacher对象
        Object obj = cls.newInstance();
        // 获取addStudent方法
        Method method = cls.getDeclaredMethod("addStudent", Student.class);
        method.invoke(obj,new Student(35,"leo"));
        method = cls.getDeclaredMethod("sayHello", new Class[]{});
        method.invoke(obj);
    }
}
