package com.lib.httpsub;

import android.util.Log;

import com.lib.callback.CallBack;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by zw on 2018/9/11.
 */

public class HttpCallback<T> extends DisposableObserver<T> {
	private CallBack<T> callBack;

	public HttpCallback(CallBack callBack) {
		this.callBack = callBack;
	}

	public void onError(Throwable e) {
		try {

			if (e instanceof SocketTimeoutException) {// 请求超时
			} else if (e instanceof ConnectException) {// 网络连接超时
				callBack.onFaul("网络连接超时");
			} else if (e instanceof SSLHandshakeException) {// 安全证书异常
				callBack.onFaul("安全证书异常");
			} else if (e instanceof HttpException) {// 请求的地址不存在
				int code = ((HttpException) e).code();
				if (code == 504) {
				} else if (code == 404) {
					callBack.onFaul("请求的地址不存在");
				} else {
					callBack.onFaul("请求失败");
				}
			} else if (e instanceof UnknownHostException) {// 域名解析失败
				callBack.onFaul("域名解析失败");
			} else {
				callBack.onFaul(("error:" + e.getMessage()));
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
		}
	}

	@Override
	public void onComplete() {
	}

	@Override
	public void onNext(T body) {
		callBack.OnSuccess(body);
	}
}