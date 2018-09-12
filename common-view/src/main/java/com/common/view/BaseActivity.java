package com.common.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.common.view.compoment.Toolbar;

public class BaseActivity extends AppCompatActivity {
	protected Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		if (isToolbar()) {
			super.setContentView(R.layout.activity_base);
			FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_layout);
			getLayoutInflater().inflate(layoutResID, contentFrameLayout, true);
			initToolbar();
		} else {
			super.setContentView(layoutResID);
		}
		initView();
	}

	private void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			if (getTitle() != null) {
				toolbar.setTitle(getTitle());
			}
			toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	public boolean isToolbar() {
		return true;
	}

	public void loadData() {

	}

	public void initView() {
		initHelper();
		loadData();
	}

	public void initHelper() {
	}

}
