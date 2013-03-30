package com.imminentmeals.android.newsreader.presentation;


/**
 *
 * @author Dandre Allison
 */
public class Messages {
	
	public static class ArticlePresentation {
		
		public static class WillCreatePresentation {
			public final boolean has_two_panes;
			public final int category_index;
			public final int article_index;
			
			public WillCreatePresentation(boolean has_two_panes, int category_index, int article_index) {
				this.has_two_panes = has_two_panes;
				this.category_index = category_index;
				this.article_index = article_index;
			}
		}
	}
	
	public static class NewsReaderPresentation {
		
		public static class WillCreatePresentation {
			public final boolean has_two_panes;
			public final int category_index;
			
			public WillCreatePresentation(boolean has_two_panes, int category_index) {
				this.has_two_panes = has_two_panes;
				this.category_index = category_index;
			}
		}
		
		public static class WillRestorePresentation {
			public final int category_index;
			public final int article_index;
			
			public WillRestorePresentation(int category_index, int article_index) {
				this.category_index = category_index;
				this.article_index = article_index;
			}
		}
		
		public static class WillStartPresentation { 
			public static final WillStartPresentation DID_START = new WillStartPresentation();
		}
		
		public static class CategorySelected {
			public final int category_index;
			
			public CategorySelected(int category_index) {
				this.category_index = category_index;
			}
		}
		
		public static class HeadlineSelected {
			public final int article_index;
			
			public HeadlineSelected(int article_index) {
				this.article_index = article_index;
			}
		}
	}
}
