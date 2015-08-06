package com.asj.pls.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.asj.pls.R;
import com.asj.pls.adapter.PromotePdAdapter;
import com.asj.pls.info.PdInfo;
import com.asj.pls.init.Model;
import com.asj.pls.util.DialogUtils;
import com.asj.pls.util.JSONUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PromoteActivity extends Activity implements OnClickListener{

	/** 
     * 返回
     */
    private ImageView backIndexPromote;
    
    /** 
     * 状态显示栏
     */
    private TextView promoteText;
    
    public static TextView cartNum,cartPriceText;
    
    /** 
     * 加载控件
     */
    private Dialog dialog;
    
    /** 
     * 商品列表
     */
	private ArrayList<PdInfo> pdlist;
	
	/** 
     * 商品容器
     */
	private PromotePdAdapter promotePdAdapter;
	
	/** 
     * 热销商品
     */
	private GridView pdGirdView;
	
	/** 
     * 底部购物车控件
     */
	private RelativeLayout footCartView;
	
	/** 
     * 底部填充
     */
    private TextView footGirdView;
    
    /** 
     * 确认购买
     */
    private TextView promoteCartGo,cart;
    
    private String pdJson = "{'data':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155242016551809605998093.jpg','pdId':0,'pdName':'早天下  泰国进口香蕉 约5斤','price':'￥1.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156291643375466617340175.jpg','pdId':1,'pdName':'早天下 青提约1斤','price':'￥1.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156595771790015738348.jpg','pdId':2,'pdName':'早天下 柠檬 约1斤','price':'￥2.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241651371556558486535.jpg','pdId':3,'pdName':'早天下 雪梨 10斤约13-15个','price':'￥3.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155242016551809605998093.jpg','pdId':4,'pdName':'早天下 进口奇异果 约1斤','price':'￥4.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156301539142722378482597.jpg','pdId':5,'pdName':'早天下 沂源红富士苹果70# 3斤装 约9-10个','price':'￥5.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241650595400631982872.jpg','pdId':6,'pdName':'早天下 烟台富士苹果80#  5斤约11-12个','price':'￥6.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241659156343945900278.jpg','pdId':7,'pdName':'早天下 西红柿 约2斤','price':'￥7.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241715445195470624928.jpg','pdId':8,'pdName':'早天下 甘蓝 约1.2斤','price':'￥8.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524173518589504599477.jpg','pdId':9,'pdName':'早天下 娃娃菜 3个约一斤','price':'￥9.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524171558416771050489.jpg','pdId':10,'pdName':'早天下 胶东铁杆大葱 约2斤','price':'￥10.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015612165725514499498907.jpg','pdId':11,'pdName':'金锣 中排 500g','price':'￥11.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015691414361654207648745.jpg','pdId':12,'pdName':'双汇 大肉块俄式风味火腿肠 70g','price':'￥12.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241546153405967881686.jpg','pdId':13,'pdName':'双汇 非常花声香肠 35g*10','price':'￥13.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156291743506795592947434.jpg','pdId':14,'pdName':'金锣 脆脆肠 葱香味 140g','price':'￥14.00'}],'errorInfo':'请求成功','errorNo':0}";
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_promote);
		
		Intent intent = getIntent();
		String promoteType = intent.getStringExtra("promotetype");
		
		dialog = DialogUtils.createLoadingDialog(this, "页面加载中...");
        dialog.show();
        
        new Thread(new Runnable() {
        	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
			}
			
        }).start();
        
		initViews(promoteType);
	}
    
    /** 
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。 
     */  
    private void initViews(String promoteType) {
    	int w =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
    	int h =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
    	
    	promoteText = (TextView) findViewById(R.id.promote_text);
		backIndexPromote = (ImageView) findViewById(R.id.back_index_promote);
		pdGirdView = (GridView) findViewById(R.id.promote_pd_girdview);
		footCartView = (RelativeLayout) findViewById(R.id.promote_foot);
		footGirdView = (TextView) findViewById(R.id.foot_gird_view);
		cartNum = (TextView) findViewById(R.id.pddetail_cart_num);
		cartPriceText = (TextView) findViewById(R.id.pddetail_cart_price);
		promoteCartGo = (TextView) findViewById(R.id.promote_cart_go);
		cart = (TextView) findViewById(R.id.foot_cart);
		
		footCartView.measure(w, h);
		footGirdView.measure(w, h);
		
		pdlist = new ArrayList<PdInfo>();
		if(promoteType.equals("热卖榜单")){
			promotePdAdapter = new PromotePdAdapter(this, pdlist, 1);
		}else{
			promotePdAdapter = new PromotePdAdapter(this, pdlist, null);
		}
		footGirdView.setHeight(footCartView.getMeasuredHeight());
		pdGirdView.setAdapter(promotePdAdapter);
		pdGirdView.setFocusable(false);
		cartNum.setText(String.valueOf(Model.cartNum));
    	cartPriceText.setText("￥" + Model.cartPrice.toString());
    	
		promoteText.setText(promoteType);
		backIndexPromote.setOnClickListener(this);
		promoteCartGo.setOnClickListener(this);
		cart.setOnClickListener(this);
		pdGirdView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PromoteActivity.this,PdetailActivity.class);
				startActivityForResult(intent,1);
			}
		});
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	cartNum.setText(String.valueOf(Model.cartNum));
    	cartPriceText.setText("￥" + Model.cartPrice.toString());
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch(v.getId()){
		case R.id.back_index_promote:
			this.finish();
			break;
		case R.id.promote_cart_go:
			intent = new Intent(PromoteActivity.this,CartActivity.class);
			startActivityForResult(intent,1);
			break;
		case R.id.foot_cart:
			intent = new Intent(PromoteActivity.this,CartActivity.class);
			startActivityForResult(intent,1);
			break;
		}
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if(pdJson != null){
					JSONArray array = JSONUtils.parseJsonMulti(pdJson, "data");
					if(array != null && array.length() > 0){
						for(int i = 0; i < array.length(); i++){
							JSONObject object = array.getJSONObject(i);
							PdInfo pd = new PdInfo();
							pd.setPdId(object.getLong("pdId"));
							pd.setImageurl(object.getString("imageurl"));
							pd.setPdName(object.getString("pdName"));
							pd.setPrice(object.getString("price"));
							pdlist.add(pd);
						}
						promotePdAdapter.notifyDataSetChanged();
					}
				}
				
			} catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
			} finally {
				dialog.dismiss();
			}
		};
		
	};
	
}
