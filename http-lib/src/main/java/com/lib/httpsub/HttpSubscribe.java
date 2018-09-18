package com.lib.httpsub;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zw on 2018/9/11.
 */

public class HttpSubscribe<T> {
	public void toSubscribe(Observable<T> o, DisposableObserver<T> s) {
		o.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).retry(0)// 请求失败重连次数
				.subscribe(s);

	}
}
