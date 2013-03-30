package com.imminentmeals.android.newsreader.controller.implementation;

import javax.inject.Singleton;

import com.imminentmeals.android.newsreader.controller.ArticleController;
import com.imminentmeals.android.newsreader.model.NewsArticle;
import com.imminentmeals.android.newsreader.model.NewsSource;
import com.imminentmeals.android.newsreader.presentation.ArticlePresentation;
import com.imminentmeals.android.newsreader.presentation.Presentation;
import com.imminentmeals.android.newsreader.presentation.Messages.ArticlePresentation.WillCreatePresentation;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 *
 * @author Dandre Allison
 */
@Singleton
/* package */class _ArticleController implements ArticleController {
	
	/* package */_ArticleController() {
		_bus = new Bus("ArticleController");
		_bus.register(this);
	}

	@Override
	public void attachPresentation(Presentation presentation) {
		if (!(presentation instanceof ArticlePresentation))
			throw new IncompatiblePresentationException(presentation);
		_presentation = (ArticlePresentation) presentation;
	}

	@Override
	public void sendMessage(Object message) {
		_bus.post(message);
	}

	@Override
	@Subscribe
	public void didCreate(WillCreatePresentation message) {
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
	private final Bus _bus;
}
