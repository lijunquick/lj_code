package com.asj.pls.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.asj.pls.R;
import com.asj.pls.info.AddressInfo;
import com.asj.pls.util.JSONUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AddressActivity extends Activity implements OnClickListener{

	/** 
     * 填单地址id
     */
    private Long addressId;
    
	/** 
     * 返回
     */
    private ImageView exitAddress;
    
    /** 
     * 新建
     */
    private TextView  addAddress;
    
    /** 
     * 地址容器
     */
    private AddressAdapter addressAdapter;
    
    /** 
     * 地址控件
     */
    private ListView addressListView;
    
    /** 
     * 定义一个ArrayList来存放地址
     */
    private ArrayList<AddressInfo> addresslist = new ArrayList<AddressInfo>();
    
    
    private String addressJson = "{'data':[{'adId':1,'detail':'下沙区天城东路77号创意大厦B座1901','isUse':1,'mobile':'13958042481','name':'王波'},{'adId':2,'detail':'九堡东方电子商务园9幢2层','isUse':1,'mobile':'15657113890','name':'金航波'},{'adId':3,'detail':'中沙金座爱巢酒店VIP包厢','isUse':0,'mobile':'15657343890','name':'庄兵'},{'adId':4,'detail':'九堡牛田社区109号(牛田庙附近)','isUse':1,'mobile':'18367474208','name':'李俊'},{'adId':5,'detail':'阳光郡5幢1201','isUse':1,'mobile':'134535574208','name':'熊超'}],'errorInfo':'请求成功','errorNo':0}";
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_address);
		initView();
		new Thread(new Runnable() {
        	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.obtainMessage().sendToTarget();
			}
			
        }).start();
	}
	
	private void initView(){
		Intent intent = getIntent();
		addressId = intent.getLongExtra("addressId", 0L);
		
		exitAddress = (ImageView)findViewById(R.id.exit_address);
		addAddress = (TextView)findViewById(R.id.add_address);
		addressListView = (ListView)findViewById(R.id.address_ListView);
		
		addressAdapter = new AddressAdapter(this, addresslist);
		addressListView.setAdapter(addressAdapter);
		addAddress.setOnClickListener(this);
		exitAddress.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.exit_address:
			this.finish();
			this.overridePendingTransition(R.anim.push_current, R.anim.push_right_out);
			break;
		case R.id.add_address:
			Intent addIntent = new Intent(AddressActivity.this,AddressOpActivity.class);
			startActivityForResult(addIntent,0);
			overridePendingTransition(R.anim.push_right_in, R.anim.push_current);
			break;
		}
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if(addressJson != null){
					JSONArray addressArray = JSONUtils.parseJsonMulti(addressJson, "data");
					if(addressArray != null && addressArray.length() > 0){
						for(int i = 0; i < addressArray.length(); i++){
							JSONObject object = addressArray.getJSONObject(i);
							AddressInfo addressItem = new AddressInfo();
							addressItem.setAdId(object.getLong("adId"));
							addressItem.setName(object.getString("name"));
							addressItem.setMobile(object.getString("mobile"));
							addressItem.setDetail(object.getString("detail"));
							addressItem.setIsUse(object.getInt("isUse"));
							addresslist.add(addressItem);
						}
						addressAdapter.notifyDataSetChanged();
					}
				}
			} catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
			}
		};
		
	};
	
	/** 
     * 地址适配器
     */
    private class AddressAdapter extends BaseAdapter{

    	private ArrayList<AddressInfo> addresslist;
    	
    	private Context context;
    	
    	public AddressAdapter(Context context, ArrayList<AddressInfo> addresslist) {
    		this.addresslist = addresslist;
    		this.context = context;
    	}
    	
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(addresslist == null){
				return 0;
			}
			return addresslist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			if(addresslist == null){
				return null;
			}
			return addresslist.get(position);
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
				convertView = View.inflate(context, R.layout.item_address, null);
				hold.name = (TextView) convertView.findViewById(R.id.address_consignee);
				hold.mobile = (TextView) convertView.findViewById(R.id.address_mobile);
				hold.detail = (TextView) convertView.findViewById(R.id.address_detail);
				hold.tag = (TextView) convertView.findViewById(R.id.address_tag);
				hold.radio = (TextView) convertView.findViewById(R.id.address_radio);
				convertView.setTag(hold);
			} else {
				hold = (Holder) convertView.getTag();
			}
			hold.name.setText(addresslist.get(position).getName());
			hold.mobile.setText(addresslist.get(position).getMobile());
			hold.detail.setText(addresslist.get(position).getDetail());
			if(addresslist.get(position).getIsUse() == 0){//不可用
				hold.tag.setBackgroundResource(R.drawable.unuser);
			}else{
				hold.tag.setBackgroundResource(R.drawable.address_edit);
				
				final String[] addressDetail = new String[4];
				addressDetail[0] = addresslist.get(position).getAdId().toString();
				addressDetail[1] = addresslist.get(position).getName();
				addressDetail[2] = addresslist.get(position).getMobile();
				addressDetail[3] = addresslist.get(position).getDetail();
						
				convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						chose(addressDetail);
					}
					
				});
				
				hold.tag.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						editAddress(addresslist.get(position).getAdId());
					}
				});
			}
			if(addressId.equals(addresslist.get(position).getAdId())){//默认
				hold.radio.setBackgroundResource(R.drawable.pay_selected);
			}else{
				hold.radio.setBackgroundResource(R.drawable.pay_unselected);
			}
			return convertView;
		}
    	
    }
    
    static class Holder {
    	private TextView name, mobile, detail, tag, radio;
	}
    
    private void chose(String[] addressDetail){
    	Intent intent = new Intent();
    	intent.putExtra("addressDetail", addressDetail);
    	setResult(0, intent);
    	this.finish();
    }
    
    private void editAddress(Long addressId){
    	Intent editIntent = new Intent(AddressActivity.this,AddressOpActivity.class);
		editIntent.putExtra("id", addressId);
		this.startActivityForResult(editIntent,1);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_current);
    }
}
