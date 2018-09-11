package com.inpassing.home.p;

import com.common.view.mvp.p.BasePresenter;
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
		Observable<ImageCategory> observable = apIurl.imageCategory("23321", "2eab5093db5c4f2b9568f32286357c94", "1",
				"1");
		toSubscribe(observable, imageCategoryCallbackSub);
	}

	@Override
	public void get(CallbackSub<ImageCategory> imageCategoryCallbackSub) {

	}
}
