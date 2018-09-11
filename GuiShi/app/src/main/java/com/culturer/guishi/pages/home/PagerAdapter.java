package com.culturer.guishi.pages.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    //标签
    List<String> pagerList = new ArrayList<>();
    //内容
    List<Fragment> fragmentList= new ArrayList<>();

    public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> pagerList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.pagerList = pagerList;
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerList.get(position);
    }
}
