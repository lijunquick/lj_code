package com.asj.pls.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.asj.pls.R;
import com.asj.pls.activity.LocationActivity;
import com.asj.pls.activity.PromoteActivity;
import com.asj.pls.activity.SearchActivity;
import com.asj.pls.activity.WebActivity;
import com.asj.pls.adapter.IndexAdapter;
import com.asj.pls.adapter.IndexCateAdapter;
import com.asj.pls.adapter.ViewPagerAdapter;
import com.asj.pls.info.CateInfo;
import com.asj.pls.info.PdInfo;
import com.asj.pls.util.JSONUtils;
import com.asj.pls.util.LOGUtils;
import com.asj.pls.util.SPUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView.ScaleType;

public class MyTabFragmentOne extends Fragment implements OnClickListener{

	/** 
     * 首页
     */
	private View indexLayout;
	
	/** 
     * 位置状态栏
     */
    private TextView addressText;
    
    /** 
     * 定位按钮
     */
    private Button locationBtn;
	
    /** 
     * 商品搜索图标
     */
    private ImageView searchPdImage;
    
    /** 
	  * 定位核心sdk
	  */
	private LocationClient mLocationClient;
	
	/** 
     * 定义ViewPager对象
     */
	private ViewPager viewPager;
		
	/** 
     * 定义ViewPager适配器
     */
	private ViewPagerAdapter vpAdapter;
	
	/** 
     * 定义一个ArrayList来存放View
     */
    private ArrayList<View> views;
    
    /** 
     * 放圆点的View的list
     */
    private List<View> dotViewsList;
    
    /** 
     * 记录当前选中位置
     */
    private int currentIndex;
    
    /** 
     * 记录当前选中位置
     */
    private boolean isAutoPlay = true;
    
    /** 
     * 定时任务
     */
    private ScheduledExecutorService scheduledExecutorService;
    
    /** 
     * 首页列表控件
     */
    private ListView indexListView;
    
    /** 
     * 首页商品类别及商品
     */
    private ArrayList<CateInfo> catelist = new ArrayList<CateInfo>();
    
    /** 
     * 商品分类适配器
     */
	private IndexAdapter indexAdapter;
	
	/** 
     * 未定位显示布局
     */
	private LinearLayout unLoction;
	
	/** 
     * 首页轮播下空间 我想要、热销榜、分类
     */
	private TextView want,hot,cate;
	
	/** 
     * 分类适配器
     */
	private IndexCateAdapter cateAdapter;
	
	/** 
     * 首页分类图片楼层
     */
	private GridView cateGirdView;
	
	private float dowxX = 0f;
	
	private float upX = 0f;
    /** 
     * 图片资源
     */
    private String[] imageUrls = {"http://image.asj.com/o2oImageAd/2015/5/24/20/08232514324702051688406185512.jpg","http://image.asj.com/o2oImageAd/2015/5/24/20/08272714324704473547639274908.jpg","http://image.asj.com/o2oImageAd/2015/5/25/10/10483914325221197047443989444.jpg"};
	 
