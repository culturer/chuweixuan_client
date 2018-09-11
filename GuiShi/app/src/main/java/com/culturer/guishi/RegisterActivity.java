package com.culturer.guishi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.culturer.guishi.R;
import com.culturer.guishi.cache.BaseCache;
import com.culturer.guishi.util.MD5Util;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
	
	private static final String TAG = "RegisterActivity";
	
	private String session = "";
	
	private EditText tel;
	private EditText pwd;
	private EditText name;
	private EditText address;
	private EditText amountType;
	private EditText amount;
	private CheckBox ok;
	private TextView commit;
	private EditText msg;
	private Button getMsg;
	private Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		changeTopBackground();
		initView();
	}
	
	private void initView(){
		initBaseView();
	}
	
	@SuppressLint("WrongViewCast")
	private void initBaseView(){
		tel = findViewById(R.id.tel);
		pwd = findViewById(R.id.pwd);
		name = findViewById(R.id.name);
		address = findViewById(R.id.address);
		amountType = findViewById(R.id.amountType);
		amount = findViewById(R.id.amount);
		ok = findViewById(R.id.ok);
		commit = findViewById(R.id.commit);
		msg = findViewById(R.id.msg);
		getMsg = findViewById(R.id.getMsg);
		register = findViewById(R.id.register);
		ok.setOnCheckedChangeListener((compoundButton, b) -> {
			if (b){
				register.setEnabled(true);
			}else {
				register.setEnabled(false);
			}
		});
		register.setOnClickListener(view -> register());
		getMsg.setOnClickListener(view -> getMsg());
	}
	
	//获取短信验证码
	private void getMsg(){
		HttpParams params = new HttpParams();
		params.put("Tel",tel.getText().toString());
		params.put("options","getMsg");
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(Map<String, String> headers, byte[] t) {
				String message = new String(t);
				Log.i(TAG, "onSuccess: "+ message);
				try {
					JSONObject jsonObject = new JSONObject(message);
					int status = jsonObject.getInt("status");
					if (status == 400 ){
						String msg = jsonObject.getString("msg");
						Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();
					}if (status == 200 ){
						session = headers.get("Set-Cookie").split(";")[0];
						Log.i(TAG, "session "+session);
					}else {
						Toast.makeText(RegisterActivity.this,"发生未知错误！",Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFailure(VolleyError error) {
				Toast.makeText(RegisterActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
				Log.i(TAG, "onFailure: "+error.getMessage());
			}
		};
		new RxVolley.Builder()
				.url("http://192.168.43.146:8080"+"/utils")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
	}
	
	//注册
	private void register(){
		
		HttpParams params = new HttpParams();
		params.put("options","register");
		params.put("code",msg.getText().toString());
		params.putHeaders("content-type","application/x-www-form-urlencoded");
		params.putHeaders("Cookie",session);
		params.put("Tel",tel.getText().toString());
		params.put("Pwd", MD5Util.encrypt(pwd.getText().toString().trim()));
		params.put("Payee",name.getText().toString());
		params.put("Address",address.getText().toString());
		params.put("Amount",amount.getText().toString());
		params.put("AmountType",amountType.getText().toString());
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i(TAG, "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					Log.i(TAG, "onSuccess: "+status);
					String msg = jsonObject.getString("msg");
					String userId = jsonObject.getString("userId");
					BaseCache.user.setId(Integer.parseInt(userId));
					Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();
					if (status == 200){
						RegisterActivity.this.finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(VolleyError error) {
				Toast.makeText(RegisterActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
				Log.i(TAG, "onFailure: "+error.getMessage());
			}
		};
		new RxVolley.Builder()
				.url("http://192.168.43.146:8080"+"/shopper")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
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
