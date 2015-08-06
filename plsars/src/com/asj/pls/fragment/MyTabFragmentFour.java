package com.asj.pls.fragment;

import com.asj.pls.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyTabFragmentFour extends Fragment implements OnClickListener{

	/** 
     * 区域 orders address coupon service pls 分别为订单、收货地址、优惠券、客服、拍立送介绍
     */
    private LinearLayout orders,address,coupon,service,pls;
    
    /** 
     * 退出
     */
    private TextView login_out;
    
    /** 
     * 个人头像
     */
    private ImageView my_head;
    
    /** 
     * 昵称、手机号
     */
    private TextView my_name,my_phone;
    
    /** 
     * 登录
     */
    private Button my_login;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View myLayout = inflater.inflate(R.layout.layout_my, container, false);
        
        orders = (LinearLayout)myLayout.findViewById(R.id.My_list_orders);//我的订单
        address = (LinearLayout)myLayout.findViewById(R.id.My_list_address);//收货地址
        coupon = (LinearLayout)myLayout.findViewById(R.id.My_list_coupon);//优惠券
        service = (LinearLayout)myLayout.findViewById(R.id.My_list_service);//客服服务
        pls = (LinearLayout)myLayout.findViewById(R.id.My_list_pls);//拍立送
        login_out = (TextView)myLayout.findViewById(R.id.My_login_out);//退出
        my_login = (Button)myLayout.findViewById(R.id.My_login);//登录
        
        my_head = (ImageView)myLayout.findViewById(R.id.My_head);
        my_name = (TextView)myLayout.findViewById(R.id.My_name);
        my_phone = (TextView)myLayout.findViewById(R.id.My_phone);
        
        orders.setOnClickListener(this);
        address.setOnClickListener(this);
        coupon.setOnClickListener(this);
        service.setOnClickListener(this);
        pls.setOnClickListener(this);
        login_out.setOnClickListener(this);
        my_login.setOnClickListener(this);
        return myLayout;  
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.My_list_orders://我的订单
			((OnClickListener)getActivity()).onClick(orders);
			break;
		case R.id.My_list_address://收货地址
			break;
		case R.id.My_list_coupon://优惠券
			break;
		case R.id.My_list_service://客服服务
			break;
		case R.id.My_list_pls://拍立送
			break;
		case R.id.My_login://登录
			my_head.setVisibility(View.VISIBLE);
			login_out.setVisibility(View.VISIBLE);
			my_login.setVisibility(View.INVISIBLE);
			my_name.setText("lijunquick");
			my_phone.setText("18367474208");
			break;
		case R.id.My_login_out://退出
			my_login.setVisibility(View.VISIBLE);
			my_head.setVisibility(View.INVISIBLE);
			login_out.setVisibility(View.INVISIBLE);
			my_name.setText("");
			my_phone.setText("");
			break;
		}
	}
}
