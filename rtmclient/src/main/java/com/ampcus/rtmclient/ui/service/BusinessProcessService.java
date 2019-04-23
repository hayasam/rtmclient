package com.ampcus.rtmclient.ui.service;

import java.net.URL;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import com.ampcus.rtmclient.ui.views.helper.RequirementImageManager;
@Component("rtmBusinessProcessService")
@Service
public class BusinessProcessService {

	private Map serverPropertiesMap;
	@Autowired
	public void setServerPropertiesMap(Map serverPropertiesMap) {
		this.serverPropertiesMap = serverPropertiesMap;
	}
	

	public BusinessProcessService() {
		// TODO Auto-generated constructor stub
	}
	

	public void pushNewRequirement(java.util.Map requirement) throws MalformedURLException, IOException
	{
		String serverUrl = (String)serverPropertiesMap.get("server.url.new.reqt");
		URL url = new URL(serverUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestProperty("CONTENT_TYPE", "application/octet-stream");
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream());
		out.writeObject(requirement);
		InputStream response = conn.getInputStream();
		out.flush();
		out.close();
	}

}
