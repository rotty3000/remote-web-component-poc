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

package com.liferay.remote.web.component.admin.web.internal.spa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;
import javax.portlet.WindowState;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.portlet.Router;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

@Component(
	factory = "spa.portlet.filter",
	service = PortletFilter.class
)
public class SPAPortletFilter implements RenderFilter {

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
		// unused
	}

	@Override
	public void destroy() {
		// unused
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse response, FilterChain chain)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			LayoutTypePortlet layoutTypePortlet = themeDisplay.getLayoutTypePortlet();

			if ((layoutTypePortlet != null) && "spa".equals(layoutTypePortlet.getLayoutTemplateId())) {
				LiferayPortletRequest liferayPortletRequest = (LiferayPortletRequest)renderRequest;

				List<Portlet> columnPortlets = layoutTypePortlet.getAllPortlets("column-2");

				Portlet portlet = liferayPortletRequest.getPortlet();

				if (columnPortlets.stream().anyMatch(portlet::equals)) {
					if (WindowState.NORMAL == renderRequest.getWindowState()) {
						try {
							if (_layoutPermission.contains(
									PermissionThreadLocal.getPermissionChecker(),
									layoutTypePortlet.getLayout(), ActionKeys.UPDATE)) {

								String layoutFriendlyURL = _portal.getLayoutRelativeURL(themeDisplay.getLayout(), themeDisplay);

								PrintWriter writer = response.getWriter();

								FriendlyURLMapper friendlyURLMapper = portlet.getFriendlyURLMapperInstance();

								RenderURL renderURL = response.createRenderURL();
								renderURL.setWindowState(WindowState.MAXIMIZED);
								renderURL.setPortletMode(PortletMode.VIEW);

								String portletURL;

								if (friendlyURLMapper != null) {
									portletURL = friendlyURLMapper.buildPath((LiferayPortletURL)renderURL);

									if (friendlyURLMapper.isCheckMappingWithPrefix()) {
										portletURL = "/-" + portletURL;
									}

									portletURL = layoutFriendlyURL + portletURL;
								}
								else {
									portletURL = renderURL.toString();
								}

								writer.append("Standard Routes:<ul>");
								writer.append("<li><a href=\"");
								writer.append(portletURL);
								writer.append("\">");
								writer.append(portletURL);
								writer.append("</a></li>");
								writer.append("</ul>");

								if (friendlyURLMapper != null) {
									writer.append("Custom Routes:<ul>");

									friendlyURLMapper.getRouter().getRoutes().stream().forEach(route -> {
										writer.append("<li><a href=\"");

										StringBuilder sb = new StringBuilder();

										sb.append(layoutFriendlyURL);
										sb.append("/-/");
										sb.append(friendlyURLMapper.getMapping());
										sb.append(route.getPattern());

										writer.append(sb.toString());
										writer.append("\">");
										writer.append(sb.toString());
										writer.append("</a></li>");
									});

									writer.append("</ul>");
								}

								writer.flush();
							}
						}
						catch (Exception exception) {
							if (_log.isErrorEnabled()) {
								_log.error(
									"Failure rendering alternate SPA view for {}",
									portlet, exception);
							}
						}

						return;
					}
				}
			}
		}

		chain.doFilter(renderRequest, response);
	}

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private Portal _portal;

	private static final Logger _log = LoggerFactory.getLogger(
		SPAPortletFilter.class);

}
