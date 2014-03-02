package com.cardsample;

import android.os.Bundle;

import com.cards.activity.CardActivity;

public class SingleColumnActivity extends CardActivity {

	private static final int[] FRUITS = { R.drawable.apple, R.drawable.apricot, R.drawable.banana, R.drawable.cherry,
		R.drawable.kiwi, R.drawable.mango, R.drawable.orange, R.drawable.peach, R.drawable.strawberry };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Code to attain cards screen
		FruitsAdapter adapter = new FruitsAdapter(this, FRUITS);
		getCardView().setCardAdapter(adapter);
	}
}
