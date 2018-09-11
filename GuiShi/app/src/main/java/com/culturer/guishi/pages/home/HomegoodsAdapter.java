package com.culturer.guishi.pages.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.culturer.guishi.R;
import com.culturer.guishi.bean.ProductsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/10 0010.
 */

public class HomegoodsAdapter extends BaseAdapter {
	
	private Context context;
	private List<ProductsBean> products;
	
	public HomegoodsAdapter(Context context,List<ProductsBean> products) {
		this.context = context;
		this.products = products;
	}
	
	@Override
	public int getCount() {
		return products.size();
	}
	
	@Override
	public ProductsBean getItem(int position) {
		return products.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return products.get(position).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.homegoods_item,null);
		TextView name = view.findViewById(R.id.name);
		TextView desc = view.findViewById(R.id.desc);
		TextView num = view.findViewById(R.id.num);
		TextView price = view.findViewById(R.id.price);
		ImageView icon = view.findViewById(R.id.icon);
		ImageView buy = view.findViewById(R.id.buy);
		name.setText(getItem(position).getName());
		desc.setText(getItem(position).getDesc());
		num.setText("库存："+getItem(position).getNum());
		price.setText("￥"+getItem(position).getPrice());
		return view;
	}
}
