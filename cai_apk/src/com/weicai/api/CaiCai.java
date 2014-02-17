package com.weicai.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.weicai.model.Product;
import com.weicai.net.HttpUtils;

public class CaiCai {

	private static final String BASE_URL = "http://192.168.0.103:3000";

	// private static final String BASE_URL = "http://115.28.160.65";

	// JSONObject jsonObject = new JSONObject(response);
	// String re_username = jsonObject.getString("username");
	// int re_user_id = jsonObject.getInt("user_id");
	/**
	 * String 转 JSONObject
	 * 
	 * @param str
	 * @return
	 */
	public static JSONObject StringToJSONObject(String str) {
		if (str == null) {
			return null;
		}

		Log.i("mylog", "请求结果-->" + str);

		JSONObject jsobj = null;
		try {
			jsobj = new JSONObject(str);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("mylog", "登陆结果-->" + str);
		}
		return jsobj;
	}

	/**
	 * String 转 JSONArray
	 * 
	 * @param str
	 * @return
	 */
	public static JSONArray StringToJSONArray(String str) {
		if (str == null) {
			return null;
		}

		Log.i("mylog", "请求结果-->" + str);

		JSONArray json = null;
		try {
			json = new JSONArray(str);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("mylog", "登陆结果-->" + str);
		}
		return json;
	}

	/**
	 * 商品列表
	 * 
	 * @return
	 */
	public static String productsStr() {
		String url = BASE_URL + "/api/products";
		return HttpUtils.doGet(url);
	}

	/**
	 * 商品列表
	 * 
	 * @return
	 */
	public static List<Product> products() {
		List<Product> products = new ArrayList<Product>();
		try {
			String url = BASE_URL + "/api/products?" + URLEncoder.encode("user[id]", "UTF-8") + "=" + 1 + "&" + URLEncoder.encode("user[level]", "UTF-8") + "=" + 1 + "&type=Vegetable";
			url = BASE_URL + "/api/products";
			JSONArray json = StringToJSONArray(HttpUtils.doGet(url));
			Product.jsonToList(json);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * 订单列表
	 * 
	 * @return
	 */
	public static JSONObject orders() {
		String url = BASE_URL + "/api/orders";
		return StringToJSONObject(HttpUtils.doGet(url));
	}

	/**
	 * 登陆
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	public static String sign_in(String phone, String password) {
		String url = BASE_URL + "/api/users/sign_in";

		Map<String, String> map = new HashMap<String, String>();
		map.put("user[phone]", phone);
		map.put("user[password]", password);

		return HttpUtils.doPost(url, map);
	}

	/**
	 * 注册
	 * 
	 * @param phone
	 * @return
	 */
	public static JSONObject sign_up(String phone) {
		String url = BASE_URL + "/api/users/sign_up";

		Map<String, String> map = new HashMap<String, String>();
		map.put("user[phone]", phone);

		return StringToJSONObject(HttpUtils.doPost(url, map));
	}

	/**
	 * 验证注册
	 * 
	 * @param phone
	 * @param password
	 * @param validate_code
	 * @return
	 */
	public static JSONObject validate(String phone, String password, String validate_code) {
		String url = "/api/users/validate";

		Map<String, String> map = new HashMap<String, String>();
		map.put("user[phone]", phone);
		map.put("user[password]", password);
		map.put("user[validate_code]", validate_code);

		return StringToJSONObject(HttpUtils.doPost(url, map));
	}

}
