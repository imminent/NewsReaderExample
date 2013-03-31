package com.imminentmeals.android.newsreader.controller.implementation;

import javax.inject.Inject;
import javax.inject.Named;

import android.util.Log;

import com.imminentmeals.android.newsreader.BuildConfig;
import com.imminentmeals.android.newsreader.controller.Controller;
import com.imminentmeals.android.newsreader.controller.NewsReaderController;
import com.imminentmeals.android.newsreader.model.NewsCategory;
import com.imminentmeals.android.newsreader.model.NewsSource;
import com.imminentmeals.android.newsreader.presentation.Messages;
import com.imminentmeals.android.newsreader.presentation.NewsReaderPresentation;
import com.imminentmeals.android.newsreader.presentation.Presentation;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 *
 * @author Dandre Allison
 */
/* package */class _NewsReaderController implements NewsReaderController, Messages.NewsReaderPresentation {
	
	@Inject
	/* package */_NewsReaderController(@Named(Controller.BUS)Bus bus) {
		bus.register(this);
	}

	@Override
	public void attachPresentation(Presentation presentation) {
		if (!(presentation instanceof NewsReaderPresentation))
			throw new IncompatiblePresentationException(presentation);
		_presentation = (NewsReaderPresentation) presentation;
	}

	@Override
	@Subscribe
	public void willCreatePresentation(WillCreatePresentation message) {
		if (BuildConfig.DEBUG) Log.d(_TAG, "did create");
		_has_two_panes = message.has_two_panes;
	    _presentation.setupActionBar(_CATEGORIES, _has_two_panes, message.category_index);
	}

	@Override
	@Subscribe
	public void willRestorePresentation(WillRestorePresentation message) {
		if (BuildConfig.DEBUG) Log.d(_TAG, "did restore");
		category(message.category_index, message.article_index);
	}

	@Override
	@Subscribe
	public void willStartPresentation(WillStartPresentation message) {
		if (BuildConfig.DEBUG) Log.d(_TAG, "did start");
		category(_category_index, _article_index);
	}

	@Override
	@Subscribe
	public void onCategorySelected(CategorySelected message) {
		if (BuildConfig.DEBUG) Log.d(_TAG, "category selected " + _CATEGORIES[message.category_index]);
		category(message.category_index, _NO_ARTICLE);
	}
	
	@Override
	@Subscribe
	public void onHeadlineSelected(HeadlineSelected message) {
		if (BuildConfig.DEBUG) Log.d(_TAG, "headline selected " + message.article_index);
		_article_index = message.article_index;
	    if (_has_two_panes)
	      _presentation.article(getCurrentCategory().getArticle(_article_index));
	    else
	      _presentation.showArticleActivity(_category_index, _article_index);
	}

	@Override
	public int categoryIndex() {
		return _category_index;
	}

	@Override
	public int articleIndex() {
		return _article_index;
	}
	
/* Helpers */
	private void category(int category_index, int article_index) {
		_category_index = category_index;
		_article_index = article_index;
		final NewsCategory category = getCurrentCategory();
		_presentation.category(category);
		// If we are displaying the article on the right, we have to update that too
		if (_has_two_panes)
			_presentation.article(article_index == _NO_ARTICLE 
				? category.getArticle(0) 
				: category.getArticle(article_index));
	}
	
	private NewsCategory getCurrentCategory() {
	    return NewsSource.getInstance().getCategory(_category_index);
	  }
	
	private static final String _TAG = "NewsReaderController";
	private static final int _NO_ARTICLE = -1;
	// List of category titles
	private final String _CATEGORIES[] = { "Top Stories", "Politics", "Economy", "Technology" };
	private NewsReaderPresentation _presentation;
	// Whether or not we are in dual-pane mode
	private boolean _has_two_panes;
	// The news category and article index currently being displayed
	private int _category_index;
	private int _article_index;
}
