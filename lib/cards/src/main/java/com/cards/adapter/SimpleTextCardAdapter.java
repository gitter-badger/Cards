package com.cards.adapter;

import android.content.Context;

import com.cards.basecards.BaseCard.CardColor;
import com.cards.basecards.Card;
import com.cards.basecards.SimpleTextCard;

public class SimpleTextCardAdapter extends BaseCardAdapter {

	/** Values to be set for the {@link SimpleTextCard} */
	private String[] values;
	
	/** Card colors to set */
	private CardColor[] cardColors;

	public SimpleTextCardAdapter(Context mContext, int[] stringResources) {
		super(mContext);
		for (int i = 0, n = stringResources.length; i < n; i++) {
			values[i] = mContext.getString(stringResources[i]);
		}
	}

	public SimpleTextCardAdapter(Context mContext, String[] stringResources) {
		super(mContext);
		this.values = stringResources;
	}

	public SimpleTextCardAdapter(Context mContext, String[] stringResources, CardColor[] colors) {
		super(mContext);
		this.values = stringResources;
		this.cardColors = colors;
	}
	
	public SimpleTextCardAdapter(Context mContext, int[] stringResources, CardColor[] colors) {
		super(mContext);
		for (int i = 0, n = stringResources.length; i < n; i++) {
			values[i] = mContext.getString(stringResources[i]);
		}
		this.cardColors = colors;
	}
	
	@Override
	public int getCount() {
		return values.length;
	}

	@Override
	public Card getCard(int position) {
		if (cardColors == null) {
			return new SimpleTextCard(getContext(), values[position]);
		} else {
			return new SimpleTextCard(getContext(), values[position], cardColors[position]);
		}
	}

}
