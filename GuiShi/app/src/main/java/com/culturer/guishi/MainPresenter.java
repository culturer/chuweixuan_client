package com.culturer.guishi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.culturer.guishi.bean.GoodsBean;
import com.culturer.guishi.bean.OrderBean;
import com.culturer.guishi.bean.OrderDataBean;
import com.culturer.guishi.bean.ProductsBean;
import com.culturer.guishi.bean.ProductsDatasBean;
import com.culturer.guishi.bean.StoresBean;
import com.culturer.guishi.bean.StoresDataBean;
import com.culturer.guishi.bean.UserBean;
import com.culturer.guishi.cache.BaseCache;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.event.GoodsListEvent;
import com.culturer.guishi.event.OrderEvent;
import com.culturer.guishi.event.OrderListEvent;
import com.culturer.guishi.event.StoreEvent;
import com.culturer.guishi.util.PreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
	
	public static final String TAG = "MainPresenter";
	public static final String HOST = "http://192.168.1.110:8080";
	
	private Gson gson = new Gson();
	private static MainPresenter instance;
	private MainPresenter() {}
	public static MainPresenter getInstance(){
		if (instance!=null){
			return instance;
		}else {
			return new MainPresenter();
		}
	}
	
	public void init(Context context){
		initUser(context);
		initShopper();
		initOffice();
		Log.i("officeId", "init: "+GoodsCache.office.getId());
		initGoodsBean(GoodsCache.office.getId(),1,15);
		initBuyCars();
	}
	
	private void initUser(Context context){
		String strUser = PreferenceUtil.getString("user","");
		if ( !strUser.equals("") ){
			BaseCache.user = gson.fromJson(strUser, UserBean.class);
		}else {
			context.startActivity(new Intent(context,LoginActivity.class));
		}
	}
	
	public void initShopper(){
		String strShoppers = PreferenceUtil.getString("shoppers","");
		if (!strShoppers.equals("")){
			GoodsCache.shoppers = gson.fromJson(strShoppers,StoresDataBean.class);
		}
		HttpParams params = new HttpParams();
		params.put("options","getShopper");
		params.put("communityId",1);
		params.put("isOffice",0);
		params.put("size",100);
		params.put("index",1);
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i("initShopper", "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if ( status == 200 ){
						GoodsCache.shoppers = gson.fromJson(t,StoresDataBean.class);
						PreferenceUtil.commitString("shoppers",t);
						EventBus.getDefault().post(new StoreEvent());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Log.i("initShopper", "onFailure: "+error.getMessage());
			}
		};
		new RxVolley.Builder()
				.url(HOST+"/client")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
	}
	
	private void initOffice(){
		Log.i("initShopper", "initOffice ");
		String strOffice = PreferenceUtil.getString("office","");
		if (!strOffice.equals("")){
			GoodsCache.office = gson.fromJson(strOffice,StoresBean.class);
		}
		HttpParams params = new HttpParams();
		params.put("options","getShopper");
		params.put("communityId",1);
		params.put("isOffice",1);
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i("initOffice", "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if ( status == 200 ){
						String strOffice = jsonObject.getString("shopper");
						Log.i("initOffice", "strOffice: "+strOffice);
						GoodsCache.office = gson.fromJson(strOffice,StoresBean.class);
						PreferenceUtil.commitString("office",strOffice);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Log.i("initShopper", "onFailure: "+error.getMessage());
			}
		};
		new RxVolley.Builder()
				.url(HOST+"/client")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
	}
	
	public void initGoodsBean(int storeId,int index,int size){
		Log.i("initGoodsBean", "initGoodsBean: ");
		String strGoodsBean = PreferenceUtil.getString("goodsBean","");
		Log.i("initGoodsBean", "read goodsBean info from preference: "+strGoodsBean);
		if (!strGoodsBean.equals("")){
			GoodsCache.goodsBean = gson.fromJson(strGoodsBean,GoodsBean.class);
			Log.i("initGoodsBean", "read goodsBean info from gson "+gson.toJson(GoodsCache.goodsBean));
		}
		HttpParams params = new HttpParams();
		params.put("options","getGoods");
		params.put("storeId",storeId);
		params.put("index",index);
		params.put("size",size);
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i("initGoodsBean", " : "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if ( status == 200 ){
						if (gson == null){
							gson = new Gson();
						}
						GoodsCache.goodsBean = gson.fromJson(t,GoodsBean.class);
						PreferenceUtil.commitString("goodsBean",t);
						EventBus.getDefault().post(new GoodsListEvent(-1));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Log.i("initShopper", "onFailure: "+error.getMessage());
			}
		};
		new RxVolley.Builder()
				.url(HOST+"/client")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
	}
	
	public void loadMoreGoodsBean(int storeId,int index,int size){
		if ( GoodsCache.goodsBean !=null ){
			HttpParams params = new HttpParams();
			params.put("options","getGoods");
			params.put("storeId",storeId);
			params.put("index",index);
			params.put("size",size);
			HttpCallback callback = new HttpCallback() {
				@Override
				public void onSuccess(String t) {
					Log.i("initGoodsBean", " : "+t);
					try {
						JSONObject jsonObject = new JSONObject(t);
						int status = jsonObject.getInt("status");
						if ( status == 200 ){
							if (gson == null){
								gson = new Gson();
							}
							GoodsBean goodsBean = gson.fromJson(t,GoodsBean.class);
							GoodsCache.goodsBean.setStatus(200);
							GoodsCache.goodsBean.setTime(goodsBean.getTime());
							List<ProductsDatasBean> datas = GoodsCache.goodsBean.getDatas();
							if (datas!=null){
								for (int i=0 ; i<goodsBean.getDatas().size();i++){
									datas.add(goodsBean.getDatas().get(i));
								}
							}
							GoodsCache.goodsBean.setDatas(datas);
							String strGoodsBean = gson.toJson(GoodsCache.goodsBean);
							PreferenceUtil.commitString("goodsBean",strGoodsBean);
							EventBus.getDefault().post(new GoodsListEvent(-1));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onFailure(VolleyError error) {
					Log.i("initShopper", "onFailure: "+error.getMessage());
				}
			};
			new RxVolley.Builder()
					.url(HOST+"/client")
					.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
					.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
					.params(params)
					.shouldCache(false) //default: get true, post false
					.callback(callback)
					.encoding("UTF-8") //default
					.doTask();
		}else {
			initGoodsBean(GoodsCache.office.getId(),1,15);
		}
	}
	
	private void initBuyCars(){
		String strOrderBean = PreferenceUtil.getString("tmpOrder","");
		if (!strOrderBean.equals("")){
			GoodsCache.tmpOrderBean = gson.fromJson(strOrderBean, OrderBean.class);
			Log.i("initBuyCars", "initBuyCars: "+GoodsCache.tmpOrderBean.getItem());
			List<ProductsBean> datas = gson.fromJson(GoodsCache.tmpOrderBean.getItem(), new TypeToken<List<ProductsBean>>(){}.getType());
			if (datas!=null){
				for (int i=0;i<datas.size();i++){
					GoodsCache.tmpOrderItem.add(new GoodsCache.BuyCars(datas.get(i),false));
				}
			}
		}
		String strOrderBean1 = PreferenceUtil.getString("tmpOrder1","");
		Log.i(TAG, "strOrderBean1: "+strOrderBean1);
		if (!strOrderBean1.equals("")){
			GoodsCache.tmpOrderBean1 = gson.fromJson(strOrderBean1, OrderBean.class);
			Log.i(TAG, "initBuyCars: "+gson.toJson(GoodsCache.tmpOrderBean1));
		}
		EventBus.getDefault().post(new OrderEvent());
	}
	
	
	public static void commitOrder(Context context){
		
		Gson gson = new Gson();
		List<ProductsBean> productsBeans1;
		List<GoodsCache.BuyCars> buyCars;
		OrderBean orderBean ;
		
		HttpParams params = new HttpParams();
		params.put("options","submitOrder");
		
		if (GoodsCache.tmpOrderBean != null && BaseCache.user!=null){
			//获取临时订单
			orderBean = GoodsCache.tmpOrderBean;
			orderBean.setUserId(BaseCache.user.getId());
			orderBean.setTel(BaseCache.user.getTel());
			orderBean.setAddress(BaseCache.user.getAddress());
			orderBean.setReceiver(BaseCache.user.getPayee());
			orderBean.setDiscount(false);
			orderBean.setDiscountMsg("");
			orderBean.setMsg("");
			orderBean.setPay(false);
			orderBean.setAppoint(false);
			orderBean.setAppointTime(0);
			orderBean.setSend(false);
			orderBean.setSend1(false);
			orderBean.setSenderId(-1);
			orderBean.setSendType(-1);
			orderBean.setReceive(false);
			orderBean.setReceiveTime(-1);
			orderBean.setCancel(false);
			orderBean.setCancelMsg("");
			orderBean.setAddTime(System.currentTimeMillis());
			orderBean.setPayType("none");
			//productsBeans 用户勾选的商品，提交到服务器
			List<ProductsBean> productsBeans = new ArrayList<>();
			//productsBeans1 用户没有勾选的商品，继续存放在购物车
			productsBeans1 = new ArrayList<>();
			buyCars = new ArrayList<>();
			float amount = 0.0f;
			for (int i=0;i<GoodsCache.tmpOrderItem.size();i++){
				if (GoodsCache.tmpOrderItem.get(i).isChecked()){
					productsBeans.add(GoodsCache.tmpOrderItem.get(i).getProductsBean());
					amount += GoodsCache.tmpOrderItem.get(i).getProductsBean().getNum() * GoodsCache.tmpOrderItem.get(i).getProductsBean().getPrice();
				}else {
					buyCars.add(GoodsCache.tmpOrderItem.get(i));
					productsBeans1.add(GoodsCache.tmpOrderItem.get(i).getProductsBean());
				}
			}
			orderBean.setAmount(amount);
			String strItems = gson.toJson(productsBeans);
			orderBean.setItem(strItems);
			try {
				GoodsCache.tmpOrderBean1 = (OrderBean) orderBean.clone();
				String strOrder1 = gson.toJson(GoodsCache.tmpOrderBean1);
				PreferenceUtil.commitString("tmpOrder1",strOrder1);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			params.put("UserId",""+orderBean.getUserId());
			params.put("Tel",orderBean.getTel());
			params.put("Address",orderBean.getAddress());
			params.put("Receiver",orderBean.getReceiver());
			params.put("Amount",""+orderBean.getAmount());
			params.put("Pay",""+orderBean.isPay());
			params.put("PayType",""+orderBean.getPayType());
			params.put("IsDiscounts",""+orderBean.isDiscount());
			params.put("DiscountMsg",""+orderBean.getDiscountMsg());
			params.put("Item",orderBean.getItem());
			params.put("IsPay",""+orderBean.isPay());
			params.put("Msg","" + orderBean.getMsg());
			params.put("Appoint",""+orderBean.isAppoint());
			params.put("AppointTime",""+orderBean.getAppointTime());
			params.put("IsSend",""+orderBean.isSend());
			params.put("IsSend1",""+orderBean.isSend1());
			params.put("SenderId",orderBean.getSenderId());
			params.put("SendType",orderBean.getSendType());
			params.put("IsReceive",""+orderBean.isReceive());
			params.put("ReceiveTiem",""+orderBean.getReceiveTime());
			params.put("IsCancel",""+orderBean.isCancel());
			params.put("CancelMsg","" + orderBean.getCancelMsg());
			params.put("AddTime",""+orderBean.getAddTime());
			
		}else {
			productsBeans1 = new ArrayList<>();
			buyCars = new ArrayList<>();
			orderBean = null;
			Log.i("commitOrder", "tmpOrderBean is null !");
		}
		
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i("commitOrder", "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					if (jsonObject.getInt("status") == 200){
						Intent intent = new Intent(context,PayActivity.class);
						context.startActivity(intent);
						//刷新购物车列表
						EventBus.getDefault().post(new OrderEvent());
						//将刷新后的购物车数据保存本地
						String strItems1 = gson.toJson(productsBeans1);
						GoodsCache.tmpOrderBean.setItem(strItems1);
						GoodsCache.tmpOrderItem = buyCars;
						String strOrder = gson.toJson(GoodsCache.tmpOrderBean);
						PreferenceUtil.commitString("tmpOrder",strOrder);
						//更新订单列表
						long orderId = jsonObject.getLong("orderId");
						GoodsCache.tmpOrderBean1.setId(orderId);
						int count =  GoodsCache.orderDataBean.getDatas().get(0).getCount()+1;
						GoodsCache.orderDataBean.getDatas().get(0).setCount(count);
						GoodsCache.orderDataBean.getDatas().get(0).getOrderData().add(0,GoodsCache.tmpOrderBean1);
						EventBus.getDefault().post(new OrderListEvent());
					}else {
						Toast.makeText(context,"提交订单失败,商家正在快马加鞭的处理中,请耐心等待~",Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int errorNo, String strMsg) {
				Log.i("commitOrder", "onFailure: "+strMsg);
			}
		};
		
		if (!GoodsCache.tmpOrderBean.getItem().equals("")){
			new RxVolley.Builder()
					.url(HOST+"/client")
					.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
					.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
					.params(params)
					.shouldCache(false) //default: get true, post false
					.callback(callback)
					.encoding("UTF-8") //default
					.doTask();
		}else {
			Toast.makeText(context,"请添加商品后再次提交!",Toast.LENGTH_SHORT).show();
		}
	}
	
	public static void getOrders(Context context){
		
		Gson gson = new Gson();
		HttpParams params = new HttpParams();
		params.put("options","getOrders");
		params.put("userId",BaseCache.user.getId());
		params.put("index",1);
		params.put("size",10);
		
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i("getOrders", "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if ( status == 200 ){
						GoodsCache.orderDataBean = gson.fromJson(t, OrderDataBean.class);
						EventBus.getDefault().post(new OrderListEvent());
					}else {
						Toast.makeText(context,"获取订单列表失败，请稍后再试！",Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(int errorNo, String strMsg) {
				Log.i("getOrders", "onFailure: "+strMsg);
			}
		};
		new RxVolley.Builder()
				.url(HOST+"/client")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
	}
	
}
