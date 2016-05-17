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
        list.add("RXAndroid测试demo");
        list.add("Retrofit测试demo");
        list.add("OkHttpFragment测试demo");
        list.add("NestedScrollView测试demo");
        list.add("RescyViewFragment测试demo");
        list.add("SwipeResfreshFragment测试demo");
        list.add("MySwipeResfreshFragment测试demo");
        list.add("ViewDraghelperLayoutFragment测试demo");
        list.add("JsCallBackJavaFragment测试demo");
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("首页");
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
