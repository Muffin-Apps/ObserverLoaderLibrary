package org.emud.content;

import java.util.List;

import org.emud.content.observer.Subject;

import android.content.Context;
import android.database.Cursor;

public class ObserverCursorLoader extends ObserverLoader<Cursor> {

	public ObserverCursorLoader(Context context, Query<Cursor> query){
		super(context, query);
	}
	
	public ObserverCursorLoader(Context context, Query<Cursor> query,
			Subject dataSubject) {
		super(context, query, dataSubject);
	}
	
	public ObserverCursorLoader(Context context, Query<Cursor> query,
			List<Subject> dataSubjects) {
		super(context, query, dataSubjects);
	}

	@Override
	protected void releaseResources(Cursor data) {
		if(data != null)
			data.close();
	}

}
