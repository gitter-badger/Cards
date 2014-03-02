package com.cards.basecards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chetu.cardapi.R;

public class SimpleTextCard extends BaseCard {

	private String cardText;

	public SimpleTextCard(Context mContext) {
		super(mContext);
	}
	
	public SimpleTextCard(Context mContext, int textResourceId){
		super(mContext);
		this.cardText = mContext.getString(textResourceId);
	}

	public SimpleTextCard(Context mContext, String cardText){
		super(mContext);
		this.cardText = cardText;
	}
	
	public SimpleTextCard(Context mContext, int textResourceId, CardColor cardColor){
		super(mContext, cardColor);
		this.cardText = mContext.getString(textResourceId);
	}
	
	public SimpleTextCard(Context mContext, String cardText, CardColor cardColor){
		super(mContext, cardColor);
		this.cardText = cardText;
	}

	@Override
	public View getCardView(ViewGroup parent) {
		View view  = getInflater().inflate(R.layout.simple_card_item_1, null);
		TextView text = (TextView) view.findViewById(R.id.text1);
		text.setText(cardText);
		view.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return view;
	}
	
	public String getCardText(){
		return cardText;
	}
}
