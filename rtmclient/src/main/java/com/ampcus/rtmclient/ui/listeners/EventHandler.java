package com.ampcus.rtmclient.ui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class EventHandler implements MouseListener, KeyListener, WindowListener {

	public EventHandler() {
		// TODO Auto-generated constructor stub
	}

	public void keyTyped(KeyEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}

	public void keyPressed(KeyEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}

	public void keyReleased(KeyEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}

	public void mouseClicked(MouseEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}

	public void mousePressed(MouseEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}

	public void mouseReleased(MouseEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}

	public void mouseEntered(MouseEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}

	public void mouseExited(MouseEvent e) {
		// Empty implementation. Method is to be overridden if ever used.

	}
	
    /**
     * Invoked when the window is set to be the user's
     * active window, which means the window (or one of its
     * subcomponents) will receive keyboard events.
     */
    public void windowActivated(WindowEvent e) {
    }
    /**
     * Invoked the first time a window is made visible.
     */
    public void windowOpened(WindowEvent e) {
    }
    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.  If the program does not
     * explicitly hide or dispose the window while processing
     * this event, the window close operation will be cancelled.
     */
    public void windowClosing(WindowEvent e) {
    	System.out.println("============================================================================================");
    	System.out.println("Now shutting down RTM Client...");
    	System.out.println("============================================================================================");
        System.exit(0);
    }
    /**
     * Invoked when a window is no longer the user's active
     * window, which means that keyboard events will no longer
     * be delivered to the window or its subcomponents.
     */
    public void windowDeactivated(WindowEvent e) {
    }
    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     */
    public void windowClosed(WindowEvent e) {
    }
    /**
     * Invoked when a window is changed from a normal to a
     * minimized state. For many platforms, a minimized window
     * is displayed as the icon specified in the window's
     * iconImage property.
     * @see java.awt.Frame#setIconImage
     */
    public void windowIconified(WindowEvent e) {
    }
    /**
     * Invoked when a window is changed from a minimized
     * to a normal state.
     */
    public void windowDeiconified(WindowEvent e) {
    }
	
	
	
}