    private String dataJson = "{'data':[{'cateId':1,'cateName':'奶酒饮料','imageUrl':'http://image.asj.com/oo/base/category/image/201506121121515392989331_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155231439474138233500374.jpg','pdId':1,'pdName':'景田 百岁山矿泉水 570ml','price':'￥2.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281149399291374893634.jpg','pdId':2,'pdName':'伊利 安慕希利乐钻酸奶 205g','price':'￥5.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155231526499799528279998.jpg','pdId':3,'pdName':'康好 芒果汁饮料 828ml/瓶','price':'￥9.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015528163734718045005125.jpg','pdId':4,'pdName':'崂山 精品啤酒 600ml/瓶','price':'￥2.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015627154151804598541129.jpg','pdId':5,'pdName':'农夫山泉 矿泉水 4L','price':'￥7.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524202514799556709621.jpg','pdId':6,'pdName':'崂山 冰镇精品啤酒 600ml/瓶','price':'￥2.90'}]},{'cateId':2,'cateName':'粮油副食','imageUrl':'http://image.asj.com/oo/base/category/image/201506121121446304835551_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015627113459418927456780.jpg','pdId':2,'pdName':'龙大 调和油 5L','price':'￥69.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156271555206795637847504.jpg','pdId':3,'pdName':'胖子 酸菜鱼佐料 300g','price':'￥7.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015628161659386184733740.jpg','pdId':4,'pdName':'甘汁园 纯正红糖 350g','price':'￥5.70'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156271031569276028322855.jpg','pdId':5,'pdName':'峪林 包子饺子料 35g','price':'￥2.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156301448586048482123079.jpg','pdId':6,'pdName':'鲁花 纯正花生油 1L','price':'￥35.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201569141014118994702510.jpg','pdId':7,'pdName':'红菱花 味精 100g/袋','price':'￥2.30'}]},{'cateId':3,'cateName':'休闲食品','imageUrl':'http://image.asj.com/oo/base/category/image/201506121121588464875941_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281017367793285427537.jpg','pdId':3,'pdName':'达利园 软面包香奶味 18个 360g','price':'￥9.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156271724104617968312061.jpg','pdId':4,'pdName':'众望 小麻花 鸡汁 130g','price':'￥3.60'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156301458537911177538609.jpg','pdId':5,'pdName':'绿箭 柠檬草薄荷味口香糖 40粒/64g','price':'￥8.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155231456419364358480384.jpg','pdId':6,'pdName':'炫迈 水密西瓜味无糖口香糖 21.6g/12片','price':'￥5.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201552314518802302964292.jpg','pdId':7,'pdName':'百醇 牛奶味注心饼干 48g','price':'￥5.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015523144994338920094282.jpg','pdId':8,'pdName':'徐福记 香酥芝麻味沙琪玛 311g','price':'￥8.90'}]},{'cateId':4,'cateName':'新鲜水果','imageUrl':'http://image.asj.com/oo/base/category/image/201506121121272502739504_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156291643375466617340175.jpg','pdId':4,'pdName':'早天下 青提约1斤','price':'￥5.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201565100148696148492325.jpg','pdId':5,'pdName':'早天下 皇冠梨 约2斤','price':'￥9.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156301539142722378482597.jpg','pdId':6,'pdName':'早天下 沂源红富士苹果70# 3斤装 约9-10个','price':'￥9.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156595771790015738348.jpg','pdId':7,'pdName':'早天下 柠檬 约1斤','price':'￥7.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241650177622312848725.jpg','pdId':8,'pdName':'早天下 沂源富士苹果80# 5斤约11-12个','price':'￥17.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156595950765574798888.jpg','pdId':9,'pdName':'早天下 绿宝甜瓜 约3斤','price':'￥5.40'}]},{'cateId':5,'cateName':'田园蔬菜','imageUrl':'http://image.asj.com/oo/base/category/image/201506121121176597128681_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155311623581975085981911.jpg','pdId':5,'pdName':'早天下 胡萝卜 约1斤','price':'￥1.60'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524171319514455836208.jpg','pdId':6,'pdName':'早天下 金针菇 约1斤','price':'￥4.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241948392294263354034.jpg','pdId':7,'pdName':'早天下 油麦菜 约1斤','price':'￥1.70'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241947501597037728325.jpg','pdId':8,'pdName':'早天下 白菜 约2.5斤','price':'￥2.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241659156343945900278.jpg','pdId':9,'pdName':'早天下 西红柿 约2斤','price':'￥3.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524171323669320128505.jpg','pdId':10,'pdName':'早天下 杏鲍菇 约0.6斤','price':'￥3.30'}]},{'cateId':6,'cateName':'鲜肉禽蛋','imageUrl':'http://image.asj.com/oo/base/category/image/201506121121095342445789_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156121655506294783383785.jpg','pdId':6,'pdName':'金锣 前排 500g','price':'￥16.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015612165725514499498907.jpg','pdId':7,'pdName':'金锣 中排 500g','price':'￥18.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015611722283006333633422.jpg','pdId':8,'pdName':'金锣精肉 500g','price':'￥16.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156121654554393649428890.jpg','pdId':9,'pdName':'金锣 后腿肉 500g','price':'￥16.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156121655357472139225149.jpg','pdId':11,'pdName':'金锣 后腿精肉 500g','price':'￥16.90'}]},{'cateId':7,'cateName':'干货/加工','imageUrl':'http://image.asj.com/oo/base/category/image/201506121121013710545518_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201562993956653072535580.jpg','pdId':7,'pdName':'双汇 加钙王火腿肠 380g','price':'￥10.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281126593011897396244.jpg','pdId':8,'pdName':'金锣 香甜王火腿肠 240g*2','price':'￥9.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241546153405967881686.jpg','pdId':9,'pdName':'双汇 非常花声香肠 35g*10','price':'￥12.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015523152833216361486710.jpg','pdId':10,'pdName':'金锣 无淀粉王中王火腿 350g/袋','price':'￥12.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201571164757281718949246.jpg','pdId':11,'pdName':'双汇 Q趣香肠 蘑菇味 85g','price':'￥2.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015523153088495685991136.jpg','pdId':12,'pdName':'金锣 Q蘑菇风味香肠 85g/个','price':'￥16.90'}]},{'cateId':8,'cateName':'冷藏冷冻','imageUrl':'http://image.asj.com/oo/base/category/image/201506121120506708398718_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201571151026795940997301.jpg','pdId':8,'pdName':'亚奥特 草莓酸奶 110g','price':'￥1.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155281521301523237195496.jpg','pdId':9,'pdName':'得益 纯牛奶 214ml/袋','price':'￥2.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156271034292922582238271.jpg','pdId':10,'pdName':'云鹤 黄金三角豆沙春卷 238g ','price':'￥3.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015628189306195028721131.jpg','pdId':11,'pdName':'佳宝 原味酸奶 180g','price':'￥2.30'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015527172458599782787382.jpg','pdId':12,'pdName':'伊利 大布丁雪糕 65g/个','price':'￥1.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155231542552706205982258.jpg','pdId':13,'pdName':'伊利 双莓酸奶味雪糕 110g/个','price':'￥3.50'}]},{'cateId':9,'cateName':'家庭清洁','imageUrl':'http://image.asj.com/oo/base/category/image/201506121120261147967322_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201562891036577948578588.jpg','pdId':9,'pdName':'爱特福 84消毒液 518ml','price':'￥3.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156271711422063546807400.jpg','pdId':10,'pdName':'清清美 卫生手套','price':'￥3.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015628181026247651451533.jpg','pdId':11,'pdName':'嘉跃 深盆 35cm 颜色随机','price':'￥7.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156301433318292967260217.jpg','pdId':12,'pdName':'佳丽 熏衣草清新剂 320ml','price':'￥11.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156271659275218200421987.jpg','pdId':13,'pdName':'清风 绿茶抹香迷你抽纸 200抽','price':'￥4.30'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155231617211046585895454.jpg','pdId':14,'pdName':'珍爱 洁阴卫生湿巾 10片','price':'￥3.60'}]},{'cateId':10,'cateName':'个人洗护','imageUrl':'http://image.asj.com/oo/base/category/image/201506121120161016333394_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281123581279980430738.jpg','pdId':10,'pdName':'高露洁 茶健牙膏 140g','price':'￥4.20'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20155241520467897663967495.jpg','pdId':11,'pdName':'皮皮狗 金银花喷雾花露水 230ml','price':'￥13.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156271531555858380045061.jpg','pdId':12,'pdName':'妮维雅 丝润柔珠洁面乳 100g','price':'￥19.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015629152017000313009741.jpg','pdId':13,'pdName':'蓝月亮 儿童洗手液 甜橙 225ml','price':'￥10.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156251843206622147226571.jpg','pdId':14,'pdName':'纳爱斯 伢牙乐儿童牙膏 香香哈密 40g','price':'￥5.90'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281134112996153292739.jpg','pdId':15,'pdName':'青蛙 牙刷 4支 颜色随机','price':'￥7.00'}]},{'cateId':11,'cateName':'家居用品','imageUrl':'http://image.asj.com/oo/base/category/image/201506121135437428861183_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281135415955529070630.jpg','pdId':11,'pdName':'日美 叉匙','price':'￥2.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281815234987488139853.jpg','pdId':12,'pdName':'丽尊 不锈钢杯架','price':'￥6.50'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015711452274112206501104.jpg','pdId':13,'pdName':'丽尊 威士忌杯','price':'￥2.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201562818155099069424111.jpg','pdId':14,'pdName':'丽尊 玻璃把杯','price':'￥4.80'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201562983178600981188034.jpg','pdId':15,'pdName':'伊家乐 圆孔脚丫地垫 40*60cm','price':'￥18.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/20156281131156377675007271.jpg','pdId':16,'pdName':'南峰 锅刷','price':'￥4.90'}]},{'cateId':12,'cateName':'成人用品','imageUrl':'http://image.asj.com/oo/base/category/image/201506121101013809123728_brand.jpg','pdlist':[{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015523163646998113371133.jpg','pdId':12,'pdName':'薄0.05 极限超薄安全套 10只','price':'￥15.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/2015524162397966991192056.jpg','pdId':13,'pdName':'耐能 动感舒适避孕套 10只','price':'￥15.00'},{'imageurl':'http://image.asj.com/oo/base/product/image/180X180/201552416238968522517322.jpg','pdId':14,'pdName':'耐能 丝感超薄避孕套 10只','price':'￥15.00'}]}],'errorInfo':'请求成功','errorNo':0}";
    
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        indexLayout = inflater.inflate(R.layout.layout_index, container, false);
        
