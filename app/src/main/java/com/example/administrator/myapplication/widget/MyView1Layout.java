package com.example.administrator.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.administrator.myapplication.R;


/**
 * android  自定义View
 */
public class MyView1Layout extends View {
    private final String TAG = this.getClass().getSimpleName();
    private float unitage;//刻度的基本单位
    private Paint mPaint_black;
    private Paint mPaint_while;
    private RectF mRectf;
    private Paint mPaint_red;
    int width;
    int height;
    public float textSize;//默认文字的大小
    private ViewGroup.LayoutParams params;

    public MyView1Layout(Context context) {
        this(context, null);
        init();
    }

    public MyView1Layout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public MyView1Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        unitage = getResources().getDimension(R.dimen.dp10);
        //默认初始化文字大小
        textSize = 6f * unitage;
        //初始黑色笔
        mPaint_black = new Paint();
        //设置抗锯齿，优化绘制效果的精细度
        mPaint_black.setAntiAlias(true);
        //设置图像抖动处理,也是用于优化图像的显示效果
        mPaint_black.setDither(true);
        //设置画笔的颜色
        mPaint_black.setColor(Color.GRAY);
        //设置画笔的风格为空心
        mPaint_black.setStyle(Paint.Style.STROKE);
        //设置“空心”的外框宽度为2dp
        mPaint_black.setStrokeWidth(unitage * 0.6f);

        //初始白色笔
        mPaint_while = new Paint();
        mPaint_while.setAntiAlias(true);
        mPaint_while.setStyle(Paint.Style.STROKE);
        mPaint_while.setStrokeWidth(unitage * 0.6f);
        mPaint_while.setColor(Color.BLUE);
        mPaint_while.setDither(true);

        //初始字体颜色笔
        mPaint_red = new Paint();
        mPaint_red.setAntiAlias(true);
        mPaint_red.setStyle(Paint.Style.FILL);
        mPaint_red.setStrokeWidth(unitage * 0.2f);
        mPaint_red.setDither(true);
        //设置文本的字号大小
        mPaint_red.setTextSize(textSize);
        //设置文本的对其方式为水平居中
        mPaint_red.setTextAlign(Paint.Align.CENTER);
        mPaint_red.setColor(Color.RED);
        //初始化圆弧所需条件（及设置圆弧的外接矩形的四边）
        mRectf = new RectF();
//        mRectf.set(unitage * 0.5f, unitage * 0.5f, unitage * 18.5f, unitage * 18.5f);

        //设置整个控件的宽高配置参数
//        setLayoutParams(new ViewGroup.LayoutParams((int) (unitage * 19.5f), (int) (unitage * 19.5f)));
        params = new ViewGroup.LayoutParams((int) (width + 20), (int) (width + 20));
        setLayoutParams(params);

        //获取该view的视图树观察者并添加绘制变化监听者
        //实现有绘制变化时的回调方法
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //2.开启子线程对绘制用到的数据进行修改
                if (enAutoAdd) {
                    new DrawThread();
                }
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
    }

    private float score = 0; //进度
    private boolean enAutoAdd = false; //是否允许自增

    public void setScore(float score) {
        this.score = score;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mPaint_red.setTextSize(textSize);
    }

    public void setEnableAutoAdd(boolean enAutoAdd) {
        this.enAutoAdd = enAutoAdd;
    }

    public class DrawThread implements Runnable {
        //2.开启子线程,并通过绘制监听实时更新绘制数据
        private final Thread mDrawThread;
        private int statek;

        public DrawThread() {
            mDrawThread = new Thread(this);
            mDrawThread.start();
        }

        @Override
        public void run() {
            while (true) {
                switch (statek) {
                    case 0://给一点点缓冲的时间
                        try {
                            Thread.sleep(500);
                            statek = 1;
                        } catch (InterruptedException e) {
                        }
                        break;
                    case 1:
                        try {//更新显示的数据
                            Thread.sleep(200);
                            score += 10f;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (score >= 360.0f)//满足该条件就结束循环
                    break;
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置矩阵的大小
        mRectf.set(unitage * 0.5f, unitage * 0.5f, width - unitage * 0.5f, width - unitage * 0.5f);
        params.width = width + 20;
        params.height = width + 20;
        //绘制弧形
        //黑笔画的是一个整圆所有从哪里开始都一样
        canvas.drawArc(mRectf, 0, 360, false, mPaint_black);
        //白笔之所以从-90度开始，是因为0度其实使我们的3点钟的位置，所以-90才是我们的0点的位置
        canvas.drawArc(mRectf, -90, score, false, mPaint_while);
        //绘制文本
        canvas.drawText(score + "", (width) / 2, (width) / 2, mPaint_red);
        //到此整个自定义View就已经写完了
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) (unitage * 18.5f);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (unitage * 18.5f);
        }
        setMeasuredDimension(width, height);
    }
}
