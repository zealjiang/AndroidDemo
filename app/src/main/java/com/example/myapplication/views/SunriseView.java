package com.example.myapplication.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.util.SizeUtil;

public class SunriseView extends View {
    private final String TAG = "SunriseView";
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Paint mSunRisePaint;
    /** 太阳光画笔 */
    private Paint mSunshinePaint;
    private String mPathBackgroundColor = "#999999";
    private String mPathProgressColor = "#FFFF00";
    private float mLineWidth;
    private float mStartX, mStartY;
    private float mEndX, mEndY;
    private float mCenterX, mCenterY;
    private PathMeasure mPathMeasure;
    private Path mSunPath;
    private Path mSunRisePath;
    private Path mSunshinePath;
    private float[] mSunPos;
    private float[] mSunTan;
    private Bitmap mSunBitmap;
    private float mSunBitmapWidth, mSunBitmapHeight;
    private float mAnimatedValue;
    private ValueAnimator mValueAnimator;
    private boolean mHasMeasured = false;
    private RectF mSunRectF;
    private RectF mSunshineRectF;


    private long mSunRiseTime;//太阳升起时间 单位:毫秒
    private long mSunsetTime;//太阳降落时间 单位:毫秒
    private float mProgress;
    private int mStartColor = Color.TRANSPARENT;
    private int mEndColor = Color.WHITE;
    /** 是否绘制太阳升起到当前位置的路径 */
    private boolean mIsDrawSunrisePath = false;


    public SunriseView(Context context) {
        this(context, null);
    }

    public SunriseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunriseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SunriseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "init");
        mContext = context;
        mLineWidth = SizeUtil.dp2px(1);

        mPaint = new Paint();
//        mPaint.setColor(Color.parseColor(mPathBackgroundColor));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setStyle(Paint.Style.STROKE);


        mSunRisePaint = new Paint();
        mSunRisePaint.setColor(Color.parseColor(mPathProgressColor));
        mSunRisePaint.setAntiAlias(true);
        mSunRisePaint.setStrokeWidth(mLineWidth);
        mSunRisePaint.setStyle(Paint.Style.STROKE);

        mSunshinePaint = new Paint();
        mSunshinePaint.setAntiAlias(true);
        mSunshinePaint.setStrokeWidth(mLineWidth);
        mSunshinePaint.setStyle(Paint.Style.FILL);

        //初始化路径测量器
        mPathMeasure = new PathMeasure();
        //初始化坐标数组
        mSunPos = new float[2];
        //初始化太阳路径
        mSunPath = new Path();
        //初始化太阳光路径
        mSunshinePath = new Path();
        mSunRisePath = new Path();
        //初始化太阳当前显示位置
        mSunRectF = new RectF();
        mSunshineRectF = new RectF();
        // 初始化图片
        mSunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
        mSunBitmapWidth = SizeUtil.dp2px(22);
        mSunBitmapHeight = SizeUtil.dp2px(22);

        // 初始化动画
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(600);
        mValueAnimator.setInterpolator(new AccelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mProgress = 0.6f;
    }

    private void setData(long sunRiseTime, long sunsetTime) {
        long curTimeMills = System.currentTimeMillis();
        if (curTimeMills < sunRiseTime || curTimeMills > sunsetTime) {

        } else {

        }
    }

    public void runAnim() {
        mValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mStartX = mSunBitmapWidth/2;//mWidth * 0.1f;
        mStartY = mHeight - mSunBitmapWidth/2;//mHeight * 2 / 3.0f;
        mEndX = mWidth - mSunBitmapWidth/2;//mWidth * 0.9f;
        mEndY = mStartY;
        mCenterX = mWidth / 2;
        mCenterY = -mHeight /3.0f;//0f;//mHeight /3.0f;
        Log.d(TAG, "onMeasure  mWidth ="+mWidth+"  mHeight ="+mHeight);
//        Log.d(TAG, "onMeasure  mStartX ="+mStartX+"  mStartY ="+mStartY);
//        Log.d(TAG, "onMeasure  mEndX ="+mEndX+"  mEndY ="+mEndY);
        //setMeasuredDimension();

        //设置路径渐变色
        Shader pathShader = new LinearGradient(
                mStartX,
                mStartY,
                mEndX,
                mEndY,
                new int[]{mStartColor, mEndColor, mStartColor},
                new float[]{0f, 0.5f, 1f},
                Shader.TileMode.CLAMP
        );
        mPaint.clearShadowLayer();
        mPaint.setShader(pathShader);


        //阳光shader
        Shader sunshineShader = new LinearGradient(
                0,
                0,
                0,
                mHeight,
                Color.parseColor("#FFFF00"),
                Color.parseColor("#20FF6600"),
                Shader.TileMode.CLAMP
        );
        mSunshinePaint.clearShadowLayer();
        mSunshinePaint.setShader(sunshineShader);


        //设置太阳升起和日落的路径
        mSunPath.moveTo(mStartX, mStartY);
        mSunPath.quadTo(mCenterX, mCenterY, mEndX, mEndY);

        //阳光路径
        mSunshinePath.moveTo(mStartX, mStartY);
        mSunshinePath.quadTo(mCenterX, mCenterY, mEndX, mEndY);
        mSunshinePath.close();//封闭路径

        if (!mHasMeasured) {
            mHasMeasured = true;
            mValueAnimator.start();
        }


    }

    private void drawSunRise() {

    }

    boolean hasDrawBack;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制太阳升起和日落的路径
        canvas.drawPath(mSunPath, mPaint);

//        canvas.drawCircle(mStartX, mStartY, 10, mPaint);
//        canvas.drawCircle(mEndX, mEndY, 10, mPaint);
//        canvas.drawCircle(mCenterX, mCenterY, 10, mPaint);

        mPathMeasure.setPath(mSunPath, false);

        float pathLength = mPathMeasure.getLength();
        Log.d(TAG, "onDraw  pathLength ="+pathLength);

        if (pathLength > 0) {
            //获取当前位置的太阳路径
            float distance = pathLength * mAnimatedValue * mProgress;
            boolean isSuc = mPathMeasure.getPosTan(distance, mSunPos, null);
            if (isSuc) {
                mSunRectF.set((int) mSunPos[0] - mSunBitmapWidth / 2, (int) mSunPos[1] - mSunBitmapWidth / 2, (int) mSunPos[0] + mSunBitmapHeight / 2, (int) mSunPos[1] + mSunBitmapHeight / 2);

                mSunshineRectF.set(mStartX, 0, mSunPos[0], mStartY);
            }

            //绘制太阳上升的金色路径
            if (mIsDrawSunrisePath) {
                mSunRisePath.reset();
                isSuc = mPathMeasure.getSegment(0, distance, mSunRisePath, true);//将路径的一部分复制到目标路径中
                if (isSuc) {
                    //绘制太阳升起的金色路径
                    canvas.drawPath(mSunRisePath, mSunRisePaint);
                }
            }

            //绘制太阳光
            canvas.save();
            canvas.clipRect(mSunshineRectF); // 限制绘制区域为从顶部到height/2
            canvas.drawPath(mSunshinePath, mSunshinePaint);
            canvas.restore();

            //绘制太阳
            canvas.drawBitmap(mSunBitmap, null, mSunRectF, null);


            //test
            //int size = 100;
            //canvas.drawRect(new Rect((int) mSunPos[0] - size/2, (int) mSunPos[1] - size/2, (int) mSunPos[0] + size/2, (int) mSunPos[1] + size/2), mSunPaint);
        }
    }
}
