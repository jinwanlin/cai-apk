package com.weicai.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import co.caicai.R;

import com.weicai.model.Product;

public class ProductListAdapter extends BaseAdapter {
	private Context context;
	
	// 得到一个LayoutInfalter对象用来导入布局
	private LayoutInflater mInflater;
	
	private List<Product> products;
	
	/*构造函数*/
	public ProductListAdapter(Context context, List<Product> products) {
		this.context = context;
		this.products = products;
		this.mInflater = LayoutInflater.from(context);
	}
	

	@Override
	public int getCount() {
		return products.size();// 返回数组的长度
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/* 书中详细解释该方法 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// 观察convertView随ListView滚动情况
		Log.v("MyListViewBase", "getView " + position + " " + convertView);
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.product, null);
			holder = new ViewHolder();
			/* 得到各个控件的对象 */
			holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
			holder.price = (TextView) convertView.findViewById(R.id.ItemPrice);
			holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
		
		Product product = products.get(position);
		Log.v("product id", product.getId()+""); // 打印Button的点击信息
		/* 设置TextView显示的内容，即我们存放在动态数组中的数据 */
		holder.title.setText(product.getName());
		holder.price.setText(product.getPrice()+"元/斤");
		holder.bt.setText("购买");
		
		
		/* 为Button添加点击事件 */
		holder.bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Product product = products.get(position);
				final String[] amounts = product.getAmountArray();
				Log.v("MyListViewBase", "你点击了按钮" + position); // 打印Button的点击信息
				
				new AlertDialog.Builder(context).setTitle(product.getName()+"：("+product.getPrice()+"元/斤)").setItems(amounts, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						holder.bt.setText(amounts[which]+"斤");
					}
				}).setNegativeButton("取消", null).show();
				
			}
		});
		

		return convertView;
	}

	/* 存放控件 */
	public final class ViewHolder {
		public TextView title;
		public TextView price;
		public Button bt;
	}
}