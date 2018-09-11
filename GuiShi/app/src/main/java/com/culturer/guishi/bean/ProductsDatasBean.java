package com.culturer.guishi.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class ProductsDatasBean {
	/**
	 * ProductType : {"Id":1,"Name":"烟酒","PartnerId":1,"StoreId":4}
	 * Count : 1
	 * Products : [{"Id":4,"Name":"黄鹤楼","ProductTypeId":1,"Desc":"烟","APrice":20,"Price":"100","Num":100,"IsLock":false,"Icon":"","SellNum":20,"StoreId":4,"Sort":0,"Format":"","Type":false,"AddTime":0}]
	 */
	
	private ProductTypeBean ProductType;
	private int Count;
	private List<ProductsBean> Products;
	
	public ProductTypeBean getProductType() {
		return ProductType;
	}
	
	public void setProductType(ProductTypeBean ProductType) {
		this.ProductType = ProductType;
	}
	
	public int getCount() {
		return Count;
	}
	
	public void setCount(int Count) {
		this.Count = Count;
	}
	
	public List<ProductsBean> getProducts() {
		return Products;
	}
	
	public void setProducts(List<ProductsBean> Products) {
		this.Products = Products;
	}
}

