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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.portlet.MimeResponse.Copy;
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

		String elementName = _remoteWebComponentConfiguration.elementName();

		try {
			PrintWriter printWriter = renderResponse.getWriter();

			LiferayPortletURL renderURL = (LiferayPortletURL)renderResponse.createRenderURL(Copy.PUBLIC);

			renderURL.addParameterIncludedInPath("p_p_state");

			String routerBaseSelf = HttpUtil.removeDomain(renderURL.toString());
			int portletURLSeparator = routerBaseSelf.indexOf("/-/");
			String routerBasePage = routerBaseSelf.substring(0, portletURLSeparator);
			int pageSeparator = routerBasePage.lastIndexOf("/");
			String routerBaseSite = routerBasePage.substring(0, pageSeparator);

			printWriter.append(StringPool.LESS_THAN);
			printWriter.append(elementName);
			printWriter.append(" id=\"");
			printWriter.append(renderResponse.getNamespace());
			printWriter.append("\" router-base-self=\"");
			printWriter.append(routerBaseSelf);
			printWriter.append("\" router-base-page=\"");
			printWriter.append(routerBasePage);
			printWriter.append("\" router-base-site=\"");
			printWriter.append(routerBaseSite);
			printWriter.append("\" statemanager-descriptor=\"StateManager\"");
			printWriter.append(" authtoken-descriptor=\"Liferay.authToken\"");

			StringBuffer sb = new StringBuffer();

			_webComponentConfigurationAttributes.forEach((k, v) -> {
				sb.append(" data-config-");
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

				sb.append("\"");
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
				elementName, throwable);
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

	private static final List<String> _configMethodNames = Stream.of(
		RemoteWebComponentConfiguration.class.getDeclaredMethods()
	).map(Method::getName).collect(toList());

	private static final List<Predicate<String>> _keyFilters = Arrays.asList(
		"component.id"::equals,
		"component.name"::equals,
		"configuration.cleaner.ignore"::equals,
		"service.pid"::equals,
		"service.factoryPid"::equals,
		k -> k.endsWith(".target"),
		k -> k.startsWith("com.liferay.portlet."),
		k -> k.startsWith("felix."),
		k -> k.startsWith("javax.portlet."),
		_configMethodNames::contains
	);

	private volatile RemoteWebComponentConfiguration _remoteWebComponentConfiguration;
	private volatile Map<String, Object> _webComponentConfigurationAttributes;

}