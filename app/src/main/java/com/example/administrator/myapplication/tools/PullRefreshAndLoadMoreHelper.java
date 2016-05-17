package com.example.administrator.myapplication.tools;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.BaseAdapter;
import com.example.administrator.myapplication.adapter.HeaderFooterAdapter;
import com.example.administrator.myapplication.widget.LoadMoreView;
import com.example.administrator.myapplication.widget.MultiStateView;

import java.util.List;

/**
 * 下拉刷新和加载更多帮助类
 * @author shijian
 * @email shijianit@163.com
 * @date 2015-12-28
 * @param <T>
 */
public class PullRefreshAndLoadMoreHelper<T extends BaseAdapter> {
	
	//临时存储正在加载第几页，成功后真正保存
	int tempPage = 1;
	
	//加载到第几页
	int mLoadPage = 1;
	
	//总页数
	int mTotalPage = 1;
	
	//是否正在加载
	boolean mLoading = false;
	
	//是否使用MultiStateView
	boolean mUseMultiStateView = false;
	
	//上下文
	Context mContext;
	
	//下拉刷新失败后的Toast提示
	Toast toast;
	
	//适配器
	T mAdapter;
	
	//头部尾部VIEW（会包装在mAdapter外面）
	HeaderFooterAdapter<T> mHeaderFooterAdapter;
	
	//RecyclerView
	RecyclerView mRecyclerView;
	
	//下拉刷新控件
	PtrFrameLayout mPtrFrame;
	
	//加载更多View
	LoadMoreView mLoadMoreView;
	
	//多态View控件
	MultiStateView mMultiStateView;
	
	//通知界面来进行获取数据的回调
	OnPullRefreshAndLoadMoreListener mListener;
	
	//RecyclerView滑动回调
	RecyclerView.OnScrollListener mOnScrollListener;
	
