/**
 * 
 */
package org.emud.content;

import java.util.ArrayList;
import java.util.List;

import org.emud.content.observer.Observer;
import org.emud.content.observer.Subject;

/**
 * @author alberto
 *
 */
public class DataSubject implements Subject{

	List<Observer> mObservers = new ArrayList<Observer>();
	
	public DataSubject(){
	}
	
	@Override
	public void registerObserver(Observer obs){
		mObservers.add(obs);
	}

	@Override
	public void unregisterObserver(Observer obs){
		mObservers.remove(obs);
	}

	@Override
	public void notifyObservers(){
		for(Observer obs : mObservers)
			obs.update();
	}
}
