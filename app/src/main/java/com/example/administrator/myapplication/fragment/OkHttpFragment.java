package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * OkHttp
 */
public class OkHttpFragment extends Fragment implements View.OnClickListener {
    private List<String> list;
    private ListView listView;
    private View view;
    private TextView tv_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<String>();
        list.add("Okkhttp3测试Demo");
        list.add("Okkhttp3测试Demo");
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Okkhttp3测试Demo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.okhttp_layout, container, false);
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

    public void post() {
//        String url  = "http://emgw1.apps123.cn/EPlus/member_commonMemberLogin.action";
        String url  = "http://183.60.90.147/grcapp/entry";
        String content="{\"body\":\"{}\",\"head\":{\"IMEI\":\"865714027501642\",\"SN\":\"79ac906e-c9a1-4d6f-ad4a-fa5361ab96cb\",\"appVersion\":\"1.0.0\",\"clientModel\":\"HUAWEI P7-L07\",\"noKeySave\":0,\"osVersion\":\"A5.1.1\",\"reqTime\":\"1460014339348\",\"sessionId\":\"fccf6221-af66-4ec2-b7ae-6574af9fa5e7\",\"txCode\":\"19000\"}}";
//        String content="{\"jsoncallback\":\"appjsoncallback\",\"identity\":\"1\",\"appId\":\"4028835353a64f530153a74a51d70158\",\"branchInfoId\":\"4028835353a64f530153a74a5495015c\",\"deviceType\":3,\"token\":\"3\",\"mobilePhone\":\"13538951807\",\"password\":\"123456\"}";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("data", content);
        OkHttpUtils
                .post()
                .url(url)
                .params(params)
//                .content(content)
                .build()
                .execute(new MyStringCallback());
    }
    public void post3() {
        String url  = "http://services.c-smb.com/interface/access/token/get";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type","app_credential");
        params.put("app_id","fd00a8a65e2b4b72b4da77fdeac99bfd");
        params.put("app_secret","2310ffb2ef6b4e04826c2bbb71736f28");
        OkHttpUtils
                .post()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }

    public void post_2() {
        String url  = "http://emgw1.apps123.cn/EPlus/member_commonMemberLogin.action";
//        String content="{\"jsoncallback\":\"appjsoncallback\",\"identity\":\"1\",\"
// appId\":\"4028835353a64f530153a74a51d70158\",\"branchInfoId\":\"4028835353a64f530153a74a5495015c\",
// \"deviceType\":3,\"token\":\"3\",\"mobilePhone\":\"13538951807\",\"password\":\"123456\"}";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("jsoncallback", "jsoncallback");
        params.put("identity", "1");
        params.put("appId", "4028835353a64f530153a74a51d70158");
        params.put("branchInfoId", "4028835353a64f530153a74a5495015c");
        params.put("deviceType", "3");
        params.put("mobilePhone", "13538951807");
        params.put("password", "123456");
        OkHttpUtils
                .post()
                .url(url)
                .params(params)
//                .content(content)
                .build()
                .execute(new MyStringCallback());
    }
    public void get() {
//        String url = "http://www.csdn.net/";
        String url = "http://emgw1.apps123.cn/EPlus/member_commonMemberLogin.action?jsoncallback=appjsoncallback&identity=1&appId=4028835353a64f530153a74a51d70158&branchInfoId=4028835353a64f530153a74a5495015c&deviceType=3&token=APPS123868013028987927d74971f65aff474ea7f840a402fa502e&mobilePhone=13538951807&password=123456&";
        OkHttpUtils
                .get()
                .url(url)
//                .addParams("username", "hyman")
//                .addParams("password", "123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        tv_content.setText(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                    tv_content.setText(response);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get();
                break;
            case R.id.btn_post:
//                post();
                post3();
                break;

        }
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
        }

        @Override
        public void onAfter() {
            super.onAfter();
        }

        @Override
        public void onError(Call call, Exception e) {
            tv_content.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            tv_content.setText("onResponse:" + response);
        }

        @Override
        public void inProgress(float progress) {
            Log.e("gxj", "inProgress:" + progress);
        }
    }
}
