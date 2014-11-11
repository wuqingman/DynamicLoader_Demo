package com.llbt.dynamicloaderdemo;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button).setOnClickListener(this);
	}

	private void initData() {
	        String pluginFolder = Environment.getExternalStorageDirectory() + "/DynamicLoadHost";
	        File file = new File(pluginFolder);
	        File[] plugins = file.listFiles();
	        if (plugins == null || plugins.length == 0) {
	            return;
	        }

	        for (File plugin : plugins) {
	            PluginItem item = new PluginItem();
	            item.pluginPath = plugin.getAbsolutePath();
	            item.packageInfo = DLUtils.getPackageInfo(this, item.pluginPath);
	            if (item.packageInfo.activities != null && item.packageInfo.activities.length > 0) {
	                item.launcherActivityName = item.packageInfo.activities[0].name;
	            }
	            mPluginItems.add(item);
	            DLPluginManager.getInstance(this).loadApk(item.pluginPath);
	        }

	        mListView.setAdapter(mPluginAdapter);
	        mListView.setOnItemClickListener(this);
	        mPluginAdapter.notifyDataSetChanged();
	    }
	
	@Override
	public void onClick(View v) {
		
	}

}
