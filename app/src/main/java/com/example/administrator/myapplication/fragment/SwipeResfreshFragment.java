package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 swipeFefreshLayout 测试demo
 */
public class SwipeResfreshFragment extends Fragment {
    private List<String> list;
    private ListView listview;
    private SwipeRefreshLayout layout;
    private FragmentActivity context;
    ArrayAdapter<String> adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("SwipeFefreshLayout");
        }
        getActivity().setTitle("SwipeFefreshLayout");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = new SwipeRefreshLayout(getActivity());
        listview = new ListView(getActivity());
        layout.addView(listview);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(0,2000);
            }
        });
        layout.setOnLoadListener(new SwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                handler.sendEmptyMessageDelayed(1,2000);
            }
        });
         adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        listview.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e("gxj","onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        layout.post(new Runnable() {
            @Override
            public void run() {
                layout.setRefreshing(false);
            }
        });
        Log.e("gxj","onDetach");
    }

    @Override
    public void onDestroy() {
        if(handler!=null){
            handler.removeMessages(0);
            handler.removeMessages(1);
            handler=null;
        }
        super.onDestroy();
        Log.e("gxj","onDestroy");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    layout.post(new Runnable() {
                        @Override
                        public void run() {
                            layout.setRefreshing(false);
                        }
                    });
                    Toast.makeText(context, "停止刷新", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    layout.setLoading(false);
                    Toast.makeText(context, "停止加载更多", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
