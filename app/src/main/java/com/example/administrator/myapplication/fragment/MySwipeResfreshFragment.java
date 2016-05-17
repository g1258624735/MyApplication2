package com.example.administrator.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.tools.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 swipeFefreshLayout 测试demo
 * 加载更多功能应该由content view 自己去实现 而不是 集成在下拉刷新控件中
 * 这样更符合google 的设计思想
 * 计划在adapter中去实现加载更多的功能
 * gxj
 * 2016/4/21
 */
public class MySwipeResfreshFragment extends Fragment {
    private List<String> list;
    private RecyclerView listview;
    private SwipeRefreshLayout layout;
    private FragmentActivity context;
    private ResyclerAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("MySwipeResfreshFragment--"+i);
        }
        getActivity().setTitle("SwipeFefreshLayout");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = new SwipeRefreshLayout(getActivity());
        listview = new RecyclerView(getActivity());
        manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(manager);
        layout.addView(listview);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        });
        /**
         * 设置下滑加载更多
         */
        listview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = manager.findLastVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    adapter.startLoading();

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        adapter = new ResyclerAdapter();
        listview.setAdapter(adapter);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "停止刷新", Toast.LENGTH_SHORT).show();
                    layout.setRefreshing(false);
                    break;
                case 1:
                    list.add(list.size()-1,"新增加的数据-----"+(list.size()-1));
                    Toast.makeText(getActivity(), "停止加载更多", Toast.LENGTH_SHORT).show();
                    adapter.stopLoadingMore();
                    break;
            }
        }
    };

    public void stopRefresh(final boolean isFeresh){
        if (layout != null) {
            layout.post(new Runnable() {
                @Override
                public void run() {
                    layout.setRefreshing(isFeresh);
                }
            });
            DebugUtil.e("gxj","stopRefresh");
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (layout != null) {
            layout.post(new Runnable() {
                @Override
                public void run() {
                    layout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("gxj","onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("gxj","onDetach");
    }
    @Override
    public void onDestroy() {
        if (handler != null) {
            handler.removeMessages(0);
            handler.removeMessages(1);
        }
        super.onDestroy();
        DebugUtil.e("gxj", "MySwipeResfreshFragment-onDestroy");
    }

    public class ResyclerAdapter extends RecyclerView.Adapter {
        private boolean isLoading = false;//开启加载更多

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                TextView textView = new TextView(context);
                textView.setTextColor(Color.BLUE);
                textView.setTextSize(20);
                textView.setPadding(10, 10, 10, 10);
                textView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new MyHodler(textView);
            } else {
                View loadingView = LayoutInflater.from(context).inflate(R.layout.footer_loding_item, null);
                loadingView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                return new MyFooterHodler(loadingView);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isLoading && position == list.size() - 1) {
                return 1;
            } else {
                return 0;
            }
        }

        public void stopLoadingMore() {
            if(isLoading) {
                isLoading = false;
                list.remove(list.size() - 1);
                this.notifyDataSetChanged();
            }
        }

        public void startLoading() {
            if(!isLoading) {
                handler.sendEmptyMessageDelayed(1, 500);
                isLoading = true;
                list.add("");
                this.notifyDataSetChanged();
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if ((isLoading && position != list.size() - 1)||!isLoading) {
                if (holder instanceof MyHodler ) {
                    MyHodler myHodler = (MyHodler) holder;
                    myHodler.tv.setText(list.get(position));
                }
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        public class MyHodler extends RecyclerView.ViewHolder {
            private TextView tv;

            public MyHodler(View itemView) {
                super(itemView);
                    tv = (TextView) itemView;
            }
        }

        public class MyFooterHodler extends RecyclerView.ViewHolder {
            public MyFooterHodler(View itemView) {
                super(itemView);
            }
        }
    }

}
