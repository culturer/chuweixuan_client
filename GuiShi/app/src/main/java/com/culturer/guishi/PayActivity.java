package com.culturer.guishi;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.culturer.guishi.bean.OrderBean;
import com.culturer.guishi.bean.ProductsBean;
import com.culturer.guishi.cache.GoodsCache;
import com.culturer.guishi.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PayActivity extends AppCompatActivity {
	
	private static final String TAG = "PayActivity";
	
	private List<ProductsBean> items = new ArrayList<>();
	
	private TextView name;
	private TextView tel;
	private TextView community;
	private TextView address;
	private CheckBox isAppoint;
	private TextView appointMsg;
	private TextView change;
	private EditText msg;
	private TextView discount;
	private ListView list;
	private TextView amount;
	private TextView pay;
	
	PayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		changeTopBackground();
		initData();
		initBaseView();
		initList();
	}
	
	private void initData(){
		Gson gson = new Gson();
		items = gson.fromJson(GoodsCache.tmpOrderBean1.getItem(), new TypeToken<List<ProductsBean>>(){}.getType());
	}
	
	private void initBaseView(){
		name = findViewById(R.id.name);
		tel = findViewById(R.id.tel);
		community = findViewById(R.id.community);
		address = findViewById(R.id.address);
		isAppoint = findViewById(R.id.isAppoint);
		appointMsg = findViewById(R.id.appointMsg);
		change = findViewById(R.id.change);
		msg = findViewById(R.id.msg);
		discount = findViewById(R.id.discount);
		list = findViewById(R.id.list);
		amount = findViewById(R.id.amount);
		pay = findViewById(R.id.pay);
		
		if (GoodsCache.tmpOrderBean1!=null){
			name.setText(""+GoodsCache.tmpOrderBean1.getReceiver());
			tel.setText(GoodsCache.tmpOrderBean1.getTel());
			community.setText("毛坦港湾");
			address.setText(GoodsCache.tmpOrderBean1.getAddress());
			isAppoint.setChecked(GoodsCache.tmpOrderBean1.isAppoint());
			isAppoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					GoodsCache.tmpOrderBean1.setAppoint(isChecked);
				}
			});
			appointMsg.setText(TimeUtil.getDataToString(GoodsCache.tmpOrderBean1.getAppointTime()));
			change.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				
				}
			});
			if (GoodsCache.tmpOrderBean1.isDiscount()){
				discount.setText(GoodsCache.tmpOrderBean1.getDiscountMsg());
			}else {
				discount.setText("无");
			}
			float all_price = 0;
			for (int i=0;i<items.size();i++){
				all_price += items.get(i).getPrice() * items.get(i).getNum();
			}
			amount.setText("应付款："+all_price);
			pay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				
				}
			});
		}
	}
	
	private void initList(){
		adapter = new PayAdapter(this);
		list.setAdapter(adapter);
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
