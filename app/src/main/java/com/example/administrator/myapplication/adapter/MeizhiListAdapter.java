/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Meizhi;
import com.example.administrator.myapplication.widget.RatioImageView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by gxj on 16/4/19.
 */
public class MeizhiListAdapter
        extends BaseAdapter<Meizhi,MyViewHolder> {
    private Context mContext;

    public MeizhiListAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meizhi, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        Meizhi meizhi = getList().get(position);
        int limit = 48;
        String text = meizhi.desc.length() > limit ? meizhi.desc.substring(0, limit) +
                "..." : meizhi.desc;
        viewHolder.meizhi = meizhi;
        viewHolder.titleView.setText(text);
        viewHolder.card.setTag(meizhi.desc);
        String url = listUrl[random.nextInt(7)];
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .into(viewHolder.meizhiView)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!viewHolder.card.isShown()) {
                            viewHolder.card.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private String[] listUrl = {"http://ww2.sinaimg.cn/large/7a8aed7bjw1f30sgi3jf0j20iz0sg40a.jpg",
            " http://ww1.sinaimg.cn/large/7a8aed7bjw1f2zwrqkmwoj20f00lg0v7.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2x7vxkj0uj20d10mi42r.jpg",
            "http://ww2.sinaimg.cn/large/7a8aed7bjw1f2w0qujoecj20f00kzjtt.jpg",
            "http://ww4.sinaimg.cn/large/610dc034jw1f2uyg3nvq7j20gy0p6myx.jpg",
            "http://ww4.sinaimg.cn/large/7a8aed7bjw1f2tpr3im0mj20f00l6q4o.jpg",
            "http://ww1.sinaimg.cn/large/7a8aed7bjw1f2sm0ns82hj20f00l8tb9.jpg",
            "http://ww3.sinaimg.cn/large/7a8aed7bjw1f2p0v9vwr5j20b70gswfi.jpg"};
    private Random random = new Random();
}
 class MyViewHolder extends ViewHolder {
    @Bind(R.id.iv_meizhi)
    RatioImageView meizhiView;
    @Bind(R.id.tv_title)
    TextView titleView;
    View card;
    Meizhi meizhi;

    public MyViewHolder(View itemView) {
        super(itemView);
        card = itemView;
        ButterKnife.bind(this, itemView);
        meizhiView.setOriginalSize(50, 50);
    }
}