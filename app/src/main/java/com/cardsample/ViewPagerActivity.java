package com.cardsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cards.activity.CardFragment;
import com.cards.adapter.SimpleTextCardAdapter;

public class ViewPagerActivity extends FragmentActivity {
	PagerAdapter mPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);

	}

	public class PagerAdapter extends FragmentPagerAdapter {
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new PageListFragment();
			Bundle args = new Bundle();
			args.putInt(PageListFragment.PAGE_TYPE, i);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Number of pages size is defined here
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// You have to put correct title based on the id
			if (position == 0) {
				return "SingleColumn";
			} else if (position == 1) {
				return "MultiColumn";
			} else if (position == 1) {
				return "ImageMultiColumn";
			} else {
				return "ImageSingle";
			}
		}
	}

	public static class PageListFragment extends CardFragment {
		public static final String PAGE_TYPE = "page_type";
		private int mNum;

		private static final int[] FRUITS = { R.drawable.apple, R.drawable.apricot, R.drawable.banana, R.drawable.cherry,
			R.drawable.kiwi, R.drawable.mango, R.drawable.orange, R.drawable.peach, R.drawable.strawberry };

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			mNum = getArguments() != null ? getArguments().getInt(PageListFragment.PAGE_TYPE) : 0;
			String[] fruits = getResources().getStringArray(R.array.fruit_long);
			if (mNum == 0) {
				setCardAdapter(new SimpleTextCardAdapter(getActivity(), fruits));
			} else if (mNum == 1) {
				setNumColumns(2);
				setCardAdapter(new SimpleTextCardAdapter(getActivity(), fruits));
			} else if (mNum == 2) {
				setNumColumns(2);
				setCardAdapter(new FruitsAdapter(getActivity(), FRUITS));
			} else {
				setCardAdapter(new FruitsAdapter(getActivity(), FRUITS));
			}
			return view;
		}
	}
}
