package com.cards.basecards;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cards.CardView;
import com.chetu.cardapi.R;

/**
 * Abstract class for Card view in android
 * 
 * @author Chaitanya, GOPIREDDY
 */
public abstract class BaseCard extends RelativeLayout implements Card {

	public enum CardColor {
		BLUE, BLUE_GREEN, DARK_ORANGE, GRAY, GREEN, ORANGE, RED, WHITE, YELLOW
	}

	/** Inflater associated with the {@link CardView} */
	private LayoutInflater inflater;

	/** Color of the card */
	private CardColor cardColor = CardColor.WHITE;

	public BaseCard(Context mContext) {
		super(mContext);
		initView(mContext);
	}

	public BaseCard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public BaseCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public BaseCard(Context mContext, CardColor cardColor) {
		super(mContext);
		this.cardColor = cardColor;
		initView(mContext);
	}

	private void initView(Context mContext) {
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setCardColor(cardColor);
		
	}

	public LayoutInflater getInflater() {
		return inflater;
	}

	@Override
	public final View getView(ViewGroup parent) {
		removeAllViews();
		this.addView(getCardView(null), new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		return this;
	}

	@Override
	public void setCardColor(CardColor color) {
		this.cardColor = color;
		setBackgroundResource(getColoredCardDrawable(cardColor));
	}

	@Override
	public CardColor getCardColor() {
		return this.cardColor;
	}

//	@Override
//	public View getDismissedView(ViewGroup parent) {
//		// TODO CGY: Dismissed view hasn't been implemented
//		return null;
//	}

//	@Override
//	public boolean isDimissible() {
//		// TODO CGY: An attribute that is controlled to see if the card is
//		// dismissable
//		return false;
//	}

	/**
	 * Abstract method that should be used by the users to return the view for
	 * card.
	 * 
	 * @param parent
	 * @return return card view
	 */
	public abstract View getCardView(ViewGroup parent);

	/**
	 * Get the colored drawable
	 * 
	 * @param color
	 * @return
	 */
	private int getColoredCardDrawable(CardColor color) {
		if (color == CardColor.BLUE) {
			return R.drawable.blue_card;
		} else if (color == CardColor.BLUE_GREEN) {
			return R.drawable.blue_green_card;
		} else if (color == CardColor.DARK_ORANGE) {
			return R.drawable.dark_orange_card;
		} else if (color == CardColor.GRAY) {
			return R.drawable.gray_card;
		} else if (color == CardColor.GREEN) {
			return R.drawable.green_card;
		} else if (color == CardColor.ORANGE) {
			return R.drawable.orange_card;
		} else if (color == CardColor.RED) {
			return R.drawable.red_card;
		} else if (color == CardColor.YELLOW) {
			return R.drawable.yellow_card;
		}
		return R.drawable.white_card;
	}
}