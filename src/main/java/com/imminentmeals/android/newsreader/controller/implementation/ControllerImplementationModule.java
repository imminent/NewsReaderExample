package com.imminentmeals.android.newsreader.controller.implementation;

import javax.inject.Named;
import javax.inject.Singleton;

import com.imminentmeals.android.newsreader.controller.ArticleController;
import com.imminentmeals.android.newsreader.controller.Controller;
import com.imminentmeals.android.newsreader.controller.NewsReaderController;
import com.squareup.otto.Bus;

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
	@Provides NewsReaderController provideNewsReaderController(@Named(Controller.BUS) Bus bus) {
		return new _NewsReaderController(bus);
	}
	
	@Provides ArticleController provideArticleController(@Named(Controller.BUS) Bus bus) {
		return new _ArticleController(bus);
	}
	
	@Provides @Singleton @Named(Controller.BUS) Bus provideControllerBus() {
		return new Bus(Controller.BUS);
	}
}
