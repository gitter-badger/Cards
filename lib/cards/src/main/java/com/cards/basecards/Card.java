package com.cards.basecards;

import android.view.View;
import android.view.ViewGroup;

import com.cards.basecards.BaseCard.CardColor;

/**
 * Card implementation 
 * @author Chaitanya, GOPIREDDY
 */
public interface Card {

	View getView(ViewGroup parent);
	
	void setCardColor(CardColor color);
	
	CardColor getCardColor();

	//View getDismissedView(ViewGroup parent);

	//boolean isDimissible();

}
