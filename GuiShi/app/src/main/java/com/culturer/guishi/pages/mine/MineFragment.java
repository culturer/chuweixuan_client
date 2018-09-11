package com.culturer.guishi.pages.mine;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culturer.guishi.MainPresenter;
import com.culturer.guishi.R;
import com.culturer.guishi.pages.home.HomegoodsFragment;
import com.culturer.guishi.pages.home.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private String mParam1;
	private String mParam2;
	
	private List<String> pagerList = new ArrayList<>();
	private List<Fragment> fragmentList = new ArrayList<>();
	private PagerAdapter pagerAdapter;
	
	private View contentView;
	private TabLayout mine_tab;
	private ViewPager mine_pager;
	
	public MineFragment() {
		// Required empty public constructor
	}
	
	public static MineFragment newInstance(String param1, String param2) {
		MineFragment fragment = new MineFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		initData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView = inflater.inflate(R.layout.fragment_mine, container, false);
			initBaseView();
			initPager();
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if ( parent!=null ){
			parent.removeView(contentView);
		}
		return contentView;
	}
	
	private void initBaseView(){
		mine_tab = contentView.findViewById(R.id.mine_tab);
		mine_pager = contentView.findViewById(R.id.mine_pager);
	}
	
	private void initPager(){
		
		pagerList = new ArrayList<>();
		pagerList.add("待付款");
		pagerList.add("待发货");
		pagerList.add("待确认");
		pagerList.add("全部");
		fragmentList = new ArrayList<>();
		fragmentList.add( OrderFragment.newInstance(OrderFragment.Is_Pay,""));
		fragmentList.add( OrderFragment.newInstance(OrderFragment.Is_Send,""));
		fragmentList.add( OrderFragment.newInstance(OrderFragment.Is_Receive,""));
		fragmentList.add( OrderFragment.newInstance(OrderFragment.ALL,""));
		
		//MODE_SCROLLABLE可滑动的展示
		//MODE_FIXED固定展示
		mine_tab.setTabMode(TabLayout.MODE_FIXED);
/*		优化参考
		if (Cache.productsTypes.size()<10){
			home_tab.setTabMode(TabLayout.MODE_FIXED);
		}else {
			home_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
		}*/
		mine_tab.setSelectedTabIndicatorColor(getContext().getColor(R.color.black));
		for (int i=0;i<pagerList.size();i++){
			mine_tab.addTab(mine_tab.newTab().setText(pagerList.get(i)));
		}
		pagerAdapter = new PagerAdapter(getChildFragmentManager(),fragmentList,pagerList);
		mine_pager.setAdapter(pagerAdapter);
		mine_tab.setupWithViewPager(mine_pager);
		
	}
	
	private void initData(){
		MainPresenter.getOrders(getContext());
	}
}
