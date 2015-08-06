package com.asj.pls.adapter;

import java.util.ArrayList;

import com.asj.pls.R;
import com.asj.pls.info.CateInfo;
import com.asj.pls.util.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 首页分类适配器
 *
 */

public class IndexCateAdapter extends BaseAdapter {

	private ArrayList<CateInfo> cateList;
	
	private Context context;
	
	public ImageLoader imageLoader = ImageLoader.getInstance();

	public DisplayImageOptions options;
	
	public IndexCateAdapter(Context context, ArrayList<CateInfo> cateList) {
		this.cateList = cateList;
		this.context = context;
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_pd_image).showImageOnFail(R.drawable.default_pd_image).cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageUtils.initImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(cateList == null){
			return 0;
		}
		return cateList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(cateList == null){
			return null;
		}
		return cateList.get(position);
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
			convertView = View.inflate(context, R.layout.item_index_cate, null);
			hold.cateName = (TextView) convertView.findViewById(R.id.index_cate_name);
			hold.imageurl = (ImageView) convertView.findViewById(R.id.index_cate_image);
			convertView.setTag(hold);
		} else {
			hold = (Holder) convertView.getTag();
		}
		hold.cateName.setText(cateList.get(position).getCateName());
		imageLoader.displayImage(cateList.get(position).getImageUrl(), hold.imageurl, options);
		convertView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), cateList.get(position).getCateName(), Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}
	
	static class Holder {
		private TextView cateName;
		private ImageView imageurl;
	}
    
}
