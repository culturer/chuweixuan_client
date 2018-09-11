package com.culturer.guishi.pages.convenient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.culturer.guishi.MainPresenter;
import com.culturer.guishi.R;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.event.GoodsListEvent;
import com.culturer.guishi.event.StoreEvent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import okhttp3.Cache;


public class StoreFragment extends Fragment {

	private static final String POSITION = "position";
	private static final String ARG_PARAM2 = "param2";

	private int position;
	private String mParam2;

	private View contentView;
	private ListView listView;
	RefreshLayout refreshLayout;
	
	private StoreAdapter adapter;
	
	public StoreFragment() {
		// Required empty public constructor
	}
	

	public static StoreFragment newInstance(int position, String param2) {
		StoreFragment fragment = new StoreFragment();
		Bundle args = new Bundle();
		args.putInt(POSITION, position);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			position = getArguments().getInt(POSITION);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		EventBus.getDefault().register(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView = inflater.inflate(R.layout.fragment_store, container, false);
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
		listView = contentView.findViewById(R.id.listView);
		refreshLayout = contentView.findViewById(R.id.refreshLayout);
	}
	
	private void initList(){
		adapter = new StoreAdapter(getContext(),GoodsCache.shoppers.getShopper().get(position));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
				Intent intent = new Intent(getContext(),StoreActivity.class);
				intent.putExtra("position",position);
				intent.putExtra("position1",position1);
				startActivity(intent);
			}
		});
	}
	
	private void initRefresh(){
		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				refreshLayout.finishLoadMore();
			}
		});
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				MainPresenter.getInstance().initShopper();
				refreshLayout.finishRefresh(100);
			}
		});
	}
	
	@Subscribe
	public void subscribe(StoreEvent event){
		adapter.update();
	}
}
