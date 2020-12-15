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

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.model.Portlet;

@Component
public class SPAPortletFilterManager {

	@Activate
	protected void activate() {
		Dictionary<String, Object> componentProperties = new Hashtable<>();

		String[] portletNames = _portlets.stream().map(
			Portlet::getRootPortletId
		).distinct().toArray(
			String[]::new
		);

		componentProperties.put(
			"filter.lifecycles", Collections.singleton("RENDER_PHASE"));
		componentProperties.put("javax.portlet.name", portletNames);
		componentProperties.put("preinitialized.filter", "true");

		_portletFilterInstance = _portletFilterFactory.newInstance(
			componentProperties);
	}

	@Deactivate
	protected void deactivate() {
		_portletFilterInstance.dispose();

		_portletFilterInstance = null;
	}

	@Reference(target = "(component.factory=spa.portlet.filter)")
	private ComponentFactory _portletFilterFactory;

	@Reference(
		cardinality = MULTIPLE,
		policyOption = GREEDY
	)
	private List<Portlet> _portlets;

	private volatile ComponentInstance _portletFilterInstance;

}