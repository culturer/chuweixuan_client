package com.culturer.guishi;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.culturer.guishi.bean.UserBean;
import com.culturer.guishi.cache.BaseCache;
import com.culturer.guishi.util.MD5Util;
import com.culturer.guishi.util.PreferenceUtil;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.vondear.rxtools.RxAnimationTool;
import com.vondear.rxtools.RxKeyboardTool;
import com.vondear.rxtools.activity.AndroidBug5497Workaround;

import org.json.JSONException;
import org.json.JSONObject;

import static com.culturer.guishi.MainPresenter.HOST;

public class LoginActivity extends AppCompatActivity {
	
	private static final String TAG = "LoginActivity";
	
	View contentView;
	
	ImageView mLogo;
	EditText mEtMobile;
	ImageView mIvCleanPhone;
	EditText mEtPassword;
	ImageView mCleanPassword;
	ImageView mIvShowPwd;
	Button mBtnLogin;
	TextView mRegist;
	TextView mForgetPassword;
	LinearLayout mContent;
	ScrollView mScrollView;
	LinearLayout mService;
	RelativeLayout mRoot;
	
	private int screenHeight = 0;//屏幕高度
	private int keyHeight = 0; //软件盘弹起后所占高度
	private float scale = 0.6f; //logo缩放比例
	private int height = 0;
	
	String tel;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		contentView = LayoutInflater.from(this).inflate(R.layout.activity_login,null);
		setContentView(contentView);
		if (isFullScreen(this)) {
			AndroidBug5497Workaround.assistActivity(this);
		}
		init();
		changeTopBackground();
	}
	
	private void init(){
		screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
		keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
		initData();
		initView();
		initEvent();
		initResult();
	}
	
	private void initData(){
	
	}
	
	private void initView(){
		initBaseView();
	}
	
	private void initBaseView(){
		
		mLogo = contentView.findViewById(R.id.logo);
		mEtMobile = contentView.findViewById(R.id.et_mobile);
		mIvCleanPhone = contentView.findViewById(R.id.iv_clean_phone);
		mEtPassword = contentView.findViewById(R.id.et_password);
		mCleanPassword = contentView.findViewById(R.id.clean_password);
		mIvShowPwd = contentView.findViewById(R.id.iv_show_pwd);
		mBtnLogin = contentView.findViewById(R.id.btn_login);
		mRegist = contentView.findViewById(R.id.regist);
		mForgetPassword = contentView.findViewById(R.id.forget_password);
		mContent = contentView.findViewById(R.id.content);
		mScrollView = contentView.findViewById(R.id.scrollView);
		mRoot = contentView.findViewById(R.id.root);
		
		mEtMobile.setText(PreferenceUtil.getString("tel",""));
		
		mIvCleanPhone.setOnClickListener(v -> mEtMobile.setText(""));
		mCleanPassword.setOnClickListener(v -> mEtPassword.setText(""));
		mIvShowPwd.setOnClickListener(v -> {
			if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
				mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				mIvShowPwd.setImageResource(R.drawable.pass_visuable);
			} else {
				mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				mIvShowPwd.setImageResource(R.drawable.pass_gone);
			}
			String pwd = mEtPassword.getText().toString();
			if (!TextUtils.isEmpty(pwd))
				mEtPassword.setSelection(pwd.length());
		});
