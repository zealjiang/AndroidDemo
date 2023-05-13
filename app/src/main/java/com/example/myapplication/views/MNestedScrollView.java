package com.example.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class MNestedScrollView extends NestedScrollView {

    public MNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (true) {
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            Log.d("mtest","onMeasure ="+heightMode);
            if (heightMode != 0) {
                if (this.getChildCount() > 0) {
                    View child = this.getChildAt(0);
                    LayoutParams lp = (LayoutParams)child.getLayoutParams();
                    int childSize = child.getMeasuredHeight();
                    Log.d("mtest","child.getMeasuredHeight() ="+child.getMeasuredHeight());
                    int parentSpace = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom() - lp.topMargin - lp.bottomMargin;
                    Log.d("mtest","this.getMeasuredHeight() ="+this.getMeasuredHeight()+"  this.getPaddingTop() ="+this.getPaddingTop()+"  lp.width ="+lp.width+"  lp.height ="+lp.height);
                    if (childSize < parentSpace) {
                        int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, this.getPaddingLeft() + this.getPaddingRight() + lp.leftMargin + lp.rightMargin, lp.width);
                        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(parentSpace, MeasureSpec.EXACTLY);

                        int childMeasureW = MeasureSpec.getSize(childWidthMeasureSpec);
                        Log.d("mtest","childMeasureW ="+childMeasureW);
                        int childMeasureH = MeasureSpec.getSize(childHeightMeasureSpec);
                        Log.d("mtest","childMeasureH ="+childMeasureH);

                        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                        Log.d("mtest","child.getMeasuredWidth() ="+child.getMeasuredWidth()+"  child.getMeasuredHeight() ="+child.getMeasuredHeight());
                    }
                }
            }
        }
    }
}
