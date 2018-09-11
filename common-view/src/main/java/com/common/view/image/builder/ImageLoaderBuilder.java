package com.common.view.image.builder;

import android.widget.ImageView;

import com.common.view.image.listener.ImageLibInterface;
import com.common.view.image.listener.ImageLoaderListener;

public class ImageLoaderBuilder {
	private boolean noFade;
	private boolean deferred;
	private int placeholderResId = 0;
	private int errorResId = 0;
	private boolean memoryCache;
	private boolean networkCache;
	private int resourceId = 0;
	private String url;
	private ImageLoaderListener callback = null;
	private ImageLibInterface loader;

	public ImageLoaderBuilder(ImageLibInterface loader, String url) {
		this.loader = loader;
		this.url = url;
	}

	public ImageLoaderBuilder(ImageLibInterface loader, int resourceId) {
		this.loader = loader;
		this.resourceId = resourceId;
	}

	public ImageLoaderBuilder noFade() {
		this.noFade = true;
		return this;
	}

	public ImageLoaderBuilder fit() {
		this.deferred = true;
		return this;
	}

	public ImageLoaderBuilder callback(ImageLoaderListener imageLoaderListener) {
		this.callback = imageLoaderListener;
		return this;
	}

	public ImageLoaderBuilder memoryCache(boolean memoryCache) {
		this.memoryCache = memoryCache;
		return this;
	}

	public ImageLoaderBuilder networkCache(boolean networkCache) {
		this.networkCache = networkCache;
		return this;
	}

	public ImageLoaderBuilder placeholder(int placeholderResId) {
		if (placeholderResId == 0) {
			throw new IllegalArgumentException("Placeholder image resource invalid.");
		} else {
			this.placeholderResId = placeholderResId;
			return this;
		}
	}

	public ImageLoaderBuilder error(int errorResId) {
		if (errorResId == 0) {
			throw new IllegalArgumentException("Error image resource invalid.");
		} else {
			this.errorResId = errorResId;
			return this;
		}
	}

	public void into(ImageView imageView) {
		if (loader != null) {
			loader.loadImage(url, resourceId, imageView, placeholderResId, errorResId, memoryCache, networkCache,
					noFade, deferred, callback);
		}

	}
}
