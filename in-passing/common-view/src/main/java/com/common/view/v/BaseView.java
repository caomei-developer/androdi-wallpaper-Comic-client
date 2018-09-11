package com.common.view.v;

/**
 * Created by zw on 2018/9/11.
 */

public interface BaseView<T> {
	void onSuccess(T v);

	void onFilure(T v);
}
