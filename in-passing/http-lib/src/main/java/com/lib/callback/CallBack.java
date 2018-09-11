package com.lib.callback;

/**
 * Created by zw on 2018/9/11.
 */

public interface CallBack<T> {
	void OnSuccess(T t);

	void onFaul(String s);
}
