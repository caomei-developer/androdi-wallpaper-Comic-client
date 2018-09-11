package com.common.view.p;

/**
 * Created by zw on 2018/9/11.
 */

public interface BasePresenter<CallBackSub> {

	void post(CallBackSub callBackSub);

	void get(CallBackSub callBackSub);
}
