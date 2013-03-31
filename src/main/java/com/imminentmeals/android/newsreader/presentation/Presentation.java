package com.imminentmeals.android.newsreader.presentation;

import com.imminentmeals.android.newsreader.controller.Controller;

/**
 *
 * @author Dandre Allison
 */
public interface Presentation {
	String EXTRA = "com.imminentmeals.android.newsreader.presentation.Extra.";
	String KEY = "com.imminentmeals.android.newsreader.presentation.Key.";
		
	@SuppressWarnings("serial")
	class IncompatibleControllerException extends IllegalArgumentException {
		
		public IncompatibleControllerException(Controller controller) {
			super("Attempting to attach imcompatible Controller: " + controller.getClass().getName());
		}
	}
}
