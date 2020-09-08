<%--
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
--%>

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

RemoteWebComponentEntry remoteWebComponentEntry = (RemoteWebComponentEntry)request.getAttribute(RemoteWebComponentAdminWebKeys.REMOTE_WEB_COMPONENT_ENTRY);

long remoteWebComponentEntryId = BeanParamUtil.getLong(remoteWebComponentEntry, request, "remoteWebComponentEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((remoteWebComponentEntry == null) ? LanguageUtil.get(request, "new-remote-web-component") : remoteWebComponentEntry.getName(locale));
%>

<portlet:actionURL name="/edit_remote_web_component_entry" var="editRemoteWebComponentEntryURL" />

<clay:container-fluid>
	<aui:form action="<%= editRemoteWebComponentEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveRemoteWebComponentEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="remoteWebComponentEntryId" type="hidden" value="<%= remoteWebComponentEntryId %>" />

		<liferay-ui:error exception="<%= DuplicateRemoteWebComponentEntryURLException.class %>" message="please-enter-a-unique-remote-web-component-url" />

		<aui:model-context bean="<%= remoteWebComponentEntry %>" model="<%= RemoteWebComponentEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper label="name">
					<liferay-ui:input-localized
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						name="name"
						xml='<%= BeanPropertiesUtil.getString(remoteWebComponentEntry, "name") %>'
					/>
				</aui:field-wrapper>

				<aui:input name="url">
					<aui:validator name="url" />
				</aui:input>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveRemoteWebComponentEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (remoteWebComponentEntry == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>