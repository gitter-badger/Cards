package com.cards;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayout;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.cards.adapter.CardAdapter;
import com.cards.basecards.Card;
import com.chetu.cardapi.R;

// Cards View 
public class CardView extends CardAbsView {

	/** Number of columns used in this card view */
	private int numColumns;

	/** {@link com.cards.adapter.CardAdapter} linked to this View to populate views in cards */
	private CardAdapter cardAdapter;

	/** {@link android.text.Layout} used to align the */
	private LinearLayout rootLayout;

	/** An empty view if the data is empty */
	private View emptyView;

	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// attrs contains the raw values for the XML attributes
		// that were specified in the layout, which don't include
		// attributes set by styles or themes, and which may have
		// unresolved references. Call obtainStyledAttributes()
		// to get the final values for each attribute.
		//
		// This call uses R.styleable.PieChart, which is an array of
		// the custom attributes that were declared in attrs.xml.
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CardView, 0, 0);
		int columns;
		try {
			// Retrieve the values from the TypedArray and store into
			// fields of this class.
			//
			// The R.styleable.PieChart_* constants represent the index for
			// each custom attribute in the R.styleable.PieChart array.
			columns = a.getInt(R.styleable.CardView_numcolumns, 1);
		} finally {
			// release the TypedArray so that it can be reused.
			a.recycle();
		}
		setColumnNumbers(columns);
		initView(context);
	}

	public CardView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CardView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		rootLayout = new LinearLayout(context);
		this.addView(rootLayout);
		rootLayout.setOrientation(LinearLayout.HORIZONTAL);
	}

	/**
	 * Define number of columns for this view
	 * 
	 * @param numColumns
	 */
	public void setColumnNumbers(int numColumns) {
		this.numColumns = numColumns;
	}

	@Override
	public void setCardAdapter(CardAdapter adapter) {
		this.cardAdapter = adapter;
	}

	@Override
	void appendCardsToLayout(ArrayList<Card> cards) {
		rootLayout.removeAllViews();
		if (cards.isEmpty()){
			rootLayout.addView(emptyView);
		} else {
			for (int i = 0; i < numColumns; i++) {
				GridLayout grid = new GridLayout(getContext());
				grid.setOrientation(GridLayout.VERTICAL);
				setPaddingForColumns(grid, i + 1);
				rootLayout.addView(grid, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, 1f));
				// Fill Columns
				fillColumns(cards, grid, i);
			}
		}
	}

	private void fillColumns(ArrayList<Card> cards, GridLayout grid, int columnIndex) {
		for (int j = 0, n = cards.size(); columnIndex + j < n; j = j + numColumns) {
			View card = cards.get(columnIndex + j).getView(null);
			GridLayout.LayoutParams params = new GridLayout.LayoutParams();
			params.bottomMargin = 8;
			grid.addView(card, params);
		}
	}

	private void setPaddingForColumns(GridLayout layout, int column) {
		if (numColumns == 1) {
			layout.setPadding(8, 8, 8, 8);
		} else if (column == 1) {
			layout.setPadding(8, 8, 4, 8);
		} else if (column == numColumns) {
			layout.setPadding(4, 8, 8, 8);
		} else {
			layout.setPadding(4, 8, 4, 8);
		}
	}

	@Override
	public CardAdapter getAdapter() {
		return this.cardAdapter;
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