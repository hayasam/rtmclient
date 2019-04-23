package com.ampcus.rtmclient.ui.custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

public class MainButtonDecorator extends JDecorator {

	JButton button;
	private boolean mouseOver; // true when user moves the mouse over this button
	
	public MainButtonDecorator(JButton button) {
		super(button);
		mouseOver=false;
		this.button=button;
		this.button.addMouseListener(new MouseAdapter()
										{
											public void mouseEntered(MouseEvent me)
											{
												mouseOver=true;
												repaint();
											}
											
											public void mouseExited(MouseEvent me)
											{
												mouseOver=false;
												repaint();
											}
										});
	}
	

	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(!mouseOver)
		{
			Dimension size = super.getSize();
			 g.setColor(Color.lightGray);
			 g.drawRect(0, 0, size.width-1, size.height-1);
			 g.drawLine(size.width-2, 0, size.width-2,
			size.height-1);
			 g.drawLine(0, size.height-2, size.width-2, size.height-2);			
		}
	}


}
