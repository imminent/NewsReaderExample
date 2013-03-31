package com.imminentmeals.android.newsreader.controller.implementation;

import javax.inject.Inject;
import javax.inject.Named;

import com.imminentmeals.android.newsreader.controller.ArticleController;
import com.imminentmeals.android.newsreader.controller.Controller;
import com.imminentmeals.android.newsreader.model.NewsArticle;
import com.imminentmeals.android.newsreader.model.NewsSource;
import com.imminentmeals.android.newsreader.presentation.ArticlePresentation;
import com.imminentmeals.android.newsreader.presentation.Messages;
import com.imminentmeals.android.newsreader.presentation.Presentation;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 *
 * @author Dandre Allison
 */
/* package */class _ArticleController implements ArticleController, Messages.ArticlePresentation {

	@Inject
	/* package */_ArticleController(@Named(Controller.BUS) Bus bus) {
		bus.register(this);
	}

	@Override
	public void attachPresentation(Presentation presentation) {
		if (!(presentation instanceof ArticlePresentation))
			throw new IncompatiblePresentationException(presentation);
		_presentation = (ArticlePresentation) presentation;
	}

	@Override
	@Subscribe
	public void willCreatePresentation(WillCreatePresentation message) {
		if (message.has_two_panes) {
			_presentation.stop();
			return;
		}
		
		// Display the correct news article.
	    final NewsArticle article = NewsSource.getInstance().getCategory(message.category_index)
	        .getArticle(message.article_index);
	    _presentation.displayArticle(article);
	}

	private ArticlePresentation _presentation;
}
