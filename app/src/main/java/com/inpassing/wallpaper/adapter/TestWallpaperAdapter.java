package com.inpassing.wallpaper.adapter;

import android.widget.ImageView;

import com.common.custom.view.adapter.BaseRecyclerAdapter;
import com.common.custom.view.swipemenu.SwipeMenuLayout;
import com.common.view.image.CommonImageLoader;
import com.inpassing.R;
import com.inpassing.wallpaper.m.Wallpaper;

public class TestWallpaperAdapter extends BaseRecyclerAdapter<Wallpaper.DataBean> {

	@Override
	public int getItemLayoutId(int viewType) {
		return R.layout.test_wallpaper_item_layout;
	}

	@Override
	public void setUpData(CommonHolder holder, int position, int viewType, Wallpaper.DataBean data) {
		final SwipeMenuLayout superSwipeMenuLayout = (SwipeMenuLayout) holder.itemView;
		superSwipeMenuLayout.setSwipeEnable(false);// 设置是否可以侧滑
		ImageView imageView = getView(holder, R.id.wallpaper_item_image);
		CommonImageLoader.load(data.getUrl()).into(imageView);
	}
}