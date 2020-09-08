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

package com.liferay.remote.web.component.internal.search;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;
import com.liferay.remote.web.component.service.RemoteWebComponentEntryLocalService;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = Indexer.class)
public class RemoteWebComponentEntryIndexer
	extends BaseIndexer<RemoteWebComponentEntry> {

	public static final String CLASS_NAME =
		RemoteWebComponentEntry.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, Field.ENTRY_CLASS_PK, false);
		addSearchTerm(searchQuery, searchContext, Field.NAME, true);
		addSearchTerm(searchQuery, searchContext, Field.URL, true);
	}

	@Override
	protected void doDelete(RemoteWebComponentEntry remoteWebComponentEntry)
		throws Exception {

		deleteDocument(
			remoteWebComponentEntry.getCompanyId(),
			remoteWebComponentEntry.getRemoteWebComponentEntryId());
	}

	@Override
	protected Document doGetDocument(
			RemoteWebComponentEntry remoteWebComponentEntry)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing remote app entry " + remoteWebComponentEntry);
		}

		Document document = getBaseModelDocument(
			CLASS_NAME, remoteWebComponentEntry);

		Localization localization = getLocalization();

		String[] nameAvailableLanguageIds =
			localization.getAvailableLanguageIds(
				remoteWebComponentEntry.getName());

		String nameDefaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			remoteWebComponentEntry.getName());

		for (String nameAvailableLanguageId : nameAvailableLanguageIds) {
			String name = remoteWebComponentEntry.getName(
				nameAvailableLanguageId);

			if (nameDefaultLanguageId.equals(nameAvailableLanguageId)) {
				document.addText(Field.NAME, name);
			}

			document.addText(
				localization.getLocalizedName(
					Field.NAME, nameAvailableLanguageId),
				name);
		}

		document.addText(Field.URL, remoteWebComponentEntry.getUrl());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + remoteWebComponentEntry +
					" indexed successfully");
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Summary summary = createSummary(document, Field.NAME, Field.URL);

		summary.setMaxContentLength(200);

		return summary;
	}

	@Override
	protected void doReindex(RemoteWebComponentEntry remoteWebComponentEntry)
		throws Exception {

		_indexWriterHelper.updateDocument(
			getSearchEngineId(), remoteWebComponentEntry.getCompanyId(),
			getDocument(remoteWebComponentEntry), isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		doReindex(
			_remoteWebComponentEntryLocalService.getRemoteWebComponentEntry(
				classPK));
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexRemoteWebComponentEntries(companyId);
	}

	protected Localization getLocalization() {

		// See LPS-72507

		if (_localization != null) {
			return _localization;
		}

		return LocalizationUtil.getLocalization();
	}

	protected void reindexRemoteWebComponentEntries(long companyId)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_remoteWebComponentEntryLocalService.
				getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(RemoteWebComponentEntry remoteWebComponentEntry) -> {
				try {
					indexableActionableDynamicQuery.addDocuments(
						getDocument(remoteWebComponentEntry));
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						long remoteWebComponentEntryId =
							remoteWebComponentEntry.
								getRemoteWebComponentEntryId();

						_log.warn(
							"Unable to index remote app entry " +
								remoteWebComponentEntryId,
							portalException);
					}
				}
			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebComponentEntryIndexer.class);

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	private Localization _localization;

	@Reference
	private RemoteWebComponentEntryLocalService
		_remoteWebComponentEntryLocalService;

}