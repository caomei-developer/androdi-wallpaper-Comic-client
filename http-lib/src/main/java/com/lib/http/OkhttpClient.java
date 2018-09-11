package com.lib.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zw on 2018/9/11.
 */

public class OkhttpClient {
	public static OkhttpClient okhttpClient;

	public static String TAG = "zw-http-methods";
	private static OkHttpClient.Builder okHttpBuilder;

	public static OkhttpClient getInstance() {
		if (okhttpClient == null) {
			synchronized (OkhttpClient.class) {
				if (okhttpClient == null) {// 双重锁
					okhttpClient = new OkhttpClient();
				}
			}
		}
		return okhttpClient;
	}

	public static OkHttpClient.Builder getOkHttpBuilder(final String token) {
		okHttpBuilder = new OkHttpClient.Builder();
		/**
		 * 设置头信息
		 */
		Interceptor headerInterceptor = new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request originalRequest = chain.request();
				Request.Builder requestBuilder = originalRequest.newBuilder().addHeader("Accept-Encoding", "gzip")
						.addHeader("Accept", "application/json")
						.addHeader("Content-Type", "application/json; charset=utf-8")
						.method(originalRequest.method(), originalRequest.body());
				// requestBuilder.addHeader("Authorization", "Bearer " + token);//
				// 添加请求头信息，服务器进行token有效性验证
				Request request = requestBuilder.build();
				return chain.proceed(request);
			}
		};
		okHttpBuilder.addInterceptor(headerInterceptor);

		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				Log.e(TAG, message);
			}
		});
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		okHttpBuilder.addInterceptor(loggingInterceptor);
		return okHttpBuilder;
	}
}
