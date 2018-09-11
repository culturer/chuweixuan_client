package com.culturer.guishi.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/2 0002.
 */

public class GoodsListBean {
	
	public List<GoodsBean> goodsListBean = new ArrayList<>();
	
	public GoodsListBean() {
	}
	
	public GoodsListBean(List<GoodsBean> goodsListBean) {
		this.goodsListBean = goodsListBean;
	}
	
	public List<GoodsBean> getGoodsListBean() {
		return goodsListBean;
	}
	
	public void setGoodsListBean(List<GoodsBean> goodsListBean) {
		this.goodsListBean = goodsListBean;
	}
}
