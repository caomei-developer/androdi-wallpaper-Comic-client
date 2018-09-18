package com.inpassing.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.view.fragment.BaseFragment;
import com.inpassing.R;
import com.inpassing.video.helper.VideoHelper;
import com.lib.bean.JsoupAnalysis;

/**
 * Created by zw on 2018/9/17.
 */

public class VideoFragment extends BaseFragment {
	private VideoHelper videoHelper;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.test_wallpaper_fragment, container, false);
	}

	@Override
	public void initView(View view) {
		super.initView(view);
	}

	@Override
	public void initHelper() {
		super.initHelper();
		videoHelper = new VideoHelper();
	}

	@Override
	public void loadData() {
		super.loadData();
		videoHelper.getViewList(jsoupAnalysis(), new VideoHelper.VideoCallBack() {
			@Override
			public void onSuccess(Video video) {

			}
		});
	}

	private JsoupAnalysis jsoupAnalysis() {
		JsoupAnalysis jsoupAnalysis = new JsoupAnalysis();
		jsoupAnalysis.setDiv("project-item");
		jsoupAnalysis.setItem("roject-item-1");
		jsoupAnalysis.setA("a");
		jsoupAnalysis.setHref("href");
		jsoupAnalysis.setTitle("title");
		jsoupAnalysis.setImg("img");
		jsoupAnalysis.setSrc("src");
		jsoupAnalysis.setSrc("src");
		return jsoupAnalysis;

	}

}