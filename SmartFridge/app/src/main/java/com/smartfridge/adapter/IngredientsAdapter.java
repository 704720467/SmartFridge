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
import com.smartfridge.mvp.module.Ingredient;
import com.smartfridge.util.DeviceUtil;

import java.util.ArrayList;

/**
 * Created by admin on 2017/9/11.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.Holder> {

    ArrayList<Ingredient> mDatas;
    LayoutInflater layoutInflater;
    int width;
    Context context;
    private int rowCount;


    public IngredientsAdapter(Context context, ArrayList<Ingredient> datas, int rowCount) {
        layoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.context = context;
        this.rowCount = rowCount;
        width = Math.round((DeviceUtil.getScreenWidthSize(context) - DeviceUtil.dp2px(context, 70)) / rowCount);
        if (this.mDatas == null)
            this.mDatas = new ArrayList<>();
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder myHolder = new Holder(layoutInflater.inflate(R.layout.item_ingredient, parent, false));
        ViewGroup.LayoutParams lp = myHolder.itemView.getLayoutParams();
        lp.width = width;
        //        lp.height = Math.round(DeviceUtil.dp2px(context, (new Random().nextInt(10) * 10 + 100)));
        lp.height = Math.round(DeviceUtil.dp2px(context, 100));
        myHolder.itemView.setLayoutParams(lp);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.name.setText(mDatas.get(position).getName());
        holder.surplus.setText("剩余：" + mDatas.get(position).getWeightList());
        Glide.with(context).load(mDatas.get(position).getImg()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return this.mDatas.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView surplus;

        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            surplus = (TextView) itemView.findViewById(R.id.surplus);
        }
    }

    public ArrayList<Ingredient> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<Ingredient> mDatas) {
        this.mDatas = mDatas;
    }
}
