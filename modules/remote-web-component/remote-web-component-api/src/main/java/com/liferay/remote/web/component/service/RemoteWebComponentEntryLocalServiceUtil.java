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

package com.liferay.remote.web.component.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for RemoteWebComponentEntry. This utility wraps
 * <code>com.liferay.remote.web.component.service.impl.RemoteWebComponentEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebComponentEntryLocalService
 * @generated
 */
public class RemoteWebComponentEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.remote.web.component.service.impl.RemoteWebComponentEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
			addRemoteWebComponentEntry(
				long userId, java.util.Map<java.util.Locale, String> nameMap,
				String url,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRemoteWebComponentEntry(
			userId, nameMap, url, serviceContext);
	}

	/**
	 * Adds the remote web component entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was added
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
		addRemoteWebComponentEntry(
			com.liferay.remote.web.component.model.RemoteWebComponentEntry
				remoteWebComponentEntry) {

		return getService().addRemoteWebComponentEntry(remoteWebComponentEntry);
	}

	/**
	 * Creates a new remote web component entry with the primary key. Does not add the remote web component entry to the database.
	 *
	 * @param remoteWebComponentEntryId the primary key for the new remote web component entry
	 * @return the new remote web component entry
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
		createRemoteWebComponentEntry(long remoteWebComponentEntryId) {

		return getService().createRemoteWebComponentEntry(
			remoteWebComponentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the remote web component entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry that was removed
	 * @throws PortalException if a remote web component entry with the primary key could not be found
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
			deleteRemoteWebComponentEntry(long remoteWebComponentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRemoteWebComponentEntry(
			remoteWebComponentEntryId);
	}

	/**
	 * Deletes the remote web component entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was removed
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
		deleteRemoteWebComponentEntry(
			com.liferay.remote.web.component.model.RemoteWebComponentEntry
				remoteWebComponentEntry) {

		return getService().deleteRemoteWebComponentEntry(
			remoteWebComponentEntry);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
		fetchRemoteWebComponentEntry(long remoteWebComponentEntryId) {

		return getService().fetchRemoteWebComponentEntry(
			remoteWebComponentEntryId);
	}

	/**
	 * Returns the remote web component entry with the matching UUID and company.
	 *
	 * @param uuid the remote web component entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
		fetchRemoteWebComponentEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchRemoteWebComponentEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the remote web component entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @return the range of remote web component entries
	 */
	public static java.util.List
		<com.liferay.remote.web.component.model.RemoteWebComponentEntry>
			getRemoteWebComponentEntries(int start, int end) {

		return getService().getRemoteWebComponentEntries(start, end);
	}

	/**
	 * Returns the number of remote web component entries.
	 *
	 * @return the number of remote web component entries
	 */
	public static int getRemoteWebComponentEntriesCount() {
		return getService().getRemoteWebComponentEntriesCount();
	}

	/**
	 * Returns the remote web component entry with the primary key.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry
	 * @throws PortalException if a remote web component entry with the primary key could not be found
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
			getRemoteWebComponentEntry(long remoteWebComponentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRemoteWebComponentEntry(
			remoteWebComponentEntryId);
	}

	/**
	 * Returns the remote web component entry with the matching UUID and company.
	 *
	 * @param uuid the remote web component entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web component entry
	 * @throws PortalException if a matching remote web component entry could not be found
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
			getRemoteWebComponentEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRemoteWebComponentEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static java.util.List
		<com.liferay.remote.web.component.model.RemoteWebComponentEntry>
				searchRemoteWebComponentEntries(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchRemoteWebComponentEntries(
			companyId, keywords, start, end, sort);
	}

	public static int searchRemoteWebComponentEntriesCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchRemoteWebComponentEntriesCount(
			companyId, keywords);
	}

	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
			updateRemoteWebComponentEntry(
				long remoteWebComponentEntryId,
				java.util.Map<java.util.Locale, String> nameMap, String url,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRemoteWebComponentEntry(
			remoteWebComponentEntryId, nameMap, url, serviceContext);
	}

	/**
	 * Updates the remote web component entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was updated
	 */
	public static com.liferay.remote.web.component.model.RemoteWebComponentEntry
		updateRemoteWebComponentEntry(
			com.liferay.remote.web.component.model.RemoteWebComponentEntry
				remoteWebComponentEntry) {

		return getService().updateRemoteWebComponentEntry(
			remoteWebComponentEntry);
	}

	public static RemoteWebComponentEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<RemoteWebComponentEntryLocalService,
		 RemoteWebComponentEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RemoteWebComponentEntryLocalService.class);

		ServiceTracker
			<RemoteWebComponentEntryLocalService,
			 RemoteWebComponentEntryLocalService> serviceTracker =
				new ServiceTracker
					<RemoteWebComponentEntryLocalService,
					 RemoteWebComponentEntryLocalService>(
						 bundle.getBundleContext(),
						 RemoteWebComponentEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}