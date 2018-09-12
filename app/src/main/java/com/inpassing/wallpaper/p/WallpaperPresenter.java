package com.inpassing.wallpaper.p;

import com.common.view.mvp.p.BasePresenter;
import com.inpassing.api.APIurl;
import com.inpassing.wallpaper.m.Wallpaper;
import com.lib.http.RetrofitClient;
import com.lib.httpsub.CallbackSub;
import com.lib.httpsub.HttpSub;

import io.reactivex.Observable;

public class WallpaperPresenter extends HttpSub implements BasePresenter<CallbackSub<Wallpaper>> {
	private int page;

	@Override
	public void post(CallbackSub<Wallpaper> imageCategoryCallbackSub) {
		APIurl apIurl = RetrofitClient.retrofit(APIurl.IMAGER_URL, "0").create(APIurl.class);
		Observable<Wallpaper> observable = apIurl.getWallpaper(page);
		toSubscribe(observable, imageCategoryCallbackSub);
	}

	@Override
	public void get(CallbackSub<Wallpaper> imageCategoryCallbackSub) {
	}

	public void setPage(int page) {
		this.page = page;
	}
}
