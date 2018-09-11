package com.culturer.guishi.bean;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class ProductTypeBean {
	/**
	 * Id : 1
	 * Name : 烟酒
	 * PartnerId : 1
	 * StoreId : 4
	 */
	
	private int Id;
	private String Name;
	private int PartnerId;
	private int StoreId;
	
	public int getId() {
		return Id;
	}
	
	public void setId(int Id) {
		this.Id = Id;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	public int getPartnerId() {
		return PartnerId;
	}
	
	public void setPartnerId(int PartnerId) {
		this.PartnerId = PartnerId;
	}
	
	public int getStoreId() {
		return StoreId;
	}
	
	public void setStoreId(int StoreId) {
		this.StoreId = StoreId;
	}
}
