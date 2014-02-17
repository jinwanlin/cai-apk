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
	 * ����������ڷֱ��ǲ��������������߳���Ϣʱ�䣩������(publishProgress�õ�)������ֵ ����
	 * 
	 * @author jinwanlin
	 * 
	 */
	class SignInTask extends AsyncTask<Integer, Integer, String> {

		/**
		 * ��һ��ִ�з���
		 */
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		/**
		 * �ڶ���ִ�з���,onPreExecute()ִ�����ִ��
		 */
		@Override
		protected String doInBackground(Integer... params) {

			String userName1 = userNameText.getText().toString();
			String password = passwordText.getText().toString();
			return CaiCai.sign_in(userName1, password);
		}

		/**
		 * ���������doInBackground����publishProgressʱ����
		 * ��������ȡ������һ������,����Ҫ��progesss[0]��ȡֵ ��n����������progress[n]��ȡֵ
		 * tv.setText(progress[0]+"%");
		 */
		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
		}

		/**
		 * doInBackground����ʱ���������仰˵������doInBackgroundִ����󴥷�
		 * �����result��������doInBackgroundִ�к�ķ���ֵ������������"ִ�����"
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
				finish();// ֹͣ��ǰ��Activity,�����д,�򰴷��ؼ�����ת��ԭ����Activity
			} else {
				Log.i(tag, "sign_in error: " + message);
			}
			super.onPostExecute(result);
		}

	}
}
