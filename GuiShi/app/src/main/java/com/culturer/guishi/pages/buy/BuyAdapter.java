package com.culturer.guishi.pages.buy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.culturer.guishi.R;
import com.culturer.guishi.bean.ProductsBean;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.event.OrderEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/10 0010.
 */

public class BuyAdapter extends BaseAdapter {
	
	private Context context;
	
	public BuyAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return GoodsCache.tmpOrderItem.size();
	}
	
	@Override
	public ProductsBean getItem(int position) {
		return GoodsCache.tmpOrderItem.get(position).getProductsBean();
	}
	
	@Override
	public long getItemId(int position) {
		if (getItem(position)!=null)
			return getItem(position).getId();
		return -1;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = LayoutInflater.from(context).inflate(R.layout.buy_item,null);
		CheckBox isSelected = view.findViewById(R.id.isSelected);
		ImageView icon = view.findViewById(R.id.icon);
		TextView product_name = view.findViewById(R.id.product_name);
		TextView single_price = view.findViewById(R.id.single_price);
		TextView count = view.findViewById(R.id.count);
		TextView price = view.findViewById(R.id.price);
		ImageButton add = view.findViewById(R.id.add);
		ImageButton desc = view.findViewById(R.id.desc);
		
		if (getItem(position) !=null){
			isSelected.setChecked(GoodsCache.tmpOrderItem.get(position).isChecked());
			isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					GoodsCache.tmpOrderItem.get(position).setChecked(isChecked);
					EventBus.getDefault().post(new OrderEvent());
				}
			});
			
			product_name.setText(getItem(position).getName());
			single_price.setText("单价："+getItem(position).getPrice());
			count.setText("数量："+getItem(position).getNum());
			price.setText(""+getItem(position).getPrice()*getItem(position).getNum());
			add.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int count = getItem(position).getNum() + 1;
					if (count>0){
						GoodsCache.tmpOrderItem.get(position).getProductsBean().setNum(count);
					}else {
						GoodsCache.tmpOrderItem.remove(position);
					}
					notifyDataSetChanged();
					EventBus.getDefault().post(new OrderEvent());
				}
			});
			desc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int count = getItem(position).getNum() -1;
					if (count>0){
						GoodsCache.tmpOrderItem.get(position).getProductsBean().setNum(count);
					}else {
						GoodsCache.tmpOrderItem.remove(position);
					}
					notifyDataSetChanged();
					EventBus.getDefault().post(new OrderEvent());
				}
			});
		}
		
		return view;
	}
	
	public void update(){
		notifyDataSetChanged();
	}
}
