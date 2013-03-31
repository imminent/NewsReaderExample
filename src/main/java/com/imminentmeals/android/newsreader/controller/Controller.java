package com.imminentmeals.android.newsreader.controller;

import com.imminentmeals.android.newsreader.presentation.Presentation;

/**
 *
 * @author Dandre Allison
 */
public interface Controller {
	String BUS = "com.imminentmeals.android.newsreader.presentation.Presentation.BUS";

	void attachPresentation(Presentation presentation);
	
	@SuppressWarnings("serial")
	class IncompatiblePresentationException extends IllegalArgumentException {
		
		public IncompatiblePresentationException(Presentation presentation) {
			super("Attempting to attach imcompatible Presentation: " + presentation.getClass().getName());
		}
	}
}
