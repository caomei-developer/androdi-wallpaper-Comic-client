package com.inpassing.api;

import com.inpassing.home.m.ImageCategory;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zw on 2018/9/11.
 */

public interface APIurl {
	String IMAGER_URL = "http://route.showapi.com";

	@POST("/852-1")
	Observable<ImageCategory> imageCategory(@Query("showapi_appid") String showapiAppid,
			@Query("showapi_sign") String showapiSign, @Query("showapi_timestamp") String showapiTimestamp,
			@Query("showapi_res_gzip") String showapi_res_gzip);
}
