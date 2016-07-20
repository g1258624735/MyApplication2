package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.widget.BotomDragLayout;
import com.example.administrator.myapplication.widget.LeftScrollDeleteDragLayout;
import com.example.administrator.myapplication.widget.MyView1Layout;

/**
 * 自定义view 1  -  z自定义圆形view
 * 2016/7/13
 */
public class MyViewFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("MyViewFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        final BotomDragLayout view = (BotomDragLayout) inflater.inflate(R.layout.my_view_1_layout, container, false);
        ListView listView = new ListView(getActivity());
        MyApdapter adapter = new MyApdapter();
        listView.setAdapter(adapter);
        return listView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class MyApdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private float[] value = {10f, 20f, 30f, 40f, 10f, 20f, 20f, 90f, 100f, 200f};

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder mholdr;
            if (convertView == null) {
                mholdr = new Holder();
                convertView = new LinearLayout(getActivity());
                mholdr.myview = new MyView1Layout(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,400);
                ((ViewGroup)convertView).addView(mholdr.myview,params);
                convertView.setTag(mholdr);
            }else{
                mholdr= (Holder) convertView.getTag();
            }
            mholdr.myview.setScore(value[position]);
            mholdr.myview.setTextSize(120);
            return convertView;
        }
        class Holder {
            private MyView1Layout myview;
        }
    }
}
