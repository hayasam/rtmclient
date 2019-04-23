package com.ampcus.rtmclient.ui.util;

import net.sourceforge.tess4j.*;

import java.awt.image.BufferedImage;

public class ImageProcessor {

	public static String getImgText(BufferedImage image) 
	{
		String imgText = null;
	    ITesseract instance = new Tesseract();
	    try 
	    {
	         imgText = instance.doOCR(image);
	         return imgText;
	    } 
	    catch (TesseractException e) 
	    {
	        imgText = "**************************** ERROR ****************************"; 
	    	imgText += "Error while reading image:\n" + e.getMessage();
	    	imgText += "**************************** ERROR ****************************";
	    }
	    
	    return imgText;
	}
	
	
	
}
