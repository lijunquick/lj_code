package com.asj.pls.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.asj.pls.R;
import com.asj.pls.adapter.ProductAdapter;
import com.asj.pls.info.PdInfo;
import com.asj.pls.init.Model;
import com.asj.pls.util.JSONUtils;
import com.asj.pls.util.KeyBoardUtils;
import com.asj.pls.util.SPUtils;
import com.asj.pls.view.AutoNewLineView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchActivity extends Activity implements OnClickListener, OnFocusChangeListener, TextWatcher{

	/**
     * 控件是否有焦点
     */
    private boolean hasFoucs;
    
    /**
	 * 删除按钮的引用
	 */
    private Drawable mClearDrawable;
	
	/** 
     * 返回
     */
    private ImageView backImageSearch;
    
    /** 
     * 搜索
     */
    private EditText searchCondition;
    
    /** 
     * 确定搜索
     */
    private TextView searchSure;

    /** 
     * 热门搜索控件
     */
    private AutoNewLineView hotWords;
    
    /** 
     * 清除历史数据
     */
    private Button clearRecord;
    
    /** 
     * 商品列表控件
     */
    private ListView pdListView;
    
    /** 
     * 搜索前区域,搜索后无结果区域,历史搜索,热门搜索
     */
    private LinearLayout searchWordsPlace,searchWithoutPlace,recordWords,hotSearchPlace;
    
    /** 
     * 商品适配器
     */
	private ProductAdapter productAdapter;
	
	/** 
     * 购物车
     */
	private RelativeLayout searchCart;
	
	/** 
     * 商品列表
     */
	private ArrayList<PdInfo> pdlist = new ArrayList<PdInfo>();
	
	/** 
     * 购物车数量控件
     */
	public static TextView searchCartNum;
	
	/** 
     * 购物车价格控件
     */
	public static TextView searchCartPrice;
	
	/** 
     * 确认购买
     */
    private TextView searchCartGo,cart;
	
    private String dataJson ="{'data':['可乐','烧烤','雪碧','西红柿','牛奶','巧克力','必胜客'],'errorInfo':'请求成功','errorNo':0}";
    
    private String pdJson = "{'data':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155242016551809605998093.jpg','pdId':0,'pdName':'早天下  泰国进口香蕉 约5斤','price':'￥0.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156291643375466617340175.jpg','pdId':1,'pdName':'早天下 青提约1斤','price':'￥1.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156595771790015738348.jpg','pdId':2,'pdName':'早天下 柠檬 约1斤','price':'￥2.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241651371556558486535.jpg','pdId':3,'pdName':'早天下 雪梨 10斤约13-15个','price':'￥3.00'},{'imageurl':'','pdId':4,'pdName':'早天下 进口奇异果 约1斤','price':'￥4.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156301539142722378482597.jpg','pdId':5,'pdName':'早天下 沂源红富士苹果70# 3斤装 约9-10个','price':'￥5.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241650595400631982872.jpg','pdId':6,'pdName':'早天下 烟台富士苹果80#  5斤约11-12个','price':'￥6.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241659156343945900278.jpg','pdId':7,'pdName':'早天下 西红柿 约2斤','price':'￥7.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241715445195470624928.jpg','pdId':8,'pdName':'早天下 甘蓝 约1.2斤','price':'￥8.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524173518589504599477.jpg','pdId':9,'pdName':'早天下 娃娃菜 3个约一斤','price':'￥9.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524171558416771050489.jpg','pdId':10,'pdName':'早天下 胶东铁杆大葱 约2斤','price':'￥10.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015612165725514499498907.jpg','pdId':11,'pdName':'金锣 中排 500g','price':'￥11.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015691414361654207648745.jpg','pdId':12,'pdName':'双汇 大肉块俄式风味火腿肠 70g','price':'￥12.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241546153405967881686.jpg','pdId':13,'pdName':'双汇 非常花声香肠 35g*10','price':'￥13.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156291743506795592947434.jpg','pdId':14,'pdName':'金锣 脆脆肠 葱香味 140g','price':'￥14.00'}],'errorInfo':'请求成功','errorNo':0}";
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		initViews();
	}
	
	/** 
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。 
     */  
    private void initViews() {
    	
    	backImageSearch = (ImageView)findViewById(R.id.back_index_search);
    	searchCondition = (EditText)findViewById(R.id.search_condition);
    	searchSure = (TextView)findViewById(R.id.search_sure);
    	hotWords = (AutoNewLineView)findViewById(R.id.hot_words_view);
    	hotSearchPlace = (LinearLayout)findViewById(R.id.hot_words_place);
    	recordWords = (LinearLayout)findViewById(R.id.record_words_view);
    	clearRecord = (Button)findViewById(R.id.clear_record_words);
    	pdListView = (ListView)findViewById(R.id.pdListView);
    	searchWordsPlace = (LinearLayout)findViewById(R.id.search_content);
    	searchWithoutPlace = (LinearLayout)findViewById(R.id.searchWithoutResult);
    	searchCart = (RelativeLayout)findViewById(R.id.search_cart_content);
    	searchCartNum = (TextView)findViewById(R.id.search_cart_num);
    	searchCartPrice = (TextView)findViewById(R.id.search_cart_price);
    	searchCartGo = (TextView) findViewById(R.id.search_cart_go);
		cart = (TextView) findViewById(R.id.foot_cart);
		
    	int w =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
    	int h =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
    	searchCart.measure(w, h);
    	
    	TextView footText = new TextView(this);
    	footText.setHeight(searchCart.getMeasuredHeight());
    	pdListView.addFooterView(footText);
    	
    	
    	productAdapter = new ProductAdapter(this, pdlist);
    	pdListView.setAdapter(productAdapter);
    	new Thread(new Runnable() {
        	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
			}
			
        }).start();
    	
    	mClearDrawable = searchCondition.getCompoundDrawables()[2];
        if (mClearDrawable == null) {
        	mClearDrawable = getResources().getDrawable(R.drawable.delete_selector); //图片样式
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);//默认设置隐藏图标
        recordSearchWordInit();//历史搜索词
        searchCartGo.setOnClickListener(this);
		cart.setOnClickListener(this);
        searchSure.setOnClickListener(this);
        clearRecord.setOnClickListener(this);
    	backImageSearch.setOnClickListener(this);
    	searchCondition.setOnClickListener(this);
    	searchCondition.setOnFocusChangeListener(this);//设置焦点改变的监听
    	searchCondition.addTextChangedListener(this);//设置输入框里面内容发生改变的监听
    	searchCondition.setOnTouchListener(new OnTouchListener() {//设置输入清空的监听

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (searchCondition.getCompoundDrawables()[2] != null) {
						
						boolean touchable = event.getX() > (searchCondition.getWidth() - searchCondition.getTotalPaddingRight())
								&& (event.getX() < ((searchCondition.getWidth() - searchCondition.getPaddingRight())));
						
						if (touchable) {
							searchCondition.setText("");
						}
					}
				}
				return false;
			}
    		
    	});
    	
    	searchCondition.setOnEditorActionListener(new OnEditorActionListener() {//设置键盘回车的监听
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				searchWord(searchCondition.getText().toString());
				return false;
			}
			
		});
    	
    	KeyBoardUtils.openKeybord(searchCondition, this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch(v.getId()){
		case R.id.back_index_search:
			KeyBoardUtils.closeKeybord(searchCondition, this);
			this.finish();
			this.overridePendingTransition(R.anim.push_current, R.anim.push_right_out);
			break;
		case R.id.search_sure:
			searchWord(searchCondition.getText().toString());
			break;
		case R.id.clear_record_words:
			SPUtils.remove("recordSearch", this);
			recordWords.removeAllViews();
			findViewById(R.id.search_record_place).setVisibility(View.GONE);
			break;
		case R.id.search_cart_go:
			intent = new Intent(SearchActivity.this,CartActivity.class);
			startActivityForResult(intent,1);
			break;
		case R.id.foot_cart:
			intent = new Intent(SearchActivity.this,CartActivity.class);
			startActivityForResult(intent,1);
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		searchCartNum.setText(String.valueOf(Model.cartNum));
		searchCartPrice.setText("￥" + Model.cartPrice.toString());
    }

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		this.hasFoucs = hasFocus;
        if (hasFocus) { 
            setClearIconVisible(searchCondition.getText().length() > 0); 
        } else { 
            setClearIconVisible(false); 
        }
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if(hasFoucs){
    		setClearIconVisible(s.length() > 0);
    	}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	
	/**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        searchCondition.setCompoundDrawables(searchCondition.getCompoundDrawables()[0], searchCondition.getCompoundDrawables()[1], right, searchCondition.getCompoundDrawables()[3]);
        if(visible == Boolean.FALSE && pdlist.size() > 0){
        	pdlist.clear();
    		productAdapter.notifyDataSetChanged();
    		pdListView.setVisibility(View.GONE);
    		searchCart.setVisibility(View.GONE);
    		searchWithoutPlace.setVisibility(View.GONE);
    		searchWordsPlace.setVisibility(View.VISIBLE);
        }
    }
    
    
    protected void recordSearchWordInit(){
    	String exitRecordWors = (String) SPUtils.get("recordSearch", "", this);
    	if(exitRecordWors != null && !exitRecordWors.equals("")){
    		String[] wordsArray = exitRecordWors.split(",");
    		for(String item : wordsArray){
    			final TextView itemView = new TextView(getApplicationContext());
    			itemView.setText(item);
    			itemView.setTextSize(13);
    			itemView.setTextColor(this.getResources().getColor(R.color.pls_bg));
    			itemView.setBackgroundResource(R.drawable.record_search_text_view);
    			itemView.setClickable(true);
    			itemView.setPadding(15, 15, 10, 15);
    			itemView.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					searchWord(itemView.getText().toString());
    				}
    			});
        		recordWords.addView(itemView);
    		}
        	findViewById(R.id.search_record_place).setVisibility(View.VISIBLE);
    	}
    }
    
    /**
     * 搜索操作
     */
    protected void searchWord(String words){
    	if(words != null && !words.equals("")){
    		searchCartNum.setText(String.valueOf(Model.cartNum));
    		searchCartPrice.setText("￥"+Model.cartPrice.toString());
    		pdListView.setVisibility(View.GONE);
    		searchCart.setVisibility(View.GONE);
    		searchWordsPlace.setVisibility(View.GONE);
    		if(pdlist.size() > 0){
    			pdlist.clear();
        		productAdapter.notifyDataSetChanged();
    		}
    		searchCondition.setText(words);
    		Editable etext = searchCondition.getText(); 
    		Selection.setSelection(etext, words.length());

    		if(words.equals("啊")){
    			pdJson = null;
    		}
    		new Thread(new Runnable() {
            	
    			@Override
    			public void run() {
    				// TODO Auto-generated method stub
    				Message msg = new Message();
    				msg.what = 2;
    				mHandler.sendMessage(msg);
    			}
    			
            }).start();
    		
    		StringBuffer storeWords = new StringBuffer();
    		String exitRecordWors = (String) SPUtils.get("recordSearch", "", this);
    		if(exitRecordWors != null && !exitRecordWors.equals("")){
    			Integer size = 0;
    			String[] wordsArray = exitRecordWors.split(",");
    			for(int i = 1; i <= wordsArray.length; i++){
    				if(wordsArray[i-1].equals(words)){
						size = i;
					}
    			}
    			storeWords = storeWords.append(words);
    			if(size != 0){
    				for(int i = 1; i <= wordsArray.length; i++){
						if(i != size){
							storeWords = storeWords.append(",").append(wordsArray[i-1]);
						}
						if(i == 4){
							break;
						}
					}
    			}if(size == 0){
    				for(int i = 1; i <= wordsArray.length; i++){
    					storeWords = storeWords.append(",").append(wordsArray[i-1]);
    					if(i == 4){
							break;
						}
    				}
    			}
    		}else{
    			storeWords = storeWords.append(words);
    		}
    		SPUtils.put("recordSearch", storeWords.toString(), this);
    		KeyBoardUtils.closeKeybord(searchCondition, this);
    	}
    }
    
    private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				JSONArray array = null;
				if(msg.what == 1){
					if(dataJson != null){
						array = JSONUtils.parseJsonMulti(dataJson, "data");
						if(array != null && array.length() > 0){
							for(int i = 0; i < array.length(); i++){
					    		final TextView item = new TextView(getApplicationContext());
					    		item.setText(array.getString(i));
					    		item.setTextSize(13);
					    		item.setTextColor(Color.parseColor("#271553"));
					    		item.setBackgroundResource(R.drawable.hot_search_text_view);
					    		item.setClickable(true);
					    		item.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										searchWord(item.getText().toString());
									}
								});
					    		
					    		hotWords.addView(item);
					    	}
							hotSearchPlace.setVisibility(View.VISIBLE);
							hotWords.setVisibility(View.VISIBLE);
						}
					}
				}else{
					if(pdJson != null){
						array = JSONUtils.parseJsonMulti(pdJson, "data");
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
							productAdapter.notifyDataSetChanged();
							pdListView.setVisibility(View.VISIBLE);
							searchCart.setVisibility(View.VISIBLE);
						}
					}else{
						searchWithoutPlace.setVisibility(View.VISIBLE);
					}
					
				}
				
			} catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
			}
		};
		
	};
	
}
