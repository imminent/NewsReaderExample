package com.imminentmeals.android.newsreader;

import javax.inject.Inject;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import butterknife.Views;

import com.imminentmeals.android.newsreader.controller.ArticleController;
import com.imminentmeals.android.newsreader.controller.Controller;
import com.imminentmeals.android.newsreader.controller.NewsReaderController;
import com.imminentmeals.android.newsreader.controller.implementation.ControllerImplementationModule;
import com.imminentmeals.android.newsreader.presentation.Presentation;
import com.imminentmeals.android.newsreader.presentation.framework.ArticleActivity;
import com.imminentmeals.android.newsreader.presentation.framework.NewsReaderActivity;

import dagger.Lazy;
import dagger.Module;
import dagger.ObjectGraph;

/**
 *
 * @author Dandre Allison
 */
public class NewsReaderSegueController extends Application implements Application.ActivityLifecycleCallbacks {
	@Inject /* package */Lazy<NewsReaderController> news_reader_controller; 
	@Inject /* package */Lazy<ArticleController> article_controller;

	@Override
	public void onCreate() {
		if (BuildConfig.DEBUG)
            StrictMode.enableDefaults();
		super.onCreate();
		
		registerActivityLifecycleCallbacks(this);
		
		final ObjectGraph object_graph = ObjectGraph.create(new ModeViewControllerModule());
		object_graph.inject(this);
	}

/* Activity Lifecycle Callbacks */
	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		Views.inject(activity);
		if (!(activity instanceof Presentation)) return;
		
		if (activity instanceof NewsReaderActivity)
			_controller = news_reader_controller.get();
		else if (activity instanceof ArticleActivity)
			_controller = article_controller.get();
		else
			throw new IllegalArgumentException("Activity " + activity + " doesn't have a Controller specified.");
		_controller.attachPresentation((Presentation) activity);
		((Presentation) activity).attachController(_controller);
		if (BuildConfig.DEBUG) Log.v("order check", "ActivityLifecyleCallbacks.onActivityCreated()");
	}

	@Override
	public void onActivityDestroyed(Activity activity) { }

	@Override
	public void onActivityPaused(Activity activity) { }

	@Override
	public void onActivityResumed(Activity activity) { }

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }

	@Override
	public void onActivityStarted(Activity activity) { }

	@Override
	public void onActivityStopped(Activity activity) { }
	
/* Dependency Inject Graph */
	@Module(
			entryPoints = {
					NewsReaderSegueController.class
			},
			includes = ControllerImplementationModule.class
	)
	/* package */static class ModeViewControllerModule {
		
	}
	
	private Controller _controller;
}
