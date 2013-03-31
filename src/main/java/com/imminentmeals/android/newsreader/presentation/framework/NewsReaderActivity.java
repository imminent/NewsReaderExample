package com.imminentmeals.android.newsreader.presentation.framework;

import static com.imminentmeals.android.newsreader.NewsReaderSegueController.controller;
import static com.imminentmeals.android.newsreader.NewsReaderSegueController.sendMessage;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import butterknife.InjectView;

import com.imminentmeals.android.newsreader.BuildConfig;
import com.imminentmeals.android.newsreader.R.id;
import com.imminentmeals.android.newsreader.R.layout;
import com.imminentmeals.android.newsreader.model.NewsArticle;
import com.imminentmeals.android.newsreader.model.NewsCategory;
import com.imminentmeals.android.newsreader.presentation.HasProtocol;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.CategorySelected;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.WillCreatePresentation;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.WillRestorePresentation;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.WillStartPresentation;
import com.imminentmeals.android.newsreader.presentation.NewsReaderPresentation;

/**
 * Main activity: shows headlines list and articles, if layout permits.
 *
 * This is the main activity of the application. It can have several different layouts depending on
 * the SDK version, screen size and orientation. The configurations are divided in two large groups:
 * single-pane layouts and dual-pane layouts.
 *
 * In single-pane mode, this activity shows a list of headlines using a {@link HeadlinesFragment}.
 * When the user clicks on a headline, a separate activity (a {@link ArticleActivity}) is launched
 * to show the news article.
 *
 * In dual-pane mode, this activity shows a {@HeadlinesFragment} on the left
 * side and an {@ArticleFragment} on the right side. When the user selects a
 * headline on the left, the corresponding article is shown on the right.
 */
@HasProtocol(NewsReaderPresentation.Protocol.class)
public class NewsReaderActivity extends Activity implements NewsReaderPresentation, 
                                                            NewsReaderNavigationCallback {
	@InjectView(id.article) /* package */View article;
	
/* Lifecycle */
	@Override
	protected void onCreate(Bundle icicle) {
		if (BuildConfig.DEBUG) Log.v(_TAG, "pre-super.onCreate()");
		super.onCreate(icicle);
		if (BuildConfig.DEBUG) Log.v(_TAG, "Activity.onCreate()");
		setContentView(layout.main_layout);
		
		// Finds the Fragment minions
		_headlines_fragment = (HeadlinesFragment) getFragmentManager().findFragmentById(id.headlines);
		_article_fragment = (ArticleFragment) getFragmentManager().findFragmentById(id.article);
		
		final boolean is_dual_pane = article != null && article.getVisibility() == View.VISIBLE;
		final int category_index = icicle == null ? 0 : icicle.getInt(_KEY_CATEGORY_INDEX, 0);
		sendMessage(this, new WillCreatePresentation(is_dual_pane, category_index));
		
		// Sets up Headlines
	    _headlines_fragment.setSelectable(is_dual_pane);
	    restoreSelection(icicle);
	}

	@Override
	protected void onStart() {
		super.onStart();
		sendMessage(this, WillStartPresentation.DID_START);
	}

/* Activity Callbacks */
	@Override 
	public void onRestoreInstanceState(Bundle icicle) {
		restoreSelection(icicle);
	}
	
	/** Save instance state. Saves current category/article index. */
	@Override
	protected void onSaveInstanceState(Bundle icicle) {
		icicle.putInt(_KEY_CATEGORY_INDEX, ((Protocol) controller(this)).categoryIndex());
		icicle.putInt(_KEY_ARTICLE_INDEX, ((Protocol) controller(this)).articleIndex());
		super.onSaveInstanceState(icicle);
	}

/* NewsReaderPresentation Contract */
	@Override
	public void category(NewsCategory category) {
		_headlines_fragment.loadCategory(category);
	}

	@Override
	public void setupActionBar(String[] categories, boolean show_tabs, int selected_tab) {
		final ActionBar action_bar = getActionBar();
		// Sets up an ActionBarNavigationObserver to deliver us the Action Bar navigation events
	    final ActionBarNavigationObserver handler = new ActionBarNavigationObserver(this);
	    if (show_tabs) {
	      action_bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	      for (String category : categories)
	    	  action_bar.addTab(action_bar.newTab().setText(category).setTabListener(handler));
	      action_bar.setSelectedNavigationItem(selected_tab);
	    } else {
	      action_bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	      action_bar.setListNavigationCallbacks(new ArrayAdapter<String>(this, layout.actionbar_list_item, categories), 
	    		                                handler);
	    }
	}

	@Override
	public void article(NewsArticle article) {
		_article_fragment.displayArticle(article);
	}

	@Override
	public void showArticleActivity(int category_index, int article_index) {
		final Intent intent = new Intent(this, ArticleActivity.class);
	    intent.putExtra(ArticleActivity.EXTRA_CATEGORY_INDEX, category_index);
	    intent.putExtra(ArticleActivity.EXTRA_ARTICLE_INDEX, article_index);
	    startActivity(intent);
	}

	@Override
	public void showCategoryDialog(String[] categories) {
		new AlertDialog.Builder(this)
			.setTitle("Select a Category")
			.setItems(categories, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						sendMessage(NewsReaderActivity.this, new CategorySelected(which));
					}
			})
			.create()
			.show();
	}
	
/* NewsReaderNavigationCallback */
	@Override
	public void onCategorySelected(int category_index) {
		sendMessage(this, new CategorySelected(category_index));
	}
	
/* Helpers */
	/** Restore category/article selection from saved state. */
	private void restoreSelection(Bundle icicle) {
	    if (icicle != null) {
	      final int category_index = icicle.getInt(_KEY_CATEGORY_INDEX, 0);
	      final int article_index = icicle.getInt(_KEY_ARTICLE_INDEX, -1);
	      sendMessage(this, new WillRestorePresentation(category_index, article_index));
	    }
	  }

	private static final String _TAG = "NewsReaderActivity";
	private static final String _KEY_CATEGORY_INDEX = KEY + "NewsReaderActivity.CATEGORY_INDEX";
	private static final String _KEY_ARTICLE_INDEX = KEY + "NewsReaderActivity.ARTICLE_INDEX";
	private HeadlinesFragment _headlines_fragment;
	private ArticleFragment _article_fragment;
	
}
