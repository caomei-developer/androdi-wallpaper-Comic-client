package com.inpassing.wallpaper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.view.fragment.BaseFragment;
import com.inpassing.R;
import com.inpassing.wallpaper.m.Wallpaper;
import com.inpassing.wallpaper.p.WallpaperPresenter;
import com.inpassing.wallpaper.v.WallpaperView;
import com.lib.callback.CallBack;
import com.lib.httpsub.CallbackSub;

public class WallpaperFragment extends BaseFragment {
	private RecyclerView recyclerView;
	private TextView title;
	private WallpaperPresenter wallpaperPresenter;
	private WallpaperView wallpaperView;

	private int page = 0;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.wallpaper_fragment, container, false);
	}

	@Override
	public void initView(View view) {
		super.initView(view);
		title = view.findViewById(R.id.fragment_tabbar_title);
		recyclerView = view.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		title.setText("美图桌面");
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
					wallpaperView.onUI(wallpaper, recyclerView);
					page++;
				}
			}

			@Override
			public void onFaul(String s) {

			}

		}));
	}

}
