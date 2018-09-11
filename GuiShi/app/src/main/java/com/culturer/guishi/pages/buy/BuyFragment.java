package com.culturer.guishi.pages.buy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.culturer.guishi.MainPresenter;
import com.culturer.guishi.R;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.event.OrderEvent;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.culturer.guishi.MainPresenter.HOST;

public class BuyFragment extends Fragment {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	private String mParam1;
	private String mParam2;
	
	private View contentView;
	private ListView listView;
	
	private CheckBox isCheck;
	private TextView all_price1;
	private TextView submit;
	
	private BuyAdapter adapter;
	
	public BuyFragment() {
		// Required empty public constructor
	}
	
	public static BuyFragment newInstance(String param1, String param2) {
		BuyFragment fragment = new BuyFragment();
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
		EventBus.getDefault().register(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView = inflater.inflate(R.layout.fragment_buy, container, false);
			initBaseView();
			initList();
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if ( parent!=null ){
			parent.removeView(contentView);
		}
		return contentView;
	}
	
	private void initBaseView(){
		listView = contentView.findViewById(R.id.listView);
		isCheck = contentView.findViewById(R.id.isCheck);
		all_price1 = contentView.findViewById(R.id.all_price1);
		submit = contentView.findViewById(R.id.submit);
		
		isCheck.setChecked(false);
		isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				for (int i=0;i<GoodsCache.tmpOrderItem.size();i++){
					GoodsCache.tmpOrderItem.get(i).setChecked(isChecked);
				}
				update();
			}
		});
		
		float all_price = 0;
		for (int i=0;i<GoodsCache.tmpOrderItem.size();i++){
			if (GoodsCache.tmpOrderItem.get(i).isChecked()){
				all_price += (
						GoodsCache.tmpOrderItem.get(i).getProductsBean().getPrice() *
						GoodsCache.tmpOrderItem.get(i).getProductsBean().getNum()
				);
			}
		}
		all_price1.setText(" ￥ "+all_price);

		if (all_price>0){
			submit.setTextColor(getContext().getColor(R.color.white));
			submit.setBackground(getContext().getDrawable(R.color.black));
			submit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MainPresenter.commitOrder(getContext());
				}
			});
		}else {
			submit.setTextColor(getContext().getColor(R.color.black));
			submit.setBackground(getContext().getDrawable(R.color.grey));
			submit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getContext(),"您购物车还空空如也哦~",Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
	private void initList(){
		adapter = new BuyAdapter(getContext());
		listView.setAdapter(adapter);
	}                  
	
	private void update(){
		adapter.update();
		float all_price = 0;
		for (int i=0;i<GoodsCache.tmpOrderItem.size();i++){
			if (GoodsCache.tmpOrderItem.get(i).isChecked()){
				all_price += (
						GoodsCache.tmpOrderItem.get(i).getProductsBean().getPrice() *
								GoodsCache.tmpOrderItem.get(i).getProductsBean().getNum()
				);
			}
		}
		all_price1.setText(" ￥ "+all_price);
		if (all_price>0){
			submit.setTextColor(getContext().getColor(R.color.white));
			submit.setBackground(getContext().getDrawable(R.color.black));
			submit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MainPresenter.commitOrder(getContext());
				}
			});
		}else {
			submit.setTextColor(getContext().getColor(R.color.black));
			submit.setBackground(getContext().getDrawable(R.color.grey));
			submit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getContext(),"您购物车还空空如也哦~",Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
	
	@Subscribe
	public void updateOrders(OrderEvent event){
		update();
	}
}
