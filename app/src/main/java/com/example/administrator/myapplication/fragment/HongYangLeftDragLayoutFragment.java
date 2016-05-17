package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.LeftDrawerLayout;

import java.util.ArrayList;
import java.util.List;

/**
 洪洋大神写的自定义左边侧滑菜单
 * 2016/4/21
 */
public class HongYangLeftDragLayoutFragment extends Fragment {

    private List<String> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("HongYangLeftDragLayoutFragment");
        list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            list.add("我是菜单栏");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LeftDrawerLayout view = (LeftDrawerLayout) inflater.inflate(R.layout.activity_left_hongyang_layout, container, false);
        ListView listview = (ListView) view.findViewById(R.id.id_container_menu);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,list);
        listview.setAdapter(adapter);
        return view;
    }
}
