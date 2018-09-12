package com.inpassing.wallpaper.v;

import android.view.View;

import com.common.custom.view.CmSwipeMenuRecyclerView;
import com.common.view.mvp.p.v.BaseInit;
import com.common.view.mvp.p.v.BaseView;
import com.inpassing.wallpaper.adapter.TestWallpaperAdapter;
import com.inpassing.wallpaper.m.Wallpaper;

/**
 * Created by zw on 2018/9/11.
 */

public class WallpaperView extends BaseInit<View> implements BaseView<Wallpaper, View> {
	private TestWallpaperAdapter recyclerAdapter;

	@Override
	public void init(View view) {
		super.init(view);
		if (recyclerAdapter == null) {
			recyclerAdapter = new TestWallpaperAdapter();
			((CmSwipeMenuRecyclerView) view).setAdapter(recyclerAdapter);
		}
	}

	@Override
	public void onUI(Wallpaper wallpaper, View view) {
	}

	@Override
	public void onLoadUI(Wallpaper wallpaper, View view) {
		recyclerAdapter.addDatas(wallpaper.getData());
		((CmSwipeMenuRecyclerView) view).setPullLoadMoreCompleted();
	}

	@Override
	public void onRefresh(Wallpaper wallpaper, View view) {
		recyclerAdapter.setDatas(wallpaper.getData());
		((CmSwipeMenuRecyclerView) view).setPullLoadMoreCompleted();
	}

}
