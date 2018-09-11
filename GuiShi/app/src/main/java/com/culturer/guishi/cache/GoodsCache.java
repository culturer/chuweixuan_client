package com.culturer.guishi.cache;

import android.content.Context;
import android.widget.Toast;

import com.culturer.guishi.bean.GoodsBean;
import com.culturer.guishi.bean.GoodsListBean;
import com.culturer.guishi.bean.OrderBean;
import com.culturer.guishi.bean.OrderDataBean;
import com.culturer.guishi.bean.ProductsBean;
import com.culturer.guishi.bean.StoresBean;
import com.culturer.guishi.bean.StoresDataBean;
import com.culturer.guishi.event.OrderEvent;
import com.culturer.guishi.util.PreferenceUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class GoodsCache {
	
	public static GoodsBean goodsBean = new GoodsBean();
	public static StoresDataBean shoppers = new StoresDataBean();
	public static StoresBean office = new StoresBean();
	public static GoodsListBean goodsList = new GoodsListBean();
	public static List<BuyCars> tmpOrderItem = new ArrayList<>();
	//一直缓存在APP中的临时订单
	public static OrderBean tmpOrderBean = new OrderBean();
	//提交后未付款的临时订单
	public static OrderBean tmpOrderBean1 = new OrderBean();
	public static OrderDataBean orderDataBean ;
	
	private static Gson gson = new Gson();
	public static void addOrder(ProductsBean productsBean, Context context){
		boolean flag = false;
		for (int i=0 ;i<GoodsCache.tmpOrderItem.size();i++){
			if (GoodsCache.tmpOrderItem.get(i).getProductsBean().getId() == productsBean.getId()){
				int num = GoodsCache.tmpOrderItem.get(i).getProductsBean().getNum()+1;
				GoodsCache.tmpOrderItem.get(i).getProductsBean().setNum(num);
				flag = true;
				break;
			}
		}
		if (!flag){
			productsBean.setNum(1);
			GoodsCache.tmpOrderItem.add(new BuyCars(productsBean,false));
			Toast.makeText(context," 【 "+productsBean.getName()+"】 添加购物车成功！",Toast.LENGTH_SHORT).show();
		}
		List<ProductsBean> tmpItem = new ArrayList<>();
		for (int i = 0;i<GoodsCache.tmpOrderItem.size();i++){
			tmpItem.add(GoodsCache.tmpOrderItem.get(i).getProductsBean());
		}
		String strOrderItem = gson.toJson(tmpItem);
		tmpOrderBean.setItem(strOrderItem);
		String strOrder = gson.toJson(tmpOrderBean);
		PreferenceUtil.commitString("tmpOrder",strOrder);
		EventBus.getDefault().post(new OrderEvent());
	}
	
	public static class BuyCars{
		
		private ProductsBean productsBean;
		private boolean isChecked;
		public BuyCars() {}
		public BuyCars(ProductsBean productsBean, boolean isChecked) {
			this.productsBean = productsBean;
			this.isChecked = isChecked;
		}
		public ProductsBean getProductsBean() {
			return productsBean;
		}
		public void setProductsBean(ProductsBean productsBean) {
			this.productsBean = productsBean;
		}
		public boolean isChecked() {
			return isChecked;
		}
		public void setChecked(boolean checked) {
			isChecked = checked;
		}
	}
	
}
