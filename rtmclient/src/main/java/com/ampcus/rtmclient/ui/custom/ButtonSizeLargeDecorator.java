package com.ampcus.rtmclient.ui.custom;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class ButtonSizeLargeDecorator {

	private static final ButtonSizeLargeDecorator decorator = new ButtonSizeLargeDecorator();
	
	public static ButtonSizeLargeDecorator getInstance()
	{
		return decorator;
	}
	
	public JButton decorate(JButton button)
	{
		
		Dimension preferredSize = new Dimension();
		preferredSize.setSize(275, 35);
		button.setPreferredSize(preferredSize);
		button.setMinimumSize(preferredSize);
		button.setMaximumSize(preferredSize);
		
		Font font = new Font("Helvetica", Font.PLAIN, 18);
		button.setFont(font);
		return button;
	}
	
	
	private ButtonSizeLargeDecorator() {
		// TODO Auto-generated constructor stub
	}

}
