package com.culturer.guishi.pages.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.culturer.guishi.MainPresenter;
import com.culturer.guishi.R;
import com.culturer.guishi.bean.GoodsBean;
import com.culturer.guishi.bean.ProductsBean;
import com.culturer.guishi.cache.BaseCache;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.event.GoodsListEvent;
import com.culturer.guishi.util.PreferenceUtil;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.List;

public class HomegoodsFragment extends Fragment {
	
	private static final String OPTION = "option";
	private static final String GOODSINDEX = "goodsIndex";
	
	private int option;
	private int goodsIndex;
	
	private int index = 1;
	private int size = 15;
	private MainPresenter presenter;
	
	private View contentView;
	private ListView listview;
	RefreshLayout refreshLayout;
	
	private HomegoodsAdapter adapter;
	
	public HomegoodsFragment() {
		// Required empty public constructor
	}

	public static HomegoodsFragment newInstance(int option, int goodsIndex) {
		HomegoodsFragment fragment = new HomegoodsFragment();
		Bundle args = new Bundle();
		args.putInt(OPTION, option);
		args.putInt(GOODSINDEX, goodsIndex);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			option = getArguments().getInt(OPTION);
			goodsIndex = getArguments().getInt(GOODSINDEX);
		}
		presenter = MainPresenter.getInstance();
		EventBus.getDefault().register(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView = inflater.inflate(R.layout.fragment_homegoods, container, false);
			initBaseView();
			initList();
			initRefresh();
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if ( parent!=null ){
			parent.removeView(contentView);
		}
		return contentView;
	}
	
	private void initBaseView(){
		listview = contentView.findViewById(R.id.listview);
		refreshLayout = contentView.findViewById(R.id.refreshLayout);
	}
	
	private void initList(){
		Log.i("initList", "goodsIndex: " + goodsIndex + ",  option: "+option);
		if ( goodsIndex == -1 && GoodsCache.goodsBean!=null){
			adapter = new HomegoodsAdapter(getContext(),GoodsCache.goodsBean.getDatas().get(option).getProducts());
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					GoodsCache.addOrder(adapter.getItem(position),getContext());
				}
			});
		}else {
			GoodsBean goodsBean =  GoodsCache.goodsList.getGoodsListBean().get(goodsIndex);
			List<ProductsBean> productsBean = goodsBean.getDatas().get(option).getProducts();
			adapter = new HomegoodsAdapter(getContext(),productsBean);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					GoodsCache.addOrder(adapter.getItem(position),getContext());
				}
			});
		}
	}
	
	private void initRefresh(){
		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				if ( goodsIndex == -1 && GoodsCache.goodsBean!=null ){
					index++;
					presenter.loadMoreGoodsBean(GoodsCache.office.getId(),index,size);
				}
				refreshLayout.finishLoadMore(500);
			}
		});
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				index = 1;
				if ( goodsIndex == -1 && GoodsCache.goodsBean!=null){
					presenter.initGoodsBean(GoodsCache.office.getId(),index,size);
				}else {
				
				}
				refreshLayout.finishRefresh(100);
			}
		});
	}
	
	@Subscribe
	public void subscribe(GoodsListEvent event){
		if (goodsIndex!=-1){
			goodsIndex = event.getGoodsIndex();
			initList();
		}else {initList();}
	}
}
