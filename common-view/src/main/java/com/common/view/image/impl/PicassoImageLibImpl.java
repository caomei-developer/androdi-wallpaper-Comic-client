package com.common.view.image.impl;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.common.view.image.CommonImageLoader;
import com.common.view.image.listener.ImageLibInterface;
import com.common.view.image.listener.ImageLoaderListener;
import com.common.view.util.FileUtil;
import com.common.view.util.Utils;
import com.lib.BaseApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Callback;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

public class PicassoImageLibImpl implements ImageLibInterface {

	private static Picasso picassoInstance;

	private static OkHttpClient okHttpClient = new OkHttpClient();

	private static Cache cache;

	@Override
	public void loadImage(String url, int resId, ImageView view, int loadingResId, int errorResId, boolean cache,
			boolean networkCache, boolean noFade, boolean deferred, final ImageLoaderListener imageLoaderListener) {
		if (!TextUtils.isEmpty(url) || resId != 0) {
			RequestCreator requestCreator = null;
			if (!TextUtils.isEmpty(url)) {
				requestCreator = getInstance().load(url);
			} else if (resId != 0) {
				requestCreator = getInstance().load(resId);
			}
			if (requestCreator == null) {
				return;
			}
			if (loadingResId > 0) {
				requestCreator.placeholder(loadingResId);
			}

			if (errorResId > 0) {
				requestCreator.error(errorResId);
			}

			if (!cache) {
				requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
			}

			if (!networkCache) {
				requestCreator.networkPolicy(NetworkPolicy.NO_CACHE);
			}
			if (noFade) {
				requestCreator.noFade();
			}
			if (deferred) {
				requestCreator.fit();
			}
			if (imageLoaderListener != null) {
				Callback callback = new Callback() {
					@Override
					public void onSuccess() {
						imageLoaderListener.onSuccess();
					}

					@Override
					public void onError() {
						imageLoaderListener.onError();
					}
				};
				requestCreator.into(view, callback);
			} else {
				requestCreator.into(view);
			}
		} else if (errorResId > 0) {
			view.setImageResource(errorResId);
		}
	}

	private static Picasso getInstance() {
		if (picassoInstance == null) {
			synchronized (CommonImageLoader.class) {
				if (picassoInstance == null) {
					init(BaseApplication.application);
				}
			}
		}
		return picassoInstance;
	}

	private static void init(Context paramContext) {
		File localFile = createDefaultCacheDir(paramContext);
		okHttpClient.setCache(new com.squareup.okhttp.Cache(localFile, Utils.calculateDiskCacheSize(localFile)));
		OkHttpDownloader downloader = new OkHttpDownloader(okHttpClient);
		cache = new LruCache(paramContext);
		picassoInstance = new Picasso.Builder(paramContext).downloader(downloader).memoryCache(cache)
				.loggingEnabled(false).build();
	}

	private static File createDefaultCacheDir(Context paramContext) {
		File file = new File(paramContext.getApplicationContext().getCacheDir(), "picasso-cache");
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	@Override
	public void clearMemoryCache() {
		if (cache != null) {
			cache.clear();
		}
	}

	@Override
	public void clearDiskCache(Context paramContext) {
		File file = new File(paramContext.getApplicationContext().getCacheDir(), "picasso-cache");
		if (file.exists()) {
			FileUtil.deleteFile(file);
		}
	}
}
