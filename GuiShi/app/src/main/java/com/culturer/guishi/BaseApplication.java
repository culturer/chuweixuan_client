package com.culturer.guishi;

import android.app.Application;

import com.culturer.guishi.util.PreferenceUtil;
import com.kymjs.okhttp3.OkHttpStack;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.http.RequestQueue;
import com.vondear.rxtools.RxTool;


import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/7/26 0026.
 */

public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init(){
		//初始化常用工具类
		RxTool.init(this);
		//初始化Preference工具
		PreferenceUtil.init(this);
//		使用okhttp代替httpurlconnection
		RxVolley.setRequestQueue(RequestQueue.newRequestQueue(RxVolley.CACHE_FOLDER, new OkHttpStack(new OkHttpClient())));
	}
	
}
