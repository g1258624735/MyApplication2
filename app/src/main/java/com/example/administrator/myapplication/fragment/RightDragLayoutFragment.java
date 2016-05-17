package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.RightDragLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 从右边划出菜单栏
 * 2016/4/21
 */
public class RightDragLayoutFragment extends Fragment {
    private List<String> list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("RightDragLayoutFragment");
        list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            list.add("我是菜单栏");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final RightDragLayout view = (RightDragLayout) inflater.inflate(R.layout.activity_right_layout, container, false);
        View tag =view.findViewById(R.id.tag);
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.STATE==RightDragLayout.CLOSEING){
                    view.openCover();
                }else{
                    view.closeCover();
                }

            }
        });

        return view;
    }

}
