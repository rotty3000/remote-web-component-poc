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

package com.liferay.remote.web.component.admin.web.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration;
import com.liferay.remote.web.component.admin.web.internal.portlet.RemoteWebComponentPortlet;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	configurationPid = "com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = RemoteWebComponentPortletRegistrar.class
)
public class RemoteWebComponentPortletRegistrar {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_remoteWebComponentConfiguration = ConfigurableUtil.createConfigurable(
			RemoteWebComponentConfiguration.class, properties);

		final RemoteWebComponentPortlet remoteWebComponentPortlet =
			new RemoteWebComponentPortlet(_remoteWebComponentConfiguration);

		if (_log.isInfoEnabled()) {
			_log.info("Starting remote web component " + _remoteWebComponentConfiguration.name());
		}

		remoteWebComponentPortlet.register(bundleContext);

		_remoteWebComponentPortlet = remoteWebComponentPortlet;

		if (_log.isInfoEnabled()) {
			_log.info(
				"Started remote app entry " +
					_remoteWebComponentConfiguration.name());
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping remote web component " + _remoteWebComponentConfiguration.name());
		}

		_remoteWebComponentPortlet.unregister();

		_remoteWebComponentPortlet = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebComponentPortletRegistrar.class);

	private volatile RemoteWebComponentConfiguration _remoteWebComponentConfiguration;
	private RemoteWebComponentPortlet _remoteWebComponentPortlet;

}