<script type="text/javascript">
Liferay.on(
	'allPortletsReady',
	e => {
		StateManager.GlobalStore.Get().DispatchAction(
			'com.liferay.portlet.navigation',
			{
				type: 'navigation/add',
				payload: {
					elementName: '$[ELEMENT_NAME]$',
					instance: '$[INSTANCE_ID]$',
					name: '$[NAME]$',
					navigations: [
						'$[ROUTER_BASE_SELF]$',
						'$[ROUTER_BASE_SELF_NORMAL]$',
						'$[ROUTER_BASE_SELF_MAXIMIZED]$',
						'$[ROUTER_BASE_SELF_EXCLUSIVE]$',
						'$[ROUTER_BASE_SELF_POPUP]$'
					]
				}
			}
		);
		Liferay.on(
			'closePortlet',
			e => StateManager.GlobalStore.Get().DispatchAction(
				'com.liferay.portlet.navigation',
				{
					type: 'navigation/remove',
					payload: {
						instance: `_${e.portletId}_`
					}
				}
			),
			Liferay
		);
	},
	Liferay
);
</script>
