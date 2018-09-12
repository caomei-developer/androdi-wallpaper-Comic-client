package com.inpassing.wallpaper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.view.image.CommonImageLoader;
import com.inpassing.R;
import com.inpassing.wallpaper.m.Wallpaper;

import java.util.List;

/**
 * Created by zw on 2018/9/11.
 */

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {
	private Context context;
	private List<Wallpaper.DataBean> list;

	public WallpaperAdapter(Context context) {
		this.context = context;
	}

	@Override
	public WallpaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		WallpaperViewHolder holder = new WallpaperViewHolder(
				LayoutInflater.from(context).inflate(R.layout.wallpaper_item_layout, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(WallpaperViewHolder holder, int position) {
		CommonImageLoader.load(list.get(position).getUrl()).into(holder.imageView);
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	class WallpaperViewHolder extends RecyclerView.ViewHolder {
		ImageView imageView;

		public WallpaperViewHolder(View itemView) {
			super(itemView);
			imageView = itemView.findViewById(R.id.wallpaper_item_image);
		}
	}

	public void setList(List<Wallpaper.DataBean> dataBeans) {
		if (list != null) {
			list.clear();
		}
		this.list = dataBeans;
	}

	public void addList(List<Wallpaper.DataBean> dataBeans) {
		if (dataBeans != null && dataBeans.size() > 0) {
			list.add((Wallpaper.DataBean) dataBeans);

		}
	}

}
