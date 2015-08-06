package com.asj.pls.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.asj.pls.R;
import com.asj.pls.adapter.IndexPdAdapter;
import com.asj.pls.adapter.ViewPagerAdapter;
import com.asj.pls.info.PdInfo;
import com.asj.pls.init.Model;
import com.asj.pls.util.DialogUtils;
import com.asj.pls.util.JSONUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;

public class PdetailActivity extends Activity implements OnClickListener{

	/** 
     * 商品id
     */
	private Long productId;
	
	/** 
     * 商品价格
     */
	private BigDecimal price;
	
    /** 
     * 记录当前选中位置
     */
    private boolean isAutoPlay = true;
    
    /** 
     * 商品图片
     */
  	private ViewPager pdViewPager;
  	
  	/** 
     * 定义一个ArrayList来存放View
     */
    private ArrayList<View> views;
    
    /** 
     * 放圆点的View的list
     */
    private List<View> dotViewsList;
    
    /** 
     * 放圆点的控件
     */
    private LinearLayout dotLayout;
    
    /** 
     * 定义ViewPager适配器
     */
	private ViewPagerAdapter vpAdapter;
	
	/** 
     * 返回
     */
    private ImageView backImagePdetail;
    
    /** 
     * 加载控件
     */
    private Dialog dialog;
    
    /** 
     * 商品名称、商品销售价、商品市场价、购物车数量、购物车商品价格
     */
    private TextView pdName,pdCost,pdPrice,cartNum,cartPriceText,pdWebDetail;
    
    /** 
     * 加入购物车、同类热销、同品牌热销
     */
    private Button cartBtn, catePdBtn,brandPdBtn;
    
    /** 
     * 热销商品
     */
	private GridView pdGirdView;
	
	/** 
     * 热销商品容器
     */
	private IndexPdAdapter pdAdapter;
	
	private ArrayList<PdInfo> productList;
	
	private ArrayList<PdInfo> catepdList;
	
	private ArrayList<PdInfo> brandPdList;
	
    private ViewGroup anim_mask_layout;//动画层
    
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
    private TextView pdDetailCartGo,cart;
    
    private String pdUrl;
    
