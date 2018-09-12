package com.inpassing.wallpaper.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.custom.view.CmSwipeMenuRecyclerView;
import com.common.custom.view.recyclerView.CmRecyclerView;
import com.common.view.fragment.BaseFragment;
import com.inpassing.R;
import com.inpassing.wallpaper.m.Wallpaper;
import com.inpassing.wallpaper.p.WallpaperPresenter;
import com.inpassing.wallpaper.v.WallpaperView;
import com.lib.callback.CallBack;
import com.lib.httpsub.CallbackSub;

public class TestFragment extends BaseFragment {
	private TextView title;
	private CmSwipeMenuRecyclerView recyclerView;

	private WallpaperPresenter wallpaperPresenter;
	private WallpaperView wallpaperView;

	private int page = 0;

	private boolean isLoading = false;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.test_wallpaper_fragment, container, false);
	}

	@Override
	public void initView(View view) {
		super.initView(view);
		title = view.findViewById(R.id.fragment_tabbar_title);
		title.setText("美图桌面");
		recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
		recyclerView.getLoadMoreFooterView().setBackgroundColor(Color.BLUE);
		recyclerView.setStaggeredGridLayout(true, 2);
		recyclerView.setLoadMoreTextColor(Color.WHITE);
	}

	@Override
	public void initHelper() {
		super.initHelper();
		wallpaperView = new WallpaperView();
		wallpaperPresenter = new WallpaperPresenter();

	}

	@Override
	public void loadData() {
		super.loadData();
		wallpaperPresenter.setPage(page);
		wallpaperPresenter.post(new CallbackSub<Wallpaper>(new CallBack<Wallpaper>() {
			@Override
			public void OnSuccess(Wallpaper wallpaper) {
				if (wallpaper != null) {
					wallpaperView.init(recyclerView);
					if (isLoading) {
						wallpaperView.onLoadUI(wallpaper, recyclerView);
					} else {
						wallpaperView.onRefresh(wallpaper, recyclerView);
					}
				}
			}

			@Override
			public void onFaul(String s) {

			}

		}));
	}

	class PullLoadMoreListener implements CmRecyclerView.PullLoadMoreListener {
		@Override
		public void onRefresh() {
			page = 0;
			isLoading = false;
			loadData();
		}

		@Override
		public void onLoadMore() {
			page = page + 1;
			isLoading = true;
			loadData();
		}
	}

}
