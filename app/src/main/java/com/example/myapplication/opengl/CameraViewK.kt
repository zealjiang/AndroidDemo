package com.example.myapplication.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.impl.PreviewConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat

class CameraViewK @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : GLSurfaceView(context, attrs) {

    private val TAG = "CameraViewK"
    init {
        initCameraX()
    }

    private fun initCameraX() {
/*        val builder = Preview.Builder()
        builder.setTargetResolution(Size(640, 480))
        //builder.setCameraSelector(CameraSelector.DEFAULT_FRONT_CAMERA);
        val previewConfig = PreviewConfig.Builder().build()
        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener
        run {
            { previewOutput -> `val` }
            parent = viewFinder.parent
            var ViewGroup: `as`
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)
            viewFinder.surfaceTexture = previewOutput.surfaceTexture
        }
        CameraX.bindToLifecycle(this, preview)*/
    }
}



