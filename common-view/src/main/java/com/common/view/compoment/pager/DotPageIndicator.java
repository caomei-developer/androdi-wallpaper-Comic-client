package com.common.view.compoment.pager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.view.R;
import com.common.view.util.CompatUtil;

public class DotPageIndicator extends LinearLayout implements PageIndicator {

	private int current = 0;

	private int total = 0;

	private Drawable normalDrawable;

	private Drawable selectedDrawable;

	private int space;

	public DotPageIndicator(Context context) {
		this(context, null);
	}

	public DotPageIndicator(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DotPageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DotPageIndicator);
		try {
			normalDrawable = typedArray.getDrawable(R.styleable.DotPageIndicator_normalDrawable);
			if (normalDrawable == null) {
				normalDrawable = CompatUtil.getDrawable(getContext(), R.drawable.dot_page_indicator_normal);
			}
			selectedDrawable = typedArray.getDrawable(R.styleable.DotPageIndicator_selectedDrawable);
			if (selectedDrawable == null) {
				selectedDrawable = CompatUtil.getDrawable(getContext(), R.drawable.dot_page_indicator_selected);
			}
			space = typedArray.getDimensionPixelOffset(R.styleable.DotPageIndicator_space, 2);
		} finally {
			typedArray.recycle();
		}
	}

	@Override
	public void setCurrent(int current) {
		if (this.current != current) {
			this.current = current;
			refresh();
		}
	}

	@Override
	public void setTotal(int total) {
		if (this.total != total) {
			this.total = total;
			recreateChildren();
		}
		refresh();
	}

	private void recreateChildren() {
		int childCount = getChildCount();
		if (childCount > total) {
			removeViews(total, childCount - total);
		} else if (childCount < total) {
			for (int i = 0; i < total - childCount; ++i) {
				ImageView imageView = new ImageView(getContext());
				imageView.setAdjustViewBounds(true);
				LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				layoutParams.leftMargin = space / 2;
				layoutParams.rightMargin = space / 2;
				this.addView(imageView, layoutParams);
			}
		}
	}

	private void refresh() {
		for (int i = 0; i < getChildCount(); ++i) {
			ImageView imageView = (ImageView) getChildAt(i);
			if (i == current) {
				imageView.setImageDrawable(selectedDrawable);
			} else {
				imageView.setImageDrawable(normalDrawable);
			}
		}
	}
}
