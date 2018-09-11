package com.culturer.guishi.pages.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.culturer.guishi.R;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.util.PreferenceUtil;
import com.culturer.guishi.wedgit.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
	
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private static final String TAG = "HomeFragment";
	
	private String mParam1;
	private String mParam2;
	
	private List<Integer> imgs = new ArrayList<>();
	private List<String> pagerList = new ArrayList<>();
	private List<Fragment> fragmentList = new ArrayList<>();
	private PagerAdapter pagerAdapter;
	
	private View contentView;
	private Banner banner;
	private TabLayout home_tab;
	private ViewPager home_pager;
	
	public HomeFragment() {
		// Required empty public constructor
	}
	
	public static HomeFragment newInstance(String param1, String param2) {
		HomeFragment fragment = new HomeFragment();
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView = inflater.inflate(R.layout.fragment_home, container, false);
			initBaseView();
			initBanner();
			initPager();
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if ( parent!=null ){
			parent.removeView(contentView);
		}
		return contentView;
	}
	private void initBaseView(){
		banner = contentView.findViewById(R.id.banner);
		home_tab = contentView.findViewById(R.id.home_tab);
		home_pager = contentView.findViewById(R.id.home_pager);
	}
	//顶部轮播
	private void initBanner(){
		
		imgs.add(R.mipmap.cover1);
		imgs.add(R.mipmap.cover2);
		imgs.add(R.mipmap.cover3);
		
		//设置图片加载器
		banner.setImageLoader(new GlideImageLoader());
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
		banner.setIndicatorGravity(BannerConfig.RIGHT);
		banner.setImages(imgs);
		banner.setOnBannerListener(new OnBannerListener() {
			@Override
			public void OnBannerClick(int position) {
				Toast.makeText(getContext(),"点击了第"+position+"个广告",Toast.LENGTH_LONG).show();
			}
		});
		//banner设置方法全部调用完毕时最后调用
		banner.start();
		
	}
	
	private void initPager(){
		
		pagerList = new ArrayList<>();
		fragmentList = new ArrayList<>();
		if (GoodsCache.goodsBean!=null && GoodsCache.goodsBean.getDatas()!=null){
			for (int i=0;i<GoodsCache.goodsBean.getDatas().size();i++){
				pagerList.add(GoodsCache.goodsBean.getDatas().get(i).getProductType().getName());
				fragmentList.add( HomegoodsFragment.newInstance(i,-1));
			}
		}
		//MODE_SCROLLABLE可滑动的展示
		//MODE_FIXED固定展示
		home_tab.setTabMode(TabLayout.MODE_FIXED);
/*		优化参考
		if (Cache.productsTypes.size()<10){
			home_tab.setTabMode(TabLayout.MODE_FIXED);
		}else {
			home_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
		}*/
		home_tab.setSelectedTabIndicatorColor(getContext().getColor(R.color.black));
		for (int i=0;i<pagerList.size();i++){
			home_tab.addTab(home_tab.newTab().setText(pagerList.get(i)));
		}
		Log.i(TAG, "fragmentList_size: "+fragmentList.size()+"||pagerList_size："+pagerList.size());
		pagerAdapter = new PagerAdapter(getChildFragmentManager(),fragmentList,pagerList);
		home_pager.setAdapter(pagerAdapter);
		home_tab.setupWithViewPager(home_pager);
		
	}
	
}
