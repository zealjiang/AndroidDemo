package com.example.myapplication.opengl;

import android.opengl.GLSurfaceView;
import android.os.Build;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer {

    int one = 0x10000;
    private float triRotate;
    private float quaterRotate;
    // 三角形三个顶点:上顶点、左下点、右下点
    private IntBuffer triBuffer = IntBuffer.wrap(new int[] { 0, one, 0, -one, -one, 0, one, -one, 0,});
    private IntBuffer quaterBuffer = IntBuffer.wrap(new int[] { one, one, 0, -one, one, 0, one, -one, 0, -one, -one, 0 });
    // 三角形的顶点颜色值(r,g,b,a)
    private IntBuffer colorBuffer = IntBuffer.wrap(new int[] { one, 0, 0, one, 0, one, 0, one, 0, 0, one, one});

    @Override
    public void onDrawFrame(GL10 gl) {
        //可以在这个方法中绘制2D或3D图形
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);// 清除屏幕和深度缓存

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// 允许设置顶点

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//设置颜色数组，开启颜色渲染功能


        ByteBuffer bb = ByteBuffer.allocateDirect(colorBuffer.array().length * 4);
        bb.order(ByteOrder.nativeOrder());
        IntBuffer _colorBuffer = bb.asIntBuffer();
        _colorBuffer.put(colorBuffer);
        _colorBuffer.position(0);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, _colorBuffer);

        gl.glLoadIdentity();// 重置当前的模型观察矩阵

        gl.glTranslatef(-1.5f, 0.0f, -6.0f);// 左移 1.5 单位，并移入屏幕 6.0

        gl.glRotatef(triRotate, 0.0f, 1.0f, 0.0f); //设置旋转,应该在坐标确定后再旋转

        gl.glVertexPointer(3, GL10.GL_FIXED, 0, triBuffer);// 设置三角形

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);// 绘制三角形

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        gl.glColor4f(1.0f, 0.0f, 0.5f, 1.0f);

        gl.glLoadIdentity();// 重置当前的模型观察矩阵

        gl.glTranslatef(1.5f, 0.0f, -6.0f);

        gl.glRotatef(quaterRotate, 1.0f, 0.0f, 0.0f); //设置旋转

        gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);



        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);// 取消顶点设置

        //改变旋转角度

        triRotate += 0.5f;

        quaterRotate -= 0.5f;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //可以在这个方法中设置场景的大小
        float ratio = (float) width / height;

        gl.glViewport(0, 0, width, height);// 设置OpenGL场景的大小

        gl.glMatrixMode(GL10.GL_PROJECTION);// 设置投影矩阵

        gl.glLoadIdentity();// 重置投影矩阵

        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);// 设置视口的大小

        gl.glMatrixMode(GL10.GL_MODELVIEW);// 选择模型观察矩阵

        gl.glLoadIdentity();// 重置模型观察矩阵

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //可以在这个方法中做一些初始化工作，比如设置背景颜色、启动平滑模型等
        gl.glShadeModel(GL10.GL_SMOOTH);// 启用阴影平滑

        gl.glClearColor(0, 0.5f, 0.5f, 0.5f);// 黑色背景

        gl.glClearDepthf(1.0f);// 设置深度缓存

        gl.glEnable(GL10.GL_DEPTH_TEST);// 启用深度测试

        gl.glDepthFunc(GL10.GL_LEQUAL);// 所作深度测试的类型

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        // 告诉系统对透视进行修正
    }
}
