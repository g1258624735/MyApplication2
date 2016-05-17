# MyApplication2
Viewdraghelper 测试demo ,欢迎下载！
简介： 一般我们在自定义ViewGroup 的时候会通常都会用到onInterceptTouchEvent ，onTouchEvent 
这些方法去进行距离的判断然后利用scroller 去进行目标的移动，从而实现ViewGroup 的自定义。
此方法不但判断麻烦，而且逻辑复杂，不易操作，今天给大家要价讲的这个工具ViewDragHelper
是谷歌IO大会上推出的触摸辅助开发工具，极大的简化了开发自定义VIewGroup 的难度。 

1.废话不多说 ，首先要初始化 ViewDragHelper 这个工具类：


  final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        //指定好需要处理拖动的ViewGroup和回调 就可以开始使用了
        mViewDragHelper = ViewDragHelper.create(this, new DefaultDragHelper());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        //设置minVelocity
        mViewDragHelper.setMinVelocity(minVel);

2.初始化 ViewDragHelper.Callback 这个触摸操作类：并初始化 要操作VIew的位置控制方法回掉类：


onViewPositionChanged（）
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            mLeft = left;
            mLeftSize =(-left)/ (dip2px(getContext(),80) * 1.0f);
            Log.d(TAG, "onViewPositionChanged()--" + "left:" + left + ",top:" + top + ",dx:" + dx + ",dy:" + dy + "mLeftSize--" + mLeftSize);
            invalidate();
        }

3.接下来我们会操作要移动VIew 的滑动限制距离 方法：


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
            final int leftBound =v_content.getPaddingLeft()-dip2px(getContext(),80);
            final int rightBound =v_content.getPaddingRight();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            Log.d(TAG, "clampViewPositionHorizontal()--left:" + left + ",dx:" + dx+"v_content.getPaddingLeft(),"+v_content.getPaddingLeft());
            return newLeft;
        }
    }

限制View活动的范围后 ，但我们释放手指的时候我们会操作另外一个方法:
    /**
         * 手指离开屏幕
         * 后续View 的坐标处理
         * 比如  滑到超过一半 直接滑到满屏 又或者滑到不到一半的时候
         * 还原坐标
         *
         * @param releasedChild
         * @param xvel
         * @param yvel
        * */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.d(TAG, "onViewReleased()--xv:" + xvel + ",yv:" + yvel);
            //上拉
            if (mLeftSize > 0.5) {
                openCover();
            } else {
                closeCover();
            }
            invalidate();
        }

这样一个基本的VIew滑动类就基本实现了。 
