package com.asj.pls.adapter;

import java.util.ArrayList;

import com.asj.pls.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 订单商品适配器
 * </BR> </BR> By lijun
 * */
public class OrdersPdAdapter extends BaseAdapter {

	private String orderNo;
	
	private ArrayList<String> grouplist; 
	
	private Context context;
	
	public OrdersPdAdapter(Context context, String orderNo, ArrayList<String> grouplist) { 
		super();
		this.context = context;
		this.orderNo = orderNo;
		this.grouplist = grouplist;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(grouplist == null){
			return 0;
		}
		return grouplist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(grouplist == null){
			return null;
		}
		return grouplist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ChildHolder childHold;
		if (convertView == null) {
			childHold = new ChildHolder();
			convertView = View.inflate(context, R.layout.item_orders_pd, null);
			childHold.pdname = (TextView) convertView.findViewById(R.id.Item_pd_name);
			convertView.setTag(childHold);
		} else {
			childHold = (ChildHolder) convertView.getTag();
		}
		childHold.pdname.setText(grouplist.get(position));
		
		convertView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), orderNo, Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	static class ChildHolder {
		TextView pdname;
	}
}
