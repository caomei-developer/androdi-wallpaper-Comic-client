
package com.common.custom.view.loadmorefooter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.custom.view.R;

public class DefLoadMoreFooter extends LinearLayout implements CmLoadMoreFooter {

	public String STR_LOADING = "正在加载";
	public String STR_LOAD_COMPLETE = "正在加载";
	public String STR_NOMORE = "已加载全部";

	private SimpleViewSwitcher mProgressBar;
	private TextView loadTv;
	private boolean isShowNoMore = true;
	private int mMeasuredHeight;
	private Context context;

	public DefLoadMoreFooter(Context context) {
		this(context, null);
	}

	public DefLoadMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public void initView() {
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setLayoutParams(lp);
		inflate(context, R.layout.load_view_layout, this);
		loadTv = findViewById(R.id.load_view_tv);
		onReset();// 初始为隐藏状态
		measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		mMeasuredHeight = getMeasuredHeight();
	}

	/**
	 * 设置加载进度条文字
	 *
	 * @param text1
	 *            加载中
	 * @param text2
	 *            加载完成
	 * @param text3
	 *            已加载全部
	 */
	public void setText(String text1, String text2, String text3) {
		STR_LOADING = text1;
		STR_LOAD_COMPLETE = text2;
		STR_NOMORE = text3;
	}

	/**
	 * 设置加载进度条文字颜色
	 */
	public void setTextColor(int color) {
		loadTv.setTextColor(color);
	}

	/**
	 * 设置加载进度条文字大小
	 */
	public void setTextSize(float textSize) {
		loadTv.setTextSize(textSize);
	}

	@Override
	public void setIsShowNoMore(boolean isShow) {
		isShowNoMore = isShow;
	}

	@Override
	public void onReset() {
		onComplete();
	}

	@Override
	public void onLoading() {
		loadTv.setText(STR_LOADING);
		this.getLayoutParams().height = mMeasuredHeight;// 动态设置高度，否则在列表中会占位高度
		this.setVisibility(View.VISIBLE);
	}

	@Override
	public void onComplete() {
		loadTv.setText(STR_LOAD_COMPLETE);
		this.getLayoutParams().height = mMeasuredHeight;
		this.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onNoMore() {
		loadTv.setText(STR_NOMORE);
		this.setVisibility(isShowNoMore ? View.VISIBLE : View.GONE);
		this.getLayoutParams().height = isShowNoMore ? mMeasuredHeight : 5;
	}

	@Override
	public View getFootView() {
		return this;
	}
}
