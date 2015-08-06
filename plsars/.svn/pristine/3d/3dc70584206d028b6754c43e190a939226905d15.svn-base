package com.asj.pls.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.asj.pls.R;
import com.asj.pls.activity.HomeActivity;
import com.asj.pls.adapter.OrdersAdapter;
import com.asj.pls.info.OrdersInfo;
import com.asj.pls.util.JSONUtils;
import com.asj.pls.view.OrdersListView;
import com.asj.pls.view.OrdersListView.OnLoadListener;
import com.asj.pls.view.OrdersListView.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyTabFragmentThree extends Fragment implements OnRefreshListener,OnLoadListener{

	/** 
     * 订单适配器
     */
	private OrdersAdapter ordersAdapter = null;
	
	/** 
     * 订单列表控件
     */
	private OrdersListView orderslistview = null;
	
	/** 
     * 订单列表
     */
	private ArrayList<OrdersInfo> orderlist = new ArrayList<OrdersInfo>();
	
	/** 
     * 商品明细列表
     */
	private ArrayList<ArrayList<String>> pdlist = new ArrayList<ArrayList<String>>();
	
	private String dataJson = "{'orderlist':[{'ordersNo':'asj000001','price':'121.00','status':'订单交易成功','time':'2016-06-21','value':0},{'ordersNo':'asj000002','price':'122.00','status':'订单待支付','time':'2016-06-22','value':1},{'ordersNo':'asj000003','price':'123.00','status':'订单取消','time':'2016-06-23','value':2},{'ordersNo':'asj000004','price':'23.00','status':'订单取消','time':'2016-06-24','value':0},{'ordersNo':'asj000005','price':'1235.00','status':'订单取消','time':'2016-06-25','value':1}],'pdlist':[['商铺名称商铺名称商铺名称 x1'],['商铺名称商铺名称商铺名称 x1','商铺名称商铺名称商铺名称 x2'],['商铺名称商铺名称商铺名称 x1','商铺名称商铺名称商铺名称 x2','商铺名称商铺名称商铺名称 x3'],['商铺名称商铺名称商铺名称 x1','商铺名称商铺名称商铺名称 x2','商铺名称商铺名称商铺名称 x3','商铺名称商铺名称商铺名称 x4'],['商铺名称商铺名称商铺名称 x1','商铺名称商铺名称商铺名称 x2','商铺名称商铺名称商铺名称 x3','商铺名称商铺名称商铺名称 x4','商铺名称商铺名称商铺名称 x5']]}";
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ordersLayout = inflater.inflate(R.layout.layout_orders, container, false);
        orderslistview = (OrdersListView) ordersLayout.findViewById(R.id.OrdersListView);
        ordersAdapter = new OrdersAdapter(ordersLayout.getContext(), orderlist, pdlist);
        orderslistview.setAdapter(ordersAdapter);
        orderslistview.setonRefreshListener(this);
        orderslistview.setonLoadListener(this);
        return ordersLayout;
    }
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Void>() {
    		protected Void doInBackground(Void... params) {
    			try {
    				Thread.sleep(1000);
    				orderlist.clear();
    				pdlist.clear();
    				JSONArray ordersJsonArray = JSONUtils.parseJsonMulti(dataJson, "orderlist");
    				JSONArray ordersPdJsonArray = JSONUtils.parseJsonMulti(dataJson, "pdlist");
                    for (int i = 0; i < ordersJsonArray.length(); i++) {
                    	JSONObject object = ordersJsonArray.getJSONObject(i);
                    	OrdersInfo od = new OrdersInfo();
                    	od.setOrdersNo(object.getString("ordersNo"));
                    	od.setTime(object.getString("time"));
                    	od.setStatus(object.getString("status"));
                    	od.setPrice(object.getString("price"));
                    	od.setValue(object.getInt("value"));
                    	orderlist.add(od);
                    }
                    for (int i = 0; i < ordersPdJsonArray.length(); i++) {
                    	JSONArray pdArray = ordersPdJsonArray.getJSONArray(i);
                    	ArrayList<String> pdItem = new ArrayList<String>();
                    	for (int j = 0; j < pdArray.length(); j++) {
                    		pdItem.add(pdArray.getString(j));
                    	}
                    	pdlist.add(pdItem);
                    }
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			return null;
    		}

    		@Override
    		protected void onPostExecute(Void result) {
    			ordersAdapter.notifyDataSetChanged();
    			orderslistview.onRefreshComplete();
    		}
    	}.execute(null, null, null);
    }
	
	@Override
	public void onLoad() {
		new AsyncTask<Void, Void, Void>() {
    		protected Void doInBackground(Void... params) {
    			try {
    				Thread.sleep(1000);
    				JSONArray ordersJsonArray = JSONUtils.parseJsonMulti(dataJson, "orderlist");
    				JSONArray ordersPdJsonArray = JSONUtils.parseJsonMulti(dataJson, "pdlist");
                    for (int i = 0; i < ordersJsonArray.length(); i++) {
                    	JSONObject object = ordersJsonArray.getJSONObject(i);
                    	OrdersInfo od = new OrdersInfo();
                    	od.setOrdersNo(object.getString("ordersNo"));
                    	od.setTime(object.getString("time"));
                    	od.setStatus(object.getString("status"));
                    	od.setPrice(object.getString("price"));
                    	od.setValue(object.getInt("value"));
                    	orderlist.add(od);
                    }
                    for (int i = 0; i < ordersPdJsonArray.length(); i++) {
                    	JSONArray pdArray = ordersPdJsonArray.getJSONArray(i);
                    	ArrayList<String> pdItem = new ArrayList<String>();
                    	for (int j = 0; j < pdArray.length(); j++) {
                    		pdItem.add(pdArray.getString(j));
                    	}
                    	pdlist.add(pdItem);
                    }
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			
    			return null;
    		}

    		@Override
    		protected void onPostExecute(Void result) {
    			ordersAdapter.notifyDataSetChanged();
    			orderslistview.loadComplete();
    		}
    	}.execute(null, null, null);
	}
	
	public void initOrdersList(){
		new Thread(new Runnable() {
        	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
    				Thread.sleep(2000);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
				Message msg = new Message();
				msg.obj = dataJson;
				mHandler.sendMessage(msg);
			}
			
        }).start();
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String jsonStr = (String) msg.obj;
			try {
				JSONArray ordersJsonArray = JSONUtils.parseJsonMulti(jsonStr, "orderlist");
				JSONArray ordersPdJsonArray = JSONUtils.parseJsonMulti(jsonStr, "pdlist");
                for (int i = 0; i < ordersJsonArray.length(); i++) {
                	JSONObject object = ordersJsonArray.getJSONObject(i);
                	OrdersInfo od = new OrdersInfo();
                	od.setOrdersNo(object.getString("ordersNo"));
                	od.setTime(object.getString("time"));
                	od.setStatus(object.getString("status"));
                	od.setPrice(object.getString("price"));
                	od.setValue(object.getInt("value"));
                	orderlist.add(od);
                }
                for (int i = 0; i < ordersPdJsonArray.length(); i++) {
                	JSONArray pdArray = ordersPdJsonArray.getJSONArray(i);
                	ArrayList<String> pdItem = new ArrayList<String>();
                	for (int j = 0; j < pdArray.length(); j++) {
                		pdItem.add(pdArray.getString(j));
                	}
                	pdlist.add(pdItem);
                }
                ordersAdapter.notifyDataSetChanged();
                
			} catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
			}finally {
				if (HomeActivity.dialog != null) {
					HomeActivity.dialog.dismiss();
				}
			}
		};
		
	};
}
