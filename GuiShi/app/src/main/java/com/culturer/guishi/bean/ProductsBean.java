package com.culturer.guishi.bean;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class ProductsBean {
	/**
	 * Id : 4
	 * Name : 黄鹤楼
	 * ProductTypeId : 1
	 * Desc : 烟
	 * APrice : 20
	 * Price : 100
	 * Num : 100
	 * IsLock : false
	 * Icon :
	 * SellNum : 20
	 * StoreId : 4
	 * Sort : 0
	 * Format :
	 * Type : false
	 * AddTime : 0
	 */
	
	private int Id;
	private String Name;
	private int ProductTypeId;
	private String Desc;
	private float APrice;
	private float Price;
	private int Num;
	private boolean IsLock;
	private String Icon;
	private int SellNum;
	private int StoreId;
	private int Sort;
	private String Format;
	private boolean Type;
	private int AddTime;
	
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
	
	public int getProductTypeId() {
		return ProductTypeId;
	}
	
	public void setProductTypeId(int ProductTypeId) {
		this.ProductTypeId = ProductTypeId;
	}
	
	public String getDesc() {
		return Desc;
	}
	
	public void setDesc(String Desc) {
		this.Desc = Desc;
	}
	
	public float getAPrice() {
		return APrice;
	}
	
	public void setAPrice(int APrice) {
		this.APrice = APrice;
	}
	
	public float getPrice() {
		return Price;
	}
	
	public void setPrice(float Price) {
		this.Price = Price;
	}
	
	public int getNum() {
		return Num;
	}
	
	public void setNum(int Num) {
		this.Num = Num;
	}
	
	public boolean isIsLock() {
		return IsLock;
	}
	
	public void setIsLock(boolean IsLock) {
		this.IsLock = IsLock;
	}
	
	public String getIcon() {
		return Icon;
	}
	
	public void setIcon(String Icon) {
		this.Icon = Icon;
	}
	
	public int getSellNum() {
		return SellNum;
	}
	
	public void setSellNum(int SellNum) {
		this.SellNum = SellNum;
	}
	
	public int getStoreId() {
		return StoreId;
	}
	
	public void setStoreId(int StoreId) {
		this.StoreId = StoreId;
	}
	
	public int getSort() {
		return Sort;
	}
	
	public void setSort(int Sort) {
		this.Sort = Sort;
	}
	
	public String getFormat() {
		return Format;
	}
	
	public void setFormat(String Format) {
		this.Format = Format;
	}
	
	public boolean isType() {
		return Type;
	}
	
	public void setType(boolean Type) {
		this.Type = Type;
	}
	
	public int getAddTime() {
		return AddTime;
	}
	
	public void setAddTime(int AddTime) {
		this.AddTime = AddTime;
	}
}
