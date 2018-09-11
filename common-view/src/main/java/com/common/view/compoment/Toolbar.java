package com.common.view.compoment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.view.R;
import com.common.view.util.CompatUtil;

public class Toolbar extends LinearLayout {

	private TextView titleTextView;

	private View dividerView;

	private ImageButton navigationButton;

	private LinearLayout actionGroupLayout;

	public Toolbar(Context context) {
		this(context, null);
	}

	public Toolbar(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, R.attr.commonToolbarStyle);
	}

	public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		LayoutInflater.from(context).inflate(R.layout.toolbar, this);

		titleTextView = (TextView) findViewById(R.id.title_text_view);
		dividerView = findViewById(R.id.bottom_divider_view);
		navigationButton = (ImageButton) findViewById(R.id.navigation_button);
		actionGroupLayout = (LinearLayout) findViewById(R.id.action_group_layout);

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Common_Toolbar,
				defStyleAttr, 0);

		try {
			Drawable backgroundDrawable = typedArray.getDrawable(R.styleable.Common_Toolbar_toolbarBackground);
			if (backgroundDrawable != null) {
				View panelLayout = findViewById(R.id.toolbar_panel_layout);
				CompatUtil.setBackground(panelLayout, backgroundDrawable);
			}

			int textColor = typedArray.getColor(R.styleable.Common_Toolbar_toolbarTextColor, Integer.MAX_VALUE);
			if (textColor != Integer.MAX_VALUE) {
				titleTextView.setTextColor(textColor);
			}

			Drawable dividerBackgroundDrawable = typedArray
					.getDrawable(R.styleable.Common_Toolbar_toolbarDividerBackground);
			if (dividerBackgroundDrawable != null) {
				CompatUtil.setBackground(dividerView, dividerBackgroundDrawable);
			}
		} finally {
			typedArray.recycle();
		}
	}

	public void setNavigationIcon(Drawable drawable) {
		if (drawable == null) {
			navigationButton.setVisibility(GONE);
		} else {
			navigationButton.setVisibility(VISIBLE);
			navigationButton.setImageDrawable(drawable);
		}
	}

	public void setNavigationIcon(int resId) {
		navigationButton.setVisibility(VISIBLE);
		navigationButton.setImageResource(resId);
	}

	public void setNavigationOnClickListener(OnClickListener listener) {
		navigationButton.setOnClickListener(listener);
	}

	public void setTitle(CharSequence title) {
		titleTextView.setText(title);
	}

	public void setTitle(int resId) {
		titleTextView.setText(resId);
	}

	public ImageButton addActionButton(Drawable drawable, OnClickListener listener) {
		ImageButton actionButton = (ImageButton) LayoutInflater.from(getContext())
				.inflate(R.layout.toolbar_action_button, actionGroupLayout, false);
		actionGroupLayout.addView(actionButton);
		actionButton.setImageDrawable(drawable);
		actionButton.setOnClickListener(listener);
		return actionButton;
	}

	public void removeActionButton(ImageButton actionButton) {
		actionGroupLayout.removeView(actionButton);
	}

	public void clearActionButtons() {
		actionGroupLayout.removeAllViews();
	}
}
