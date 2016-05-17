package com.example.administrator.myapplication.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


/**
 * Created by shijian
 */
public class LoadMoreView extends LinearLayout{
	
	public static final int LOADMORE_STATUS_HIDE = 0;
	public static final int LOADMORE_STATUS_CONTENT = 1;
	public static final int LOADMORE_STATUS_LOADING = 2;
	public static final int LOADMORE_STATUS_FAIL = 3;
	public static final int LOADMORE_STATUS_END = 4;
	public static final int LOADMORE_STATUS_EMPTY = 5;
	
    public LoadMoreView(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	public LoadMoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mContext = context;
		init();
	}
	
	
	Context mContext;
	Handler mHandler = new Handler();
	
	View mViewContent;
	View mViewLoading;
	View mViewFail;
	View mViewEnd;
	View mViewEmpty;
	View mDiving;
	
	ImageView mImgLoading;
	
	OnLoadMoreListener onLoadMoreListener;
	
	private void init() {
    	View rootView = inflate(mContext, R.layout.layout_loadmore, this);
    	mDiving = rootView.findViewById(R.id.diving);
    	mViewContent = rootView.findViewById(R.id.linMoreContent);
    	mViewLoading = rootView.findViewById(R.id.linMoreLoading);
    	mViewFail = rootView.findViewById(R.id.linMoreFail);
    	mViewEnd = rootView.findViewById(R.id.linMoreEnd);
    	mViewEmpty = rootView.findViewById(R.id.linMoreEmpty); 
    	mImgLoading = (ImageView) rootView.findViewById(R.id.imgLoading);
    	
    	try {
        	((AnimationDrawable) mImgLoading.getDrawable()).start();
		} catch (Exception e) {
		}
    	
    	mViewContent.setOnClickListener(loadMoreClick);
    	mViewFail.setOnClickListener(loadMoreClick);
    	
    	changeStatus(LOADMORE_STATUS_HIDE);
    }
	
	public void setEmptyString(String empty){
		TextView text = (TextView) mViewEmpty.findViewById(R.id.textMoreEmpty);
		text.setText(empty);
	}
	
	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
		this.onLoadMoreListener = onLoadMoreListener;
	}
	
	private OnClickListener loadMoreClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			changeStatus(LOADMORE_STATUS_LOADING);
			if(view == mViewContent){
				if(onLoadMoreListener != null){
					onLoadMoreListener.loadNext();
				}
			}else if(view == mViewFail){
				if(onLoadMoreListener != null){
					onLoadMoreListener.retryLoad();
				}
			}
		}
	};
	
	public void changeStatus(int status){
		mViewContent.setVisibility(View.GONE);
		mViewLoading.setVisibility(View.GONE);
		mViewFail.setVisibility(View.GONE);
		mViewEnd.setVisibility(View.GONE);
		mViewEmpty.setVisibility(View.GONE);
		mDiving.setVisibility(View.GONE);
		switch (status) {
		case LOADMORE_STATUS_HIDE:
			mDiving.setVisibility(View.GONE);
			break;
		case LOADMORE_STATUS_CONTENT:
			mViewContent.setVisibility(View.VISIBLE);
			break;
		case LOADMORE_STATUS_LOADING:
			mViewLoading.setVisibility(View.VISIBLE);
			break;
		case LOADMORE_STATUS_FAIL:
			mViewFail.setVisibility(View.VISIBLE);
			break;
		case LOADMORE_STATUS_END:
			mViewEnd.setVisibility(View.VISIBLE);
			break;
		case LOADMORE_STATUS_EMPTY:
			mViewEmpty.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	
	public interface OnLoadMoreListener{
		public void loadNext();
		public void retryLoad();
	}
}
