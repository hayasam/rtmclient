package com.ampcus.rtmclient.ui.custom;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class InstructionsLabelDecorator {

	private final static InstructionsLabelDecorator decorator = new InstructionsLabelDecorator();
	
	public static InstructionsLabelDecorator getInstance()
	{
		return decorator;
	}
	
	private InstructionsLabelDecorator() {
		// TODO Auto-generated constructor stub
	}
	
	public JLabel decorate(JLabel label)
	{
		Font font = new Font("Helvetica", Font.PLAIN, 18);
		label.setFont(font);
		label.setPreferredSize(new Dimension(640,35));

		return label;
	}

}
