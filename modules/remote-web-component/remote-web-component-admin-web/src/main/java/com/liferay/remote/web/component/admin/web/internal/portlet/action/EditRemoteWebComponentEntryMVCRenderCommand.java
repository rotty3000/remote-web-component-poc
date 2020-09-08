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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.remote.web.component.admin.web.internal.constants.RemoteWebComponentAdminPortletKeys;
import com.liferay.remote.web.component.admin.web.internal.constants.RemoteWebComponentAdminWebKeys;
import com.liferay.remote.web.component.admin.web.internal.display.context.RemoteWebComponentAdminDisplayContext;
import com.liferay.remote.web.component.exception.NoSuchEntryException;
import com.liferay.remote.web.component.service.RemoteWebComponentEntryLocalService;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteWebComponentAdminPortletKeys.REMOTE_WEB_COMPONENT_ADMIN,
		"mvc.command.name=/edit_remote_web_component_entry"
	},
	service = MVCRenderCommand.class
)
public class EditRemoteWebComponentEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			RemoteWebComponentAdminWebKeys.
				REMOTE_WEB_COMPONENT_ADMIN_DISPLAY_CONTEXT,
			new RemoteWebComponentAdminDisplayContext(
				renderRequest, renderResponse,
				_remoteWebComponentEntryLocalService));

		try {
			long remoteAppEntryId = ParamUtil.getLong(
				renderRequest, "remoteWebComponentEntryId");

			if (remoteAppEntryId > 0) {
				renderRequest.setAttribute(
					RemoteWebComponentAdminWebKeys.REMOTE_WEB_COMPONENT_ENTRY,
					_remoteWebComponentEntryLocalService.
						getRemoteWebComponentEntry(remoteAppEntryId));
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchEntryException) {
				SessionErrors.add(renderRequest, exception.getClass());

				return "/admin/error.jsp";
			}

			throw new PortletException(exception);
		}

		return "/admin/edit_remote_web_component_entry.jsp";
	}

	@Reference
	private RemoteWebComponentEntryLocalService
		_remoteWebComponentEntryLocalService;

}