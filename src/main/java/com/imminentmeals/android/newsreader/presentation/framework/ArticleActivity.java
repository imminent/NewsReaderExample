package com.imminentmeals.android.newsreader.presentation.framework;

import android.app.Activity;
import android.os.Bundle;

import com.imminentmeals.android.newsreader.R.bool;
import com.imminentmeals.android.newsreader.controller.Controller;
import com.imminentmeals.android.newsreader.model.NewsArticle;
import com.imminentmeals.android.newsreader.presentation.ArticlePresentation;
import com.imminentmeals.android.newsreader.presentation.Messages.ArticlePresentation.WillCreatePresentation;

public class ArticleActivity extends Activity implements ArticlePresentation {
	public static final String EXTRA_CATEGORY_INDEX = EXTRA + "ArticleActivity.CATEGORY_INDEX";
	public static final String EXTRA_ARTICLE_INDEX = EXTRA + "ArticleActivity.ARTICLE_INDEX";

/* Lifecycle */
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// The news category index and the article index for the article we are to display
	    final int category_index, article_index;
	    category_index = getIntent().getExtras().getInt(EXTRA_CATEGORY_INDEX, 0);
	    article_index = getIntent().getExtras().getInt(EXTRA_ARTICLE_INDEX, 0);
	    final boolean has_two_panes = getResources().getBoolean(bool.has_two_panes);
	    _controller.sendMessage(new WillCreatePresentation(has_two_panes, category_index, article_index));
	}

/* ArticlePresentation Contract */
	@Override
	public void attachController(Controller controller) {
		_controller = controller;
	}
	
	@Override
	public Controller controller() {
		return _controller;
	}
	
	@Override
	public void displayArticle(NewsArticle article) {
		// Place an ArticleFragment as our content pane
	    ArticleFragment fragment = new ArticleFragment();
	    getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
	    fragment.displayArticle(article);
	}
	
	@Override
	public void stop() {
		finish();
	}

	private Controller _controller;
}
