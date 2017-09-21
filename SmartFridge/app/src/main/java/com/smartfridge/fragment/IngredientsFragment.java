package com.smartfridge.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.smartfridge.R;
import com.smartfridge.activity.EditActivity;
import com.smartfridge.adapter.IngredientsAdapter;
import com.smartfridge.decoration.DividerGridItemDecoration;
import com.smartfridge.listener.OnRecyclerItemClickListener;
import com.smartfridge.mvp.module.Ingredient;
import com.smartfridge.util.TextUtil;
import com.smartfridge.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by zp on 2017/9/20.
 */

public class IngredientsFragment extends Fragment {
    private ArrayList<Ingredient> ingredients;
    private RecyclerView rcIngredients;//食材列表
    IngredientsAdapter adapter;
    LinearLayoutManager layoutManager;


    public static IngredientsFragment newInstance() {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void initData() {
        if (ingredients == null)
            ingredients = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            ingredients.add(new Ingredient(i + "", "http://or4us98za.bkt.clouddn.com/timg.jpg", "西红柿", 200));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initData();
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcIngredients = (RecyclerView) view.findViewById(R.id.rc_ingredients);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new IngredientsAdapter(getContext(), ingredients, 4);
        rcIngredients.setAdapter(adapter);

        //        rcIngredients.setLayoutManager(layoutManager);
        //        rcIngredients.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));

        rcIngredients.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rcIngredients.addItemDecoration(new DividerGridItemDecoration(getContext()));

        rcIngredients.addOnItemTouchListener(new OnRecyclerItemClickListener(rcIngredients) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                startActivity(new Intent(getContext(), EditActivity.class));
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtil.showDefaultSuperToast(getContext(), "长按" + +viewHolder.getAdapterPosition());
            }
        });
    }
}
