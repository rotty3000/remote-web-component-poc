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

package com.liferay.remote.web.component.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.remote.web.component.exception.DuplicateRemoteWebComponentEntryURLException;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;
import com.liferay.remote.web.component.service.base.RemoteWebComponentEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @see RemoteWebComponentEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.remote.web.component.model.RemoteWebComponentEntry",
	service = AopService.class
)
public class RemoteWebComponentEntryLocalServiceImpl
	extends RemoteWebComponentEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteWebComponentEntry addRemoteWebComponentEntry(
			long userId, Map<Locale, String> nameMap, String url,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		validate(companyId, 0, url);

		long remoteWebComponentEntryId = counterLocalService.increment();

		RemoteWebComponentEntry remoteWebComponentEntry =
			remoteWebComponentEntryPersistence.create(
				remoteWebComponentEntryId);

		remoteWebComponentEntry.setUuid(serviceContext.getUuid());
		remoteWebComponentEntry.setCompanyId(companyId);
		remoteWebComponentEntry.setUserId(user.getUserId());
		remoteWebComponentEntry.setUserName(user.getFullName());
		remoteWebComponentEntry.setNameMap(nameMap);
		remoteWebComponentEntry.setUrl(url);

		return remoteWebComponentEntryPersistence.update(
			remoteWebComponentEntry);
	}

	@Override
	public List<RemoteWebComponentEntry> searchRemoteWebComponentEntries(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		return searchRemoteWebComponentEntries(searchContext);
	}

	@Override
	public int searchRemoteWebComponentEntriesCount(
			long companyId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return searchRemoteWebComponentEntriesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteWebComponentEntry updateRemoteWebComponentEntry(
			long remoteWebComponentEntryId, Map<Locale, String> nameMap,
			String url, ServiceContext serviceContext)
		throws PortalException {

		validate(serviceContext.getCompanyId(), remoteWebComponentEntryId, url);

		RemoteWebComponentEntry remoteWebComponentEntry =
			remoteWebComponentEntryPersistence.findByPrimaryKey(
				remoteWebComponentEntryId);

		remoteWebComponentEntry.setNameMap(nameMap);
		remoteWebComponentEntry.setUrl(url);

		return remoteWebComponentEntryPersistence.update(
			remoteWebComponentEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				Field.URL, keywords
			).build());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	protected List<RemoteWebComponentEntry> getRemoteWebComponentEntries(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<RemoteWebComponentEntry> remoteWebComponentEntries =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long remoteWebComponentEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			RemoteWebComponentEntry remoteWebComponentEntry =
				remoteWebComponentEntryPersistence.fetchByPrimaryKey(
					remoteWebComponentEntryId);

			if (remoteWebComponentEntry == null) {
				remoteWebComponentEntries = null;

				Indexer<RemoteWebComponentEntry> indexer =
					IndexerRegistryUtil.getIndexer(
						RemoteWebComponentEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				remoteWebComponentEntries.add(remoteWebComponentEntry);
			}
		}

		return remoteWebComponentEntries;
	}

	protected List<RemoteWebComponentEntry> searchRemoteWebComponentEntries(
			SearchContext searchContext)
		throws PortalException {

		Indexer<RemoteWebComponentEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				RemoteWebComponentEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<RemoteWebComponentEntry> remoteWebComponentEntries =
				getRemoteWebComponentEntries(hits);

			if (remoteWebComponentEntries != null) {
				return remoteWebComponentEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchRemoteWebComponentEntriesCount(
			SearchContext searchContext)
		throws PortalException {

		Indexer<RemoteWebComponentEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				RemoteWebComponentEntry.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected void validate(
			long companyId, long remoteWebComponentEntryId, String url)
		throws PortalException {

		RemoteWebComponentEntry remoteWebComponentEntry =
			remoteWebComponentEntryPersistence.fetchByC_U(
				companyId, StringUtil.trim(url));

		if ((remoteWebComponentEntry != null) &&
			(remoteWebComponentEntry.getRemoteWebComponentEntryId() !=
				remoteWebComponentEntryId)) {

			throw new DuplicateRemoteWebComponentEntryURLException(
				"{remoteWebComponentEntryId=" + remoteWebComponentEntryId +
					"}");
		}
	}

}