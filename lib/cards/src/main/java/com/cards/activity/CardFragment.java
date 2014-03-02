package com.cards.activity;

import com.cards.CardView;
import com.cards.adapter.CardAdapter;
import com.chetu.cardapi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * {@link android.support.v4.app.Fragment} which embed the creation of cards view in this Fragment,
 * Usage of this fragment is quite simple.
 * 
 * @author Chaitanya, GOPIREDDY
 */
public class CardFragment extends Fragment {

	private CardView cardView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.activity_card_layout, container, false);
		cardView = (CardView) view.findViewById(R.id.cards_view_id);
		return view;
	}

	public void setCardAdapter(CardAdapter cardAdapter) {
		if (cardView != null) {
			cardView.setCardAdapter(cardAdapter);
		}
	}

	public CardView getCardView() {
		return cardView;
	}

	public void setNumColumns(int numColumns) {
		getCardView().setColumnNumbers(numColumns);
	}
}
