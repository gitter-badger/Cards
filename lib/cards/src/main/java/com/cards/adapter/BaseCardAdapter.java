package com.cards.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;

import com.cards.CardView;

/**
 * Base card adapter used to populate items as cards in {@link CardView}
 * 
 * @author Chaitanya, GOPIREDDY
 */
public abstract class BaseCardAdapter implements CardAdapter {
	private final DataSetObservable mDataSetObservable = new DataSetObservable();
	private Context mContext;

	public BaseCardAdapter(Context mContext) {
		this.mContext = mContext;
	}

	protected Context getContext() {
		return mContext;
	}

	@Override
	public void notifyDataSetChanged() {
		mDataSetObservable.notifyChanged();
	}

	@Override
	public void notifyDataSetInvalidated() {
		mDataSetObservable.notifyInvalidated();
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/** Checks if the cards in adapter are empty */
	public boolean isEmpty() {
		return getCount() == 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mDataSetObservable.registerObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mDataSetObservable.unregisterObserver(observer);
	}
}
