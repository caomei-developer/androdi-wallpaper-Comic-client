package com.common.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import com.common.custom.view.recyclerView.CmRecyclerView;
import com.common.custom.view.swipemenu.SwipeMenuLayout;
import com.common.custom.view.swipemenu.SwipeMenuRecyclerView;

/**
 * Created by zw on 2018/9/12.
 */

public class CmSwipeMenuRecyclerView extends CmRecyclerView {
	private SwipeMenuRecyclerView swipeMenuRecyclerView;

	public CmSwipeMenuRecyclerView(Context context) {
		this(context, null);
	}

	public CmSwipeMenuRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs, R.layout.cm_recyclerview_swipemenu_pullrecycler);
		swipeMenuRecyclerView = (SwipeMenuRecyclerView) getRecyclerView();
		setIsProceeConflict(true);
	}

	public void setCloseInterpolator(Interpolator interpolator) {
		swipeMenuRecyclerView.setCloseInterpolator(interpolator);
	}

	public void setOpenInterpolator(Interpolator interpolator) {
		swipeMenuRecyclerView.setOpenInterpolator(interpolator);
	}

	public Interpolator getOpenInterpolator() {
		return swipeMenuRecyclerView.getOpenInterpolator();
	}

	public Interpolator getCloseInterpolator() {
		return swipeMenuRecyclerView.getCloseInterpolator();
	}

	/**
	 * open menu manually
	 *
	 * @param position
	 *            the adapter position
	 */
	public void smoothOpenMenu(int position) {
		swipeMenuRecyclerView.smoothOpenMenu(position);
	}

	/**
	 * close the opened menu manually
	 */
	public void smoothCloseMenu() {
		swipeMenuRecyclerView.smoothCloseMenu();
	}

	public void setOnSwipeListener(SwipeMenuRecyclerView.OnSwipeListener onSwipeListener) {
		swipeMenuRecyclerView.setOnSwipeListener(onSwipeListener);
	}

	/**
	 * get current touched view
	 *
	 * @return touched view, maybe null
	 */
	public SwipeMenuLayout getTouchView() {
		return swipeMenuRecyclerView.getTouchView();
	}

	/**
	 * set the swipe direction
	 *
	 * @param direction
	 *            swipe direction (left or right)
	 */
	public void setSwipeDirection(int direction) {
		swipeMenuRecyclerView.setSwipeDirection(direction);
	}
}
