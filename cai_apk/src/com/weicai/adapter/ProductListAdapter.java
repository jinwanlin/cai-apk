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
	
	// �õ�һ��LayoutInfalter�����������벼��
	private LayoutInflater mInflater;
	
	private List<Product> products;
	
	/*���캯��*/
	public ProductListAdapter(Context context, List<Product> products) {
		this.context = context;
		this.products = products;
		this.mInflater = LayoutInflater.from(context);
	}
	

	@Override
	public int getCount() {
		return products.size();// ��������ĳ���
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/* ������ϸ���͸÷��� */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		// �۲�convertView��ListView�������
		Log.v("MyListViewBase", "getView " + position + " " + convertView);
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.product, null);
			holder = new ViewHolder();
			/* �õ������ؼ��Ķ��� */
			holder.title = (TextView) convertView.findViewById(R.id.ItemTitle);
			holder.price = (TextView) convertView.findViewById(R.id.ItemPrice);
			holder.bt = (Button) convertView.findViewById(R.id.ItemButton);
			convertView.setTag(holder);// ��ViewHolder����
		} else {
			holder = (ViewHolder) convertView.getTag();// ȡ��ViewHolder����
		}
		
		Product product = products.get(position);
		Log.v("product id", product.getId()+""); // ��ӡButton�ĵ����Ϣ
		/* ����TextView��ʾ�����ݣ������Ǵ���ڶ�̬�����е����� */
		holder.title.setText(product.getName());
		holder.price.setText(product.getPrice()+"Ԫ/��");
		holder.bt.setText("����");
		
		
		/* ΪButton��ӵ���¼� */
		holder.bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Product product = products.get(position);
				final String[] amounts = product.getAmountArray();
				Log.v("MyListViewBase", "�����˰�ť" + position); // ��ӡButton�ĵ����Ϣ
				
				new AlertDialog.Builder(context).setTitle(product.getName()+"��("+product.getPrice()+"Ԫ/��)").setItems(amounts, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						holder.bt.setText(amounts[which]+"��");
					}
				}).setNegativeButton("ȡ��", null).show();
				
			}
		});
		

		return convertView;
	}

	/* ��ſؼ� */
	public final class ViewHolder {
		public TextView title;
		public TextView price;
		public Button bt;
	}
}