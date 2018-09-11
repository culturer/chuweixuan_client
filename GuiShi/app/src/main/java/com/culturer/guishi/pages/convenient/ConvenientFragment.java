package com.culturer.guishi.pages.convenient;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culturer.guishi.R;
import com.culturer.guishi.bean.StoresDataBean;
import com.culturer.guishi.cache.BaseCache;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.pages.home.HomegoodsFragment;
import com.culturer.guishi.pages.home.PagerAdapter;
import com.culturer.guishi.util.PreferenceUtil;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.vondear.rxtools.RxKeyboardTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ConvenientFragment extends Fragment {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private String mParam1;
	private String mParam2;
	Gson gson = new Gson();
	
	private View contentView;
	private TabLayout home_tab;
	private ViewPager home_pager;
	
	private List<String> pagerList = new ArrayList<>();
	private List<Fragment> fragmentList = new ArrayList<>();
	private PagerAdapter pagerAdapter;
	
	public ConvenientFragment() {
		// Required empty public constructor
	}
	
	public static ConvenientFragment newInstance(String param1, String param2) {
		ConvenientFragment fragment = new ConvenientFragment();
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
	}
	
	@Override
	public void onResume() {
		RxKeyboardTool.hideSoftInput(getActivity());
		super.onResume();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView = inflater.inflate(R.layout.fragment_convenient, container, false);
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
		home_tab = contentView.findViewById(R.id.home_tab);
		home_pager = contentView.findViewById(R.id.home_pager);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.M)
	private void initPager(){
		fragmentList = new ArrayList<>();
		pagerList = new ArrayList<>();
		if (GoodsCache.shoppers!=null && GoodsCache.shoppers.getShopper()!=null){
			for (int i=0;i<GoodsCache.shoppers.getShopper().size();i++){
				pagerList.add(GoodsCache.shoppers.getShopper().get(i).getStoreType().getName());
				fragmentList.add( StoreFragment.newInstance(i,""));
			}
			//MODE_SCROLLABLE可滑动的展示
			//MODE_FIXED固定展示
			home_tab.setTabMode(TabLayout.MODE_FIXED);
//		优化参考
			if (GoodsCache.shoppers.getShopper().size()<5){
				home_tab.setTabMode(TabLayout.MODE_FIXED);
			}else {
				home_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
			}
			home_tab.setSelectedTabIndicatorColor(getContext().getColor(R.color.black));
			for (int i=0;i<pagerList.size();i++){
				home_tab.addTab(home_tab.newTab().setText(pagerList.get(i)));
			}
			pagerAdapter = new PagerAdapter(getChildFragmentManager(),fragmentList,pagerList);
			home_pager.setAdapter(pagerAdapter);
			home_tab.setupWithViewPager(home_pager);
		}
		
	}
	
}