	/**
	 * 下拉刷新，加载更多，封装操作
	 * @param context 上下文
	 * @param ptrFrame 下拉刷新控件
	 * @param recyclerView  列表控件
	 * @param MultiStateView 多态View控件
	 * @param adapter 适配器
	 * @param listener 回调
	 */
	@SuppressWarnings("deprecation")
	public PullRefreshAndLoadMoreHelper(Context context,RecyclerView recyclerView,T adapter,OnPullRefreshAndLoadMoreListener listener){
		this.mContext = context;
		this.mRecyclerView = recyclerView;
		this.mAdapter = adapter;
		this.mListener = listener;
		this.toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		/**
		 * 设置线性布局
		 */
		final LinearLayoutManager linManager = new LinearLayoutManager(mContext);
		mRecyclerView.setLayoutManager(linManager);
		mRecyclerView.getWidth();
		/**
		 * 设置头部、尾部Adapter,包在自定义Adapter外面
		 */
		mHeaderFooterAdapter = new HeaderFooterAdapter<T>(adapter);
		
		/**
		 * 头部尾部Adapter更新
		 */
		adapter.setOnDataChangeListener(new BaseAdapter.OnDataChangeListener() {
			@Override
			public void OnDataChange() {
				mHeaderFooterAdapter.notifyDataSetChanged();
			}
		});
		
		/**
		 * 设置下滑加载更多
		 */
		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(mOnScrollListener != null){
                	mOnScrollListener.onScrolled(recyclerView, dx, dy);
                }
                int lastVisibleItem = linManager.findLastVisibleItemPosition();
                int totalItemCount = linManager.getItemCount();
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if(!mLoading && mLoadPage < mTotalPage){
                    	int page = mLoadPage+1;
                    	loadingStart(page);
                    }
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
            		int newState) {
            	super.onScrollStateChanged(recyclerView, newState);
            	if(mOnScrollListener != null){
                	mOnScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }
        });
		recyclerView.setAdapter(mHeaderFooterAdapter);
	}
	
	public void addPullToRefrensh(PtrFrameLayout ptrFrame){
		this.mPtrFrame = ptrFrame;
		/**
		 * 设置下拉刷新回调
		 */
		mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            	loadingStart(1);
            }
			@Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return mListener.checkCanDoRefresh() && PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }
        });
		mPtrFrame.setResistance(2);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1);
		mPtrFrame.setEnabledNextPtrAtOnce(true);
	}
	
	public void addLoadMoreView(){
		/**
		 * 添加尾部
		 */
		mLoadMoreView = new LoadMoreView(mContext);
		mLoadMoreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mHeaderFooterAdapter.addFooter(mLoadMoreView);
		/**
		 * 设置尾部点击事件
		 */
		mLoadMoreView.setOnLoadMoreListener(new LoadMoreView.OnLoadMoreListener() {
			@Override
			public void loadNext() {
				int page = mLoadPage+1;
				loadingStart(page);
			}

			@Override
			public void retryLoad() {
				mListener.loadListData(mLoadPage);
			}
		});
	}
	
	/**
	 * 获取加载更多View
	 * @return
	 */
	public LoadMoreView getLoadMoreView(){
		return mLoadMoreView;
	}
	
	public void resetView(){
		if(mMultiStateView != null){
			this.mUseMultiStateView = true;
		}
		mLoadPage = 1;
		tempPage = 1;
		mTotalPage = 1;
		mAdapter.clearList();
	}
	
	public void setFirstLoadingAndFailView(MultiStateView multiStateView){
		this.mUseMultiStateView = true;
		this.mMultiStateView = multiStateView;
		multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
	}
	
	public void setFirstLoadingAndFailViewDefault(MultiStateView multiStateView){
		this.mUseMultiStateView = true;
		this.mMultiStateView = multiStateView;
		multiStateView.setViewForState(R.layout.layout_view_loading, MultiStateView.VIEW_STATE_LOADING);
		multiStateView.setViewForState(R.layout.layout_view_error_img, MultiStateView.VIEW_STATE_ERROR);
		multiStateView.setViewForState(R.layout.layout_view_empty, MultiStateView.VIEW_STATE_EMPTY);
		multiStateView.getView(MultiStateView.VIEW_STATE_ERROR)
			.findViewById(R.id.retry).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					loadingStart(mLoadPage);
				}
			});
		multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
	}
	
	public void addHeader(View view){
		mHeaderFooterAdapter.addHeader(view);
	}
	
	public void addFooter(View view){
		mHeaderFooterAdapter.addFooter(view);
	}
	
	public void setOnScrollListener(RecyclerView.OnScrollListener listener){
		mOnScrollListener = listener;
	}
	
	public LinearLayoutManager getLayoutManager(){
		return (LinearLayoutManager) mRecyclerView.getLayoutManager();
	}
	
	/**
	 * 显示Loading操作，自动判断显示下拉Loading还是加载更多Loading
	 * @param page
	 */
	public void loadingStart(int page){
		mLoading = true;
		tempPage = page;
		if(page == 1){
			if(mUseMultiStateView && mMultiStateView != null){
				mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
			}else{
				if(mPtrFrame != null){
					mPtrFrame.post(new Runnable() {
			            @Override
			            public void run() {
			            	mPtrFrame.autoRefresh(true);
			            }
			        });
				}else{
					if(mLoadMoreView != null)
						mLoadMoreView.changeStatus(LoadMoreView.LOADMORE_STATUS_LOADING);
				}
			}
		}else{
			if(mLoadMoreView != null)
				mLoadMoreView.changeStatus(LoadMoreView.LOADMORE_STATUS_LOADING);
		}
		mListener.loadListData(tempPage);
	}
	/**
	 * Loading成功操作
	 */
	public void loadingSuccess(List list,int totalPage){
		mLoading = false;
		mTotalPage = totalPage;
		mLoadPage = tempPage;
		
		if(mLoadPage < mTotalPage){
			if(mLoadMoreView != null)
				mLoadMoreView.changeStatus(LoadMoreView.LOADMORE_STATUS_CONTENT);
		}else{
			if(mLoadMoreView != null)
				mLoadMoreView.changeStatus(LoadMoreView.LOADMORE_STATUS_END);
		}
		if(mLoadPage == 1){
			//如果加载的是第一页 ，并且数据为空的话，显示emptyView
			if(list.size() == 0){
				if(mUseMultiStateView && mMultiStateView != null){
					mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
				}
				if(mLoadMoreView != null)
					mLoadMoreView.changeStatus(LoadMoreView.LOADMORE_STATUS_EMPTY);
			}else{
				if(mUseMultiStateView && mMultiStateView != null){
					mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
					mUseMultiStateView = false;
				}
			}
			if(mPtrFrame != null){
				mPtrFrame.refreshComplete();
			}
			mAdapter.setList(list);
		}else{
			mAdapter.addList(list);
		}
	}
	
	/**
	 * Loading失败操作
	 * @param page
	 */
	public void loadingFail(String failMsg){
		mLoading = false;
		if(tempPage == 1){
			if(mPtrFrame != null){
				mPtrFrame.refreshComplete();
			}
			if(mUseMultiStateView && mMultiStateView != null){
				mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
				if(failMsg != null){
					TextView textView = (TextView) mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR)
							.findViewById(R.id.errorMsg);
					if(textView != null){
						textView.setText(failMsg);
					}else{
						toast.setText(failMsg);
						toast.show();
					}	
				}
			}else{
				if(mLoadMoreView != null){
					mLoadMoreView.changeStatus(LoadMoreView.LOADMORE_STATUS_FAIL);
				}else{
					toast.setText(failMsg);
					toast.show();
				}
			}
		}else{
			if(mLoadMoreView != null)
				mLoadMoreView.changeStatus(LoadMoreView.LOADMORE_STATUS_FAIL);
		}
	}
	
	public interface OnPullRefreshAndLoadMoreListener{
		public void loadListData(int page);
		public boolean checkCanDoRefresh();
	}

	public int getTotalPage() {
		return mTotalPage;
	}
	
}
