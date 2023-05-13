package com.example.myapplication.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import com.example.myapplication.R;

public class Frame3DActivity extends Activity {

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glView = new GLSurfaceView(this);
        MyRenderer renderer = new MyRenderer();
        glView.setRenderer(renderer);
        setContentView(glView);
    }*/

    private TestGlSurfaceView mGlSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.test_gl_surfaceview_activity);
        //mGlSurfaceView = (TestGlSurfaceView) findViewById(R.id.gl_surface_view);

        TestGlSurfaceView mGlSurfaceView = new TestGlSurfaceView(this);
        TestRender render = new TestRender();
        //为GlSurfaceView设置渲染器Render
        //这一步很关键，当设置渲染器后，会触发GlThread线程启动，然后创建EGL环境，
        // 并在线程中根据条件判断，来对应调用Render的各个接口
        mGlSurfaceView.setRenderer(render);
        setContentView(mGlSurfaceView);
    }

}
