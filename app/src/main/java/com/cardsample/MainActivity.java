package com.cardsample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cards.CardAbsView.OnCardClickListener;
import com.cards.CardAbsView.OnCardLongClickListener;
import com.cards.activity.CardActivity;
import com.cards.adapter.CardAdapter;
import com.cards.adapter.SimpleTextCardAdapter;
import com.cardsample.orderedtreemap.PivotBiggestOrderedTreeMapActivity;
import com.cardsample.orderedtreemap.PivotMiddleOrderedTreeMapActivity;
import com.cardsample.orderedtreemap.PivotSizeOrderedTreeMapActivity;

/**
 * Main launcher activity
 * 
 * @author Chaitanya, GOPIREDDY
 */
public class MainActivity extends CardActivity implements OnCardClickListener, OnCardLongClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getCardView().setCardAdapter(new SimpleTextCardAdapter(this, getResources().getStringArray(R.array.main_list)));
		getCardView().setOnCardClickListener(this);
		getCardView().setOnCardLongClickListener(this);
	}

	@Override
	public void onCardClick(CardAdapter cardAdapter, int cardPosition) {
		if (cardPosition == 0) {
			startActivity(new Intent(this, SingleColumnActivity.class));
		} else if (cardPosition == 1) {
			startActivity(new Intent(this, MulticolumnActivity.class));
		} else if (cardPosition == 2) {
			startActivity(new Intent(this, MultiSizedColumnActivity.class));
		} else if (cardPosition == 3) {
			startActivity(new Intent(this, ViewPagerActivity.class));
		} else if (cardPosition == 4) {
			startActivity(new Intent(this, ColorCardsActivity.class));
		} else if (cardPosition == 5) {
			startActivity(new Intent(this, PivotMiddleOrderedTreeMapActivity.class));
		} else if (cardPosition == 6) {
			startActivity(new Intent(this, PivotSizeOrderedTreeMapActivity.class));
		} else if (cardPosition == 7) {
			startActivity(new Intent(this, PivotBiggestOrderedTreeMapActivity.class));
		}
	}

	@Override
	public void onCardLongClick(CardAdapter cardAdapter, int cardPosition) {
		Toast.makeText(this, "Card is clicked at position " + cardPosition, Toast.LENGTH_SHORT).show();
	}
}