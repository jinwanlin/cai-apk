package com.weicai.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.weicai.dao.UserDao;
import com.weicai.model.User;

public class MainActivity extends Activity {
	static final String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		User user = UserDao.first();
		Class<?> targetClass = null;
		if(user!=null){ //已登录，跳转到首页
			targetClass = ProductsActivity.class;
		}else{ //转到登陆页面
			targetClass = SignInActivity.class;
		}
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, targetClass);
		startActivity(intent);
		finish();// 停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
	}
}
