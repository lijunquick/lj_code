package com.asj.pls.adapter;

import java.util.ArrayList;

import com.asj.pls.R;
import com.asj.pls.info.CateInfo;
import com.asj.pls.info.PdInfo;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 首页列表适配器
 *
 */

public class IndexAdapter extends BaseAdapter {

	private ArrayList<CateInfo> catelist;
	
	private Context context;
	
	public IndexAdapter(Context context, ArrayList<CateInfo> catelist) {
		this.catelist = catelist;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(catelist == null){
			return 0;
		}
		return catelist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(catelist == null){
			return null;
		}
		return catelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Holder hold;
		if (convertView == null) {
			hold = new Holder();
			convertView = View.inflate(context, R.layout.item_index, null);
			hold.cateName = (TextView) convertView.findViewById(R.id.cate_name);
			hold.cateMore = (Button) convertView.findViewById(R.id.cate_more);
			hold.pdGridView = (GridView) convertView.findViewById(R.id.cate_pd_girdview);
			convertView.setTag(hold);
		} else {
			hold = (Holder) convertView.getTag();
		}
		hold.cateName.setText(catelist.get(position).getCateName());
		hold.cateMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), catelist.get(position).getCateId().toString(), Toast.LENGTH_SHORT).show();
			}
		});
		ArrayList<PdInfo> pdList = catelist.get(position).getProductlist();
		if(pdList != null && pdList.size() > 0){
			hold.pdGridView.setAdapter(new IndexPdAdapter(context, pdList));
		}
		return convertView;
	}
	
	static class Holder {
		private TextView cateName;
		private Button cateMore;
		private GridView pdGridView; 
	}
    
}
