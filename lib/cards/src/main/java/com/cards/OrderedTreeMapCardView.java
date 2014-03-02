package com.cards;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.graphics.RectF;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cards.adapter.CardAdapter;
import com.cards.basecards.Card;
import com.cards.treemap.OrderedTreemap;
import com.cards.treemap.OrderedTreemap.Pivot;

/**
 * Tree mapping view
 * 
 * @author Chaitanya, GOPIREDDY
 */
public class OrderedTreeMapCardView extends CardAbsView {

	/** {@link android.text.Layout} used to align the */
	private RelativeLayout rootLayout;

	/** Values */
	private Integer[] values;

	/** Algorithm used to retrieve the rectangles */
	private OrderedTreemap treeMap;

	/** {@link com.cards.adapter.CardAdapter} used to populate the cards in this {@link android.view.ViewGroup} */
	private CardAdapter cardAdapter;

	/** An empty view if the data is empty */
	private View emptyView;

	public OrderedTreeMapCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public OrderedTreeMapCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public OrderedTreeMapCardView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		setPadding(2, 2, 2, 2);
		treeMap = new OrderedTreemap();

		rootLayout = new RelativeLayout(context);
		this.addView(rootLayout, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	@Override
	public void setCardAdapter(CardAdapter adapter) {
		this.cardAdapter = adapter;
	}

	public void setCardValues(Integer[] values) {
		this.values = values;
	}

	public void setPivotType(Pivot pivotType) {
		((OrderedTreemap) treeMap).setPivotType(pivotType);
	}

	@Override
	void appendCardsToLayout(ArrayList<Card> cards) {
		if (cardAdapter == null || values == null) {
			throw new RuntimeException("You forgot to set the values for TreeMapping");
		}
		rootLayout.removeAllViews();
		if (cards.isEmpty()) {
			rootLayout.addView(emptyView);
		} else {
			displayCardsInRects(cards);
		}
	}

	private void displayCardsInRects(ArrayList<Card> cards) {
		ArrayList<RectF> rects = treeMap.getRects(new ArrayList<Integer>(Arrays.asList(values)), getWidth(),
				getHeight());

		for (int i = 0, n = cards.size(); i < n; i++) {
			RectF rect = rects.get(i);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) rect.width(),
					(int) rect.height());

			params.leftMargin = (int) rect.left;
			params.topMargin = (int) rect.top;
			rootLayout.addView(cards.get(i).getView(null), params);
		}
	}

	@Override
	public CardAdapter getAdapter() {
		return cardAdapter;
	}

	@Override
	public void setEmptyView(View view) {
		this.emptyView = view;
	}

	@Override
	public View getEmptyView() {
		return emptyView;
	}
}