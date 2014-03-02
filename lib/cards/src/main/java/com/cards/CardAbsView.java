package com.cards;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.cards.adapter.CardAdapter;
import com.cards.basecards.Card;

public abstract class CardAbsView extends ScrollView {

	/**
	 * Animation duration recommended is 300 milli seconds from Google we set
	 * little lower to it to have a nice feel of animation
	 */
	private int minAnimDuration = 300;

	/** On card long click listener */
	private OnCardLongClickListener mOnCardLongClickListener;

	/** Card click listener */
	private OnCardClickListener mOnCardClickListener;

	/** Cards that are linked with this {@link android.view.ViewGroup} */
	private ArrayList<Card> cards = new ArrayList<Card>();

	public CardAbsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public CardAbsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public CardAbsView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		// Observer for animation listener
		ViewTreeObserver observer = this.getViewTreeObserver();
		observer.addOnPreDrawListener(listener);
	}

	OnClickListener cardListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mOnCardClickListener != null) {
				mOnCardClickListener.onCardClick(getAdapter(), cards.indexOf(v));
			}
		}
	};

	OnPreDrawListener listener = new OnPreDrawListener() {
		@Override
		public boolean onPreDraw() {
			CardAbsView.this.getViewTreeObserver().removeOnPreDrawListener(this);
			notifyDataSetChanged();
			animateCards();
			return true;
		}
	};

	OnLongClickListener cardLongClickListener = new OnLongClickListener() {
		@Override
		public boolean onLongClick(View v) {
			if (mOnCardLongClickListener != null) {
				mOnCardLongClickListener.onCardLongClick(getAdapter(), cards.indexOf(v));
				return true;
			}
			return false;
		}
	};

	private void animateInHigherAndroidVersion(View viewToAnimate, int index, int height) {
		viewToAnimate.setTranslationY(height);
		viewToAnimate.setRotation(45);
		viewToAnimate.setRotationX(-45);
		viewToAnimate.animate().setDuration(minAnimDuration).setStartDelay(index * 50).translationY(0).rotation(0)
				.rotationX(0).setInterpolator(new DecelerateInterpolator()).start();
	}

	private void animateInOlderAndroidSDK(View viewToAnimate, int index, int height) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, height, 0);
		animation.setDuration(minAnimDuration);
		animation.setInterpolator(new DecelerateInterpolator());
		animation.setStartOffset(index * 50);
		viewToAnimate.startAnimation(animation);
	}

	void animateCards() {
		for (Card card : cards) {
			int index = cards.indexOf(card);
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				animateInHigherAndroidVersion((View) card, index, getHeight());
			} else {
				animateInOlderAndroidSDK((View) card, index, getHeight());
			}
		}
	}
	
	private void createCards(){
		CardAdapter cardAdapter = getAdapter();
		if (cardAdapter == null) {
			throw new RuntimeException("You forgot to set the CardAdapter for this view");
		}
		cards.clear();
		for (int i = 0, n = cardAdapter.getCount(); i < n; i++) {
			Card card = cardAdapter.getCard(i);
			cards.add(card);
			((View) card).setOnClickListener(cardListener);
			((View) card).setOnLongClickListener(cardLongClickListener);
		}
	}
	
	public final ArrayList<Card> getCards(){
		return cards;
	}
	
	public final void notifyDataSetChanged(){
		createCards();
		appendCardsToLayout(cards);
	}

	public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
		this.mOnCardClickListener = onCardClickListener;
	}

	public void setOnCardLongClickListener(OnCardLongClickListener onCardLongClickListener) {
		this.mOnCardLongClickListener = onCardLongClickListener;
	}

	public interface OnCardClickListener {
		void onCardClick(CardAdapter cardAdapter, int cardPosition);
	}

	public interface OnCardLongClickListener {
		void onCardLongClick(CardAdapter cardAdapter, int cardPosition);
	}

	public abstract void setCardAdapter(CardAdapter adapter);

	public abstract CardAdapter getAdapter();

	public abstract void setEmptyView(View view);

	public abstract View getEmptyView();

	abstract void appendCardsToLayout(ArrayList<Card> cards);

}
