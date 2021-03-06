package com.smartfridge.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.smartfridge.R;
import com.smartfridge.adapter.MainPagerAdapter;
import com.smartfridge.view.HorizonVerticalViewPager;

/**
 * 整理冰箱界面，功能包括：添加食材，删除食材
 */
public class DapperFridgeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private HorizonVerticalViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private MainPagerAdapter pagerAdapter;
    private RadioButton mTabIngredients;
    private RadioButton mTabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dapperfridge);
        initView();
        mViewPager.setCurrentItem(0);
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        mTabIngredients = (RadioButton) findViewById(R.id.main_tab_ingredients);
        mTabMenu = (RadioButton) findViewById(R.id.main_tab_menu);
        mTabIngredients.setOnClickListener(this);
        mTabMenu.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager = (HorizonVerticalViewPager) findViewById(R.id.main_viewpager);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setScanScroll(false);
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), false);
        mViewPager.setAdapter(pagerAdapter);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRadioGroup.check(R.id.main_tab_ingredients);
                break;
            case 1:
                mRadioGroup.check(R.id.main_tab_menu);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Drawable mTabIngredientsDra = null;
        Drawable mTabMenuDra = null;
        mTabIngredientsDra = getResources().getDrawable((checkedId == R.id.main_tab_ingredients) ? R.mipmap.tabbar_ingredients_selected : R.mipmap.tabbar_ingredients);
        mTabIngredientsDra.setBounds(0, 0, mTabIngredientsDra.getMinimumWidth(), mTabIngredientsDra.getMinimumHeight());
        mTabMenuDra = getResources().getDrawable((checkedId == R.id.main_tab_ingredients) ? R.mipmap.tabbar_menu : R.mipmap.tabbar_menu_selected);
        mTabMenuDra.setBounds(0, 0, mTabMenuDra.getMinimumWidth(), mTabMenuDra.getMinimumHeight());
        mTabMenu.setCompoundDrawables(null, mTabMenuDra, null, null);
        mTabMenu.setTextColor(Color.parseColor((checkedId == R.id.main_tab_menu) ? "#FFD600" : "#333333"));
        mTabIngredients.setCompoundDrawables(null, mTabIngredientsDra, null, null);
        mTabIngredients.setTextColor(Color.parseColor((checkedId == R.id.main_tab_ingredients) ? "#FFD600" : "#333333"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.info, menu);

        //menu.add(1, Menu.FIRST, 1, "Change Site ID");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.quit1:
                super.finish();
                System.exit(0);
                return true;
            default:
                return false;
        }
    }
}
