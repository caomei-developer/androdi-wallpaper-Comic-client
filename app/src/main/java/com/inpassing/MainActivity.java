package com.inpassing;

import android.os.Bundle;

import com.common.view.activity.TabActivity;
import com.common.view.util.CompatUtil;
import com.inpassing.account.AccountFragment;
import com.inpassing.find.FindFragment;
import com.inpassing.video.VideoFragment;
import com.inpassing.wallpaper.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends TabActivity {
	private List<TabItem> tabItemList = new ArrayList<>();

	private List<PagerFragment> viewPagerFragmentList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initTabBar();
		initViewPager();
	}

	private void initTabBar() {
		tabItemList.add(new TabItem("图片", CompatUtil.getDrawable(this, R.mipmap.wallpaper_default),
				CompatUtil.getDrawable(this, R.mipmap.wallpaper_select), false));

		tabItemList.add(new TabItem("漫画", CompatUtil.getDrawable(this, R.mipmap.comic_default),
				CompatUtil.getDrawable(this, R.mipmap.comic_select), false));

		tabItemList.add(new TabItem("发现", CompatUtil.getDrawable(this, R.mipmap.find_default),
				CompatUtil.getDrawable(this, R.mipmap.find_select), false));

		tabItemList.add(new TabItem("我的", CompatUtil.getDrawable(this, R.mipmap.account_default),
				CompatUtil.getDrawable(this, R.mipmap.account_select), false));
		setTabItemList(tabItemList);
	}

	private void initViewPager() {
		Bundle bundle = new Bundle();
		viewPagerFragmentList.add(new PagerFragment(TestFragment.class, bundle));
		viewPagerFragmentList.add(new PagerFragment(VideoFragment.class, bundle));
		viewPagerFragmentList.add(new PagerFragment(FindFragment.class, bundle));
		viewPagerFragmentList.add(new PagerFragment(AccountFragment.class, bundle));
		setPagerFragmentList(viewPagerFragmentList);
		viewPager.setOffscreenPageLimit(3);
	}

	@Override
	public boolean isToolbar() {
		return false;
	}
}
