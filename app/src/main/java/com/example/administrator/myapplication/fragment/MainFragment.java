package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.tools.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主Fragment 首页
 */
public class MainFragment extends Fragment {
    private List<String> list;
    private ListView listView;
    private View view;

    private OnItemClickListener onItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<String>();
        list.add("响应式开发RXAndroid");
        list.add("最新网络请求神器Retrofit");
        list.add("最新网络请求框架OkHttpFragment");
        list.add("NestedScrollView");
        list.add("ViewDraghelper自定义神器-ViewDraghelperLayoutFragment");
        list.add("JS 回掉android-JsCallBackJavaFragment");
        list.add("自定义ProgressBarFragment");
        list.add("刷新集合列表-ShuaxinFragment");
        list.add("自定义View列表-MyViewListFragment");
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("首页");
        int cout = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.main_central_layout);
        if(fragment instanceof MainFragment){
            DebugUtil.i("MainFragment", "我是MainFragment");
        }else{
            DebugUtil.i("MainFragment", "我是MySwipeResfreshFragment");
        }
        List<Fragment> list = getActivity().getSupportFragmentManager().getFragments();
        DebugUtil.i("MainFragment", cout+"---"+list.size() );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_centent_list, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        DebugUtil.i("gxj", "getBackStackEntryCount" + count);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    public interface OnItemClickListener extends AdapterView.OnItemClickListener {

    }
}
