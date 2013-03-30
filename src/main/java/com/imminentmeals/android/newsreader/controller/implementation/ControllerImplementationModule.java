package com.imminentmeals.android.newsreader.controller.implementation;

import javax.inject.Singleton;

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
	@Provides @Singleton NewsReaderController provideNewsReaderController() {
		return new _NewsReaderController();
	}
	
	@Provides @Singleton ArticleController provideArticleController() {
		return new _ArticleController();
	}
}
