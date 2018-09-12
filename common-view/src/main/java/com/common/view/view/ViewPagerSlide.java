package com.common.view.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zw on 2018/9/12.
 */

public class ViewPagerSlide extends ViewPager {
	// 是否可以进行滑动
	private boolean isSlide = false;

	public void setSlide(boolean slide) {
		isSlide = slide;
	}

	public ViewPagerSlide(Context context) {
		super(context);
	}

	public ViewPagerSlide(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isSlide;
	}
}