package com.inpassing.wallpaper.p;

import com.common.view.mvp.p.BasePresenter;
import com.inpassing.api.APIurl;
import com.inpassing.wallpaper.m.Wallpaper;
import com.lib.http.RetrofitClient;
import com.lib.httpsub.HttpCallback;
import com.lib.httpsub.HttpSubscribe;

import io.reactivex.Observable;

public class WallpaperPresenter extends HttpSubscribe implements BasePresenter<HttpCallback<Wallpaper>> {
	private int page;

	@Override
	public void post(HttpCallback<Wallpaper> imageCategoryHttpCallback) {
		APIurl apIurl = RetrofitClient.retrofit(APIurl.IMAGER_URL, "0").create(APIurl.class);
		Observable<Wallpaper> observable = apIurl.getWallpaper(page);
		toSubscribe(observable, imageCategoryHttpCallback);
	}

	@Override
	public void get(HttpCallback<Wallpaper> imageCategoryHttpCallback) {
	}

	public void setPage(int page) {
		this.page = page;
	}
}
