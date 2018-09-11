package com.lib;

import android.app.Application;

/**
 * Created by zw on 2018/9/11.
 */

public class BaseApplication extends Application {
	public static BaseApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}
}
