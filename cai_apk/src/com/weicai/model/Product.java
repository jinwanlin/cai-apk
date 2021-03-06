package com.weicai.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weicai.daoCore.Id;
import com.weicai.daoCore.Table;
import com.weicai.daoCore.Transient;

/**
 * @author jiuwuerliu@sina.com
 * 
 *         数据库实体对象
 */
@Table(name = "t_product")
public class Product {

	/**
	 * 主键字段
	 */
	@Id
	private int id;
	private Date createdAt;
	private Date updatedAt;

	private String sn;
	private String name;
	private String type;
	private String amounts;
	private double price;
	private String unit;

	/**
	 * 非数据库字段
	 */
	@Transient
	private String detail;

	@Transient
	private String[] amountArray;

	public Product() {
	}

	public Product(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmounts() {
		return amounts;
	}

	public void setAmounts(String amounts) {
		this.amounts = amounts;
	}

	public String[] getAmountArray() {
		String[] amountArray;
		if (amounts != null && !amounts.equals("")) {
			amountArray = amounts.split(",");
		} else {
			amountArray = new String[0];
		}
		return amountArray;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public static List<Product> jsonToList(JSONArray array) {
		List<Product> list = new ArrayList<Product>();
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				try {
					JSONObject p = array.getJSONObject(i);
					Product product = new Product();
					product.setId(p.getInt("id"));
					product.setSn(p.getString("sn"));
					product.setName(p.getString("name"));
					product.setType(p.getString("type"));
					product.setAmounts(p.getString("amounts"));
					product.setPrice(p.getDouble("price"));
					product.setUnit(p.getString("unit"));
					list.add(product);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}