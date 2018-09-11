package com.culturer.guishi.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class GoodsBean {
	
	/**
	 * datas : [{"ProductType":{"Id":1,"Name":"烟酒","PartnerId":1,"StoreId":4},"Count":1,"Products":[{"Id":4,"Name":"黄鹤楼","ProductTypeId":1,"Desc":"烟","APrice":20,"Price":"100","Num":100,"IsLock":false,"Icon":"","SellNum":20,"StoreId":4,"Sort":0,"Format":"","Type":false,"AddTime":0}]},{"ProductType":{"Id":2,"Name":"零食","PartnerId":0,"StoreId":4},"Count":1,"Products":[{"Id":6,"Name":"花菜","ProductTypeId":2,"Desc":"花菜","APrice":2,"Price":"3","Num":1000,"IsLock":false,"Icon":"","SellNum":200,"StoreId":4,"Sort":0,"Format":"","Type":false,"AddTime":0}]},{"ProductType":{"Id":3,"Name":"蔬菜","PartnerId":0,"StoreId":4},"Count":1,"Products":[{"Id":5,"Name":"江小白","ProductTypeId":3,"Desc":"酒","APrice":100,"Price":"200","Num":100,"IsLock":false,"Icon":"","SellNum":20,"StoreId":4,"Sort":0,"Format":"","Type":false,"AddTime":0}]},{"ProductType":{"Id":4,"Name":"水果","PartnerId":0,"StoreId":4},"Count":0,"Products":[]},{"ProductType":{"Id":5,"Name":"哈哈哈","PartnerId":0,"StoreId":4},"Count":0,"Products":[]},{"ProductType":{"Id":6,"Name":"1231456687","PartnerId":0,"StoreId":4},"Count":0,"Products":[]},{"ProductType":{"Id":7,"Name":"123456789","PartnerId":0,"StoreId":4},"Count":0,"Products":[]},{"ProductType":{"Id":8,"Name":"369258147","PartnerId":0,"StoreId":4},"Count":0,"Products":[]},{"ProductType":{"Id":9,"Name":"坚果","PartnerId":0,"StoreId":4},"Count":0,"Products":[]}]
	 * status : 200
	 * time : 2018-07-28 13:06:55
	 */
	
	private int status;
	private String time;
	private List<ProductsDatasBean> datas;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public List<ProductsDatasBean> getDatas() {
		return datas;
	}
	
	public void setDatas(List<ProductsDatasBean> datas) {
		this.datas = datas;
	}
	
}
