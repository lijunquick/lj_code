package com.asj.pls.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.asj.pls.R;
import com.asj.pls.info.CartInfo;
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

/**
 * 购物车适配器
 *
 */
public class CartAdapter extends BaseAdapter{

	private ArrayList<CartInfo> cartlist;
	
	private Context context;
	
	public ImageLoader imageLoader = ImageLoader.getInstance();

	public DisplayImageOptions options;
	
	private CartListener cartListener;
	
	public CartAdapter(Context context, ArrayList<CartInfo> cartlist) {
		this.cartlist = cartlist;
		this.context = context;
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_pd_image).showImageOnFail(R.drawable.default_pd_image).cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageUtils.initImageLoader(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(cartlist == null){
			return 0;
		}
		return cartlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(cartlist == null){
			return null;
		}
		return cartlist.get(position);
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
			convertView = View.inflate(context, R.layout.item_cart, null);
			hold.name = (TextView) convertView.findViewById(R.id.cart_pd_name);
			hold.price = (TextView) convertView.findViewById(R.id.cart_pd_price);
			hold.num = (TextView) convertView.findViewById(R.id.cart_pd_num);
			hold.image = (ImageView) convertView.findViewById(R.id.cart_pd_image);
			convertView.setTag(hold);
		} else {
			hold = (Holder) convertView.getTag();
		}
		hold.name.setText(cartlist.get(position).getPdName());
		hold.price.setText(cartlist.get(position).getPrice());
		hold.num.setText(cartlist.get(position).getNum());
		imageLoader.displayImage(cartlist.get(position).getImageurl(), hold.image, options);
		
		ImageView minus = (ImageView) convertView.findViewById(R.id.minus);
		ImageView plus = (ImageView) convertView.findViewById(R.id.plus);
		
		minus.setOnClickListener(new OnClickListener(){//-

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cartListener.minus(hold.num,new BigDecimal(cartlist.get(position).getPrice()), position);
			}
			
		});
		
		plus.setOnClickListener(new OnClickListener(){//+

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cartListener.plus(hold.num, new BigDecimal(cartlist.get(position).getPrice()));
			}
			
		});
		
		return convertView;
	}
	
	static class Holder {
		private TextView name, price, num;
		private ImageView image;
	}
	
	//购物车操作接口
    public interface CartListener{
		public void minus(TextView num, BigDecimal price, int position);
		public void plus(TextView num, BigDecimal price);
	}
    
    public void setCartListener(CartListener cartListener){
		this.cartListener = cartListener;
	}
}
