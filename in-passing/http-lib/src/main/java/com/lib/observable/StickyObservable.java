package com.lib.observable;

import java.util.Observable;
import java.util.Observer;

public class StickyObservable extends Observable {

	private Object stickyData;

	public StickyObservable() {
	}

	public StickyObservable(Object stickyData) {
		this.stickyData = stickyData;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		o.update(this, stickyData);
	}

	@Override
	public void notifyObservers(Object arg) {
		stickyData = arg;
		setChanged();
		super.notifyObservers(arg);
	}
}
