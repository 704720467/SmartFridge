package com.smartfridge.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smartfridge.R;
import com.smartfridge.adapter.MenusAdapter;
import com.smartfridge.decoration.DividerItemDecoration;
import com.smartfridge.listener.OnRecyclerItemClickListener;
import com.smartfridge.mvp.module.Menu;
import com.smartfridge.util.TextUtil;
import com.smartfridge.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by zp on 2017/9/20.
 */

public class MenuFragment extends Fragment {
    private ArrayList<Menu> menus;
    private RecyclerView rcIngredients;//食材列表
    private MenusAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ImageView menuImage;
    private TextView mIntroduce;


    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void initData() {
        if (menus == null)
            menus = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            menus.add(new Menu(i + "", "http://or4us98za.bkt.clouddn.com/8718367adab44aedd3eb8350b91c8701a08bfbd9.jpg", "鱼香肉丝"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initData();
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuImage = (ImageView) view.findViewById(R.id.menu_image);
        mIntroduce = (TextView) view.findViewById(R.id.introduce);
        rcIngredients = (RecyclerView) view.findViewById(R.id.rc_menu);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new MenusAdapter(getContext(), menus);
        rcIngredients.setAdapter(adapter);

        rcIngredients.setLayoutManager(layoutManager);
        rcIngredients.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));


        rcIngredients.addOnItemTouchListener(new OnRecyclerItemClickListener(rcIngredients) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtil.showDefaultSuperToast(getContext(), "单击" + viewHolder.getAdapterPosition());
                Glide.with(getContext()).load(menus.get(viewHolder.getAdapterPosition()).getImg()).into(menuImage);
                TextUtil.setStringToTextView(mIntroduce, menus.get(viewHolder.getAdapterPosition()).getIntroduce());
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                ToastUtil.showDefaultSuperToast(getContext(), "长按" + +viewHolder.getAdapterPosition());
            }
        });
    }
}
