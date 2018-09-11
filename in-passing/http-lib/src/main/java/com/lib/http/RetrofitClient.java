package com.lib.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zw on 2018/9/11.
 */

public class RetrofitClient {
	private static RetrofitClient retrofitClient;

	public static RetrofitClient getInstance() {
		if (retrofitClient == null) {
			synchronized (RetrofitClient.class) {
				if (retrofitClient == null) {// 双重锁
					retrofitClient = new RetrofitClient();
				}
			}
		}
		return retrofitClient;
	}

	public static Retrofit retrofit(String url, String token) {
		Retrofit retrofit = new Retrofit.Builder().client(OkhttpClient.getInstance().getOkHttpBuilder(token).build())
				.addConverterFactory(GsonConverterFactory.create())// json转换成JavaBean
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(url).build();
		return retrofit;
	}

}
