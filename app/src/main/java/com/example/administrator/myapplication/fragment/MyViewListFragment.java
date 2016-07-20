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
 * MyViewListFragment
 * 主要是利用 继承view 来自定义view
 * 是单一控件
 * 2016/4/21
 */
public class MyViewListFragment extends ListFragment {
    private List<String> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("MyViewListFragment");
        list = new ArrayList<String>();
        list.add("自定义圆形-MyViewFragment");
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
                transaction.replace(R.id.main_central_layout, new MyViewFragment()).addToBackStack(null).commit();
                break;
        }

    }
}
