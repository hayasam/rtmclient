package com.ampcus.rtmclient.ui.util;

import java.sql.Timestamp;
import java.util.Date;

public class CommonTransformations {

	public static String getCurrentTime()
	{
		Date date = new Date();
		String tempTime = new Timestamp(date.getTime()).toString();
		String finalTimeStamp = tempTime.replace(":","_").replace("-","_").replace(" ","_").replace(".","_");
		return finalTimeStamp;
	}
	
	public static String generateProperFilename(String fileName)
	{
		fileName = fileName.replace(":","_").replace("-","_").replace(" ","_").replace(".","_").replace("/", "_").replace("\\", "_").replace("|", "_");
		return fileName;
	}

}
