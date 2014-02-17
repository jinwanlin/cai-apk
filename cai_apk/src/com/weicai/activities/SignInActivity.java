package com.weicai.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import co.caicai.R;

import com.weicai.api.CaiCai;
import com.weicai.dao.UserDao;
import com.weicai.model.User;

public class SignInActivity extends Activity {
	static final String tag = "SignUpActivity";
	private EditText userNameText, passwordText;
	private Button btSignIN = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);

		userNameText = (EditText) findViewById(R.id.phone);
		passwordText = (EditText) findViewById(R.id.password);
		btSignIN = (Button) findViewById(R.id.sign_in);

		btSignIN.setOnClickListener(new ClickViewHandler());
	}

	public class ClickViewHandler implements OnClickListener {
		@Override
		public void onClick(View v) {
			SignInTask dTask = new SignInTask();
			dTask.execute(0);
		}
	}

	/**
	 * 后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值 类型
	 * 
	 * @author jinwanlin
	 * 
	 */
	class SignInTask extends AsyncTask<Integer, Integer, String> {

		/**
		 * 第一个执行方法
		 */
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		/**
		 * 第二个执行方法,onPreExecute()执行完后执行
		 */
		@Override
		protected String doInBackground(Integer... params) {

			String userName1 = userNameText.getText().toString();
			String password = passwordText.getText().toString();
			return CaiCai.sign_in(userName1, password);
		}

		/**
		 * 这个函数在doInBackground调用publishProgress时触发
		 * 但是这里取到的是一个数组,所以要用progesss[0]来取值 第n个参数就用progress[n]来取值
		 * tv.setText(progress[0]+"%");
		 */
		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
		}

		/**
		 * doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
		 * 这里的result就是上面doInBackground执行后的返回值，所以这里是"执行完毕"
		 */
		@Override
		protected void onPostExecute(String result) {
			Log.i(tag, "sign_in result: " + result);
			JSONObject json = CaiCai.StringToJSONObject(result);

			int status = -1;
			String message = "";
			JSONObject userObj = null;
			try {
				status = json.getInt("status");
				message = json.getString("status");
				userObj = json.getJSONObject("user");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if (status == 0) {

				User user = User.jsonToUser(userObj);
				UserDao.create(user);

				Intent intent = new Intent();
				intent.setClass(SignInActivity.this, ProductsActivity.class);
				startActivity(intent);
				finish();// 停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
			} else {
				Log.i(tag, "sign_in error: " + message);
			}
			super.onPostExecute(result);
		}

	}
}
