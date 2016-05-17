package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * NestedScrollView
 * 可以嵌套scrollView的 View
 */
public class NestedScrollViewFragment extends Fragment {
    private List<String> list;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<String>();
        for (int i = 0; i <10; i++) {
            list.add("NestedScrollView测试demo");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        (getActivity()).setTitle("NestedScrollView测试demo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(view==null){
           view = inflater.inflate(R.layout.nestedscroll_view_layout,null);
       }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
    }
}
