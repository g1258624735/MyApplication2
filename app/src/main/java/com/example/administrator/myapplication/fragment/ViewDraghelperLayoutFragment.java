package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewDraghelperLayoutFragment
 * 各种策划效果
 * 主要是利用 VieDragHelper 这个控件来实现 自定义的
 * 2016/4/21
 */
public class ViewDraghelperLayoutFragment extends ListFragment {
    private List<String> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("ViewDraghelperLayoutFragment");
        list = new ArrayList<String>();
        list.add("左边滑出菜单-LeftDragLayoutFragment");
        list.add("底部滑出菜单-BottomDraLayoutFragment");
        list.add("HongYangLeftDragLayoutFragment");
        list.add("右边滑出菜单-RightDragLayoutFragment");
        list.add("顶部滑出菜单-TopDraLayoutFragment");
        list.add("仿苹果左滑删除效果-LeftScrollDeleteLayoutFragment");
        list.add("自定义下拉刷新控件-ReFreshDraLayoutFragment");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        getListView().setAdapter(adapter);
        setListShown(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.replace(R.id.main_central_layout, new LeftDragLayoutFragment()).addToBackStack(null).commit();
                break;
            case 1:
                transaction.replace(R.id.main_central_layout, new BottomDraLayoutFragment()).addToBackStack(null).commit();
                break;
            case 2:
                transaction.replace(R.id.main_central_layout, new HongYangLeftDragLayoutFragment()).addToBackStack(null).commit();
                break;
            case 3:
                transaction.replace(R.id.main_central_layout, new RightDragLayoutFragment()).addToBackStack(null).commit();
                break;
            case 4:
                transaction.replace(R.id.main_central_layout, new TopDraLayoutFragment()).addToBackStack(null).commit();
                break;
            case 5:
                transaction.replace(R.id.main_central_layout, new LeftScrollDeleteLayoutFragment()).addToBackStack(null).commit();
                break;
            case 6:
                transaction.replace(R.id.main_central_layout, new ReFreshDraLayoutFragment()).addToBackStack(null).commit();
                break;
        }

    }
}
