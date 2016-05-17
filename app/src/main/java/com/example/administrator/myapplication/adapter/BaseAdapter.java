package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author work
 *
 * @param <T> 列表项实体
 * @param <K> ViewHolder的子类
 */
public abstract class BaseAdapter<T,K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {

	private List<T> list = new ArrayList<T>();
	
	OnDataChangeListener onDataChangeListener;
	
	protected Context mContext;
	
	public BaseAdapter(Context mContext){
		this.mContext = mContext;
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list){
		this.list = list;
		dataSetChange();
	}
	
	public void addList(List<T> list){
		this.list.addAll(list);
		dataSetChange();
	}
	
	public void clearList(){
		this.list.clear();
		dataSetChange();
	}
	
	@Override
	public int getItemCount() {
		return list.size();
	}
	
	public void setOnDataChangeListener(OnDataChangeListener listener){
		this.onDataChangeListener = listener;
	}
	
	public void dataSetChange(){
		notifyDataSetChanged();
		if(onDataChangeListener != null){
			onDataChangeListener.OnDataChange();
		}
	}

	public interface OnDataChangeListener {
		public void OnDataChange();
	}
}
