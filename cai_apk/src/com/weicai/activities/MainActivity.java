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
		if(user!=null){ //�ѵ�¼����ת����ҳ
			targetClass = ProductsActivity.class;
		}else{ //ת����½ҳ��
			targetClass = SignInActivity.class;
		}
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, targetClass);
		startActivity(intent);
		finish();// ֹͣ��ǰ��Activity,�����д,�򰴷��ؼ�����ת��ԭ����Activity
	}
}
