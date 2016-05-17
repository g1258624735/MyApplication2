package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myapplication.MyAplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.BusBean;
import com.example.administrator.myapplication.bean.HeadBean;
import com.example.administrator.myapplication.intefece.RetrofitSingleton;
import com.example.administrator.myapplication.tools.DebugUtil;
import com.example.administrator.myapplication.tools.JsonUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
/**
 * OkHttp demo 结合公司里面的网络请求 加密解密
 * 写的一个demo 主要是兼容android 6.0
 * 去掉之前volley 网络请求框架的一些阿帕奇的一些 api
 * 带完成 (rsa 加密 和 3des加密请求)
 */
public class RetrofitFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView tv_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Retrofit测试Demo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retrofit_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        Button btn_get = (Button) view.findViewById(R.id.btn_get);
        Button btn_post = (Button) view.findViewById(R.id.btn_post);
        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get();
                break;
            case R.id.btn_post:
                post();
                break;
        }
    }

    private void get() {
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                tv_content.setText("onError"+e.getMessage());
            }
            @Override
            public void onNext(String weather) {
                tv_content.setText("onNext"+weather);
            }
    };

        RetrofitSingleton.getApiService(getActivity())
                .mGetAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String weather) {
//                        aCache.put("WeatherData", weather,
//                                (mSetting.getInt(Setting.AUTO_UPDATE, 0) + 1) * Setting.ONE_HOUR);//默认一小时后缓存失效
                    }
                })
                .subscribe(observer);

    }

    private void post() {
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                DebugUtil.i("gxj", "onCompleted" );
            }

            @Override
            public void onError(Throwable e) {
                DebugUtil.i("gxj", "onError" + e);
            }

            @Override
            public void onNext(String weather) {
                DebugUtil.i("gxj", weather);
            }
        };

        RetrofitSingleton.getApiService(getActivity())
                .mGetLoginAPI( "18516590402", "yy0402")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 不加密
     */
    public static <T> String getData(MyAplication application,
                                     String bodyJson, String txCode) {
        BusBean bean = new BusBean();
        HeadBean headBean = new HeadBean(application);
        headBean.setTxCode(txCode);
        DebugUtil.d("请求报文Body", bodyJson);
        bean.setHead(headBean);
        bean.setBody(bodyJson);
        DebugUtil.i("gxj", JsonUtil.serialize(bean));
        return JsonUtil.serialize(bean);
    }
}
