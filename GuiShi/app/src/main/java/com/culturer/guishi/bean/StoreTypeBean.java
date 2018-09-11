package com.culturer.guishi.bean;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class StoreTypeBean {
	/**
	 * Id : 1
	 * Name : 便利店
	 * PartnerId : 0
	 * CommunityId : 0
	 * IsOffice : false
	 */
	
	private int Id;
	private String Name;
	private int PartnerId;
	private int CommunityId;
	private boolean IsOffice;
	
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
	
	public int getCommunityId() {
		return CommunityId;
	}
	
	public void setCommunityId(int CommunityId) {
		this.CommunityId = CommunityId;
	}
	
	public boolean isIsOffice() {
		return IsOffice;
	}
	
	public void setIsOffice(boolean IsOffice) {
		this.IsOffice = IsOffice;
	}
	
}