//		mRegist.setOnClickListener(v -> {
//			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
//			startActivity(intent);
//		});
//		mForgetPassword.setOnClickListener(v -> {
//			Intent intent = new Intent(LoginActivity.this,ForgetPwdActivity.class);
//			startActivity(intent);
//		});
	}
	
	@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
	private void initEvent() {
		mEtMobile.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(s) && mIvCleanPhone.getVisibility() == View.GONE) {
					mIvCleanPhone.setVisibility(View.VISIBLE);
				} else if (TextUtils.isEmpty(s)) {
					mIvCleanPhone.setVisibility(View.GONE);
				}
			}
		});
		mEtPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
					mCleanPassword.setVisibility(View.VISIBLE);
				} else if (TextUtils.isEmpty(s)) {
					mCleanPassword.setVisibility(View.GONE);
				}
				if (s.toString().isEmpty())
					return;
				if (!s.toString().matches("[A-Za-z0-9]+")) {
					String temp = s.toString();
					Toast.makeText(LoginActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
					s.delete(temp.length() - 1, temp.length());
					mEtPassword.setSelection(s.length());
				}
			}
		});
		/**
		 * 禁止键盘弹起的时候可以滚动
		 */
		mScrollView.setOnTouchListener((v, event) -> true);
		mScrollView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
          /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
          现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
			if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
				Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
				int dist = mContent.getBottom() - bottom;
				if (dist > 0) {
					ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
					mAnimatorTranslateY.setDuration(300);
					mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
					mAnimatorTranslateY.start();
					RxAnimationTool.zoomIn(mLogo, 0.6f, dist);
				}
				
			} else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
				Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
				if ((mContent.getBottom() - oldBottom) > 0) {
					ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
					mAnimatorTranslateY.setDuration(300);
					mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
					mAnimatorTranslateY.start();
					//键盘收回后，logo恢复原来大小，位置同样回到初始位置
					RxAnimationTool.zoomOut(mLogo, 0.6f);
				}
			}
		});
		
		mBtnLogin.setOnClickListener(v -> {
			RxKeyboardTool.hideSoftInput(LoginActivity.this);
			tel = mEtMobile.getText().toString();
			password = MD5Util.encrypt(mEtPassword.getText().toString().trim());
			login(tel,password);
		});
	}
	
	public boolean isFullScreen(Activity activity) {
		return (activity.getWindow().getAttributes().flags &
				WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
	}
	
	private void login(String tel,String pwd){
		HttpParams params = new HttpParams();
		params.put("options","login");
		params.put("Pwd",pwd);
		params.put("Tel",tel);
		HttpCallback callback = new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				Log.i(TAG, "onSuccess: "+t);
				try {
					JSONObject jsonObject = new JSONObject(t);
					int status = jsonObject.getInt("status");
					if (status == 200){
						String user = jsonObject.getString("user");
						Log.i(TAG, "user: "+user);
						Gson gson = new Gson();
						BaseCache.user = gson.fromJson(user, UserBean.class);
						PreferenceUtil.commitString("user",user);
						if (BaseCache.user.isIsSeller() && !BaseCache.user.isIsLock()){
							Toast.makeText(LoginActivity.this,"欢迎来到鬼市！",Toast.LENGTH_LONG).show();
							loginSuccess();
						}else {
							loginFail();
						}
					}else if (status == 400){
						String msg = jsonObject.getString("msg");
						Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
						loginFail();
					}else {
						Toast.makeText(LoginActivity.this,"登录失败，请稍后再试！",Toast.LENGTH_LONG).show();
						loginFail();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(VolleyError error) {
				Log.i(TAG, "onFailure: "+error.getMessage());
				loginFail();
			}
		};
		new RxVolley.Builder()
				.url(HOST+"/client")
				.httpMethod(RxVolley.Method.POST) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
				.contentType(RxVolley.ContentType.FORM)//default FORM or JSON
				.params(params)
				.shouldCache(false) //default: get true, post false
				.callback(callback)
				.encoding("UTF-8") //default
				.doTask();
		logining();
	}
	
	public void loginSuccess() {
		PreferenceUtil.commitString("tel",tel);
		PreferenceUtil.commitString("pwd",password);
		startActivity(new Intent(LoginActivity.this,MainActivity.class));
		finish();
	}
	
	
	public void loginFail() {
		mBtnLogin.setEnabled(true);
		mEtMobile.setEnabled(true);
		mIvCleanPhone.setEnabled(true);
		mEtPassword.setEnabled(true);
		mCleanPassword.setEnabled(true);
		mIvShowPwd.setEnabled(true);
		mRegist.setEnabled(true);
		mForgetPassword.setEnabled(true);
	}
	
	private void initResult() {
		Intent data = this.getIntent();
		String msg = data.getStringExtra("msg");
		if ( !(msg == null || msg.equals("")) ){
			Toast.makeText(this,"加载数据失败 "+msg,Toast.LENGTH_LONG).show();
		}
		mBtnLogin.setClickable(true);
	}
	
	public void logining() {
		mBtnLogin.setEnabled(false);
		mEtMobile.setEnabled(false);
		mIvCleanPhone.setEnabled(false);
		mEtPassword.setEnabled(false);
		mCleanPassword.setEnabled(false);
		mIvShowPwd.setEnabled(false);
		mRegist.setEnabled(false);
		mForgetPassword.setEnabled(false);
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