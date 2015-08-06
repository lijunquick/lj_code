package com.asj.pls.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * tab适配器
 * </BR> </BR> By lijun
 * */
public class FragmentPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragmentsList;
	
	public FragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentsList) {
		super(fm);
		this.fragmentsList = fragmentsList;
	}
	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragmentsList.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentsList.size();
	}
	
}
