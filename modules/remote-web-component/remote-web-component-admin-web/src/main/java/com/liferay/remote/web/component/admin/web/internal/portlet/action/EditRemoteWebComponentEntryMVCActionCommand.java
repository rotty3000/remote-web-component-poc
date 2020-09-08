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

package com.liferay.remote.web.component.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.web.component.admin.web.internal.RemoteWebComponentPortletRegistrar;
import com.liferay.remote.web.component.admin.web.internal.constants.RemoteWebComponentAdminPortletKeys;
import com.liferay.remote.web.component.exception.DuplicateRemoteWebComponentEntryURLException;
import com.liferay.remote.web.component.exception.NoSuchEntryException;
import com.liferay.remote.web.component.model.RemoteWebComponentEntry;
import com.liferay.remote.web.component.service.RemoteWebComponentEntryLocalService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteWebComponentAdminPortletKeys.REMOTE_WEB_COMPONENT_ADMIN,
		"mvc.command.name=/edit_remote_web_component_entry"
	},
	service = MVCActionCommand.class
)
public class EditRemoteWebComponentEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String url = ParamUtil.getString(actionRequest, "url");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			RemoteWebComponentEntry.class.getName(), actionRequest);

		try {
			if (cmd.equals(Constants.ADD)) {
				RemoteWebComponentEntry remoteAppEntry =
					_remoteWebComponentEntryLocalService.
						addRemoteWebComponentEntry(
							serviceContext.getUserId(), nameMap, url,
							serviceContext);

				_remoteWebComponentPortletRegistrar.registerPortlet(
					remoteAppEntry);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				long remoteAppEntryId = ParamUtil.getLong(
					actionRequest, "remoteWebComponentEntryId");

				RemoteWebComponentEntry remoteAppEntry =
					_remoteWebComponentEntryLocalService.
						updateRemoteWebComponentEntry(
							remoteAppEntryId, nameMap, url, serviceContext);

				_remoteWebComponentPortletRegistrar.unregisterPortlet(
					remoteAppEntry);

				_remoteWebComponentPortletRegistrar.registerPortlet(
					remoteAppEntry);
			}

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/admin/error.jsp");
			}
			else if (exception instanceof
						DuplicateRemoteWebComponentEntryURLException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private RemoteWebComponentEntryLocalService
		_remoteWebComponentEntryLocalService;

	@Reference
	private RemoteWebComponentPortletRegistrar
		_remoteWebComponentPortletRegistrar;

}