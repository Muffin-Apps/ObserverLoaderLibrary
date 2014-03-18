package org.emud.content;

import org.emud.content.observer.Observer;
import org.emud.content.observer.Subject;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

public abstract class ObserverLoader<D> extends AsyncTaskLoader<D> implements Observer {
	private D mData;
	private Query<D> mQuery;
	private Subject mSubject;

	public ObserverLoader(Context context, Query<D> query, Subject dataSubject) {
		super(context);
		mQuery = query;
		mSubject = dataSubject;
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

		if(mSubject != null)
			mSubject.registerObserver(this);

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

		if (mSubject != null) {
			mSubject.unregisterObserver(this);
		}
	}

	@Override
	public void onCanceled(D data) {
		super.onCanceled(data);
		releaseResources(data);
	}

	public void update(){
		onContentChanged();
	}

	protected abstract void releaseResources(D data);

}
