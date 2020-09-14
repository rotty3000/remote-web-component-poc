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

package com.liferay.remote.web.component.admin.web.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

@ExtendedObjectClassDefinition(
	category = "web-api",
	factoryInstanceLabelAttribute = "elementName"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration",
	localization = "content/Language", name = "remote-web-component-configuration-name"
)
public interface RemoteWebComponentConfiguration {

	@Meta.AD
	public String name();

	@Meta.AD(name = "web-component-url")
	public String webComponentUrl();

	@Meta.AD(name = "element-name")
	public String elementName();

}