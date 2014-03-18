package org.emud.content;

import org.emud.content.observer.Subject;

import android.content.Context;
import android.database.Cursor;

public class ObserverCursorLoader extends ObserverLoader<Cursor> {

	public ObserverCursorLoader(Context context, Query<Cursor> query,
			Subject dataSubject) {
		super(context, query, dataSubject);
	}

	@Override
	protected void releaseResources(Cursor data) {
		data.close();
	}

}
