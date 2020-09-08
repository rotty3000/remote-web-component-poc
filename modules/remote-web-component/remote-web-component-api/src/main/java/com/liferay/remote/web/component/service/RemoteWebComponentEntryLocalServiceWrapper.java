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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RemoteWebComponentEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebComponentEntryLocalService
 * @generated
 */
public class RemoteWebComponentEntryLocalServiceWrapper
	implements RemoteWebComponentEntryLocalService,
			   ServiceWrapper<RemoteWebComponentEntryLocalService> {

	public RemoteWebComponentEntryLocalServiceWrapper(
		RemoteWebComponentEntryLocalService
			remoteWebComponentEntryLocalService) {

		_remoteWebComponentEntryLocalService =
			remoteWebComponentEntryLocalService;
	}

	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
			addRemoteWebComponentEntry(
				long userId, java.util.Map<java.util.Locale, String> nameMap,
				String url,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.addRemoteWebComponentEntry(
			userId, nameMap, url, serviceContext);
	}

	/**
	 * Adds the remote web component entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was added
	 */
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
		addRemoteWebComponentEntry(
			com.liferay.remote.web.component.model.RemoteWebComponentEntry
				remoteWebComponentEntry) {

		return _remoteWebComponentEntryLocalService.addRemoteWebComponentEntry(
			remoteWebComponentEntry);
	}

	/**
	 * Creates a new remote web component entry with the primary key. Does not add the remote web component entry to the database.
	 *
	 * @param remoteWebComponentEntryId the primary key for the new remote web component entry
	 * @return the new remote web component entry
	 */
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
		createRemoteWebComponentEntry(long remoteWebComponentEntryId) {

		return _remoteWebComponentEntryLocalService.
			createRemoteWebComponentEntry(remoteWebComponentEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the remote web component entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry that was removed
	 * @throws PortalException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
			deleteRemoteWebComponentEntry(long remoteWebComponentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.
			deleteRemoteWebComponentEntry(remoteWebComponentEntryId);
	}

	/**
	 * Deletes the remote web component entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was removed
	 */
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
		deleteRemoteWebComponentEntry(
			com.liferay.remote.web.component.model.RemoteWebComponentEntry
				remoteWebComponentEntry) {

		return _remoteWebComponentEntryLocalService.
			deleteRemoteWebComponentEntry(remoteWebComponentEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _remoteWebComponentEntryLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _remoteWebComponentEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _remoteWebComponentEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _remoteWebComponentEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _remoteWebComponentEntryLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _remoteWebComponentEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
		fetchRemoteWebComponentEntry(long remoteWebComponentEntryId) {

		return _remoteWebComponentEntryLocalService.
			fetchRemoteWebComponentEntry(remoteWebComponentEntryId);
	}

	/**
	 * Returns the remote web component entry with the matching UUID and company.
	 *
	 * @param uuid the remote web component entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
		fetchRemoteWebComponentEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return _remoteWebComponentEntryLocalService.
			fetchRemoteWebComponentEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _remoteWebComponentEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _remoteWebComponentEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _remoteWebComponentEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _remoteWebComponentEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.getPersistedModel(
			primaryKeyObj);
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
	@Override
	public java.util.List
		<com.liferay.remote.web.component.model.RemoteWebComponentEntry>
			getRemoteWebComponentEntries(int start, int end) {

		return _remoteWebComponentEntryLocalService.
			getRemoteWebComponentEntries(start, end);
	}

	/**
	 * Returns the number of remote web component entries.
	 *
	 * @return the number of remote web component entries
	 */
	@Override
	public int getRemoteWebComponentEntriesCount() {
		return _remoteWebComponentEntryLocalService.
			getRemoteWebComponentEntriesCount();
	}

	/**
	 * Returns the remote web component entry with the primary key.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry
	 * @throws PortalException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
			getRemoteWebComponentEntry(long remoteWebComponentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.getRemoteWebComponentEntry(
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
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
			getRemoteWebComponentEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.
			getRemoteWebComponentEntryByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public java.util.List
		<com.liferay.remote.web.component.model.RemoteWebComponentEntry>
				searchRemoteWebComponentEntries(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.
			searchRemoteWebComponentEntries(
				companyId, keywords, start, end, sort);
	}

	@Override
	public int searchRemoteWebComponentEntriesCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.
			searchRemoteWebComponentEntriesCount(companyId, keywords);
	}

	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
			updateRemoteWebComponentEntry(
				long remoteWebComponentEntryId,
				java.util.Map<java.util.Locale, String> nameMap, String url,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _remoteWebComponentEntryLocalService.
			updateRemoteWebComponentEntry(
				remoteWebComponentEntryId, nameMap, url, serviceContext);
	}

	/**
	 * Updates the remote web component entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 * @return the remote web component entry that was updated
	 */
	@Override
	public com.liferay.remote.web.component.model.RemoteWebComponentEntry
		updateRemoteWebComponentEntry(
			com.liferay.remote.web.component.model.RemoteWebComponentEntry
				remoteWebComponentEntry) {

		return _remoteWebComponentEntryLocalService.
			updateRemoteWebComponentEntry(remoteWebComponentEntry);
	}

	@Override
	public RemoteWebComponentEntryLocalService getWrappedService() {
		return _remoteWebComponentEntryLocalService;
	}

	@Override
	public void setWrappedService(
		RemoteWebComponentEntryLocalService
			remoteWebComponentEntryLocalService) {

		_remoteWebComponentEntryLocalService =
			remoteWebComponentEntryLocalService;
	}

	private RemoteWebComponentEntryLocalService
		_remoteWebComponentEntryLocalService;

}