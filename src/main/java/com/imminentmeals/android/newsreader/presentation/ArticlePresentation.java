package com.imminentmeals.android.newsreader.presentation;

import com.imminentmeals.android.newsreader.model.NewsArticle;

/**
 * 
 * @author Dandre Allison
 */
public interface ArticlePresentation extends Presentation {
	void displayArticle(NewsArticle article);

	void stop();
}
