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

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"name",
								"elementName",
								"webComponentUrl",
								"webComponentCssUrl"
							}
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 6,
							value = {
								"instanceable"
							}
						),
						@DDMFormLayoutColumn(
							size = 6,
							value = {
								"portletDisplayCategory"
							}
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"routes",
								"portletServiceProperties"
							}
						)
					}
				)
			}
		)
	}
)
@ExtendedObjectClassDefinition(
	category = "widget-tools",
	factoryInstanceLabelAttribute = "elementName"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration",
	localization = "content/Language", name = "remote-web-component-configuration-name"
)
public interface RemoteWebComponentConfiguration {

	@Meta.AD(
		name = "name",
		description = "name-description"
	)
	@DDMFormField(
		name = "%name",
		tip = "%name-description",
		type = "text",
		required = true
	)
	public String name();

	@Meta.AD(
		name = "element-name",
		description = "element-name-description"
	)
	@DDMFormField(
		label = "%element-name",
		tip = "%element-name-description",
		required = true
	)
	public String elementName();

	@Meta.AD(
		name = "web-component-url",
		description = "web-component-url-description"
	)
	@DDMFormField(
		label = "%web-component-url",
		tip = "%web-component-url-description",
		required = true
	)
	public String webComponentUrl();

	@Meta.AD(
		name = "web-component-css-url",
		description = "web-component-css-url-description",
		required = false
	)
	@DDMFormField(
		label = "%web-component-css-url",
		tip = "%web-component-css-url-description"
	)
	public String webComponentCssUrl();

	@Meta.AD(
		name = "instanceable",
		description = "instanceable-desciption",
		deflt = "false",
		required = false
	)
	public boolean instanceable();

	@Meta.AD(
		name = "portlet-display-category",
		description = "portlet-display-category-desciption",
		deflt = "sample",
		required = false
	)
	@DDMFormField(
		label = "%portlet-display-category",
		tip = "%portlet-display-category-desciption",
		predefinedValue = "sample"
	)
	public String portletDisplayCategory();

	@Meta.AD(
		name = "portlet-service-properties",
		description = "portlet-service-properties-description",
		deflt = "",
		required = false
	)
	public String portletServiceProperties();

	@Meta.AD(
		name = "routes",
		description = "routes-description",
		deflt = "",
		required = false
	)
	@DDMFormField(
		label = "%routes",
		tip = "%routes-description",
		predefinedValue = ""
	)
	public String[] routes();

}
