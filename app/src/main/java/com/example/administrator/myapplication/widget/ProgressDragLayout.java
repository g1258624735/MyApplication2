package com.example.administrator.myapplication.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


/**
 * 自定义progressBar
 */
public class ProgressDragLayout extends ViewGroup {
    private final String TAG = this.getClass().getSimpleName();
    /**
     * 滑动view 的滑动距离占自身高度的比例
     */
    private double mDrange = 0;
    private ProgressBar pb;
    private ImageView view_mid;
    private TextView view_top;
    private int viewWidth = 1080 - dip2px(getContext(), 40);
    private int size;

    public ProgressDragLayout(Context context) {
        this(context, null);
    }

    public ProgressDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view_top = (TextView) findViewById(R.id.view_top);
        view_mid = (ImageView) findViewById(R.id.view_mid);
        pb = (ProgressBar) findViewById(R.id.pb);
    }

    private int progressAdd = 0;
    private double drange_1 = 0;

    /**
     * RelativeLayout.LayoutParams params_top =  (RelativeLayout.LayoutParams)view_top.getLayoutParams();
     * RelativeLayout.LayoutParams params_mid =  (RelativeLayout.LayoutParams)view_mid.getLayoutParams();
     *
     * @param value
     */
    public void setValue(double value) {
        if(value<=0){
            mDrange = 0;
            pb.setProgress(0);
            ProgressDragLayout.this.requestLayout();
            ProgressDragLayout.this.invalidate();
            return;
        }
        progressAdd = 0;
        mDrange = 0;
        drange_1=0;
        int progress = (int) (value * 100);
        size = progress;
        double drange = (int) (value * viewWidth);
        drange_1 = drange / size;
        Log.e("size-------", size +"");
        mhander.sendEmptyMessageDelayed(view_top.hashCode(),20);
    }
    private Handler mhander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressAdd++;
            mDrange = mDrange +drange_1;
            pb.setProgress(progressAdd);
            view_top.setText(progressAdd + "%");
            ProgressDragLayout.this.requestLayout();
            ProgressDragLayout.this.invalidate();
            Log.e("tag", "progressAdd--" + progressAdd + "|mDrange--" + mDrange);
            if(progressAdd<size){
                mhander.sendEmptyMessageDelayed(view_top.hashCode(),20);
            }
        }
    };

    /**
     * 丈量所有控件的高度
     * 可以得到每个控件的最终高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.e("onLayout", l + "|" + t + "|" + r + "|" + b);
        view_top.layout((int) mDrange + dip2px(getContext(), 20) - view_top.getMeasuredWidth() / 2, t, (int) mDrange + view_top.getMeasuredWidth() / 2 + dip2px(getContext(), 20), view_top.getMeasuredHeight());
        view_mid.layout((int) mDrange + dip2px(getContext(), 20) - view_mid.getMeasuredWidth() / 2, view_top.getMeasuredHeight(), (int) mDrange + view_mid.getMeasuredWidth() / 2 + dip2px(getContext(), 20), view_top.getMeasuredHeight() + view_mid.getMeasuredHeight());
        pb.layout(dip2px(getContext(), 20) + l, view_top.getMeasuredHeight() + view_mid.getMeasuredHeight(), r - dip2px(getContext(), 20), view_top.getMeasuredHeight() + view_mid.getMeasuredHeight() + pb.getMeasuredHeight());
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
