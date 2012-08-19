/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.deskclock2;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class ActionBarActivity extends Activity implements ActionBar.TabListener {
	void onAlarmSettingsClicked(){
		Fragment SF = new SettingsFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(0, SF);
	}
	
	public interface OnAlarmSettingsClickListener {
		void onAlarmSettingsClicked();	
	}
	ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		final ActionBar bar = getActionBar();
		if (bar != null) {
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_TITLE);
			bar.setTitle(R.string.app_label);
			bar.addTab(bar.newTab().setText("AlarmClock").setTabListener(this));
			bar.addTab(bar.newTab().setText("DesckClock").setTabListener(this));

			if (savedInstanceState != null) {
				bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
			}
		}

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.e("efo", "cakes");
		Object tag = tab.getTag();
		for (int i = 0; i < mTabs.size(); i++) {
			if (mTabs.get(i) == tag && mTabs.get(i).clss.toString() != this.getClass().toString()) {
				Log.e("efo", "Tab " + tag.toString() + " tapped");
				mViewPager.setCurrentItem(i);
				Intent myIntent = new Intent(this, ((TabInfo) tag).clss);
				// mContext.startActivityForResult(myIntent, 0);
				startActivity(myIntent);
			}
		}

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	static final class TabInfo {
		private final Class<?> clss;
		private final Bundle args;

		TabInfo(Class<?> _class, Bundle _args) {
			clss = _class;
			args = _args;
		}
	}
}