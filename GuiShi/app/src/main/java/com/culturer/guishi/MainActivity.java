package com.culturer.guishi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.culturer.guishi.bean.StoresDataBean;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.pages.buy.BuyFragment;
import com.culturer.guishi.pages.convenient.ConvenientFragment;
import com.culturer.guishi.pages.home.HomeFragment;
import com.culturer.guishi.pages.mine.MineFragment;
import com.culturer.guishi.pages.send.SendFragment;
import com.culturer.guishi.util.PreferenceUtil;
import com.culturer.guishi.wedgit.bottom_navigation.BottomNavigationViewHelper;
import com.culturer.guishi.wedgit.bottom_navigation.MyPagerAdapter;
import com.culturer.guishi.wedgit.bottom_navigation.MyViewPager;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.vondear.rxtools.RxKeyboardTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	
	//Android6.0以上动态权限
	//定位需要的权限
	protected String[] needPermissions = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE,
	};
	
	private static final int PERMISSON_REQUESTCODE = 0;
	
	private TextView mTextMessage;
	private MyViewPager pager;
	BottomNavigationView navigation;
	private MyPagerAdapter adapter;
	private MenuItem menuItem;
	
	MainPresenter presenter =  MainPresenter.getInstance();
	
	List<String> tabs = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		//授权
		checkPermissions(needPermissions);
		initBaseView();
		changeTopBackground();
		changeBottomNavigation();
		changePager();
	}
	
	private void initData(){
		presenter.init(this);
	}
	
	private void initBaseView(){
		mTextMessage = (TextView) findViewById(R.id.message);
		navigation = (BottomNavigationView) findViewById(R.id.navigation);
		pager = findViewById(R.id.pager);
		tabs.add("官    方");
		tabs.add("便    利");
		tabs.add("购  物  车");
		tabs.add("我    的");
		tabs.add("配    送");
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
	
	//底部导航栏
	private void changeBottomNavigation(){
		BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.navigation_home:
						mTextMessage.setText(tabs.get(0));
						pager.setCurrentItem(0);
						return true;
					case R.id.navigation_dashboard:
						mTextMessage.setText(tabs.get(1));
						pager.setCurrentItem(1);
						return true;
					case R.id.navigation_car:
						mTextMessage.setText(tabs.get(2));
						pager.setCurrentItem(2);
						return true;
					case R.id.navigation_mine:
						mTextMessage.setText(tabs.get(3));
						pager.setCurrentItem(3);
						return true;
					case R.id.navigation_send:
						mTextMessage.setText(tabs.get(4));
						pager.setCurrentItem(4);
						return true;
				}
				return false;
			}
		};
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		//默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
		BottomNavigationViewHelper.disableShiftMode(navigation);
	}
	
	//修改ViewPager
	private void changePager(){
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(HomeFragment.newInstance("",""));
		adapter.addFragment(ConvenientFragment.newInstance("",""));
		adapter.addFragment(BuyFragment.newInstance("",""));
		adapter.addFragment(MineFragment.newInstance("",""));
		adapter.addFragment(SendFragment.newInstance("",""));
		ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			
			}
			
			@Override
			public void onPageSelected(int position) {
				if (menuItem != null) {
					menuItem.setChecked(false);
				} else {
					navigation.getMenu().getItem(0).setChecked(false);
					mTextMessage.setText(tabs.get(0));
				}
				menuItem = navigation.getMenu().getItem(position);
				mTextMessage.setText(tabs.get(position));
				menuItem.setChecked(true);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			
			}
		};
		pager.addOnPageChangeListener(pageChangeListener);
		pager.setOffscreenPageLimit(5);
		pager.setAdapter(adapter);
	}
	
	/**
	 * 检查定位权限
	 *
	 * @param needPermissions
	 */
	private void checkPermissions(String[] needPermissions) {
		List<String> needRequestPermissionList = findDeniedPermission(needPermissions);
		if (needRequestPermissionList != null && needRequestPermissionList.size() > 0) {
			ActivityCompat.requestPermissions(this,
					needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]),
					PERMISSON_REQUESTCODE);
		}
	}
	/**
	 * 获取集中需要申请权限的列表
	 *
	 * @param needPermissions
	 * @return
	 */
	private List<String> findDeniedPermission(String[] needPermissions) {
		List<String> needRequestPermissionList = new ArrayList<String>();
		for (String permisson : needPermissions) {
			if (ContextCompat.checkSelfPermission(this, permisson) != PackageManager.PERMISSION_GRANTED
					|| ActivityCompat.shouldShowRequestPermissionRationale(this, permisson)) {
				needRequestPermissionList.add(permisson);
			}
		}
		return needRequestPermissionList;
	}
	
}
