package com.imminentmeals.android.newsreader.presentation;

import com.imminentmeals.android.newsreader.model.NewsArticle;
import com.imminentmeals.android.newsreader.model.NewsCategory;

/**
 *
 * @author Dandre Allison
 */
public interface NewsReaderPresentation extends Presentation {

	void category(NewsCategory category);

	void setupActionBar(String[] categories, boolean show_tabs, int selected_index);

	void article(NewsArticle article);

	void showArticleActivity(int category_index, int article_index);

	void showCategoryDialog(String[] categories);
	
	public interface Protocol {
		int categoryIndex();
		int articleIndex();
	}
}
