package com.inpassing.wallpaper.v;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.view.mvp.p.v.BaseView;
import com.inpassing.wallpaper.adapter.WallpaperAdapter;
import com.inpassing.wallpaper.m.Wallpaper;

/**
 * Created by zw on 2018/9/11.
 */

public class WallpaperView implements BaseView<Wallpaper, View> {

	@Override
	public void onUI(Wallpaper wallpaper, View view) {
		WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(view.getContext(), wallpaper.getData());
		((RecyclerView) view).setAdapter(wallpaperAdapter);
	}
}
