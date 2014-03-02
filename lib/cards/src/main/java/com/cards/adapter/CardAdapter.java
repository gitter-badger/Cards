package com.cards.adapter;

import com.cards.basecards.Card;

import android.database.DataSetObserver;

/**
 * {@link com.cards.adapter.CardAdapter} interface used to populate the items in the {@link com.cards.CardAbsView}
 *
 * @author Chaitanya, GOPIREDDY
 */
public interface CardAdapter {
    /** Number of items in this adapter */
	int getCount();

    /** Id associated with the item if it contains else return 0 */
	long getItemId(int position);

    /** Has stable ids */
	boolean hasStableIds();

    /** Return the card associated with the index provided in the parameter */
	Card getCard(int position);

	void registerDataSetObserver(DataSetObserver o);

	void unregisterDataSetObserver(DataSetObserver o);

	void notifyDataSetChanged();

	void notifyDataSetInvalidated();
}