package com.cardsample;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.cards.CardAbsView.OnCardClickListener;
import com.cards.activity.CardActivity;
import com.cards.adapter.CardAdapter;

/**
 * Multi sized column screen
 * 
 * @author Chaitanya. GOPIREDDY
 */
public class MultiSizedColumnActivity extends CardActivity implements OnCardClickListener {

	private static final int[] FRUITS = { R.drawable.apple, R.drawable.apple, R.drawable.apple,
			R.drawable.apricot, R.drawable.banana, R.drawable.cherry };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Way to change the number of columns in the screen
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			setNumColumns(3);
		} else {
			setNumColumns(2);
		}

		getCardView().setCardAdapter(new FruitsAdapter(this, FRUITS));
		getCardView().setOnCardClickListener(this);
	}

	@Override
	public void onCardClick(CardAdapter cardAdapter, int cardPosition) {
		Toast.makeText(this, "Card is selected", Toast.LENGTH_SHORT).show();
	}
}
