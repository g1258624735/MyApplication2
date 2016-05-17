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
import com.example.administrator.myapplication.widget.BotomDragLayout;
import com.example.administrator.myapplication.widget.LeftDragLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 底从左边划出菜单栏
 * 2016/4/21
 */
public class LeftDragLayoutFragment extends Fragment {
    private List<String> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("LeftDragLayoutFragment");
        list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            list.add("我是菜单栏");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LeftDragLayout view = (LeftDragLayout) inflater.inflate(R.layout.activity_left_layout, container, false);
        View view_bg =view.findViewById(R.id.view_bg);
        ListView listview = (ListView) view.findViewById(R.id.view_left);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, list);
        listview.setAdapter(adapter);
        view_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.STATE == BotomDragLayout.CLOSEING){
//                    view.openCover();
                }else{
//                    view.closeCover();
                }
            }
        });
        return view;
    }

}