        unLoction = (LinearLayout)indexLayout.findViewById(R.id.index_body_unlogin);
        addressText = (TextView)indexLayout.findViewById(R.id.search_address_txt);
        searchPdImage = (ImageView)indexLayout.findViewById(R.id.index_search_image);
        locationBtn = (Button)indexLayout.findViewById(R.id.index_locatin_btn);
        indexListView = (ListView)indexLayout.findViewById(R.id.index_ListView);
        indexAdapter = new IndexAdapter(getActivity(), catelist);
        cateAdapter = new IndexCateAdapter(getActivity(), catelist);
        
		LinearLayout headerView = (LinearLayout) inflater.inflate(R.layout.layout_index_header, null);
        // 设置内边距，正好距离顶部为一个负的整个布局的高度，正好把头部隐藏
        headerView.setPadding(0, -1 * headerView.getMeasuredHeight(), 0, 0);
        // 重绘一下
        headerView.invalidate();
        indexListView.addHeaderView(headerView);
        indexListView.setAdapter(indexAdapter);
        
        want = (TextView)indexLayout.findViewById(R.id.index_i_want);
        hot = (TextView)indexLayout.findViewById(R.id.index_hot_buy);
        cate = (TextView)indexLayout.findViewById(R.id.index_cate_go);
        
