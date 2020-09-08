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

package com.liferay.remote.web.component.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.remote.web.component.exception.NoSuchEntryException;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;
import com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryImpl;
import com.liferay.remote.web.component.model.impl.RemoteWebComponentEntryModelImpl;
import com.liferay.remote.web.component.service.persistence.RemoteWebComponentEntryPersistence;
import com.liferay.remote.web.component.service.persistence.impl.constants.RemoteWebComponentPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the remote web component entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = RemoteWebComponentEntryPersistence.class)
public class RemoteWebComponentEntryPersistenceImpl
	extends BasePersistenceImpl<RemoteWebComponentEntry>
	implements RemoteWebComponentEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RemoteWebComponentEntryUtil</code> to access the remote web component entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RemoteWebComponentEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the remote web component entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching remote web component entries
	 */
	@Override
	public List<RemoteWebComponentEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<RemoteWebComponentEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteWebComponentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RemoteWebComponentEntry remoteWebComponentEntry : list) {
					if (!uuid.equals(remoteWebComponentEntry.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_REMOTEWEBCOMPONENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(RemoteWebComponentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				list = (List<RemoteWebComponentEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry findByUuid_First(
			String uuid,
			OrderByComparator<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebComponentEntry remoteWebComponentEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (remoteWebComponentEntry != null) {
			return remoteWebComponentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator) {

		List<RemoteWebComponentEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry findByUuid_Last(
			String uuid,
			OrderByComparator<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebComponentEntry remoteWebComponentEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (remoteWebComponentEntry != null) {
			return remoteWebComponentEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<RemoteWebComponentEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the remote web component entries before and after the current remote web component entry in the ordered set where uuid = &#63;.
	 *
	 * @param remoteWebComponentEntryId the primary key of the current remote web component entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote web component entry
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public RemoteWebComponentEntry[] findByUuid_PrevAndNext(
			long remoteWebComponentEntryId, String uuid,
			OrderByComparator<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RemoteWebComponentEntry remoteWebComponentEntry = findByPrimaryKey(
			remoteWebComponentEntryId);

		Session session = null;

		try {
			session = openSession();

			RemoteWebComponentEntry[] array =
				new RemoteWebComponentEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, remoteWebComponentEntry, uuid, orderByComparator,
				true);

			array[1] = remoteWebComponentEntry;

			array[2] = getByUuid_PrevAndNext(
				session, remoteWebComponentEntry, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RemoteWebComponentEntry getByUuid_PrevAndNext(
		Session session, RemoteWebComponentEntry remoteWebComponentEntry,
		String uuid,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_REMOTEWEBCOMPONENTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RemoteWebComponentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						remoteWebComponentEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<RemoteWebComponentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the remote web component entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (RemoteWebComponentEntry remoteWebComponentEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(remoteWebComponentEntry);
		}
	}

	/**
	 * Returns the number of remote web component entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching remote web component entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_REMOTEWEBCOMPONENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"remoteWebComponentEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(remoteWebComponentEntry.uuid IS NULL OR remoteWebComponentEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the remote web component entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching remote web component entries
	 */
	@Override
	public List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<RemoteWebComponentEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteWebComponentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RemoteWebComponentEntry remoteWebComponentEntry : list) {
					if (!uuid.equals(remoteWebComponentEntry.getUuid()) ||
						(companyId != remoteWebComponentEntry.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_REMOTEWEBCOMPONENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(RemoteWebComponentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<RemoteWebComponentEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebComponentEntry remoteWebComponentEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (remoteWebComponentEntry != null) {
			return remoteWebComponentEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator) {

		List<RemoteWebComponentEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException {

		RemoteWebComponentEntry remoteWebComponentEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (remoteWebComponentEntry != null) {
			return remoteWebComponentEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last remote web component entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<RemoteWebComponentEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public RemoteWebComponentEntry[] findByUuid_C_PrevAndNext(
			long remoteWebComponentEntryId, String uuid, long companyId,
			OrderByComparator<RemoteWebComponentEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RemoteWebComponentEntry remoteWebComponentEntry = findByPrimaryKey(
			remoteWebComponentEntryId);

		Session session = null;

		try {
			session = openSession();

			RemoteWebComponentEntry[] array =
				new RemoteWebComponentEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, remoteWebComponentEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = remoteWebComponentEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, remoteWebComponentEntry, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected RemoteWebComponentEntry getByUuid_C_PrevAndNext(
		Session session, RemoteWebComponentEntry remoteWebComponentEntry,
		String uuid, long companyId,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_REMOTEWEBCOMPONENTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(RemoteWebComponentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						remoteWebComponentEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<RemoteWebComponentEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the remote web component entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (RemoteWebComponentEntry remoteWebComponentEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(remoteWebComponentEntry);
		}
	}

	/**
	 * Returns the number of remote web component entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching remote web component entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REMOTEWEBCOMPONENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"remoteWebComponentEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(remoteWebComponentEntry.uuid IS NULL OR remoteWebComponentEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"remoteWebComponentEntry.companyId = ?";

	private FinderPath _finderPathFetchByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns the remote web component entry where companyId = &#63; and url = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web component entry
	 * @throws NoSuchEntryException if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry findByC_U(long companyId, String url)
		throws NoSuchEntryException {

		RemoteWebComponentEntry remoteWebComponentEntry = fetchByC_U(
			companyId, url);

		if (remoteWebComponentEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", url=");
			msg.append(url);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return remoteWebComponentEntry;
	}

	/**
	 * Returns the remote web component entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry fetchByC_U(long companyId, String url) {
		return fetchByC_U(companyId, url, true);
	}

	/**
	 * Returns the remote web component entry where companyId = &#63; and url = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote web component entry, or <code>null</code> if a matching remote web component entry could not be found
	 */
	@Override
	public RemoteWebComponentEntry fetchByC_U(
		long companyId, String url, boolean useFinderCache) {

		url = Objects.toString(url, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, url};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_U, finderArgs, this);
		}

		if (result instanceof RemoteWebComponentEntry) {
			RemoteWebComponentEntry remoteWebComponentEntry =
				(RemoteWebComponentEntry)result;

			if ((companyId != remoteWebComponentEntry.getCompanyId()) ||
				!Objects.equals(url, remoteWebComponentEntry.getUrl())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_REMOTEWEBCOMPONENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			boolean bindUrl = false;

			if (url.isEmpty()) {
				query.append(_FINDER_COLUMN_C_U_URL_3);
			}
			else {
				bindUrl = true;

				query.append(_FINDER_COLUMN_C_U_URL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindUrl) {
					qPos.add(url);
				}

				List<RemoteWebComponentEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_U, finderArgs, list);
					}
				}
				else {
					RemoteWebComponentEntry remoteWebComponentEntry = list.get(
						0);

					result = remoteWebComponentEntry;

					cacheResult(remoteWebComponentEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_U, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (RemoteWebComponentEntry)result;
		}
	}

	/**
	 * Removes the remote web component entry where companyId = &#63; and url = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the remote web component entry that was removed
	 */
	@Override
	public RemoteWebComponentEntry removeByC_U(long companyId, String url)
		throws NoSuchEntryException {

		RemoteWebComponentEntry remoteWebComponentEntry = findByC_U(
			companyId, url);

		return remove(remoteWebComponentEntry);
	}

	/**
	 * Returns the number of remote web component entries where companyId = &#63; and url = &#63;.
	 *
	 * @param companyId the company ID
	 * @param url the url
	 * @return the number of matching remote web component entries
	 */
	@Override
	public int countByC_U(long companyId, String url) {
		url = Objects.toString(url, "");

		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, url};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_REMOTEWEBCOMPONENTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			boolean bindUrl = false;

			if (url.isEmpty()) {
				query.append(_FINDER_COLUMN_C_U_URL_3);
			}
			else {
				bindUrl = true;

				query.append(_FINDER_COLUMN_C_U_URL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindUrl) {
					qPos.add(url);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"remoteWebComponentEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_URL_2 =
		"remoteWebComponentEntry.url = ?";

	private static final String _FINDER_COLUMN_C_U_URL_3 =
		"(remoteWebComponentEntry.url IS NULL OR remoteWebComponentEntry.url = '')";

	public RemoteWebComponentEntryPersistenceImpl() {
		setModelClass(RemoteWebComponentEntry.class);

		setModelImplClass(RemoteWebComponentEntryImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the remote web component entry in the entity cache if it is enabled.
	 *
	 * @param remoteWebComponentEntry the remote web component entry
	 */
	@Override
	public void cacheResult(RemoteWebComponentEntry remoteWebComponentEntry) {
		entityCache.putResult(
			entityCacheEnabled, RemoteWebComponentEntryImpl.class,
			remoteWebComponentEntry.getPrimaryKey(), remoteWebComponentEntry);

		finderCache.putResult(
			_finderPathFetchByC_U,
			new Object[] {
				remoteWebComponentEntry.getCompanyId(),
				remoteWebComponentEntry.getUrl()
			},
			remoteWebComponentEntry);

		remoteWebComponentEntry.resetOriginalValues();
	}

	/**
	 * Caches the remote web component entries in the entity cache if it is enabled.
	 *
	 * @param remoteWebComponentEntries the remote web component entries
	 */
	@Override
	public void cacheResult(
		List<RemoteWebComponentEntry> remoteWebComponentEntries) {

		for (RemoteWebComponentEntry remoteWebComponentEntry :
				remoteWebComponentEntries) {

			if (entityCache.getResult(
					entityCacheEnabled, RemoteWebComponentEntryImpl.class,
					remoteWebComponentEntry.getPrimaryKey()) == null) {

				cacheResult(remoteWebComponentEntry);
			}
			else {
				remoteWebComponentEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all remote web component entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RemoteWebComponentEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the remote web component entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RemoteWebComponentEntry remoteWebComponentEntry) {
		entityCache.removeResult(
			entityCacheEnabled, RemoteWebComponentEntryImpl.class,
			remoteWebComponentEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(RemoteWebComponentEntryModelImpl)remoteWebComponentEntry, true);
	}

	@Override
	public void clearCache(
		List<RemoteWebComponentEntry> remoteWebComponentEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RemoteWebComponentEntry remoteWebComponentEntry :
				remoteWebComponentEntries) {

			entityCache.removeResult(
				entityCacheEnabled, RemoteWebComponentEntryImpl.class,
				remoteWebComponentEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(RemoteWebComponentEntryModelImpl)remoteWebComponentEntry,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, RemoteWebComponentEntryImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RemoteWebComponentEntryModelImpl remoteWebComponentEntryModelImpl) {

		Object[] args = new Object[] {
			remoteWebComponentEntryModelImpl.getCompanyId(),
			remoteWebComponentEntryModelImpl.getUrl()
		};

		finderCache.putResult(
			_finderPathCountByC_U, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_U, args, remoteWebComponentEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		RemoteWebComponentEntryModelImpl remoteWebComponentEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				remoteWebComponentEntryModelImpl.getCompanyId(),
				remoteWebComponentEntryModelImpl.getUrl()
			};

			finderCache.removeResult(_finderPathCountByC_U, args);
			finderCache.removeResult(_finderPathFetchByC_U, args);
		}

		if ((remoteWebComponentEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_U.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				remoteWebComponentEntryModelImpl.getOriginalCompanyId(),
				remoteWebComponentEntryModelImpl.getOriginalUrl()
			};

			finderCache.removeResult(_finderPathCountByC_U, args);
			finderCache.removeResult(_finderPathFetchByC_U, args);
		}
	}

	/**
	 * Creates a new remote web component entry with the primary key. Does not add the remote web component entry to the database.
	 *
	 * @param remoteWebComponentEntryId the primary key for the new remote web component entry
	 * @return the new remote web component entry
	 */
	@Override
	public RemoteWebComponentEntry create(long remoteWebComponentEntryId) {
		RemoteWebComponentEntry remoteWebComponentEntry =
			new RemoteWebComponentEntryImpl();

		remoteWebComponentEntry.setNew(true);
		remoteWebComponentEntry.setPrimaryKey(remoteWebComponentEntryId);

		String uuid = PortalUUIDUtil.generate();

		remoteWebComponentEntry.setUuid(uuid);

		remoteWebComponentEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return remoteWebComponentEntry;
	}

	/**
	 * Removes the remote web component entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry that was removed
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public RemoteWebComponentEntry remove(long remoteWebComponentEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)remoteWebComponentEntryId);
	}

	/**
	 * Removes the remote web component entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the remote web component entry
	 * @return the remote web component entry that was removed
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public RemoteWebComponentEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			RemoteWebComponentEntry remoteWebComponentEntry =
				(RemoteWebComponentEntry)session.get(
					RemoteWebComponentEntryImpl.class, primaryKey);

			if (remoteWebComponentEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(remoteWebComponentEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected RemoteWebComponentEntry removeImpl(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(remoteWebComponentEntry)) {
				remoteWebComponentEntry = (RemoteWebComponentEntry)session.get(
					RemoteWebComponentEntryImpl.class,
					remoteWebComponentEntry.getPrimaryKeyObj());
			}

			if (remoteWebComponentEntry != null) {
				session.delete(remoteWebComponentEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (remoteWebComponentEntry != null) {
			clearCache(remoteWebComponentEntry);
		}

		return remoteWebComponentEntry;
	}

	@Override
	public RemoteWebComponentEntry updateImpl(
		RemoteWebComponentEntry remoteWebComponentEntry) {

		boolean isNew = remoteWebComponentEntry.isNew();

		if (!(remoteWebComponentEntry instanceof
				RemoteWebComponentEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(remoteWebComponentEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					remoteWebComponentEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in remoteWebComponentEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RemoteWebComponentEntry implementation " +
					remoteWebComponentEntry.getClass());
		}

		RemoteWebComponentEntryModelImpl remoteWebComponentEntryModelImpl =
			(RemoteWebComponentEntryModelImpl)remoteWebComponentEntry;

		if (Validator.isNull(remoteWebComponentEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			remoteWebComponentEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (remoteWebComponentEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				remoteWebComponentEntry.setCreateDate(now);
			}
			else {
				remoteWebComponentEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!remoteWebComponentEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				remoteWebComponentEntry.setModifiedDate(now);
			}
			else {
				remoteWebComponentEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (remoteWebComponentEntry.isNew()) {
				session.save(remoteWebComponentEntry);

				remoteWebComponentEntry.setNew(false);
			}
			else {
				remoteWebComponentEntry =
					(RemoteWebComponentEntry)session.merge(
						remoteWebComponentEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				remoteWebComponentEntryModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				remoteWebComponentEntryModelImpl.getUuid(),
				remoteWebComponentEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((remoteWebComponentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					remoteWebComponentEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					remoteWebComponentEntryModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((remoteWebComponentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					remoteWebComponentEntryModelImpl.getOriginalUuid(),
					remoteWebComponentEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					remoteWebComponentEntryModelImpl.getUuid(),
					remoteWebComponentEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, RemoteWebComponentEntryImpl.class,
			remoteWebComponentEntry.getPrimaryKey(), remoteWebComponentEntry,
			false);

		clearUniqueFindersCache(remoteWebComponentEntryModelImpl, false);
		cacheUniqueFindersCache(remoteWebComponentEntryModelImpl);

		remoteWebComponentEntry.resetOriginalValues();

		return remoteWebComponentEntry;
	}

	/**
	 * Returns the remote web component entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the remote web component entry
	 * @return the remote web component entry
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public RemoteWebComponentEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		RemoteWebComponentEntry remoteWebComponentEntry = fetchByPrimaryKey(
			primaryKey);

		if (remoteWebComponentEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return remoteWebComponentEntry;
	}

	/**
	 * Returns the remote web component entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry
	 * @throws NoSuchEntryException if a remote web component entry with the primary key could not be found
	 */
	@Override
	public RemoteWebComponentEntry findByPrimaryKey(
			long remoteWebComponentEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)remoteWebComponentEntryId);
	}

	/**
	 * Returns the remote web component entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteWebComponentEntryId the primary key of the remote web component entry
	 * @return the remote web component entry, or <code>null</code> if a remote web component entry with the primary key could not be found
	 */
	@Override
	public RemoteWebComponentEntry fetchByPrimaryKey(
		long remoteWebComponentEntryId) {

		return fetchByPrimaryKey((Serializable)remoteWebComponentEntryId);
	}

	/**
	 * Returns all the remote web component entries.
	 *
	 * @return the remote web component entries
	 */
	@Override
	public List<RemoteWebComponentEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

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
	@Override
	public List<RemoteWebComponentEntry> findAll(
		int start, int end,
		OrderByComparator<RemoteWebComponentEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<RemoteWebComponentEntry> list = null;

		if (useFinderCache) {
			list = (List<RemoteWebComponentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_REMOTEWEBCOMPONENTENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_REMOTEWEBCOMPONENTENTRY;

				sql = sql.concat(
					RemoteWebComponentEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<RemoteWebComponentEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the remote web component entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RemoteWebComponentEntry remoteWebComponentEntry : findAll()) {
			remove(remoteWebComponentEntry);
		}
	}

	/**
	 * Returns the number of remote web component entries.
	 *
	 * @return the number of remote web component entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_REMOTEWEBCOMPONENTENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "remoteWebComponentEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REMOTEWEBCOMPONENTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RemoteWebComponentEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the remote web component entry persistence.
	 */
	@Activate
	public void activate() {
		RemoteWebComponentEntryModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		RemoteWebComponentEntryModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			RemoteWebComponentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			RemoteWebComponentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			RemoteWebComponentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			RemoteWebComponentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			RemoteWebComponentEntryModelImpl.UUID_COLUMN_BITMASK |
			RemoteWebComponentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			RemoteWebComponentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			RemoteWebComponentEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			RemoteWebComponentEntryModelImpl.UUID_COLUMN_BITMASK |
			RemoteWebComponentEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			RemoteWebComponentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByC_U = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			RemoteWebComponentEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_U",
			new String[] {Long.class.getName(), String.class.getName()},
			RemoteWebComponentEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			RemoteWebComponentEntryModelImpl.URL_COLUMN_BITMASK);

		_finderPathCountByC_U = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(RemoteWebComponentEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = RemoteWebComponentPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.remote.web.component.model.RemoteWebComponentEntry"),
			true);
	}

	@Override
	@Reference(
		target = RemoteWebComponentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RemoteWebComponentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REMOTEWEBCOMPONENTENTRY =
		"SELECT remoteWebComponentEntry FROM RemoteWebComponentEntry remoteWebComponentEntry";

	private static final String _SQL_SELECT_REMOTEWEBCOMPONENTENTRY_WHERE =
		"SELECT remoteWebComponentEntry FROM RemoteWebComponentEntry remoteWebComponentEntry WHERE ";

	private static final String _SQL_COUNT_REMOTEWEBCOMPONENTENTRY =
		"SELECT COUNT(remoteWebComponentEntry) FROM RemoteWebComponentEntry remoteWebComponentEntry";

	private static final String _SQL_COUNT_REMOTEWEBCOMPONENTENTRY_WHERE =
		"SELECT COUNT(remoteWebComponentEntry) FROM RemoteWebComponentEntry remoteWebComponentEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"remoteWebComponentEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RemoteWebComponentEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RemoteWebComponentEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebComponentEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(
				RemoteWebComponentPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}