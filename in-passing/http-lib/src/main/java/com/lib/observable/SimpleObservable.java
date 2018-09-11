package com.lib.observable;

import java.util.Observable;

public class SimpleObservable extends Observable {

	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}
