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

/**S
 * 刷新 集合列表
 * 2016/4/21
 */
public class ShuaxinFragment extends ListFragment {
    private List<String> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("ShuaxinFragment");
        list = new ArrayList<String>();
        list.add("自定义上下拉刷新类-MywipeFefreshLayout ");
        list.add("旧版本的上下拉刷新-ReFreshDraLayoutFragment ");
        list.add("网上开源的上下拉刷新-RescyViewFragment ");
        list.add("RescyView上下拉刷新神器adapter-RescyViewFragment ");
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
                transaction.replace(R.id.main_central_layout, new MySwipeResfreshFragment()).addToBackStack(null).commit();
                break;
            case 1:
                transaction.replace(R.id.main_central_layout, new ReFreshDraLayoutFragment()).addToBackStack(null).commit();
                break;
            case 2:
                transaction.replace(R.id.main_central_layout, new RescyViewFragment()).addToBackStack(null).commit();
                break;
            case 3:
                transaction.replace(R.id.main_central_layout, new RescyViewFragment()).addToBackStack(null).commit();
                break;
        }

    }
}
