package com.common.view.compoment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.view.R;
import com.common.view.util.CompatUtil;

import java.util.ArrayList;
import java.util.List;

public class Tabbar extends LinearLayout {

	private View dividerView;

	private LinearLayout itemPanelLayout;

	private LayoutInflater layoutInflater;

	private Adapter adapter;

	private int selectedIndex;

	private List<Listener> listenerList = new ArrayList<>();

	private int textColor;

	private int selectedTextColor;

	private Drawable reminderDrawable;

	public Tabbar(Context context) {
		this(context, null);
	}

	public Tabbar(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, R.attr.commonTabbarStyle);
	}

	public Tabbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		layoutInflater = LayoutInflater.from(context);
		layoutInflater.inflate(R.layout.tabbar, this);
		dividerView = findViewById(R.id.top_divider_view);
		itemPanelLayout = (LinearLayout) findViewById(R.id.item_panel_layout);

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Common_Tabbar,
				defStyleAttr, R.style.Common_Tabbar);

		try {
			Drawable backgroundDrawable = typedArray.getDrawable(R.styleable.Common_Tabbar_tabbarBackground);
			if (backgroundDrawable != null) {
				CompatUtil.setBackground(itemPanelLayout, backgroundDrawable);
			}
			textColor = typedArray.getColor(R.styleable.Common_Tabbar_tabbarTextColor, Color.parseColor("#333333"));
			selectedTextColor = typedArray.getColor(R.styleable.Common_Tabbar_tabbarSelectedTextColor,
					Color.parseColor("#e63528"));
			Drawable dividerBackgroundDrawable = typedArray
					.getDrawable(R.styleable.Common_Tabbar_tabbarDividerBackground);
			if (dividerBackgroundDrawable != null) {
				CompatUtil.setBackground(dividerView, dividerBackgroundDrawable);
			}
			reminderDrawable = typedArray.getDrawable(R.styleable.Common_Tabbar_tabReminderBackground);
		} finally {
			typedArray.recycle();
		}
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
		selectedIndex = 0;
		initItems();
	}

	public void select(int index) {
		if (index == selectedIndex) {
			return;
		}
		int oldSelectedIndex = selectedIndex;
		selectedIndex = index;
		refresh(oldSelectedIndex);
		refresh(index);
	}

	public void refresh(int index) {
		LinearLayout itemLinearLayout = (LinearLayout) itemPanelLayout.getChildAt(index);
		if (itemLinearLayout != null) {
			((ImageView) itemLinearLayout.findViewById(R.id.icon_image_view)).setImageDrawable(
					index == selectedIndex ? adapter.getSelectedIcon(index) : adapter.getNormalIcon(index));
			TextView titleTextView = ((TextView) itemLinearLayout.findViewById(R.id.title_text_view));
			titleTextView.setText(adapter.getTitle(index));
			titleTextView.setTextColor(index == selectedIndex ? selectedTextColor : textColor);
			ImageView reminderImageView = (ImageView) itemLinearLayout.findViewById(R.id.reminder_image_view);
			reminderImageView.setVisibility(adapter.hasReminder(index) ? VISIBLE : INVISIBLE);
			if (adapter.hasReminder(index)) {
				reminderImageView.setImageDrawable(reminderDrawable);
			}
		}
	}

	public void addListener(Listener listener) {
		listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		listenerList.remove(listener);
	}

	private void initItems() {
		itemPanelLayout.removeAllViews();
		for (int i = 0; i < adapter.getCount(); ++i) {
			final LinearLayout itemLinearLayout = (LinearLayout) layoutInflater.inflate(R.layout.tabbar_action_item,
					itemPanelLayout, false);
			itemPanelLayout.addView(itemLinearLayout);

			final int index = i;
			itemLinearLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					select(index);
					for (Listener listener : listenerList) {
						listener.onSelected(index);
					}
				}
			});

			refresh(i);
		}
	}

	public static interface Adapter {

		public int getCount();

		public String getTitle(int index);

		public Drawable getNormalIcon(int index);

		public Drawable getSelectedIcon(int index);

		public boolean hasReminder(int index);
	}

	public static interface Listener {

		public void onSelected(int index);
	}
}