        cateGirdView = (GridView)indexLayout.findViewById(R.id.cate_girdview);
        cateGirdView.setAdapter(cateAdapter);
        
        viewPager = (ViewPager) indexLayout.findViewById(R.id.slide_show_viewPager);//实例化ViewPager
    	viewPager.setFocusable(true);
        
        if(!isLocation()){//网络状态正常且第一次定位
			mLocationClient = new LocationClient(indexLayout.getContext().getApplicationContext());//实例化LocationClient类
			mLocationClient.registerLocationListener(new MyLocationListener());//注册监听函数
			this.InitLocation();//设置定位参数
			mLocationClient.start();
        }else{
        	String currentAddress = (String) SPUtils.get("isLocation", "请选择..", getActivity());
        	if(!currentAddress.equals("请选择..")){
        		currentAddress = "送至   " + currentAddress;
        	}
        	addressText.setText(currentAddress);
		}
        
        want.setOnClickListener(this);
        hot.setOnClickListener(this);
        cate.setOnClickListener(this);
        locationBtn.setOnClickListener(this);
        addressText.setOnClickListener(this);
        searchPdImage.setOnClickListener(this);
        
        String exitId = (String) SPUtils.get("shop_id", "0", getActivity());
        if(!exitId.equals("0")){
        	initSlideShow(Long.parseLong(exitId));
        }
        
        return indexLayout;  
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch(v.getId()){
		case R.id.index_search_image://商品搜索
			intent = new Intent(getActivity(),SearchActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_current);
			break;
		case R.id.index_i_want://我常买
			intent = new Intent(getActivity(),PromoteActivity.class);
			intent.putExtra("promotetype", "我常买");
			startActivity(intent);
			break;
		case R.id.index_hot_buy://热销榜
			intent = new Intent(getActivity(),PromoteActivity.class);
			intent.putExtra("promotetype", "热卖榜单");
			startActivity(intent);
			break;
		case R.id.index_cate_go://分类
			Toast.makeText(v.getContext(), "分类", Toast.LENGTH_SHORT).show();
			break;
		default://定位
			intent = new Intent(getActivity(),LocationActivity.class);
			startActivityForResult(intent,1);
			getActivity().overridePendingTransition(R.anim.push_up_in, R.anim.push_current);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        addressText.setText("送至   " + SPUtils.get("isLocation", "请选择..", getActivity()));
        String id = data.getExtras().getString("result");
        String exitId = (String) SPUtils.get("shop_id", "0", getActivity());
        
        if(!id.equals("0") && !id.equals(exitId)){
        	SPUtils.put("shop_id", id, getActivity());
        	initSlideShow(Long.parseLong(id));
        }
    }
	
