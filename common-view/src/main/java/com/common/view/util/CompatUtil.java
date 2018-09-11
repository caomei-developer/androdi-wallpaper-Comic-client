package com.common.view.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import static android.os.Build.VERSION.SDK_INT;

public class CompatUtil {

	public static void setBackground(View view, Drawable drawable) {
		if (SDK_INT >= 16) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

	public static Drawable getDrawable(Context context, int resId) {
		if (SDK_INT >= 21) {
			return context.getResources().getDrawable(resId, context.getTheme());
		} else {
			return context.getResources().getDrawable(resId);
		}
	}
}
