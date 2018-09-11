package com.common.view.compoment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.common.view.R;
import com.common.view.compoment.pager.PageIndicator;
import com.common.view.image.CommonImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoSlideView extends RelativeLayout {

	private float ratio = -1.0f;

	private boolean autoSlide = true;

	private int autoSlideInterval = 5000;

	private long lastSlideTime = System.currentTimeMillis();

	private PageIndicator pageIndicator;

	private ViewPager viewPager;

	private Adapter adapter;

	public AutoSlideView(Context context) {
		this(context, null);
	}

	public AutoSlideView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AutoSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoSlideView);
		try {
			ratio = typedArray.getFloat(R.styleable.AutoSlideView_ratio, -1.0f);
			autoSlide = typedArray.getBoolean(R.styleable.AutoSlideView_autoSlide, true);
			autoSlideInterval = typedArray.getInteger(R.styleable.AutoSlideView_autoSlideInterval, 5000);
		} finally {
			typedArray.recycle();
		}
		initViewPager();
		if (autoSlide) {
			post(runnable);
		}
	}

	private void initViewPager() {
		viewPager = new ViewPager(getContext());
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(viewPager, layoutParams);
		viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				lastSlideTime = System.currentTimeMillis();
				if (pageIndicator != null && adapter != null) {
					pageIndicator.setCurrent(position % (adapter.getCount() == 0 ? 1 : adapter.getCount()));
				}
			}
		});
	}

	@Override
	public void onViewAdded(View child) {
		super.onViewAdded(child);
		if (child instanceof PageIndicator) {
			pageIndicator = (PageIndicator) child;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		if (ratio > 0) {
			int heightSize = (int) (widthSize / ratio);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setImages(List<?> images, Drawable defaultDrawable) {
		setAdapter(new ImageViewAdapter(getContext(), images, defaultDrawable));
	}

	public void setAdapter(final Adapter adapter) {
		this.adapter = adapter;
		viewPager.setAdapter(new PagerAdapter() {

			private Map<Integer, List<View>> viewCacheMap = new HashMap<Integer, List<View>>();

			private Map<View, Integer> viewTypeMap = new HashMap<View, Integer>();

			@Override
			public int getCount() {
				return Integer.MAX_VALUE;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				position = position % (adapter.getCount() == 0 ? 1 : adapter.getCount());
				int viewType = adapter.getViewType(position);
				View view = adapter.setView(container, position, peekCache(viewType));
				viewTypeMap.put(view, viewType);
				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
				addCache((View) object, viewTypeMap.get(object));
			}

			private void addCache(View view, int viewType) {
				List<View> viewList = viewCacheMap.get(viewType);
				if (viewList == null) {
					viewList = new ArrayList<View>();
					viewCacheMap.put(viewType, viewList);
				}
				viewList.add(view);
			}

			private View peekCache(int viewType) {
				List<View> viewList = viewCacheMap.get(viewType);
				if (viewList != null && !viewList.isEmpty()) {
					return viewList.remove(0);
				}
				return null;
			}
		});
	}

	public void notifyDataChange() {
		if (viewPager.getAdapter() != null) {
			viewPager.getAdapter().notifyDataSetChanged();
		}
	}

	public void pause() {
		this.autoSlide = false;
		removeCallbacks(runnable);
	}

	public void resume() {
		this.autoSlide = true;
		postDelayed(runnable, autoSlideInterval);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		pause();
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (autoSlide) {
				if (System.currentTimeMillis() > lastSlideTime + autoSlideInterval - 1000) {
					if (adapter != null && adapter.getCount() > 1) {
						viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
					}
				}
				postDelayed(runnable, autoSlideInterval);
			}
		}
	};

	public static abstract class Adapter {

		abstract public int getCount();

		public int getViewType(int position) {
			return 0;
		}

		abstract public View setView(ViewGroup container, int position, View view);
	}

	private static class ImageViewAdapter extends Adapter {

		private Context context;

		private List<?> images;

		private Drawable defaultDrawable;

		public ImageViewAdapter(Context context, List<?> images, Drawable defaultDrawable) {
			this.context = context;
			this.images = images;
			this.defaultDrawable = defaultDrawable;
		}

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public View setView(ViewGroup container, int position, @Nullable View view) {
			ImageView imageView = null;
			if (view == null) {
				imageView = new ImageView(context);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));
			} else {
				imageView = (ImageView) view;
			}
			container.addView(imageView);
			if (defaultDrawable != null) {
				imageView.setImageDrawable(defaultDrawable);
			}
			if (position < images.size()) {
				Object image = images.get(position);
				if (image instanceof String) {
					CommonImageLoader.load((String) image).into(imageView);
				} else if (image instanceof Integer) {
					CommonImageLoader.load((int) image).into(imageView);
				} else if (image instanceof Drawable) {
					imageView.setImageDrawable((Drawable) image);
				}
			}
			return imageView;
		}
	}
}
