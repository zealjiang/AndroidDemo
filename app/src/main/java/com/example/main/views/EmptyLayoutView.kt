package com.example.main.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

open class EmptyLayoutView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    var mPaint: Paint?= null
    init {
        mPaint = Paint()
        mPaint?.color = Color.RED
        setWillNotDraw(false)
    }


    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas!!)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint!!)
        Log.d("EmptyLayoutView", "width ="+width.toFloat()+", height ="+height.toFloat())
    }
}