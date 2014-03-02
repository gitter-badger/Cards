package com.cards.activity;

import android.app.Activity;
import android.os.Bundle;

import com.cards.CardView;
import com.cards.adapter.CardAdapter;
import com.chetu.cardapi.R;

public class CardActivity extends Activity {
	
	private CardView cardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_card_layout);
		cardView = (CardView) findViewById(R.id.cards_view_id);
		
	}
	
	public void setCardAdapter(CardAdapter cardAdapter){
		cardView.setCardAdapter(cardAdapter);
	}
	
	public CardView getCardView(){
		return cardView; 
	}
	
	@Override
	public final void setContentView(int layoutResID) {
		throw new RuntimeException("When you extend CardActivity no need to set a layout for it");
	}
	
	public void setNumColumns(int numColumns){
		getCardView().setColumnNumbers(numColumns);
	}
	
}
