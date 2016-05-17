package com.example.administrator.myapplication.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.myapplication.tools.DebugUtil;
import com.example.administrator.myapplication.tools.RxBus;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.rx.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxBus消息订阅
 * RXjava 响应式编程
 */
public class RXFragment extends ListFragment {
    private List<String> list;
    private AlertDialog dialog;
    private Subscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<String>();
        list.add("RXjava");
        list.add("RxVolley_post请求");
        list.add("RxBus消息订阅");
        list.add("普通网络请求结合RXjava");
        list.add("RxVolley_get请求");
        RxBus.getDefault().event(Person.class).subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                DebugUtil.i("onCompleted", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                DebugUtil.i("onNext", "onNext");
                showDialogText("收到来自于RXBus 的订阅消息--" + ((Person) o).name).show();
            }
        });

    }

    public class Person {
        public Person(String name) {
            this.name = name;
        }

        public String name;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        getListView().setAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0: //Rxjava 测试demo
                RxjavaFrom();
                break;
            case 1://RxVolley post请求 可能存在网络阻塞的情况 请谨慎使用
                RxVolley_post();
                break;
            case 2:
                RxBus.getDefault().post(new Person("小明"));
                break;
            case 3://普通网络请求 结合 rxjava
                commGet();
                break;
            case 4://RxVolley get请求
                RxVolleyGet();
                break;
        }
    }


    /**
     * RX 主要方法的解析
     * just()  --> （接受 集合 数组）该方法会依次发送集合里面的事件给订阅者
     * from()  --> （接受 集合 数组）该方法会依次发送集合里面的事件给订阅者
     * flatMap() -->变换Observable订阅事件的方式 从一种方式变换成另外一种方式 返回的是Observable 和 map()
     * 的区别
     * Map()---> map 和flatMap（）也是一样的 。也是其转换的作用。但返回的不是 Observable
     */
    private void RxjavaFrom() {
        final String[] str = {"我是第一"};
        Observable.from(str).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String string) {
                return string.length();
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                showDialogText(integer + "").show();
            }
        });
    }

    private void RxVolley_post() {

        HttpParams params = new HttpParams();
        params.put("grant_type","app_credential");
        params.put("app_id","fd00a8a65e2b4b72b4da77fdeac99bfd");
        params.put("app_secret","2310ffb2ef6b4e04826c2bbb71736f28");
        Observable<Result> observable = new RxVolley.Builder()
                .url("http://services.c-smb.com//CloudWeb/api.jsp")
                .params(params)
                .httpMethod(RxVolley.Method.POST)
                .contentType(RxVolley.ContentType.FORM)
                .getResult();

        subscription = observable
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return new String(result.data);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        showDialogText(result + "").show();
                    }
                });
    }
    private void RxVolleyGet() {
        Action1 action = new Action1<String>() {
            @Override
            public void call(String result) {
                showDialogText(result + "").show();
            }
        };
        Observable<Result> observable = new RxVolley.Builder()
                .url("http://kymjs.com/feed.xml")
                .httpMethod(RxVolley.Method.GET)
                .contentType(RxVolley.ContentType.FORM)
                .getResult();

        subscription = observable
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return new String(result.data);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);

    }

    /**
     * 普通的网络请求
     * 结合RXjava
     */
    private void commGet(){
        Observable.just("http://kymjs.com/feed.xml")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String result) {
                        return sendGet(result,"");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        showDialogText("我是普通网络请求"+result + "").show();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        (getActivity()).setTitle("RXAndroid");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
    /**
     * 普通网络请求
     * @param url
     * @param param
     * @return
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    public AlertDialog showDialogText(String msg) {
        if (dialog != null) {
            dialog.setMessage(msg);
            return dialog;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示").setMessage(msg).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        return dialog;
    }
    @Override
    public void onDestroy() {
        if (subscription != null) {//记得 RXVolley 在 destrory 的时候一定要 解绑 这样有可能不会导致 内存泄露
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
