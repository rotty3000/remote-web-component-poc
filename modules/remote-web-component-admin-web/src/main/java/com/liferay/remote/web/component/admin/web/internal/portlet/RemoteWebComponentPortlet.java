/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.remote.web.component.admin.web.internal.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	factory = "remote.web.component.portlet",
	service = Portlet.class
)
public class RemoteWebComponentPortlet extends MVCPortlet {

	public String getName() {
		return _remoteWebComponentConfiguration.name();
	}

	public String getName(Locale locale) {
		return _remoteWebComponentConfiguration.name();
	}

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			String elementName = _remoteWebComponentConfiguration.elementName();

			ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String basePath = _portal.getLayoutRelativeURL(themeDisplay.getLayout(), themeDisplay);

			printWriter.append(StringPool.LESS_THAN);
			printWriter.append(elementName);
			printWriter.append(" id=\"remote-web-component-");
			printWriter.append(renderResponse.getNamespace());
			printWriter.append("\" base-path=\"");
			printWriter.append(basePath);
			printWriter.append("\" namespace=\"");
			printWriter.append(renderResponse.getNamespace());
			printWriter.append("\" ");
			printWriter.append(" store-descriptor=\"Liferay.State\" ");

			renderRequest.getParameterMap().forEach((k, v) -> {
				printWriter.append("data-param-");
				printWriter.append(k);
				printWriter.append("=\"");
				printWriter.append(v.length > 1 ? StringUtil.merge(v, " ") : v[0]);
				printWriter.append("\" ");
			});

			renderRequest.getPublicParameterMap().forEach((k, v) -> {
				printWriter.append("data-param-");
				printWriter.append(k);
				printWriter.append("=\"");
				printWriter.append(v.length > 1 ? StringUtil.merge(v, " ") : v[0]);
				printWriter.append("\" ");
			});

			StringBuffer sb = new StringBuffer();

			_webComponentConfigurationAttributes.forEach((k, v) -> {
				sb.append("data-config-");
				sb.append(k.replaceAll("\\.", "-"));
				sb.append("=\"");

				if (v.getClass().isArray()) {
					sb.append(
						Arrays.asList(v).stream().map(String::valueOf).collect(joining(" ")));
				}
				else if (v instanceof Collection) {
					sb.append(
						((Collection<?>)v).stream().map(String::valueOf).collect(joining(" ")));
				}
				else {
					sb.append(String.valueOf(v));
				}

				sb.append("\" ");
			});

			printWriter.append(sb.toString());

			printWriter.append(StringPool.GREATER_THAN);
			printWriter.append("</");
			printWriter.append(elementName);
			printWriter.append(StringPool.GREATER_THAN);

			printWriter.flush();
		}
		catch (Throwable throwable) {
			_log.error(
				"Unable to render web Component <{}>",
				_remoteWebComponentConfiguration.elementName(),
				throwable);
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_remoteWebComponentConfiguration = ConfigurableUtil.createConfigurable(
			RemoteWebComponentConfiguration.class, properties);

		_webComponentConfigurationAttributes = properties.entrySet().stream().filter(
			e -> _keyFilters.stream().noneMatch(p -> p.test(e.getKey()))
		).collect(toMap(Entry::getKey, Entry::getValue));
	}

	@Reference
	private Portal _portal;

	private static final Logger _log = LoggerFactory.getLogger(
		RemoteWebComponentPortlet.class);

	private static final List<Predicate<String>> _keyFilters = Arrays.asList(
		"component.id"::equals,
		"component.name"::equals,
		"service.pid"::equals,
		"service.factoryPid"::equals,
		"portletServiceProperties"::equals,
		"routes"::equals,
		k -> k.endsWith(".target"),
		k -> k.startsWith("com.liferay.portlet."),
		k -> k.startsWith("felix."),
		k -> k.startsWith("javax.portlet.")
	);

	private volatile RemoteWebComponentConfiguration _remoteWebComponentConfiguration;
	private volatile Map<String, Object> _webComponentConfigurationAttributes;

}