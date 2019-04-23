package com.ampcus.rtmclient.ui.custom;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComponent;

public class JDecorator extends JComponent {

	public JDecorator(JComponent decoratedComponent) {
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, decoratedComponent);
	}
	
	
}
