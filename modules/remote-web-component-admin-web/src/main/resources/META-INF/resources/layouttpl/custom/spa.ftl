<div class="columns-spa" id="main-content" role="main">
	<div class="portlet-layout row">
		<div class="col-md-12 portlet-column portlet-column-only" id="column-1">
			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
		</div>
	</div>

	<div class="portlet-layout row">
		<#if themeDisplay??>
			<#assign permissionChecker = (themeDisplay.getPermissionChecker())!>
			<#assign layout = (themeDisplay.getLayout())!>
			<#if permissionChecker?? && layout??>
				<#if (layoutPermission.contains(permissionChecker, layout, "UPDATE"))!>
					<#assign locale = themeDisplay.getLocale()>

					<div>
						<@liferay.language key="place-portlets-to-be-loaded-via-SPA-routing-bellow-this-line" />
					</div>
					<hr />
				</#if>
			</#if>
		</#if>
		<div class="col-md-12 portlet-column portlet-column-only" id="column-2">
			${processor.processColumn("column-2", "portlet-column-content portlet-column-content-only")}
		</div>
	</div>
</div>