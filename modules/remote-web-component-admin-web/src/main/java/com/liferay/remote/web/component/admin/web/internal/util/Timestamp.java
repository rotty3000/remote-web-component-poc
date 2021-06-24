package com.liferay.remote.web.component.admin.web.internal.util;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

public class Timestamp {

	private static final String[] EMPTY = new String[0];

	private Timestamp() {
	}

	public static String[] append(String[] urls, Instant now) {
		return Stream.of((urls != null) ? urls : EMPTY).filter(
			Objects::nonNull
		).map(
			String::trim
		).filter(
			url -> !url.isEmpty()
		).map(
			url -> {
				if (url.indexOf('?') > -1) {
					return url + "&ts=" + now.toEpochMilli();
				}
				else {
					return url + "?ts=" + now.toEpochMilli();
				}
			}
		).toArray(String[]::new);
	}

}
