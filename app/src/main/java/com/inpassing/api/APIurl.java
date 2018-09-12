package com.inpassing.api;

import com.inpassing.wallpaper.m.Wallpaper;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zw on 2018/9/11.
 */

public interface APIurl {
	String IMAGER_URL = "https://www.apiopen.top";

	@POST("/meituApi?")
	Observable<Wallpaper> getWallpaper(@Query("page") int page);
}
