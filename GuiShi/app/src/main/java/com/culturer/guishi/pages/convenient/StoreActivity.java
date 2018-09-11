package com.culturer.guishi.pages.convenient;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.culturer.guishi.MainPresenter;
import com.culturer.guishi.R;
import com.culturer.guishi.bean.GoodsBean;
import com.culturer.guishi.bean.GoodsListBean;
import com.culturer.guishi.bean.StoresBean;
import com.culturer.guishi.bean.TabBean;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.event.GoodsListEvent;
import com.culturer.guishi.pages.home.HomegoodsFragment;
import com.culturer.guishi.pages.home.PagerAdapter;
import com.culturer.guishi.util.PreferenceUtil;
import com.culturer.guishi.util.ThreadUtil;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
	
	private static final String TAG = "StoreActivity";
	private Gson gson = new Gson();
	private  int position ;
	private int position1;
	
	private StoresBean storesBean;
	private GoodsBean goodsBean;
	private boolean isLoad = false;
	private int goodsIndex = 0 ;
	
	private List<String> pagerList = new ArrayList<>();
	private List<Fragment> fragmentList = new ArrayList<>();
	
	private TextView name;
	private ImageView icon;
	private TextView address;
	private TextView tel;
	private TextView desc;
	private TextView tab1;
	private TextView tab2;
	private TextView tab3;
	private TextView open_time;

	private TextView back;
	private TextView toushu;
	
	private TabLayout store_tab;
	private ViewPager store_pager;
	private PagerAdapter pagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		initBaseView();
		initData();
		changeTopBackground();
		
	}
	
	private void initBaseView(){
		name = findViewById(R.id.name);
		icon = findViewById(R.id.icon);
		address = findViewById(R.id.address);
		open_time = findViewById(R.id.open_time);
		tel = findViewById(R.id.tel);
		desc = findViewById(R.id.desc);
		tab1 = findViewById(R.id.tab1);
		tab2 = findViewById(R.id.tab2);
		tab3 = findViewById(R.id.tab3);
		
		back = findViewById(R.id.back);
		toushu = findViewById(R.id.toushu);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StoreActivity.this.finish();
			}
		});
		toushu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			
			}
		});
		
		store_tab = findViewById(R.id.store_tab);
		store_pager = findViewById(R.id.store_pager);
		
	}
	
	private void initData(){
		position = getIntent().getIntExtra("position",0);
		position1 = getIntent().getIntExtra("position1",0);
		Log.i(TAG, "initData: position --- "+position+" , position1 --- "+position1);
		storesBean = GoodsCache.shoppers.getShopper().get(position).getStores().get(position1);
		//如果数据已经被加载则不重复加载，否则加载一次数据
		//检查数据是否已经加载到缓存中
		if (GoodsCache.goodsList.goodsListBean != null){
			for (int i=0;i<GoodsCache.goodsList.goodsListBean.size();i++){
				if (
						(GoodsCache.goodsList.goodsListBean.get(i)!=null)
						&& (GoodsCache.goodsList.goodsListBean.get(i).getDatas().get(0)!=null)
						&& (GoodsCache.goodsList.goodsListBean.get(i).getDatas().get(0).getProducts().get(0)!=null)
				){
					if (GoodsCache.goodsList.goodsListBean.get(i).getDatas().get(0).getProductType().getStoreId() == storesBean.getId()){
						Log.i(TAG, "initData: ");
						goodsIndex = i;
						isLoad = true;
						break;
					}
				}
			}
		}else {
			isLoad = false;
		}
		
		if ( !isLoad ){
			String strGoodsList = PreferenceUtil.getString("goodsList","");
			Log.i("initGoodsBean", "read goodsBean info from preference: "+strGoodsList);
			if ( (!strGoodsList.equals("")) && GoodsCache.goodsList == null ){
				GoodsCache.goodsList = gson.fromJson(strGoodsList,GoodsListBean.class);
				Log.i(TAG, "read goodsBean info to gson "+gson.toJson(GoodsCache.goodsBean));
			}
			if ( GoodsCache.goodsList.getGoodsListBean()!=null && GoodsCache.goodsList.getGoodsListBean().size()>0){
				Log.i(TAG, "initGoodsBean: ");
				goodsBean =  GoodsCache.goodsList.getGoodsListBean().get(goodsIndex);
			}
			HttpParams params = new HttpParams();
			params.put("options","getGoods");
			params.put("storeId",storesBean.getId());
			params.put("index",1);
			params.put("size",15);
			HttpCallback callback = new HttpCallback() {
				@Override
				public void onSuccess(String t) {
					Log.i(TAG, " : "+t);
					try {
						JSONObject jsonObject = new JSONObject(t);
						int status = jsonObject.getInt("status");
						if ( status == 200 ){
							if (gson == null){
								gson = new Gson();
							}
							goodsBean = gson.fromJson(t,GoodsBean.class);
							if (GoodsCache.goodsList.getGoodsListBean() == null){
								GoodsCache.goodsList.setGoodsListBean(new ArrayList<>());
							}
							GoodsCache.goodsList.goodsListBean.add(goodsBean);
							String strGoodsList = gson.toJson(GoodsCache.goodsList);
							Log.i(TAG, "read goodsbean from gson: "+strGoodsList);
							PreferenceUtil.commitString("goodsList",t);
							initPager();
							goodsIndex = GoodsCache.goodsList.goodsListBean.size()-1;
							GoodsListEvent event = new GoodsListEvent(goodsIndex);
							EventBus.getDefault().post(event);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onFailure(VolleyError error) {
					Log.i("initShopper", "onFailure: "+error.getMessage());
				}
			};
			new RxVolley.Builder()
					.url(MainPresenter.HOST+"/client")
					.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
					.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
					.params(params)
					.shouldCache(false) //default: get true, post false
					.callback(callback)
					.encoding("UTF-8") //default
					.doTask();
		}else {
			goodsBean =  GoodsCache.goodsList.getGoodsListBean().get(goodsIndex);
			initPager();
		}
		
	}
	
	private void initPager(){
		name.setText(storesBean.getName());
		tel.setText(    storesBean.getTel());
		desc.setText(storesBean.getDesc());
		address.setText("地址："+storesBean.getAddress());
		open_time.setText(storesBean.getOpenTime());
		TabBean tabBean = gson.fromJson(storesBean.getTab(),TabBean.class);
		tab1.setText(tabBean.getTabs().get(0));
		tab2.setText(tabBean.getTabs().get(1));
		tab3.setText(tabBean.getTabs().get(2));
		pagerList = new ArrayList<>();
		fragmentList = new ArrayList<>();
		if (goodsBean!=null && goodsBean.getDatas()!=null){
			for (int i=0;i<goodsBean.getDatas().size();i++){
				pagerList.add(goodsBean.getDatas().get(i).getProductType().getName());
				fragmentList.add( HomegoodsFragment.newInstance(i,goodsIndex));
			}
			//MODE_SCROLLABLE可滑动的展示
			//MODE_FIXED固定展示
			store_tab.setTabMode(TabLayout.MODE_FIXED);
//		优化参考
			if (pagerList.size()<5){
				store_tab.setTabMode(TabLayout.MODE_FIXED);
			}else {
				store_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
			}
			store_tab.setSelectedTabIndicatorColor(getColor(R.color.black));
			for (int i=0;i<pagerList.size();i++){
				store_tab.addTab(store_tab.newTab().setText(pagerList.get(i)));
			}
			Log.i(TAG, "fragmentList_size: "+fragmentList.size()+"||pagerList_size："+pagerList.size());
			pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragmentList,pagerList);
			store_pager.setAdapter(pagerAdapter);
			store_tab.setupWithViewPager(store_pager);
		}
	}
	
	//修改顶部状态栏颜色
	private void changeTopBackground(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
//设置修改状态栏
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
			window.setStatusBarColor(getColor(R.color.black));
		}
	}
	
}
