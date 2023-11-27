package com.example.main;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CostTime {


        public static void main(String[] args) {
            redefinePersonClass();
        }

        private static void redefinePersonClass() {
            String className = "com.lijiankun24.asmpractice.demo.HelloWorld";
            try {
                InputStream inputStream = new FileInputStream("/Users/lijiankun/Desktop/HelloWorld.class");
                ClassReader reader = new ClassReader(inputStream);                               // 1. 创建 ClassReader 读入 .class 文件到内存中
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);                 // 2. 创建 ClassWriter 对象，将操作之后的字节码的字节数组回写
                ClassVisitor change = new ChangeVisitor(writer);                                        // 3. 创建自定义的 ClassVisitor 对象
                reader.accept(change, ClassReader.EXPAND_FRAMES);                                       // 4. 将 ClassVisitor 对象传入 ClassReader 中

/*                Class clazz = new MyClassLoader().defineClass(className, writer.toByteArray());
                Object personObj = clazz.newInstance();
                Method nameMethod = clazz.getDeclaredMethod("sayHello", null);
                nameMethod.invoke(personObj, null);
                System.out.println("Success!");
                byte[] code = writer.toByteArray();                                                               // 获取修改后的 class 文件对应的字节数组
                try {
                    FileOutputStream fos = new FileOutputStream("/Users/lijiankun/Desktop/HelloWorld2.class");    // 将二进制流写到本地磁盘上
                    fos.write(code);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failure!");
            }
        }

        static class ChangeVisitor extends ClassVisitor {

            ChangeVisitor(ClassVisitor classVisitor) {
                super(Opcodes.ASM5, classVisitor);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
                if (name.equals("<init>")) {
                    return methodVisitor;
                }
                return new ChangeAdapter(Opcodes.ASM4, methodVisitor, access, name, desc);
            }
        }

        static class ChangeAdapter extends AdviceAdapter {
            private int startTimeId = -1;

            private String methodName = null;

            ChangeAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
                super(api, mv, access, name, desc);
                methodName = name;
            }

            @Override
            protected void onMethodEnter() {
                super.onMethodEnter();
                startTimeId = newLocal(Type.LONG_TYPE);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitIntInsn(LSTORE, startTimeId);
            }

            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode);
                int durationId = newLocal(Type.LONG_TYPE);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitVarInsn(LLOAD, startTimeId);
                mv.visitInsn(LSUB);
                mv.visitVarInsn(LSTORE, durationId);
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("The cost time of " + methodName + " is ");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitVarInsn(LLOAD, durationId);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
        }
}
