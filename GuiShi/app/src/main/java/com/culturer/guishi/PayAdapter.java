package com.culturer.guishi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.culturer.guishi.bean.ProductsBean;
import com.culturer.guishi.cache.GoodsCache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/31 0031.
 */

public class PayAdapter extends BaseAdapter {
	
	private static final String TAG = "PayAdapter";
	
 	private List<ProductsBean> items = new ArrayList<>();
	private Context context;
	
	public PayAdapter(Context context) {
		this.context = context;
		Gson gson = new Gson();
		Log.i(TAG, "PayAdapter: "+gson.toJson(GoodsCache.tmpOrderBean1));
		items = gson.fromJson(GoodsCache.tmpOrderBean1.getItem(), new TypeToken<List<ProductsBean>>(){}.getType());
		Log.i(TAG, "PayAdapter: "+gson.toJson(items));
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public ProductsBean getItem(int position) {
		return items.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View contentView = LayoutInflater.from(context).inflate(R.layout.order_item,null);
		TextView name = contentView.findViewById(R.id.name);
		TextView count = contentView.findViewById(R.id.count);
		TextView price = contentView.findViewById(R.id.price);
		name.setText(getItem(position).getName());
		count.setText("数量："+getItem(position).getNum());
		price.setText("￥"+getItem(position).getPrice()*getItem(position).getNum());
		return contentView;
	}
}
