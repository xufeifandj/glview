package com.rendernode.test.demos;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;

import com.glview.thread.Handler;
import com.glview.view.View;
import com.glview.widget.ArrayAdapter;
import com.glview.widget.xlistview.XListView;
import com.glview.widget.xlistview.XListView.IXListViewListener;
import com.rendernode.test.R;

public class XListViewActivity extends BaseActivity implements IXListViewListener {
	private XListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		geneItems();
		setGLContentView(R.layout.activity_xlistview);
	}

	@Override
	public void onAttached(View content) {
		super.onAttached(content);
		mListView = (XListView) content.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
		mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
	}
	
	private void geneItems() {
		for (int i = 0; i != 40; ++i) {
			items.add("refresh cnt " + (++start));
		}
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(XListViewActivity.this, R.layout.list_item, items);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

}