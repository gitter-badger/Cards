package com.cardsample.orderedtreemap;

import java.security.SecureRandom;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cards.CardAbsView.OnCardClickListener;
import com.cards.OrderedTreeMapCardView;
import com.cards.adapter.CardAdapter;
import com.cards.adapter.SimpleTextCardAdapter;
import com.cards.basecards.BaseCard.CardColor;
import com.cards.treemap.OrderedTreemap.Pivot;
import com.cardsample.R;

public class PivotMiddleOrderedTreeMapActivity extends Activity implements OnCardClickListener {

	private OrderedTreeMapCardView mappingView;
	
	/** Colors for all the months that are drawn */
	private static final CardColor[] COLORS = { CardColor.BLUE, CardColor.BLUE_GREEN, CardColor.DARK_ORANGE, CardColor.ORANGE,
			CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.ORANGE, CardColor.RED, CardColor.BLUE_GREEN,
			CardColor.ORANGE, CardColor.YELLOW };

	/** Months */
	private static final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct",
			"Nov", "Dec" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mappingView = new OrderedTreeMapCardView(this);
		mappingView.setCardAdapter(new SimpleTextCardAdapter(this, MONTHS, COLORS));
		mappingView.setOnCardClickListener(this);
		mappingView.setPivotType(Pivot.PIVOT_MIDDLE);
		setRandomValues();
		
		setContentView(mappingView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.reset) {
			setRandomValues();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setRandomValues() {
		SecureRandom random = new SecureRandom();
		Integer[] values = { 100 + random.nextInt(1000), 100 + random.nextInt(1000), 100 + random.nextInt(1000),
				100 + random.nextInt(1000), 100 + random.nextInt(1000), 100 + random.nextInt(1000),
				100 + random.nextInt(1000), 100 + random.nextInt(1000), 100 + random.nextInt(1000),
				100 + random.nextInt(1000), 100 + random.nextInt(1000), 100 + random.nextInt(1000) };
		mappingView.setCardValues(values);
	}
	
	@Override
	public void onCardClick(CardAdapter cardAdapter, int cardPosition) {
		Toast.makeText(this, "Selected month is " + MONTHS[cardPosition], Toast.LENGTH_SHORT).show();
	}

}
