package com.liferay.remote.web.component.admin.web.internal.form;

import org.osgi.service.component.annotations.Component;

import com.liferay.configuration.admin.definition.ConfigurationDDMFormDeclaration;
import com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration;

@Component(
	property = "configurationPid=com.liferay.remote.web.component.admin.web.configuration.RemoteWebComponentConfiguration"
)
public class RemoteWebComponentConfigurationForm implements ConfigurationDDMFormDeclaration {

	@Override
	public Class<?> getDDMFormClass() {
		return RemoteWebComponentConfiguration.class;
	}

}
