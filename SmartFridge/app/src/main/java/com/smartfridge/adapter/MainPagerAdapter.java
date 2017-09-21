package com.smartfridge.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.smartfridge.fragment.IngredientsFragment;
import com.smartfridge.fragment.MenuFragment;

/**
 * Created by keke on 2017/6/16.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private boolean hasTeam;//是否加入过球队
    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    /**
     * @param fm
     * @param hasTeam 用户有没有球队
     */
    public MainPagerAdapter(FragmentManager fm, boolean hasTeam) {
        super(fm);
        this.hasTeam = hasTeam;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        switch (position) {
            case 0:
                f = new IngredientsFragment().newInstance();
                break;
            case 1:
                f = new MenuFragment().newInstance();
                break;
            default:
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
}