    private String pdDetailJson = "{'data':{'brandPdList':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015523163646998113371133.jpg','pdId':0,'pdName':'薄0.05 极限超薄安全套 10只','price':'￥15.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524162397966991192056.jpg','pdId':1,'pdName':'耐能 动感舒适避孕套 10只','price':'￥15.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201552416238968522517322.jpg','pdId':2,'pdName':'耐能 丝感超薄避孕套 10只','price':'￥15.00'}],'catePdList':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201576182528754809091682.jpg','pdId':0,'pdName':'将军 雪豹·雪茄 20支/包','price':'￥16.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201576185349004808334808.jpg','pdId':1,'pdName':'利群 硬盒香烟 200支/条','price':'￥200.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201576180424449549230990.jpg','pdId':2,'pdName':'黄山 中国松硬盒烟 200支/条','price':'￥110.00'}],'cost':'2.70','imageList':['http://image.asj.com/oo/base/product/image/420X420/20155241616445671089420561.jpg','http://image.asj.com/oo/base/product/image/420X420/20155241616445674619694100.jpg','http://image.asj.com/oo/base/product/image/420X420/20156301658321530350285624.jpg'],'pdId':825,'pdName':'美年达 橙味汽水 600ml','price':'3.00'}}";
	
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pdetail);
		
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
		initViews();
	}
    
    /** 
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。 
     */  
    private void initViews() {
    	
    	int w =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
    	int h =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
    	
    	pdViewPager = (ViewPager) findViewById(R.id.pd_detai_viewpager);
    	dotLayout = (LinearLayout) findViewById(R.id.pd_detai_dotLayout);
    	backImagePdetail = (ImageView) findViewById(R.id.back_index_pdDetail);
    	pdName = (TextView) findViewById(R.id.pd_detai_pdname);
    	pdCost = (TextView) findViewById(R.id.pd_detai_cost);
    	pdPrice = (TextView) findViewById(R.id.pd_detai_price);
    	cartBtn = (Button) findViewById(R.id.add_cart_btn);
    	cartNum = (TextView) findViewById(R.id.pddetail_cart_num);
    	pdWebDetail = (TextView) findViewById(R.id.pd_detail_web);
    	cartPriceText = (TextView) findViewById(R.id.pddetail_cart_price);
    	catePdBtn = (Button) findViewById(R.id.cate_pd);
    	brandPdBtn = (Button) findViewById(R.id.brand_pd);
    	pdGirdView = (GridView)findViewById(R.id.pd_girdview);
    	pdDetailCartGo = (TextView) findViewById(R.id.pdeatil_cart_go);
    	cart = (TextView) findViewById(R.id.foot_cart);
    	
    	footCartView = (RelativeLayout) findViewById(R.id.pd_foot);
		footGirdView = (TextView) findViewById(R.id.pd_foot_girdview);
		footCartView.measure(w, h);
		footGirdView.measure(w, h);
		footGirdView.setHeight(footCartView.getMeasuredHeight());
		
    	productList = new ArrayList<PdInfo>();
    	catepdList = new ArrayList<PdInfo>();
    	brandPdList = new ArrayList<PdInfo>();
    	
    	pdAdapter = new IndexPdAdapter(getApplicationContext(), productList);
    	pdGirdView.setAdapter(pdAdapter);
    	pdGirdView.setFocusable(false);

    	cartNum.setText(String.valueOf(Model.cartNum));
    	cartPriceText.setText("￥" + Model.cartPrice.toString());
    	pdPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
    	
    	backImagePdetail.setOnClickListener(this);
    	cartBtn.setOnClickListener(this);
    	catePdBtn.setOnClickListener(this);
    	brandPdBtn.setOnClickListener(this);
    	pdWebDetail.setOnClickListener(this);
    	pdDetailCartGo.setOnClickListener(this);
    	cart.setOnClickListener(this);
    }
	
    private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if(pdDetailJson != null){
					views = new ArrayList<View>();
					dotViewsList = new ArrayList<View>();
			        dotLayout.removeAllViews();
					JSONObject pdObject = JSONUtils.parseJson(pdDetailJson, "data");
					if(pdObject != null){
						price = new BigDecimal(pdObject.getString("cost"));
						productId = pdObject.getLong("pdId");
						pdName.setText(pdObject.getString("pdName"));
						pdCost.setText("￥"+pdObject.getString("cost"));
						if(pdObject.getString("price") != null){
							pdPrice.setText("￥"+pdObject.getString("price"));
						}else{
							pdPrice.setVisibility(View.GONE);
						}
						JSONArray array = pdObject.getJSONArray("imageList");
						JSONArray catePdArray = pdObject.getJSONArray("catePdList");
						JSONArray brandPdArray = pdObject.getJSONArray("brandPdList");
						if(catePdArray != null && catePdArray.length() > 0){
							for(int i = 0; i < catePdArray.length(); i++){
								JSONObject object = catePdArray.getJSONObject(i);
								PdInfo catePdItem = new PdInfo();
								catePdItem.setPdId(object.getLong("pdId"));
								catePdItem.setPdName(object.getString("pdName"));
								catePdItem.setPrice(object.getString("price"));
								catePdItem.setImageurl(object.getString("imageurl"));
								catepdList.add(catePdItem);
								productList.add(catePdItem);
							}
							pdAdapter.notifyDataSetChanged();
						}
						if(brandPdArray != null && brandPdArray.length() > 0){
							for(int i = 0; i < brandPdArray.length(); i++){
								JSONObject object = brandPdArray.getJSONObject(i);
								PdInfo brandPdItem = new PdInfo();
								brandPdItem.setPdId(object.getLong("pdId"));
								brandPdItem.setPdName(object.getString("pdName"));
								brandPdItem.setPrice(object.getString("price"));
								brandPdItem.setImageurl(object.getString("imageurl"));
								brandPdList.add(brandPdItem);
							}
						}
						if(array != null && array.length() > 0){
							for(int i = 0; i < array.length(); i++){
								ImageView view = new ImageView(getApplicationContext());
					    		if(i==0){
					    			pdUrl = (String) array.get(i);
					    			view.setBackgroundResource(R.drawable.default_pd_image);//给一个默认图
					    		}
					    		view.setTag(array.get(i));
					    		view.setScaleType(ScaleType.FIT_XY);
						        views.add(view);
						        
						        ImageView dotView = new ImageView(getApplicationContext());
					            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
					            params.leftMargin = 4;
					            params.rightMargin = 4;
					            if(i == 0){
					            	dotView.setBackgroundResource(R.drawable.point_select);
					            }else{
					            	dotView.setBackgroundResource(R.drawable.point_normal);
					            }
					            dotLayout.addView(dotView, params);
					            dotViewsList.add(dotView);
							}
							vpAdapter = new ViewPagerAdapter(views,getApplicationContext());//实例化ViewPager适配器
					    	vpAdapter.notifyDataSetChanged();
					    	pdViewPager.setAdapter(vpAdapter);//设置数据
					    	
					    	pdViewPager.setOnPageChangeListener(new OnPageChangeListener() {
								
								@Override
								public void onPageSelected(int pos) {
									// TODO Auto-generated method stub
						            for(int i=0;i < dotViewsList.size();i++){
						                if(i == pos){
						                	((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.point_select);
						                }else {
						                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.point_normal);
						                }
						            }
								}
								
								@Override
								public void onPageScrolled(int arg0, float arg1, int arg2) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onPageScrollStateChanged(int arg0) {
									// TODO Auto-generated method stub
									switch (arg0) {
						            case 1:// 手势滑动，空闲中
						                isAutoPlay = false;
						                break;
						            case 2:// 界面切换中
						                isAutoPlay = true;
						                break;
						            case 0:// 滑动结束，即切换完毕或者加载完毕
						                // 当前为最后一张，此时从右向左滑，则切换到第一张
						                if (pdViewPager.getCurrentItem() == pdViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
						                	pdViewPager.setCurrentItem(0);
						                }
						                // 当前为第一张，此时从左向右滑，则切换到最后一张
						                else if (pdViewPager.getCurrentItem() == 0 && !isAutoPlay) {
						                	pdViewPager.setCurrentItem(pdViewPager.getAdapter().getCount() - 1);
						                }
						                break;
						            }
								}
							});
						}
						
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch(v.getId()){
		case R.id.back_index_pdDetail:
			this.finish();
			break;
		case R.id.add_cart_btn:
			int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
			pdViewPager.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
			ImageView buyImg = new ImageView(this);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
			vpAdapter.imageLoader.displayImage(pdUrl, buyImg);
			setAnim(buyImg, start_location);// 开始执行动画
			break;
		case R.id.cate_pd:
			productList.clear();
			for(PdInfo item : catepdList){
				productList.add(item);
			}
			pdAdapter.notifyDataSetChanged();
			break;
		case R.id.brand_pd:
			productList.clear();
			for(PdInfo item : brandPdList){
				productList.add(item);
			}
			pdAdapter.notifyDataSetChanged();
			break;
		case R.id.pd_detail_web:
			intent = new Intent(PdetailActivity.this,WebActivity.class);
			intent.putExtra("url", "http://pls.asj.com/wxo2o/index.htm");
			startActivity(intent);
			break;
		case R.id.foot_cart:
			intent = new Intent(PdetailActivity.this,CartActivity.class);
			startActivityForResult(intent,1);
			break;
		case R.id.pdeatil_cart_go:
			intent = new Intent(PdetailActivity.this,CartActivity.class);
			startActivityForResult(intent,1);
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	cartNum.setText(String.valueOf(Model.cartNum));
    	cartPriceText.setText("￥" + Model.cartPrice.toString());
    }
	
	/**
	 * @Description: 创建动画层
	 * @param
	 * @return void
	 * @throws
	 */
	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) (this).getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	private void setAnim(final View v, int[] start_location) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);//把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
		cartNum.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int endX = end_location[0] - start_location[0];// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setInterpolator(new LinearInterpolator());
		scaleAnimation.setFillAfter(true);
		
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(false);
		set.addAnimation(scaleAnimation);
		set.addAnimation(translateAnimationX);
		set.addAnimation(translateAnimationY);
		set.setDuration(800);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				Model.cartNum += 1;
				cartNum.setText(String.valueOf(Model.cartNum));
				Model.cartPrice = Model.cartPrice.add(price);
		    	cartPriceText.setText("￥" + Model.cartPrice.toString());
				v.setVisibility(View.GONE);
			}
		});

	}
	
}
