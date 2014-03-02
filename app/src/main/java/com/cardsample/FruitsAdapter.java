package com.cardsample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cards.adapter.BaseCardAdapter;
import com.cards.basecards.BaseCard;
import com.cards.basecards.Card;

public class FruitsAdapter extends BaseCardAdapter {

	private int[] fruits;
	private Context mContext;

	public FruitsAdapter(Context mContext, int[] fruits) {
		super(mContext);
		this.mContext = mContext;
		this.fruits = fruits;
	}

	@Override
	public int getCount() {
		return fruits.length;
	}

	@Override
	public Card getCard(int position) {
		return new FruitCard(mContext, position);
	}

	class FruitCard extends BaseCard {
		private int position;
		private Context mContext;

		public FruitCard(Context mContext, int position) {
			super(mContext);
			this.mContext = mContext;
			this.position = position;
		}

		@Override
		public View getCardView(ViewGroup parent) {
			ImageView image = new ImageView(mContext);
			image.setImageResource(fruits[position]);
			image.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			return image;
		}
	}
}
