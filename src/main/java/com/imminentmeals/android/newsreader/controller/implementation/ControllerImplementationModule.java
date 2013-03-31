package com.imminentmeals.android.newsreader.controller.implementation;

import com.imminentmeals.android.newsreader.controller.ArticleController;
import com.imminentmeals.android.newsreader.controller.NewsReaderController;

import dagger.Module;
import dagger.Provides;

/**
 *
 * @author Dandre Allison
 */
@Module(
		entryPoints = { 
				_NewsReaderController.class,
				_ArticleController.class
				},
		complete = false
)
public class ControllerImplementationModule {
	@Provides NewsReaderController provideNewsReaderController() {
		return new _NewsReaderController();
	}
	
	@Provides ArticleController provideArticleController() {
		return new _ArticleController();
	}
}
