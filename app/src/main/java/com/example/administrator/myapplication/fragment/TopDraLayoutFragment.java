package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.BotomDragLayout;
import com.example.administrator.myapplication.widget.TopDragLayout;

/**
 * 顶部划出菜单栏
 * 2016/4/21
 */
public class TopDraLayoutFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("TopDraLayoutFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final TopDragLayout view = (TopDragLayout) inflater.inflate(R.layout.activity_top_layout, container, false);
        View view_bg = view.findViewById(R.id.tv);
        view_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.STATE == BotomDragLayout.CLOSEING) {
                    view.openCover();
                } else {
                    view.closeCover();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
