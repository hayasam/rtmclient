package com.ampcus.rtmclient.ui.views.helper;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ampcus.rtmclient.config.ClientConfiguration;

@Component("uiHelper")
public class UIHelperComponent {

	private ClientConfiguration config;
	
	@Autowired
	public UIHelperComponent(ClientConfiguration config) {
		this.config=config;
	}

	@Autowired
	public void setConfig(ClientConfiguration config) {
		this.config = config;
	}
	
	public String getOpeningLogoPath()
	{
		return (String)config.getUiPropertiesMap().get("opening.logo.path");
	}

}
