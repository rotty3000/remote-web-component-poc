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

package com.liferay.remote.web.component.admin.web.internal.route;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration;

@Component(
	factory = "remote.web.component.friendly.url.mapper",
	service = FriendlyURLMapper.class
)
public class RemoteWebComponentFriendlyURLMapper extends DefaultFriendlyURLMapper {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_remoteWebComponentConfiguration = ConfigurableUtil.createConfigurable(
			RemoteWebComponentConfiguration.class, properties);

		boolean instanceable = _remoteWebComponentConfiguration.instanceable();
		Router router = new RouterImpl();

		if ((_remoteWebComponentConfiguration.routes().length == 1) && _remoteWebComponentConfiguration.routes()[0].length() == 0) {
			// skip
		}
		else {
			for (String routePattern : _remoteWebComponentConfiguration.routes()) {
				if (!validate(routePattern)) {
					if (_log.isErrorEnabled()) {
						_log.error(
								"Invalid route [{}] was specified for web component <{}>",
								routePattern, _remoteWebComponentConfiguration.elementName());
					}

					continue;
				}

				Route route = router.addRoute(instanceable ? "/{instanceId}" + routePattern : routePattern);

				route.addImplicitParameter("p_p_lifecycle", "0");
				route.addImplicitParameter("p_p_mode", PortletMode.VIEW.toString());
				route.addImplicitParameter("p_p_state", WindowState.MAXIMIZED.toString());
			}
		}

		String baseRoute = instanceable ? "/{instanceId}" : "";

		if (router.getRoutes().stream().map(Route::getPattern).noneMatch(baseRoute::equals)) {
			Route route = router.addRoute(baseRoute);

			route.addImplicitParameter("p_p_lifecycle", "0");
			route.addImplicitParameter("p_p_mode", PortletMode.VIEW.toString());
			route.addImplicitParameter("p_p_state", WindowState.MAXIMIZED.toString());
		}

		super.router = router;
	}

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		Map<String, String> routeParameters = new HashMap<>();

		buildRouteParameters(liferayPortletURL, routeParameters);

		String friendlyURLPath = router.parametersToUrl(routeParameters);

		if (friendlyURLPath == null) {
			return null;
		}

		addParametersIncludedInPath(liferayPortletURL, routeParameters);

		return StringPool.SLASH.concat(
			getMapping()
		).concat(
			friendlyURLPath
		);
	}

	@Override
	public String getMapping() {
		return _remoteWebComponentConfiguration.elementName();
	}

	@Override
	public boolean isCheckMappingWithPrefix() {
		return super.isCheckMappingWithPrefix();
	}

	@Override
	public void setRouter(Router router) {
		// make sure we don't get reset
	}

	private boolean validate(String routePattern) {
		return routePattern.startsWith("/") && (routePattern.length() > 1);
	}

	private static final Logger _log = LoggerFactory.getLogger(
		RemoteWebComponentFriendlyURLMapper.class);

	private RemoteWebComponentConfiguration _remoteWebComponentConfiguration;

}