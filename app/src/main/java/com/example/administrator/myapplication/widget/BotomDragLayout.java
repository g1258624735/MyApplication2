package com.example.administrator.myapplication.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;


/**
 * 可以向上滑出的View
 */
public class BotomDragLayout extends RelativeLayout {
    private final String TAG = this.getClass().getSimpleName();
    private ViewDragHelper mViewDragHelper;
    /**
     * 菜单栏的状态
     */
    public  int STATE = 0;
    public static final int CLOSEING = 0;
    public static final int OPENED = 1;
    private View v_bottom;
    private int viewHeight = 0;
    /**
     * 滑动view 的顶部位置
     */
    private int mTop;
    /**
     * 滑动view 的滑动距离占自身高度的比例
     */
    private float mTopSize;
    private int viewMaxHeight;
    private View view_bg;

    public BotomDragLayout(Context context) {
        this(context, null);
    }

    public BotomDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BotomDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        v_bottom = findViewById(R.id.view_bootom);
        view_bg = findViewById(R.id.view_bg);
        viewHeight = v_bottom.getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidateOnAnimation();
//            postInvalidate();
        }
    }

    public void openCover() {
        STATE = BotomDragLayout.OPENED;
        mViewDragHelper.smoothSlideViewTo(v_bottom, 0, viewMaxHeight - viewHeight);
        postInvalidateOnAnimation();
    }

    public void closeCover() {
        STATE = BotomDragLayout.CLOSEING;
        mViewDragHelper.smoothSlideViewTo(v_bottom, 0, viewMaxHeight);
        postInvalidateOnAnimation();
    }

    private void init() {
        //指定好需要处理拖动的ViewGroup和回调 就可以开始使用了
        mViewDragHelper = ViewDragHelper.create(this, new DefaultDragHelper());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM);
    }

    private class DefaultDragHelper extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == v_bottom;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            mTop = top;
            mTopSize = (viewMaxHeight - top) / (viewHeight * 1.0f);
            Log.d(TAG, "onViewPositionChanged()--" + "left:" + left + ",top:" + top + ",dx:" + dx + ",dy:" + dy + "mTopSize--" + mTopSize);
            invalidate();
        }

        /**
         * 当captureview被捕获时回调
         *
         * @param capturedChild
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.d(TAG, "onViewCaptured()--:");
        }

        /**
         * 手指离开屏幕
         * 后续View 的坐标处理
         * 比如  滑到超过一半 直接滑到满屏 又或者滑到不到一半的时候
         * 还原坐标
         *
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.d(TAG, "onViewReleased()--xv:" + xvel + ",yv:" + yvel);
            //上拉
            if (mTopSize > 0.5) {
                openCover();

            } else {
                closeCover();

            }
            invalidate();
        }


        /**
         * 当触摸到边缘的时候会调用
         *
         * @param edgeFlags
         * @param pointerId
         */
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            Log.d(TAG, "onEdgeTouched()");
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            Log.d(TAG, "onEdgeLock()");
            return super.onEdgeLock(edgeFlags);
        }

        /**
         * 当触摸到边缘的时候会调用
         *
         * @param edgeFlags
         * @param pointerId 可以指定触摸边缘的子View
         */
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            Log.d(TAG, "onEdgeDragStarted()");
            mViewDragHelper.captureChildView(v_bottom, pointerId);
        }

        /**
         * 返回当前移动的View 的position
         *
         * @param index
         * @return
         */
        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        /**
         * 限制水平移动范围
         * Return the magnitude of a draggable child view's horizontal range of motion in pixels.
         * 似乎作用不大，其他情况只用于判断是否可以拖动
         * 具体返回值真正起作用在于{@link ViewDragHelper#smoothSlideViewTo(View, int, int)}
         *
         * @param child Child view to check
         * @return range of horizontal motion in pixels
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
//            Log.d(TAG, "getViewHorizontalDragRange():" + child.getClass().getSimpleName());
//            if (child == mCoverView) {
//                return 0;
//            }
            return super.getViewHorizontalDragRange(child);
        }

        /**
         * 垂直移动的范围
         *
         * @param child
         * @return
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return getHeight() - viewHeight;
        }

        /**
         * 限制子View水平拖拉操作。默认不支持水平操作，重写该方法提供新的水平坐标（根据提供的渴望的水平坐标）
         * 不重写就不会支持水平坐标变化
         *
         * @param child Child view being dragged
         * @param left  Attempted motion along the X axis
         * @param dx    Proposed change in position for left
         * @return The new clamped position for left
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal()--left:" + left + ",dx:" + dx);
            return super.clampViewPositionHorizontal(child, left, dx);
        }

        /**
         * 在指定的区域内
         * 垂直滑动
         *
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.d(TAG, "clampViewPositionVertical()--top:" + top + ",dy:" + dy);
            final int topBound = getPaddingTop() + viewMaxHeight - viewHeight;
            final int bottomBound = getHeight() - v_bottom.getPaddingBottom();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        //通过这个方法判断是否拦截 滑动事件
        boolean flag = mViewDragHelper.shouldInterceptTouchEvent(event);
        return flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //通过这个方法判断是否处理拦截的触摸事件
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

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
        viewHeight = v_bottom.getMeasuredHeight();
        viewMaxHeight = this.getMeasuredHeight();
        mTop = viewMaxHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("onLayout", l + "|" + t + "|" + r + "|" + b);
        v_bottom.layout(0, mTop, r, mTop + v_bottom.getMeasuredHeight());
        view_bg.layout(0, 0, r, this.getMeasuredHeight());
    }
}
