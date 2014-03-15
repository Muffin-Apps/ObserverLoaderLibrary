package org.emud.content;

import android.content.Context;
import android.database.Cursor;

public class ObserverCursorLoader extends ObserverLoader<Cursor> {

	public ObserverCursorLoader(Context context, Query<Cursor> query,
			DataSubject dataSubject) {
		super(context, query, dataSubject);
	}

	@Override
	protected void releaseResources(Cursor data) {
		data.close();
	}

}
