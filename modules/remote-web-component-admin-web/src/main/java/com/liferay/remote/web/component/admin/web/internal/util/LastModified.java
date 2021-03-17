package com.liferay.remote.web.component.admin.web.internal.util;

import org.osgi.framework.FrameworkUtil;

public class LastModified {

	private LastModified() {
	}

	public static long get() {
		return FrameworkUtil.getBundle(LastModified.class).getLastModified();
	}

	public static String getString() {
		return String.valueOf(get());
	}

}
