package com.asj.pls.activity;

import com.asj.pls.R;
import com.asj.pls.util.KeyBoardUtils;
import com.asj.pls.util.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressOpActivity extends Activity implements OnClickListener{

	/** 
     * 返回
     */
    private ImageView exitOp;
    
    /** 
     * 标题、所在区域、提交
     */
    private TextView title,zone,submit;
    
    /** 
     * 姓名、手机、详细地址
     */
    private EditText conEditText,moblieEditText,detailEditText;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_address_op);
		initView();
		
		Intent intent = getIntent();
		Long addressId = intent.getLongExtra("id", 0L);
		if(addressId != 0L){
			title.setText("编辑收货地址");
		}else{
			title.setText("新建收货地址");
		}
	}
	
	private void initView(){
		
		exitOp = (ImageView)findViewById(R.id.exit_op);
		zone = (TextView)findViewById(R.id.link_zone);
		title = (TextView)findViewById(R.id.address_op_title);
		submit = (TextView)findViewById(R.id.address_submit);
		conEditText = (EditText) findViewById(R.id.link_name);
		moblieEditText = (EditText) findViewById(R.id.link_mobile);
		detailEditText = (EditText) findViewById(R.id.link_detail);
		
		KeyBoardUtils.openKeybord(conEditText, this);
		zone.setText((CharSequence) SPUtils.get("isLocation", "请选择..", this));
		exitOp.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.exit_op:
			KeyBoardUtils.closeKeybord(conEditText, this);
			this.finish();
			this.overridePendingTransition(R.anim.push_current, R.anim.push_right_out);
			break;
		case R.id.address_submit:
			KeyBoardUtils.closeKeybord(conEditText, this);
			this.finish();
			this.overridePendingTransition(R.anim.push_current, R.anim.push_right_out);
			break;
		}
	}
}
