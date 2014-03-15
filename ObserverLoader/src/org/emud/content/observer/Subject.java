package org.emud.content.observer;


public interface Subject {

	void registerObserver(Observer obs);

	void unregisterObserver(Observer obs);

	void notifyObservers();

}
