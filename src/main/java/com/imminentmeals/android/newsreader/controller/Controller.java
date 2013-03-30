package com.imminentmeals.android.newsreader.controller;

import com.imminentmeals.android.newsreader.presentation.Presentation;

/**
 *
 * @author Dandre Allison
 */
public interface Controller {
	void attachPresentation(Presentation presentation);
	void sendMessage(Object message);
	
	@SuppressWarnings("serial")
	class IncompatiblePresentationException extends IllegalArgumentException {
		
		public IncompatiblePresentationException(Presentation presentation) {
			super("Attempting to attach imcompatible Presentation: " + presentation.getClass().getName());
		}
	}
}
