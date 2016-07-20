package com.example.administrator.myapplication.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.BotomDragLayout;
import com.example.administrator.myapplication.widget.DemoFreshLayout;
import com.example.administrator.myapplication.widget.MaterialProgressDrawable;
import com.example.administrator.myapplication.widget.ReFreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉刷新 碎片
 * 基于ViewDragHelper改写的 改写的
 *
 * 2016/4/21
 */
public class ReFreshDraLayoutFragment extends Fragment {
    private List<String> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("ReFreshDraLayoutFragment");
        list = new ArrayList<String>();
        for (int i = 0; i <20 ; i++) {
            list.add("ReFreshDraLayoutFragment");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final DemoFreshLayout view = (DemoFreshLayout) inflater.inflate(R.layout.demo_layout, container, false);
//        ListView listview = (ListView) view.findViewById(R.id.listview);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line,list);
//        listview.setAdapter(arrayAdapter);
//        arrayAdapter.notifyDataSetChanged();
        return  view ;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
