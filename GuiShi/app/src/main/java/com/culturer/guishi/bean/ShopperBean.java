package com.culturer.guishi.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class ShopperBean {
	/**
	 * StoreType : {"Id":1,"Name":"便利店","PartnerId":0,"CommunityId":0,"IsOffice":false}
	 * Count : 0
	 * Stores : [{"Id":5,"IsOffice":false,"Name":"啊啊啊啊","Icon":"","Tel":"联系电话：yggg","Desc":"进来了","Content":"把框架","Tab":"{\"tabs\":[\"进来了\",\"标签二\",\"标签三\"]}","Address":"交流交流","Position":"{ latitude:,longitude:}","UserId":7,"IsClose":false,"CloseMsg":"","OpenTime":"营业时间：2358","IsLock":false,"AddTime":1532864822,"StoreTypeId":1,"CommunityId":0},{"Id":4,"IsOffice":false,"Name":"杂货店","Icon":"","Tel":"联系电话：18588263631","Desc":"烟酒副食","Content":"生活必须品，一元购。","Tab":"{\"tabs\":[\"标签一\",\"标签二\",\"标签三\"]}","Address":"","Position":"{ latitude:,longitude:}","UserId":6,"IsClose":false,"CloseMsg":"高温避暑","OpenTime":"营业时间：9:00～21:00","IsLock":false,"AddTime":1532835834,"StoreTypeId":1,"CommunityId":0}]
	 */
	
	private StoreTypeBean StoreType;
	private int Count;
	private List<StoresBean> Stores;
	
	public StoreTypeBean getStoreType() {
		return StoreType;
	}
	
	public void setStoreType(StoreTypeBean StoreType) {
		this.StoreType = StoreType;
	}
	
	public int getCount() {
		return Count;
	}
	
	public void setCount(int Count) {
		this.Count = Count;
	}
	
	public List<StoresBean> getStores() {
		return Stores;
	}
	
	public void setStores(List<StoresBean> Stores) {
		this.Stores = Stores;
	}
	
	
	
	
}
