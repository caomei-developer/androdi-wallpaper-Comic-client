package com.lib.observable;

import android.os.Handler;
import android.os.Looper;

import java.util.Observable;
import java.util.Observer;

public abstract class UISafeObserver implements Observer {

	@Override
	public final void update(final Observable o, final Object arg) {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					UISafeObserver.this.doUpdate(o, arg);
				}
			});
		} else {
			doUpdate(o, arg);
		}
	}

	protected abstract void doUpdate(final Observable o, final Object arg);
}
