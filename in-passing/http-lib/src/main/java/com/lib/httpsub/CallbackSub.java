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

public class CallbackSub<T> extends DisposableObserver<T> {
	private CallBack<T> callBack;

	public CallbackSub(CallBack callBack) {
		this.callBack = callBack;
	}

	public void onError(Throwable e) {
		try {

			if (e instanceof SocketTimeoutException) {// 请求超时
			} else if (e instanceof ConnectException) {// 网络连接超时
				// ToastManager.showShortToast("网络连接超时");
				callBack.onFaul("网络连接超时");
			} else if (e instanceof SSLHandshakeException) {// 安全证书异常
				// ToastManager.showShortToast("安全证书异常");
				callBack.onFaul("安全证书异常");
			} else if (e instanceof HttpException) {// 请求的地址不存在
				int code = ((HttpException) e).code();
				if (code == 504) {
					// ToastManager.showShortToast("网络异常，请检查您的网络状态");
					callBack.onFaul("网络异常，请检查您的网络状态");
				} else if (code == 404) {
					// ToastManager.showShortToast("请求的地址不存在");
					callBack.onFaul("请求的地址不存在");
				} else {
					// ToastManager.showShortToast("请求失败");
					callBack.onFaul("请求失败");
				}
			} else if (e instanceof UnknownHostException) {// 域名解析失败
				// ToastManager.showShortToast("域名解析失败");
				callBack.onFaul("域名解析失败");
			} else {
				// ToastManager.showShortToast("error:" + e.getMessage());
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

	/**
	 * 当result等于1回调给调用者，否则自动显示错误信息，若错误信息为401跳转登录页面。 ResponseBody body =
	 * response.body();//获取响应体 InputStream inputStream = body.byteStream();//获取输入流
	 * byte[] bytes = body.bytes();//获取字节数组 String str = body.string();//获取字符串数据
	 */
	@Override
	public void onNext(T body) {
		callBack.OnSuccess(body);

	}
}