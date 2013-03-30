package com.imminentmeals.android.newsreader.controller;

import com.imminentmeals.android.newsreader.presentation.Messages.ArticlePresentation.WillCreatePresentation;
import com.squareup.otto.Subscribe;


/**
 *
 * @author Dandre Allison
 */
public interface ArticleController extends Controller {
	@Subscribe void didCreate(WillCreatePresentation message);
}
