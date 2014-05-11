package org.emud.support.v4.content;

import java.util.ArrayList;
import java.util.List;

import org.emud.content.Query;
import org.emud.content.observer.Observer;
import org.emud.content.observer.Subject;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

public class ObserverLoader<D> extends AsyncTaskLoader<D> implements Observer {
	private D mData;
	private Query<D> mQuery;
	private ArrayList<Subject> mSubjects;
	private boolean registered;

	public ObserverLoader(Context context, Query<D> query){
		super(context);
		registered = false;
		mQuery = query;
		mSubjects = new ArrayList<Subject>();
	}
	
	public ObserverLoader(Context context, Query<D> query, Subject dataSubject) {
		super(context);
		registered = false;
		mQuery = query;
		mSubjects = new ArrayList<Subject>();
		
		if(dataSubject != null)
			mSubjects.add(dataSubject);
	}
	
	public ObserverLoader(Context context, Query<D> query, List<Subject> dataSubjects){
		super(context);
		registered = false;
		mQuery = query;
		mSubjects = new ArrayList<Subject>(dataSubjects);
	}

	@Override
	public D loadInBackground() {
		return mQuery.execute();
	}

	@Override
	public void deliverResult(D data) {
		if (isReset()) {
			releaseResources(data);
			return;
		}

		D oldData = mData;
		mData = data;

		if (isStarted()) {
			super.deliverResult(data);
		}

		if (oldData != null && oldData != data) {
			releaseResources(oldData);
		}
	}
	
	@Override
	protected void onStartLoading(){
		if (mData != null) {
			deliverResult(mData);
		}

		if(!registered){
			registered = true;
			for(Subject sub : mSubjects)
				sub.registerObserver(this);
		}

		if (takeContentChanged() || mData == null) {
			forceLoad();
		}
	}
	
	@Override
	protected void onStopLoading() {
		cancelLoad();
	}
	
	@Override
	protected void onReset() {
		onStopLoading();

		if (mData != null) {
			releaseResources(mData);
			mData = null;
		}

		if(registered){
			registered = false;
			for(Subject sub : mSubjects)
				sub.unregisterObserver(this);
		}
	}

	@Override
	public void onCanceled(D data) {
		super.onCanceled(data);
		releaseResources(data);
	}

	@Override
	public void update(){
		onContentChanged();
	}
	
	public void addSubject(Subject sub){
		mSubjects.add(sub);
	}
	
	public void removeSubject(Subject sub){
		mSubjects.remove(sub);
	}
	
	public Query<D> getQuery() {
		return mQuery;
	}

	public void setQuery(Query<D> query) {
		this.mQuery = query;
		update();
	}

	protected void releaseResources(D data){
		
	}

}
