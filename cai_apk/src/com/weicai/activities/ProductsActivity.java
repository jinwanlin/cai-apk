package com.weicai.activities;

import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import co.caicai.R;

import com.weicai.adapter.ProductListAdapter;
import com.weicai.api.CaiCai;
import com.weicai.model.Product;

public class ProductsActivity extends Activity {
	static final String tag = "MainActivity";

	ListView productItemLV;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.products);

		productItemLV = (ListView) findViewById(R.id.productItem);

		DownloadTask dTask = new DownloadTask();
		dTask.execute(0);
	}

	class DownloadTask extends AsyncTask<Integer, Integer, String> {
		// ����������ڷֱ��ǲ��������������߳���Ϣʱ�䣩������(publishProgress�õ�)������ֵ ����

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
			return CaiCai.productsStr();
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
			JSONArray json = CaiCai.StringToJSONArray(result);
			List<Product> products = Product.jsonToList(json);

			ProductListAdapter productListAdapter = new ProductListAdapter(ProductsActivity.this, products);
			productItemLV.setAdapter(productListAdapter);

			super.onPostExecute(result);
		}

	}
}