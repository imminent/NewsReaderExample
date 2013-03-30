package com.imminentmeals.android.newsreader.controller;

import com.imminentmeals.android.newsreader.presentation.NewsReaderPresentation;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.CategorySelected;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.WillCreatePresentation;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.WillRestorePresentation;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.WillStartPresentation;
import com.imminentmeals.android.newsreader.presentation.Messages.NewsReaderPresentation.HeadlineSelected;
import com.squareup.otto.Subscribe;

/**
 *
 * @author Dandre Allison
 */
public interface NewsReaderController extends Controller, NewsReaderPresentation.Protocol {
	@Subscribe void didCreate(WillCreatePresentation message);
	@Subscribe void didRestore(WillRestorePresentation message);
	@Subscribe void didStart(WillStartPresentation message);
	@Subscribe void onCategorySelected(CategorySelected message);
	@Subscribe void onHeadlineSelected(HeadlineSelected message);
}
