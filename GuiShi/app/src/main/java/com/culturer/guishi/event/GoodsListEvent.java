package com.culturer.guishi.event;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class GoodsListEvent {
	
	private int goodsIndex;
	
	public GoodsListEvent(int goodsIndex) {
		this.goodsIndex = goodsIndex;
	}
	
	public int getGoodsIndex() {
		return goodsIndex;
	}
	
	public void setGoodsIndex(int goodsIndex) {
		this.goodsIndex = goodsIndex;
	}
}
