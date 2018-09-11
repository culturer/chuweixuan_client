package com.culturer.guishi.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/28 0028.
 */

public class TabBean {
	private List<String> tabs = new ArrayList<>(3);
	
	public List<String> getTabs() {
		return tabs;
	}
	
	public void setTabs(List<String> tabs) {
		this.tabs = tabs;
	}
}
