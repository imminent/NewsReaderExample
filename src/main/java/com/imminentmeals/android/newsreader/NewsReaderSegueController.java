package com.imminentmeals.android.newsreader;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import butterknife.Views;

import com.imminentmeals.android.newsreader.controller.ArticleController;
import com.imminentmeals.android.newsreader.controller.Controller;
import com.imminentmeals.android.newsreader.controller.NewsReaderController;
import com.imminentmeals.android.newsreader.controller.implementation.ControllerImplementationModule;
import com.imminentmeals.android.newsreader.presentation.Presentation;
import com.imminentmeals.android.newsreader.presentation.HasProtocol;
import com.imminentmeals.android.newsreader.presentation.framework.ArticleActivity;
import com.imminentmeals.android.newsreader.presentation.framework.NewsReaderActivity;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.ObjectGraph;

/**
 *
 * @author Dandre Allison
 */
public class NewsReaderSegueController extends Application implements Application.ActivityLifecycleCallbacks {
	@Inject /* package */Provider<NewsReaderController> news_reader_controller; 
	@Inject /* package */Provider<ArticleController> article_controller;
	@Inject @Named(Controller.BUS)/* package */Bus controller_bus;

/* Lifecycle */
	@Override
	public void onCreate() {
		if (BuildConfig.DEBUG)
            StrictMode.enableDefaults();
		super.onCreate();
		
		registerActivityLifecycleCallbacks(this);
		
		final ObjectGraph object_graph = ObjectGraph.create(new ModeViewControllerModule());
		object_graph.inject(this);
		
		_controllers = new HashMap<Activity, Object>();
	}

/* Activity Lifecycle Callbacks */
	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		Views.inject(activity);
		if (!(activity instanceof Presentation)) return;
		
		final Controller controller;
		if (activity instanceof NewsReaderActivity)
			controller = news_reader_controller.get();
		else if (activity instanceof ArticleActivity)
			controller = article_controller.get();
		else
			throw new IllegalArgumentException("Activity " + activity.getClass().getName() 
					+ " doesn't have a Controller specified in NewsReaderSequeController.");
		controller.attachPresentation((Presentation) activity);
		// Registers the Controller as the Presentations Protocol implementation
		HasProtocol annotation = activity.getClass().getAnnotation(HasProtocol.class);
		if (annotation != null) {
			Class<?> protocol = annotation.value();
			
			assert protocol.isInterface();
			if (protocol.isInstance(controller)) 
				_controllers.put(activity, controller);
			else
				throw new IllegalArgumentException("Controller for" + activity.getClass().getName()
						+ " doesn't implement expected Protocol: " + protocol.getName()); 
		}
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
	
/* SequeController Contract */
	@SuppressWarnings("unchecked")
	public static <T, U extends Activity & Presentation> T controller(U presentation) {
		final T controller;
		try {
			controller = (T) ((NewsReaderSegueController) presentation.getApplication())._controllers.get(presentation);
		} catch (ClassCastException _) {
			throw new IllegalStateException("Controller for " + presentation.getClass().getName() 
					+ " doesn't have a defined Protocol");
		}
		return controller;
	}
	
	@SuppressWarnings("unchecked")
	public static <T, U extends Activity & Presentation> T controller(Fragment presentation_fragment) {
		final U presentation;
		try {
			presentation = (U) presentation_fragment.getActivity();
		} catch(ClassCastException _) {
			throw new IllegalArgumentException("Fragment " + presentation_fragment.getClass().getName()
					+ " isn't attached to a Presentation");
		}
		if (presentation == null)
			throw new IllegalStateException("Fragment " + presentation_fragment.getClass().getName()
					+ " isn't attached to an Activity");			
		 
		return controller((U) presentation);
	}

	public static <T extends Activity & Presentation> void sendMessage(T presentation, Object message) {
		((NewsReaderSegueController) presentation.getApplication()).controller_bus.post(message);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Activity & Presentation> void sendMessage(Fragment presentation_fragment, Object message) {
		final T presentation;
		try {
			presentation = (T) presentation_fragment.getActivity();
		} catch(ClassCastException _) {
			throw new IllegalArgumentException("Fragment " + presentation_fragment.getClass().getName()
					+ " isn't attached to a Presentation");
		}
		if (presentation == null)
			throw new IllegalStateException("Fragment " + presentation_fragment.getClass().getName()
					+ " isn't attached to an Activity");	
		sendMessage(presentation, message);
	}
	
/* Dependency Inject Graph */
	@Module(
			entryPoints = {
					NewsReaderSegueController.class
			},
			includes = ControllerImplementationModule.class
	)
	/* package */static class ModeViewControllerModule {
		
	}
	
	private HashMap<Activity, Object> _controllers;
}
