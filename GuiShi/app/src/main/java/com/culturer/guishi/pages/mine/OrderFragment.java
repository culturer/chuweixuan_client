package com.culturer.guishi.pages.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.culturer.guishi.R;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.event.OrderListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class OrderFragment extends Fragment {

	private static final String ORDER_TYPE = "order_type";
	private static final String ARG_PARAM2 = "param2";
	
	public static final String Is_Pay = "is_pay";
	public static final String Is_Send = "is_send";
	public static final String Is_Receive = "is_receive";
	public static final String ALL = "all";
	
	private String order_type;
	private String mParam2;
	
	private View contentView;
	private ExpandableListView expandableListView;
	private OrderAdapter adapter;
	private TextView msg;
	
	public OrderFragment() {
		// Required empty public constructor
	}

	public static OrderFragment newInstance(String order_type, String param2) {
		OrderFragment fragment = new OrderFragment();
		Bundle args = new Bundle();
		args.putString(ORDER_TYPE, order_type);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			order_type = getArguments().getString(ORDER_TYPE);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		EventBus.getDefault().register(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
	                         ViewGroup container,
	                         Bundle savedInstanceState) {
		if (contentView == null){
			contentView =inflater.inflate(R.layout.fragment_order, container, false);
			initBaseView();
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if ( parent!=null ){
			parent.removeView(contentView);
		}
		return contentView;
	}
	private void initBaseView(){
		expandableListView = contentView.findViewById(R.id.expandableListView);
		msg = contentView.findViewById(R.id.msg);
		if ( GoodsCache.orderDataBean!=null ){
			initList();
		}
	}
	
	private void initList(){
		Log.i("OrderFragment", "initList: "+order_type);
		int position = 0;
		if (order_type.equals(Is_Pay)){
			position = 0 ;
		}else if (order_type.equals(Is_Send)){
			position = 1;
		}else if (order_type.equals(Is_Receive)){
			position = 2;
		}else if (order_type.equals(ALL)){
			position = 3;
		}
		if (position<3){
			if (GoodsCache.orderDataBean.getDatas().get(position).getOrderData().size() == 0){
				msg.setVisibility(View.VISIBLE);
			}else {
				msg.setVisibility(View.GONE);
			}
		}
		adapter = new OrderAdapter(getContext(),position);
		expandableListView.setAdapter(adapter);
	}
	
	@Subscribe
	public void subscribe(OrderListEvent event){
		initList();
	}
}
