package com.weicai.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.weicai.daoCore.Id;
import com.weicai.daoCore.Table;

/**
 * @author jiuwuerliu@sina.com
 * 
 *         ���ݿ�ʵ�����
 */
@Table(name = "t_user")
public class User {

	/**
	 * �����ֶ�
	 */
	@Id
	private int id;
	private String name;

	/**
	 * �����ݿ��ֶ�
	 */
	// @Transient
	// private String detail;

	public User() {
	}

	public User(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// public String getDetail() {
	// return detail;
	// }
	//
	// public void setDetail(String detail) {
	// this.detail = detail;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static User jsonToUser(JSONObject obj) {
		User user = null;
		if (obj != null) {
			user = new User();
			try {
				user.setId(obj.getInt("id"));
				user.setName(obj.getString("name"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

}