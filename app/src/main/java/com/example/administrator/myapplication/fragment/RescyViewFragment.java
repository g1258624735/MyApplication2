package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.MeizhiListAdapter;
import com.example.administrator.myapplication.bean.MeizhiData;
import com.example.administrator.myapplication.intefece.RetrofitSingleton;
import com.example.administrator.myapplication.tools.PtrClassicFrameLayout;
import com.example.administrator.myapplication.tools.PullRefreshAndLoadMoreHelper;
import com.google.gson.Gson;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 新版本 recyclerview 的应用
 * 2016.4.19
 * gxj
 * 服务器地址  http://gank.io/api/day/2016/4/15
 */
public class RescyViewFragment extends Fragment {

    private RecyclerView listView;
    private Subscription subscribe;
    private Observer observer;
    private MeizhiListAdapter adapter;
    private PullRefreshAndLoadMoreHelper<MeizhiListAdapter> mPLHelper_list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MeizhiListAdapter(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("新版本recyclerview");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rescyview_layout, null);
        listView = (RecyclerView) view.findViewById(R.id.recylerview);
        PtrClassicFrameLayout ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_classic);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        listView.setLayoutManager(layoutManager);
//        listView.setAdapter(adapter);
        mPLHelper_list = new PullRefreshAndLoadMoreHelper<>(getActivity(),
                listView, adapter, new PullRefreshAndLoadMoreHelper.OnPullRefreshAndLoadMoreListener() {
            @Override
            public void loadListData(int page) {
                Log.e("tag", "开始请求数据");
                get(15);
            }
            @Override
            public boolean checkCanDoRefresh() {
                return true;
            }
        });
        mPLHelper_list.addPullToRefrensh(ptrClassicFrameLayout);
        mPLHelper_list.addLoadMoreView();
        mPLHelper_list.getLoadMoreView().setEmptyString("加载更多");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(String json) {
                MeizhiData data = new Gson().fromJson(json, MeizhiData.class);
                mPLHelper_list.loadingSuccess(data.getResults().getAndroid(), 10);
            }
        };
        mPLHelper_list.loadingStart(0);
    }

    private void get(int day) {
        subscribe = RetrofitSingleton.getApiService(getActivity())
                .mGetPicList(day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }
}
