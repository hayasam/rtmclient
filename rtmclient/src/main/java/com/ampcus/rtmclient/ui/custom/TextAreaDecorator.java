package com.ampcus.rtmclient.ui.custom;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class TextAreaDecorator {

	private static final TextAreaDecorator decorator = new TextAreaDecorator();
	
	public static TextAreaDecorator getInstance()
	{
		return decorator;
	}
	
	private TextAreaDecorator() {
		// TODO Auto-generated constructor stub
	}

	public JTextArea decorate(JTextArea textArea)
	{
		Dimension originalDimension = textArea.getSize();
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		textArea.setBorder(border);
		textArea.setEditable(true);
		textArea.setLineWrap(false);
		textArea.setRows(10);
		textArea.setColumns(75);
		
		return textArea;
	}
	
}
