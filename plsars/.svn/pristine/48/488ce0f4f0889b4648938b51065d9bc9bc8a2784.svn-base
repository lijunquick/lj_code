package com.asj.pls.util;

import com.asj.pls.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/** 
 * dialog工具类
 *  
 * @author lijun 
 *  
 */
public class DialogUtils {

	public static Dialog createLoadingDialog(Context context, String msg) {
		
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_view);//加载布局
        // main.xml中的ImageView
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);//提示文字
        // 使用ImageView显示动画
        tipTextView.setText(msg);// 设置加载信息
        Dialog loadingDialog = new Dialog(context, R.style.publicloadingProgressStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ImageView imageView = (ImageView) v.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        return loadingDialog;
    }
	
}
