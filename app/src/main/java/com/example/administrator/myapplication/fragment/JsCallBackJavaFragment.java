package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

/**
 * js 回掉java
 * 2016/4/21
 */
public class JsCallBackJavaFragment extends Fragment  {

    private WebView web;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("JsCallBackJavaFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View  view =  inflater.inflate(R.layout.js_call_back_java_layout, container, false);
        web = (WebView) view.findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.addJavascriptInterface(new Ceshi(), "ucsmyApp");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        web.loadUrl("http://172.17.22.188:8080/yhsms/");
//        web.loadUrl("file:///android_asset/demo.html");
    }
    public class Ceshi{
        @JavascriptInterface
        public void show(){
            Toast.makeText(getActivity(),"我是原生Toast",Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void share(String title,String content,String url,String pngurl,String other){
            Toast.makeText(getActivity(),title+content+url+pngurl,Toast.LENGTH_SHORT).show();
        }
    }

}
