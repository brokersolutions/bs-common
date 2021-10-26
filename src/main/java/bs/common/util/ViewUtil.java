package bs.common.util;

import lombok.val;

import java.io.Serializable;

import bs.common.payload.ViewDto;

public class ViewUtil implements Serializable {
	private static final long serialVersionUID = 2540673030728713690L;

	private ViewUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static ViewDto populateView(final String title) {
		val view = new ViewDto();
		view.setTitle(title);
		return view;
	}

	public ViewDto populateView() {
		val view = new ViewDto();
		view.setTitleHide("no-title");
		return view;
	}

	public static ViewDto populateView(final String nav, final String title) {
		val view = new ViewDto();
		view.setNav(nav);
		view.setTitle(title);
		return view;
	}

	public static ViewDto populateView(final String parent, final String parentPath, final String title) {
		val view = new ViewDto();
		view.setParent(parent);
		view.setParentPath(parentPath);
		view.setTitle(title);
		return view;
	}

	public static ViewDto populateView(final String nav, final String parent, final String parentPath,
			final String title) {
		val view = new ViewDto();
		view.setNav(nav);
		view.setParent(parent);
		view.setParentPath(parentPath);
		view.setTitle(title);
		return view;
	}

	public static ViewDto populateView(final String parent, final String parentPath, final String sibling,
			final String siblingPath, final String title) {
		val view = new ViewDto();
		view.setParent(parent);
		view.setParentPath(parentPath);
		view.setSibling(sibling);
		view.setSiblingPath(siblingPath);
		view.setTitle(title);
		return view;
	}

	public static ViewDto populateView(final String nav, final String parent, final String parentPath,
			final String sibling, final String siblingPath, final String title) {
		val view = new ViewDto();
		view.setNav(nav);
		view.setParent(parent);
		view.setParentPath(parentPath);
		view.setSibling(sibling);
		view.setSiblingPath(siblingPath);
		view.setTitle(title);
		return view;
	}
}