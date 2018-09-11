package com.common.view.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RoundColorDrawable extends Drawable {

	private float radius;

	private Paint paint;

	private RectF boundsF;

	public RoundColorDrawable(int color, float radius) {
		this.radius = radius;
		paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		paint.setColor(color);
		boundsF = new RectF();
	}

	public RoundColorDrawable(int color) {
		this(color, 0);
	}

	@Override
	public void draw(@NonNull Canvas canvas) {
		canvas.drawRoundRect(boundsF, radius, radius, paint);
	}

	@Override
	public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
		paint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(@Nullable ColorFilter colorFilter) {
		paint.setColorFilter(colorFilter);
	}

	public void setColor(int color) {
		if (this.paint.getColor() == color) {
			return;
		}
		paint.setColor(color);
		invalidateSelf();
	}

	public void setRadius(float radius) {
		if (this.radius == radius) {
			return;
		}
		this.radius = radius;
		invalidateSelf();
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		boundsF.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
	}
}
