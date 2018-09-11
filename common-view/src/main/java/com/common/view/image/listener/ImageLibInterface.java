package com.common.view.image.listener;

import android.content.Context;
import android.widget.ImageView;

public interface ImageLibInterface {

	public void loadImage(String url, int resId, ImageView view, int loadingResId, int errorResId, boolean cache,
			boolean networkCache, boolean noFade, boolean deferred, final ImageLoaderListener imageLoaderListener);

	public void clearMemoryCache();

	public void clearDiskCache(Context paramContext);
}
