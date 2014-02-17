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
		// 后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值 类型

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
			return CaiCai.productsStr();
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
			JSONArray json = CaiCai.StringToJSONArray(result);
			List<Product> products = Product.jsonToList(json);

			ProductListAdapter productListAdapter = new ProductListAdapter(ProductsActivity.this, products);
			productItemLV.setAdapter(productListAdapter);

			super.onPostExecute(result);
		}

	}
}