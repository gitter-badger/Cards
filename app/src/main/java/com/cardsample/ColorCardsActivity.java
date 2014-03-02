package com.cardsample;

import android.content.res.Configuration;
import android.os.Bundle;

import com.cards.activity.CardActivity;

public class ColorCardsActivity extends CardActivity {
	
	private static final int[] FRUITS = { R.drawable.apple, R.drawable.apricot, R.drawable.banana, R.drawable.cherry,
		R.drawable.kiwi, R.drawable.mango, R.drawable.orange, R.drawable.peach, R.drawable.strawberry };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			setNumColumns(3);
		} else {
			setNumColumns(2);
		}
		
		getCardView().setCardAdapter(new ColoredFruitsAdapter(this, FRUITS));
	}

}
