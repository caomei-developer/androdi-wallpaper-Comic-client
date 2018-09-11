package com.common.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		initView();
		loadData();
	}

	public boolean isToolbar() {
		return true;
	}

	protected void loadData() {

	}

	protected void initView() {
	}

}
