package com.smartfridge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smartfridge.R;
import com.smartfridge.mvp.module.Menu;

import java.util.ArrayList;

/**
 * Created by admin on 2017/9/11.
 */

public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.Holder> implements View.OnClickListener {

    private ArrayList<Menu> mDatas;
    private LayoutInflater layoutInflater;
    private Context context;


    public MenusAdapter(Context context, ArrayList<Menu> datas) {
        layoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.context = context;
        if (this.mDatas == null)
            this.mDatas = new ArrayList<>();
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder myHolder = new Holder(layoutInflater.inflate(R.layout.item_menu, parent, false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.name.setText(mDatas.get(position).getName());
        Glide.with(context).load(mDatas.get(position).getImg()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return this.mDatas.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public ArrayList<Menu> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<Menu> mDatas) {
        this.mDatas = mDatas;
    }
}
