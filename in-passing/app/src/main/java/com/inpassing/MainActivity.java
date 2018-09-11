package com.inpassing;

import android.os.Bundle;

import com.common.view.BaseActivity;
import com.inpassing.home.m.ImageCategory;
import com.inpassing.home.p.ImagePresenter;
import com.lib.callback.CallBack;
import com.lib.httpsub.CallbackSub;

public class MainActivity extends BaseActivity {

	private ImagePresenter imagePresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imagePresenter = new ImagePresenter();
	}

	@Override
	protected void initView() {
		super.initView();
	}

	@Override
	protected void loadData() {
		super.loadData();
		imagePresenter.post(new CallbackSub<ImageCategory>(new CallBack<ImageCategory>() {
			@Override
			public void OnSuccess(ImageCategory imageCategory) {

			}

			@Override
			public void onFaul(String s) {

			}

		}));
	}

	private void uiHandler(ImageCategory image) {

	}

	@Override
	public boolean isToolbar() {
		return true;
	}
}
