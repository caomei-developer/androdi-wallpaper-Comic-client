package com.inpassing.home.p;

import com.common.view.p.BasePresenter;
import com.inpassing.api.APIurl;
import com.inpassing.home.m.ImageCategory;
import com.lib.http.RetrofitClient;
import com.lib.httpsub.CallbackSub;
import com.lib.httpsub.HttpSub;

import io.reactivex.Observable;

public class ImagePresenter extends HttpSub implements BasePresenter<CallbackSub<ImageCategory>> {
	@Override
	public void post(CallbackSub<ImageCategory> imageCategoryCallbackSub) {
		APIurl apIurl = RetrofitClient.retrofit(APIurl.IMAGER_URL, "0").create(APIurl.class);
		Observable<ImageCategory> observable = apIurl.imageCategory("27d21e7ae0ea1", "44133120e3aef3144aecdaa9c3cf7f6d",
				"1", "1");
		toSubscribe(observable, imageCategoryCallbackSub);
	}

	@Override
	public void get(CallbackSub<ImageCategory> imageCategoryCallbackSub) {

	}
}
