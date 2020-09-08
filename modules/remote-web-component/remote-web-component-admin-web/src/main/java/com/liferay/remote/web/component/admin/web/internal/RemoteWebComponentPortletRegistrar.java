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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.remote.web.component.admin.web.internal.portlet.RemoteWebComponentPortlet;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;
import com.liferay.remote.web.component.service.RemoteWebComponentEntryLocalService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = RemoteWebComponentPortletRegistrar.class)
public class RemoteWebComponentPortletRegistrar {

	public void registerPortlet(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		_registerPortlet(remoteWebComponentEntry);
	}

	public void unregisterPortlet(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		_unregisterPortlet(
			remoteWebComponentEntry.getRemoteWebComponentEntryId());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		if (_log.isInfoEnabled()) {
			_log.info("Starting remote app entries");
		}

		for (RemoteWebComponentEntry remoteWebComponentEntry :
				remoteWebComponentEntryLocalService.
					getRemoteWebComponentEntries(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			registerPortlet(remoteWebComponentEntry);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_log.isInfoEnabled()) {
			_log.info("Stopping remote app entries");
		}

		for (long remoteWebComponentEntryId : _remoteAppPortlets.keySet()) {
			_unregisterPortlet(remoteWebComponentEntryId);
		}
	}

	@Reference
	protected RemoteWebComponentEntryLocalService
		remoteWebComponentEntryLocalService;

	private void _registerPortlet(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		RemoteWebComponentPortlet remoteWebComponentPortlet =
			new RemoteWebComponentPortlet(remoteWebComponentEntry);

		long remoteAppEntryId =
			remoteWebComponentEntry.getRemoteWebComponentEntryId();

		RemoteWebComponentPortlet existingRemoteWebComponentPortlet =
			_remoteAppPortlets.putIfAbsent(
				remoteAppEntryId, remoteWebComponentPortlet);

		if (existingRemoteWebComponentPortlet != null) {
			throw new IllegalStateException(
				"Remote app entry " + remoteAppEntryId +
					" is already registered");
		}

		remoteWebComponentPortlet.register(_bundleContext);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Started remote app entry " +
					remoteWebComponentPortlet.getName());
		}
	}

	private void _unregisterPortlet(long remoteAppEntryId) {
		RemoteWebComponentPortlet remoteWebComponentPortlet =
			_remoteAppPortlets.remove(remoteAppEntryId);

		if (remoteWebComponentPortlet != null) {
			remoteWebComponentPortlet.unregister();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Stopped remote app entry " +
						remoteWebComponentPortlet.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebComponentPortletRegistrar.class);

	private BundleContext _bundleContext;
	private final ConcurrentMap<Long, RemoteWebComponentPortlet>
		_remoteAppPortlets = new ConcurrentHashMap<>();

}