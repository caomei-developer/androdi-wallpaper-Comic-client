package com.common.view.mvp.p.v;

/**
 * Created by zw on 2018/9/11.
 */

public interface BaseView<T, V> {
	void onUI(T t, V v);

	void onLoadUI(T t, V v);

	void onRefresh(T t, V v);

}
