package com.culturer.guishi.pages.mine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.culturer.guishi.R;
import com.culturer.guishi.bean.OrderBean;
import com.culturer.guishi.bean.ProductsBean;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class OrderAdapter extends BaseExpandableListAdapter {
	
	private static final String TAG = "OrderAdapter" ;
	
	private Context context;
	private int position;
	private Gson gson;
	
	public OrderAdapter(Context context,int position) {
		this.context = context;
		this.position = position;
		gson = new Gson();
	}
	
	@Override
	public int getGroupCount() {
		if (position<3){
			return GoodsCache.orderDataBean.getDatas().get(position).getOrderData().size();
		}else {
			int size;
			size = GoodsCache.orderDataBean.getDatas().get(0).getOrderData().size()
					+ GoodsCache.orderDataBean.getDatas().get(1).getOrderData().size()
					+ GoodsCache.orderDataBean.getDatas().get(2).getOrderData().size();
			return 	size;
		}
	}
	
	@Override
	public int getChildrenCount(int i) {
		if (position<3){
			List<ProductsBean> datas = gson.fromJson(GoodsCache.orderDataBean.getDatas().get(position).getOrderData().get(i).getItem(), new TypeToken<List<ProductsBean>>(){}.getType());
			return datas.size();
		}else {
			return 0;
		}
	}
	
	@Override
	public OrderBean getGroup(int i) {
		if (position<3){
			return GoodsCache.orderDataBean.getDatas().get(position).getOrderData().get(i);
		}else {
			return null;
		}
	}
	
	@Override
	public ProductsBean getChild(int i, int i1) {
		if (position<3){
			List<ProductsBean> datas = gson.fromJson(GoodsCache.orderDataBean.getDatas().get(position).getOrderData().get(i).getItem(), new TypeToken<List<ProductsBean>>(){}.getType());
			return datas.get(i1);
		}else {
			return null;
		}
	}
	
	@Override
	public long getGroupId(int i) {
		return 0;
	}
	
	@Override
	public long getChildId(int i, int i1) {
		return 0;
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	@Override
	public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
		View gruopView = LayoutInflater.from(context).inflate(R.layout.order_group,null);
		TextView order_time = gruopView.findViewById(R.id.order_time);
		TextView group_id = gruopView.findViewById(R.id.group_id);
		TextView all_price = gruopView.findViewById(R.id.all_price);
		TextView group_tel = gruopView.findViewById(R.id.group_tel);
		TextView send = gruopView.findViewById(R.id.send);
		OrderBean orderBean = getGroup(i);
		if (orderBean!=null){
			String strTime = TimeUtil.getDataToString(orderBean.getAddTime()*1000);
			order_time.setText(strTime);
			group_id.setText( "订单编号：" + orderBean.getId());
			group_tel.setText("联系电话：" + orderBean.getTel());
			all_price.setText("金额："+orderBean.getAmount());
		}
		send.setBackground(context.getDrawable(R.drawable.bg_circle));
		if ( position == 0 ){
			send.setText("付    款");
			send.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				
				}
			});
		}else if (position == 1){
			send.setText("提醒卖家");
			send.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				
				}
			});
		}else if (position == 2){
			send.setText("签    收");
			send.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				
				}
			});
		}
		return gruopView;
	}
	
	@Override
	public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
		View childView = LayoutInflater.from(context).inflate(R.layout.order_item,null);
		TextView name = childView.findViewById(R.id.name);
		TextView count = childView.findViewById(R.id.count);
		TextView price = childView.findViewById(R.id.price);
		ProductsBean productsBean = getChild(i,i1);
		if (productsBean!=null){
			name.setText(productsBean.getName());
			count.setText( "数量：" + productsBean.getNum() );
			price.setText( "￥" + productsBean.getPrice() );
		}
		return childView;
	}
	
	@Override
	public boolean isChildSelectable(int i, int i1) {
		return true;
	}
}
