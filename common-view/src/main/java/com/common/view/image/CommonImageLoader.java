package com.common.view.image;

import android.content.Context;

import com.common.view.image.builder.ImageLoaderBuilder;
import com.common.view.image.impl.PicassoImageLibImpl;
import com.common.view.image.listener.ImageLibInterface;

public class CommonImageLoader {

	private static ImageLibInterface loader = new PicassoImageLibImpl();

	public static ImageLoaderBuilder load(String url) {
		return new ImageLoaderBuilder(loader, url);
	}

	public static ImageLoaderBuilder load(int resourceId) {
		if (resourceId == 0) {
			throw new IllegalArgumentException("Resource ID must not be zero.");
		} else {
			return new ImageLoaderBuilder(loader, resourceId);
		}
	}

	public static void clearMemoryCache() {
		if (loader != null) {
			loader.clearMemoryCache();
		}
	}

	public static void clearDiskCache(Context paramContext) {
		if (loader != null) {
			loader.clearDiskCache(paramContext);
		}
	}

}
