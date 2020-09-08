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

package com.liferay.remote.web.component.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.remote.web.component.exception.NoSuchEntryException;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the remote web component entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebComponentEntryUtil
 * @generated
 */
@ProviderType
public interface RemoteWebComponentEntryPersistence
	extends BasePersistence<RemoteWebComponentEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RemoteWebComponentEntryUtil} to access the remote web component entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the remote web component entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the remote web component entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @return the range of matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the remote web component entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the remote web component entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator);

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator);

	/**
	 * Returns the remote web component entries before and after the current remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param remoteWebComponentEntryId the primary key of the current remote web component entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web component entry
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	public RemoteWebComponentEntry[] findByUuid_PrevAndNext(
			long remoteWebComponentEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the remote web component entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of remote web component entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote web component entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the remote web component entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the remote web component entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @return the range of matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the remote web component entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the remote web component entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator);

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator);

	/**
	 * Returns the remote web component entries before and after the current remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param remoteWebComponentEntryId the primary key of the current remote web component entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web component entry
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	public RemoteWebComponentEntry[] findByUuid_C_PrevAndNext(
			long remoteWebComponentEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Removes all the remote web component entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of remote web component entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote web component entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the remote web component entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry findByC_U(long companyId, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the remote web component entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry fetchByC_U(long companyId, String url);

	/**
	 * Returns the remote web component entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	public RemoteWebComponentEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache);

	/**
	 * Removes the remote web component entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote web component entry that was removed
	 */
	public RemoteWebComponentEntry removeByC_U(long companyId, String url)
		throws NoSuchEntryException;

	/**
	 * Returns the number of remote web component entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote web component entries
	 */
	public int countByC_U(long companyId, String url);

	/**
	 * Caches the remote web component entry in the entity cache if it is enabled.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 */
	public void cacheResult(RemoteWebComponentEntry remoteWebComponentEntry);

	/**
	 * Caches the remote web component entries in the entity cache if it is enabled.
	 *
	 * @param remoteWebComponentEntries the remote web component entries
	 */
	public void cacheResult(
		java.util.List<RemoteWebComponentEntry> remoteWebComponentEntries);

	/**
	 * Creates a new remote web component entry with the primary key. Does not add the remote web component entry to the database.
	 *
	 * @param remoteWebComponentEntryId the primary key for the new remote web component entry
	 * @return the new remote web component entry
	 */
	public RemoteWebComponentEntry create(long remoteWebComponentEntryId);

	/**
	 * Removes the remote web component entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry that was removed
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	public RemoteWebComponentEntry remove(long remoteWebComponentEntryId)
		throws NoSuchEntryException;

	public RemoteWebComponentEntry updateImpl(
		RemoteWebComponentEntry remoteWebComponentEntry);

	/**
	 * Returns the remote web component entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	public RemoteWebComponentEntry findByPrimaryKey(
			long remoteWebComponentEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the remote web component entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry, or <code>null</code> if a remote web component entry with the primary key could not be found
	 */
	public RemoteWebComponentEntry fetchByPrimaryKey(
		long remoteWebComponentEntryId);

	/**
	 * Returns all the remote web component entries.
	 *
	 * @return the remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findAll();

	/**
	 * Returns a range of all the remote web component entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @return the range of remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the remote web component entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the remote web component entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteWebComponentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote web component entries
	 * @param end the upper bound of the range of remote web component entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote web component entries
	 */
	public java.util.List<RemoteWebComponentEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<RemoteWebComponentEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the remote web component entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of remote web component entries.
	 *
	 * @return the number of remote web component entries
	 */
	public int countAll();

}