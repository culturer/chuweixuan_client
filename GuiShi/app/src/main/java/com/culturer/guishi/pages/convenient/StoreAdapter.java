package com.culturer.guishi.pages.convenient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.culturer.guishi.R;
import com.culturer.guishi.bean.ShopperBean;
import com.culturer.guishi.bean.StoresBean;
import com.culturer.guishi.bean.StoresDataBean;
import com.culturer.guishi.bean.TabBean;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/7/10 0010.
 */

public class StoreAdapter extends BaseAdapter {
	
	private Context context;
	ShopperBean shopperBean;
	Gson gson = new Gson();
	
	public StoreAdapter(Context context,ShopperBean shopperBean ) {
		this.context = context;
		this.shopperBean = shopperBean;
	}
	
	@Override
	public int getCount() {
		return shopperBean.getStores().size();
	}
	
	@Override
	public StoresBean getItem(int position) {
		return shopperBean.getStores().get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return shopperBean.getStores().get(position).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = LayoutInflater.from(context).inflate(R.layout.store_item,null);
		ImageView icon = view.findViewById(R.id.icon);
		TextView name = view.findViewById(R.id.name);
		TextView distant = view.findViewById(R.id.distant);
		TextView desc = view.findViewById(R.id.desc);
		TextView tab1 = view.findViewById(R.id.tab1);
		TextView tab2 = view.findViewById(R.id.tab2);
		TextView tab3 = view.findViewById(R.id.tab3);
		name.setText(getItem(position).getName());
		distant.setText("500m");
		desc.setText(getItem(position).getDesc());
		TabBean tabBean = gson.fromJson(getItem(position).getTab(),TabBean.class);
		tab1.setText(tabBean.getTabs().get(0));
		tab2.setText(tabBean.getTabs().get(1));
		tab3.setText(tabBean.getTabs().get(2));
		return view;
	}
	
	public void update(){
		notifyDataSetChanged();
	}
}
