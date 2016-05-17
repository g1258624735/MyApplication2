package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.BotomDragLayout;
import com.example.administrator.myapplication.widget.LeftScrollDeleteDragLayout;

/**
 * 侧滑删除列表
 * 2016/4/21
 * gxj
 */
public class LeftScrollDeleteLayoutFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("LeftScrollDeleteLayoutFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LeftScrollDeleteDragLayout view = (LeftScrollDeleteDragLayout) inflater.inflate(R.layout.left_scroll_delete_layout, container, false);
        View view_bg =view.findViewById(R.id.del);
        view_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.STATE == BotomDragLayout.CLOSEING){
                    view.openCover();
                }else{
                    view.closeCover();
                    Toast.makeText(getActivity(),"删除",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
