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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration;
import com.liferay.remote.web.component.admin.web.internal.util.Timestamp;

import static com.liferay.portal.kernel.util.Validator.isNotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Iván Zaera Avellón
 * @author Raymond Auge
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

		_elementName = _remoteWebComponentConfiguration.elementName();

		if (_log.isInfoEnabled()) {
			_log.info("Starting remote web component {}", _elementName);
		}

		// Portlet

		Dictionary<String, Object> componentProperties = new Hashtable<>(properties);

		componentProperties.remove(Constants.SERVICE_PID);

		componentProperties.put(
			"com.liferay.portlet.css-class-wrapper",
			"portlet-remote-web-component");
		componentProperties.put(
			"com.liferay.portlet.requires-namespaced-parameters", "false");
		componentProperties.put("javax.portlet.name", _getPortletName());
		componentProperties.put(
			"javax.portlet.preferences",
			"classpath:/META-INF/portlet-preferences/default-preferences.xml");
		componentProperties.put("javax.portlet.security-role-ref", "power-user,user");
		componentProperties.put("javax.portlet.version", "3.0");

		UnicodeProperties customProperties = new UnicodeProperties();

		try {
			customProperties.load(_remoteWebComponentConfiguration.portletServiceProperties());
			customProperties.forEach(componentProperties::put);
		}
		catch (IOException ioe) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"Could not parse portlet service properties for {}",
					_elementName, ioe);
			}
		}

		Instant now = Instant.now();

		String[] webComponentJSUrls = Timestamp.append(
			_remoteWebComponentConfiguration.webComponentUrl(), now);

		componentProperties.put(
			"com.liferay.portlet.header-portal-javascript",
			webComponentJSUrls);

		String[] webComponentCSSUrls = Timestamp.append(
			_remoteWebComponentConfiguration.webComponentCssUrl(), now);

		if (webComponentCSSUrls.length > 0) {
			componentProperties.put(
				"com.liferay.portlet.header-portal-css",
				webComponentCSSUrls);
		}

		String displayCategory = _remoteWebComponentConfiguration.portletDisplayCategory();

		displayCategory = isNotNull(displayCategory) ? displayCategory : "sample";

		componentProperties.put(
			"com.liferay.portlet.display-category", "category." + displayCategory);
		componentProperties.put(
			"com.liferay.portlet.instanceable",
			String.valueOf(_remoteWebComponentConfiguration.instanceable()));
		//componentProperties.put(
		//    "com.liferay.portlet.single-page-application", "false");
		componentProperties.put(
			"javax.portlet.resource-bundle", _getResourceBundleName());

		_portletInstance = _remoteWebComponentPortletFactory.newInstance(
			componentProperties);

		// Resource Bundle Loader

		componentProperties = new Hashtable<>(properties);

		componentProperties.remove(Constants.SERVICE_PID);
		componentProperties.put(
			"resource.bundle.base.name", _getResourceBundleName());
		componentProperties.put(
			"servlet.context.name", "remote-web-component-admin-web");
		componentProperties.put(
			"javax.portlet.title." + _getPortletName(),
			_remoteWebComponentConfiguration.name());
		componentProperties.put(
			"category." + displayCategory, displayCategory);

		_bundleResourceLoaderInstance =
			_remoteWebComponentResourceBundleLoaderFactory.newInstance(
				componentProperties);

		// Friendly URL Mapper

		componentProperties = new Hashtable<>(properties);

		componentProperties.remove(Constants.SERVICE_PID);
		componentProperties.put("javax.portlet.name", _getPortletName());

		_friendlyURLMapperInstance =
			_remoteWebComponentFriendlyURLMapperFactory.newInstance(
				componentProperties);

		// Top Js Dynamic Include

		componentProperties = new Hashtable<>(properties);

		componentProperties.remove(Constants.SERVICE_PID);
		componentProperties.put("javax.portlet.name", _getPortletName());

		_topJsDynamicInclude =
			_remoteWebComponentTopJsDynamicInclude.newInstance(
				componentProperties);

		if (_log.isInfoEnabled()) {
			_log.info("Started remote web component {}", _elementName);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping remote web component {}", _elementName);
		}

		_portletInstance.dispose();
		_bundleResourceLoaderInstance.dispose();
		_friendlyURLMapperInstance.dispose();
		_topJsDynamicInclude.dispose();

		_portletInstance = null;
		_bundleResourceLoaderInstance = null;
		_friendlyURLMapperInstance = null;
		_topJsDynamicInclude = null;

		if (_log.isInfoEnabled()) {
			_log.info("Stopped remote web component {}", _elementName);
		}
	}

	private String _getPortletName() {
		final String portletAlias = _remoteWebComponentConfiguration.portletAlias();
		return PortalUtil.getJsSafePortletId(
			"rwc_" + (isNotNull(portletAlias) ? portletAlias : _elementName));
	}

	private String _getResourceBundleName() {
		return _getPortletName() + ".Language";
	}

	@Reference(target = "(component.factory=remote.web.component.friendly.url.mapper)")
	private ComponentFactory _remoteWebComponentFriendlyURLMapperFactory;

	@Reference(target = "(component.factory=remote.web.component.portlet)")
	private ComponentFactory _remoteWebComponentPortletFactory;

	@Reference(target = "(component.factory=remote.web.component.resource.bundle.loader)")
	private ComponentFactory _remoteWebComponentResourceBundleLoaderFactory;

	@Reference(target = "(component.factory=remote.web.component.top.js.dynamic.include)")
	private ComponentFactory _remoteWebComponentTopJsDynamicInclude;

	private static final Logger _log = LoggerFactory.getLogger(
		RemoteWebComponentPortletRegistrar.class);

	private volatile ComponentInstance _bundleResourceLoaderInstance;
	private volatile String _elementName;
	private volatile ComponentInstance _friendlyURLMapperInstance;
	private volatile ComponentInstance _portletInstance;
	private volatile ComponentInstance _topJsDynamicInclude;
	private volatile RemoteWebComponentConfiguration _remoteWebComponentConfiguration;

}
