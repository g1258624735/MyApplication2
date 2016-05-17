package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.tools.DensityUtils;


/**
 * 多列adapter
 * @version v1.2
 * @author shijian
 * @param <T> 存储的实体类型
 */
public abstract class BaseGridAdapter<T> extends BaseAdapter<T, BaseGridViewHolder>{

	private int column = 2;
	
	private int divingColorRes;
	private int divingWidth;
	private int itemViewWidth;
	
	public BaseGridAdapter(Context mContext,int column){
		super(mContext);
		this.column = column;
		
		//默认是用2dp宽度，透明色分隔线
		setDivingStyle(DensityUtils.dip2px(mContext, 2),android.R.color.transparent);
		
		//默认使用屏幕宽度计算
		int screenWidth = DensityUtils.getDisplayWidth(mContext);
		calculateWidth(screenWidth);
	}
	
	public BaseGridAdapter(Context mContext,int column,int totalWidth){
		super(mContext);
		this.column = column;
		calculateWidth(totalWidth);
	}
	
	public void setDivingStyle(int divingWidth,int divingColorRes){
		this.divingWidth = divingWidth;
		this.divingColorRes = divingColorRes;
	}
	
	private void calculateWidth(int totalWidth){
		itemViewWidth = (totalWidth - divingWidth*(column-1))/column;
	}
	
	@Override
	public int getItemCount() {
		if(super.getItemCount() == 0)
			return 0;
		else{
			if(super.getItemCount() % column > 0){
				return super.getItemCount() / column + 1;
			}else{
				return super.getItemCount() / column;
			}
		}
	}

	@Override
	public void onBindViewHolder(final BaseGridViewHolder holder, int position) {
		int startIndex = position * column;
		for (int i = 0; i < column; i++) {
			int index = startIndex + i;
			View itemView = holder.baseLayout.findViewWithTag(i);
			if(getList().size() > index){
				itemView.setVisibility(View.VISIBLE);
				onBindView(itemView,itemViewWidth,index);
			}else{
				itemView.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public BaseGridViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_base_grid,null);
		BaseGridViewHolder holder = new BaseGridViewHolder(view,divingWidth,divingColorRes);
		for (int i = 0; i < column; i++) {
			View itemView = LayoutInflater.from(mContext).inflate(getItemViewLayoutID(),null);
			itemView.setTag(i);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.weight = 1;
			itemView.setLayoutParams(params);
			holder.baseLayout.addView(itemView);
			if(i < column-1){
				View lineView = new View(mContext);
				lineView.setTag("diving"+i);
				lineView.setLayoutParams(new LayoutParams(divingWidth, LayoutParams.MATCH_PARENT));
				lineView.setBackgroundResource(divingColorRes);
				holder.baseLayout.addView(lineView);
			}
		}
		return holder;
	}
	
	public abstract int getItemViewLayoutID();
	
	public abstract void onBindView(View view,int width,int position);
}

class BaseGridViewHolder extends ViewHolder{
	
	LinearLayout baseLayout;
	View divingLine;
	
	public BaseGridViewHolder(View view,int divingLineHeight,int divingColorRes){
		super(view);
		baseLayout = (LinearLayout) view.findViewById(R.id.baseLayout);
		divingLine = view.findViewById(R.id.divingLine);
		divingLine.setBackgroundResource(divingColorRes);
		LayoutParams params = (LayoutParams) divingLine.getLayoutParams();
		params.height = divingLineHeight;
		divingLine.setLayoutParams(params);
	}
}
