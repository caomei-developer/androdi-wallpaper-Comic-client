package com.common.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.common.view.BaseActivity;
import com.common.view.R;
import com.common.view.compoment.Tabbar;
import com.common.view.fragment.BaseFragment;
import com.common.view.view.ViewPagerSlide;

import java.util.List;

public class TabActivity extends BaseActivity {
	protected Tabbar tabbar;

	protected ViewPagerSlide viewPager;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		tabbar = findViewById(R.id.tabbar);
		viewPager = findViewById(R.id.viewpager);
		viewPager.setSlide(false);
		linkTabbarAndViewpager();
	}

	@Override
	public boolean isToolbar() {
		return false;
	}

	protected void setTabItemList(List<TabItem> tabItemList) {
		tabbar.setAdapter(new TabItemArrayAdapter(tabItemList));
	}

	protected void setPagerFragmentList(List<PagerFragment> viewPagerFragmentList) {
		viewPager.setAdapter(new PagerFragmentArrayAdapter(this, viewPagerFragmentList));
	}

	private void linkTabbarAndViewpager() {
		tabbar.addListener(new Tabbar.Listener() {
			@Override
			public void onSelected(int index) {
				viewPager.setCurrentItem(index, false);
			}
		});
		viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				tabbar.select(position);
			}
		});
	}

	protected static class TabItem {

		private String title;

		private Drawable normalIcon;

		private Drawable selectedIcon;

		private boolean reminder;

		public TabItem(String title, Drawable normalIcon, Drawable selectedIcon, boolean reminder) {
			this.title = title;
			this.normalIcon = normalIcon;
			this.selectedIcon = selectedIcon;
			this.reminder = reminder;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Drawable getNormalIcon() {
			return normalIcon;
		}

		public void setNormalIcon(Drawable normalIcon) {
			this.normalIcon = normalIcon;
		}

		public Drawable getSelectedIcon() {
			return selectedIcon;
		}

		public void setSelectedIcon(Drawable selectedIcon) {
			this.selectedIcon = selectedIcon;
		}

		public boolean hasReminder() {
			return reminder;
		}

		public void setReminder(boolean reminder) {
			this.reminder = reminder;
		}
	}

	private static class TabItemArrayAdapter implements Tabbar.Adapter {

		private List<TabItem> tabItemList;

		public TabItemArrayAdapter(List<TabItem> tabItemList) {
			this.tabItemList = tabItemList;
		}

		@Override
		public int getCount() {
			return tabItemList.size();
		}

		@Override
		public String getTitle(int index) {
			return tabItemList.get(index).getTitle();
		}

		@Override
		public Drawable getNormalIcon(int index) {
			return tabItemList.get(index).getNormalIcon();
		}

		@Override
		public Drawable getSelectedIcon(int index) {
			return tabItemList.get(index).getSelectedIcon();
		}

		@Override
		public boolean hasReminder(int index) {
			return tabItemList.get(index).hasReminder();
		}
	}

	protected static class PagerFragment {

		private Class<? extends BaseFragment> fragmentClass;

		private Bundle arguments;

		public PagerFragment(Class<? extends BaseFragment> fragmentClass, Bundle arguments) {
			this.fragmentClass = fragmentClass;
			this.arguments = arguments;
		}

		public Class<? extends BaseFragment> getFragmentClass() {
			return fragmentClass;
		}

		public void setFragmentClass(Class<? extends BaseFragment> fragmentClass) {
			this.fragmentClass = fragmentClass;
		}

		public Bundle getArguments() {
			return arguments;
		}

		public void setArguments(Bundle arguments) {
			this.arguments = arguments;
		}
	}

	private static class PagerFragmentArrayAdapter extends FragmentPagerAdapter {

		private TabActivity activity;

		private List<PagerFragment> viewPagerFragmentList;

		public PagerFragmentArrayAdapter(TabActivity activity, List<PagerFragment> viewPagerFragmentList) {
			super(activity.getSupportFragmentManager());
			this.activity = activity;
			this.viewPagerFragmentList = viewPagerFragmentList;
		}

		@Override
		public Fragment getItem(int position) {
			return Fragment.instantiate(activity, viewPagerFragmentList.get(position).getFragmentClass().getName(),
					viewPagerFragmentList.get(position).getArguments());
		}

		@Override
		public int getCount() {
			return viewPagerFragmentList.size();
		}
	}

}