	/**
	  * 定位
	  */
	 private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setAddrType("all");
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	    
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location
			if(location != null && location.getStreet() != null){
				SPUtils.put("isLocation", location.getStreet(), getActivity());
				LOGUtils.d("location", location.getStreet());
				addressText.setText("送至   " + location.getStreet());
			}else{
				addressText.setText("请选择..");
			}
			mLocationClient.stop();
		}
	}
		
	/** 
	 * 判断是否第一次定位
	 */
	private boolean isLocation() {
		boolean isFirstRun = SPUtils.contains("isLocation",getActivity());
		return isFirstRun;
	}
	
	/** 
	 * 动作定位
	 */
	public void selfLocation(){
		mLocationClient.start();
	}
	
	/** 
	 * 加载轮播
	 */
	public void initSlideShow(Long shopId){
		
		catelist.clear();
		unLoction.setVisibility(View.GONE);
		indexListView.setVisibility(View.VISIBLE);
		searchPdImage.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {
        	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.obtainMessage().sendToTarget();
			}
			
        }).start();
	    
	}

	
	/**
     * 开始轮播图切换
     */
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }
    
    /**
     *执行轮播图切换任务
     *
     */
    private class SlideShowTask implements Runnable{
    	
        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
            	currentIndex = (currentIndex+1)%views.size();
                handler.obtainMessage().sendToTarget();
            }
        }
        
    }
    
    private Handler handler = new Handler(){
    	
        @Override  
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentIndex);
        }
        
    };
    
    private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				JSONArray array = JSONUtils.parseJsonMulti(dataJson, "data");
				if(array != null && array.length() > 0){
					for(int i = 0; i < array.length(); i++){
						JSONObject object = array.getJSONObject(i);
						CateInfo cateItem = new CateInfo();
						ArrayList<PdInfo> productlist = new ArrayList<PdInfo>();
						JSONArray pdArray = object.getJSONArray("pdlist");
						for(int j = 0; j < pdArray.length(); j++){
							JSONObject pdObject = pdArray.getJSONObject(j);
							PdInfo pd = new PdInfo();
							pd.setPdId(pdObject.getLong("pdId"));
							pd.setPdName(pdObject.getString("pdName"));
							pd.setPrice(pdObject.getString("price"));
							pd.setImageurl(pdObject.getString("imageurl"));
							productlist.add(pd);
						}
						cateItem.setCateId(object.getLong("cateId"));
						cateItem.setCateName(object.getString("cateName"));
						cateItem.setImageUrl(object.getString("imageUrl"));
						cateItem.setProductlist(productlist);
						catelist.add(cateItem);
					}
					cateAdapter.notifyDataSetChanged();
					indexAdapter.notifyDataSetChanged();
				}
				
				//初始化轮播数据
				views = new ArrayList<View>();
				dotViewsList = new ArrayList<View>();
		        LinearLayout dotLayout = (LinearLayout)indexLayout.findViewById(R.id.dotLayout);
		        dotLayout.removeAllViews();
		        
		    	for (int i = 0; i < imageUrls.length; i++) {
		    		ImageView view = new ImageView(getActivity());
		    		if(i==0) view.setBackgroundResource(R.drawable.default_banner);//给一个默认图
		    		view.setTag(imageUrls[i]);
		    		view.setScaleType(ScaleType.FIT_XY);
			        views.add(view);
			        
			        ImageView dotView = new ImageView(getActivity());
		            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		            params.leftMargin = 4;
		            params.rightMargin = 4;
		            dotLayout.addView(dotView, params);
		            dotViewsList.add(dotView);
		    	}
		    	vpAdapter = new ViewPagerAdapter(views,getActivity());//实例化ViewPager适配器
		    	vpAdapter.notifyDataSetChanged();
			    viewPager.setAdapter(vpAdapter);//设置数据
			    if(isAutoPlay){
		            startPlay();
		        }
			    
			    viewPager.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						
						switch(event.getAction()){
						case MotionEvent.ACTION_DOWN:
							dowxX = event.getX();
							viewPager.getParent().requestDisallowInterceptTouchEvent(true);
							break;
						case MotionEvent.ACTION_MOVE:
							viewPager.getParent().requestDisallowInterceptTouchEvent(true);
							break;
						case MotionEvent.ACTION_UP:
							upX = event.getX();
							if(dowxX - upX < 5 && dowxX - upX > -5){
								Intent intent = new Intent(getActivity(),WebActivity.class);
								intent.putExtra("url", "http://pls.asj.com/wxo2o/index.htm");
								startActivity(intent);
							}
							viewPager.getParent().requestDisallowInterceptTouchEvent(false);
							break;
						case MotionEvent.ACTION_CANCEL:
							viewPager.getParent().requestDisallowInterceptTouchEvent(false);
							break;
						}
						return false;
					}
				});
			    
			    viewPager.setOnPageChangeListener(new OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int pos) {
						// TODO Auto-generated method stub
						currentIndex = pos;
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
			                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
			                    viewPager.setCurrentItem(0);
			                }
			                // 当前为第一张，此时从左向右滑，则切换到最后一张
			                else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
			                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
			                }
			                break;
			            }
					}
				});
			} catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
			}
		};
		
	};
}
