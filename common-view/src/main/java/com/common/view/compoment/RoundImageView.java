package com.common.view.compoment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.common.view.R;
import com.common.view.drawable.RoundColorDrawable;

public class RoundImageView extends AppCompatImageView {

	private float radius = 0f;

	public RoundImageView(Context context) {
		this(context, null);
	}

	public RoundImageView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
		try {
			radius = typedArray.getDimension(R.styleable.RoundImageView_radius, 0f);
		} finally {
			typedArray.recycle();
		}
	}

	@Override
	public void setImageDrawable(@Nullable Drawable drawable) {
		Drawable newDrawable = drawable;
		if (drawable instanceof BitmapDrawable) {
			newDrawable = RoundedBitmapDrawableFactory.create(getResources(), ((BitmapDrawable) drawable).getBitmap());
		} else if (drawable instanceof ColorDrawable) {
			newDrawable = new RoundColorDrawable(((ColorDrawable) drawable).getColor());
		}
		super.setImageDrawable(newDrawable);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() != null)
			if (getDrawable() instanceof RoundedBitmapDrawable) {
				RoundedBitmapDrawable roundedBitmapDrawable = (RoundedBitmapDrawable) getDrawable();
				roundedBitmapDrawable.setCornerRadius(radius * roundedBitmapDrawable.getIntrinsicWidth() / getWidth());
			} else if (getDrawable() instanceof RoundColorDrawable) {
				RoundColorDrawable roundColorDrawable = (RoundColorDrawable) getDrawable();
				roundColorDrawable.setRadius(radius);
			}
		super.onDraw(canvas);
	}
}
