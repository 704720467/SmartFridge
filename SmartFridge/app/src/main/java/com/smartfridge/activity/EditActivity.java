package com.smartfridge.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.smartfridge.R;
import com.smartfridge.adapter.MainPagerAdapter;
import com.smartfridge.util.DeviceUtil;
import com.smartfridge.view.HorizonVerticalViewPager;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private HorizonVerticalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = Math.round(DeviceUtil.getScreenHeightSize(this) * 9 / (16.0f));
        lp.windowAnimations = R.style.buttomdialogAnim;
        window.setAttributes(lp);
        initView();
//        mViewPager.setCurrentItem(0);
    }

    private void initView() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab_ingredients:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.main_tab_menu:
                mViewPager.setCurrentItem(1);
                break;
        }
    }
}
