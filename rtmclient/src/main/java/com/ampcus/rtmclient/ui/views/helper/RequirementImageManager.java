package com.ampcus.rtmclient.ui.views.helper;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.ampcus.rtmclient.ui.util.ImageProcessor;
import com.ampcus.rtmclient.ui.views.helper.RequirementImageManager.RECT;
import com.ampcus.rtmclient.ui.views.helper.RequirementImageManager.User32;
import com.ampcus.rtmclient.ui.views.helper.RequirementImageManager.WindowInfo;
import com.ampcus.rtmclient.ui.views.helper.RequirementImageManager.WndEnumProc;
import com.ampcus.rtmclient.ui.util.CommonTransformations;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;


public class RequirementImageManager {

	public RequirementImageManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static final String RESULTS_KEY_IMAGE = "RESULTS_KEY_IMAGE";
	public static final String RESULTS_KEY_TEXT_FROM_IMAGE = "RESULTS_KEY_TEXT_FROM_IMAGE";
	private String selectedWindowName = null;
	
	private String readTextFromImage(BufferedImage image)
	{
		String textFromImage = null;
		ImageProcessor textReader = new ImageProcessor();
		
		textFromImage = textReader.getImgText(image);
		return textFromImage;
	}
	
	public byte[] captureWindowScreenShot(WindowInfo w) throws NullPointerException, IOException, AWTException
	{
		byte[] imageInBytes = null;
        User32.instance.SetForegroundWindow(w.hwnd);
        
        try
        {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_PRINTSCREEN);
            robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
            Thread.sleep(2000);
            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            RenderedImage image = (RenderedImage) t.getTransferData(DataFlavor.imageFlavor);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image,"png",baos);
            baos.flush();
            imageInBytes = baos.toByteArray();
            baos.close();
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return imageInBytes;

	}
	
	public static Rectangle getAllScreensBounds()
	{
		Rectangle allScreensBounds = new Rectangle();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();
		
		for(GraphicsDevice screen: screens)
		{
			Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
			allScreensBounds.width+=screenBounds.width;
			allScreensBounds.height=Math.max(allScreensBounds.height, screenBounds.height);
		}
		
		return allScreensBounds;
	}
	
	public Map extractDataFromSelectedArea(BufferedImage image, Rectangle area)
	{
		Map croppedScreenResults = new HashMap();
		String fullFilePath = null;
		try
		{
			Rectangle clip = area.intersection(new Rectangle(image.getWidth(), image.getHeight()));
			BufferedImage croppedImage = image.getSubimage(clip.x, clip.y, clip.width, clip.height);
			
			croppedScreenResults.put(RESULTS_KEY_IMAGE, new ImageIcon(croppedImage));
			croppedScreenResults.put(RESULTS_KEY_TEXT_FROM_IMAGE, readTextFromImage(croppedImage));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return croppedScreenResults;
	}
	
	public byte[] takeWindowScreenshot(String windowTitle)
	{
		String fileName = null;
		byte[] imageInBytes = null;
		this.setSelectedWindowName(windowTitle);
		WindowInfo selectedWindow = null;
		try
		{
			List<WindowInfo> inflList = this.returnAllOpenWindows();
			
	        for (WindowInfo w : inflList) 
	        {
	        	if(w.title.equals(windowTitle))
	        	{
	        		selectedWindow = w;
	        	}
	        }
	        imageInBytes = this.captureWindowScreenShot(selectedWindow);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return imageInBytes;
	}
	

    public static List<WindowInfo> returnAllOpenWindows() throws AWTException, IOException {
        final List<WindowInfo> inflList = new ArrayList<WindowInfo>();
        final List<Integer> order = new ArrayList<Integer>();
        int top = User32.instance.GetTopWindow(0);
        
        while (top != 0) 
        {
    		order.add(top);
    		top = User32.instance.GetWindow(top, User32.GW_HWNDNEXT);
        }

        User32.instance.EnumWindows(new WndEnumProc() 
        {
            public boolean callback(int hWnd, int lParam) 
            {
            	WindowInfo info = getWindowInfo(hWnd);
        		inflList.add(info);
                return true;
            }

        }, 0);
        Collections.sort(inflList, new Comparator<WindowInfo>() 
        {
            public int compare(WindowInfo o1, WindowInfo o2) 
            {
                return order.indexOf(o1.hwnd) - order.indexOf(o2.hwnd);
            }
        });
        
        List<WindowInfo> resultsList = new ArrayList();
        
        for (WindowInfo w : inflList) {
        	if(User32.instance.IsWindowVisible(w.hwnd) && (!w.title.isEmpty()))
        	{
        		resultsList.add(w);
        	}
        }
        
        return resultsList;
    }

    public static  WindowInfo getWindowInfo(int hWnd) {
        RECT r = new RECT();
        User32.instance.GetWindowRect(hWnd, r);
        byte[] buffer = new byte[1024];
        User32.instance.GetWindowTextA(hWnd, buffer, buffer.length);
        String title = Native.toString(buffer);
        WindowInfo info = new WindowInfo(hWnd, r, title);
        return info;
    }

    protected static interface WndEnumProc extends StdCallLibrary.StdCallCallback {
        boolean callback(int hWnd, int lParam);
    }

    protected static interface User32 extends StdCallLibrary {
        public static final String SHELL_TRAY_WND = "Shell_TrayWnd";
        public static final int WM_COMMAND = 0x111;
        public static final int MIN_ALL = 0x1a3;
        public static final int MIN_ALL_UNDO = 0x1a0;

        final User32 instance = (User32) Native.loadLibrary("user32", User32.class);

        boolean EnumWindows(WndEnumProc wndenumproc, int lParam);

        boolean IsWindowVisible(int hWnd);

        int GetWindowRect(int hWnd, RECT r);

        void GetWindowTextA(int hWnd, byte[] buffer, int buflen);

        int GetTopWindow(int hWnd);

        int GetWindow(int hWnd, int flag);

        boolean ShowWindow(int hWnd);

        boolean BringWindowToTop(int hWnd);

        int GetActiveWindow();

        boolean SetForegroundWindow(int hWnd);

        int FindWindowA(String winClass, String title);

        long SendMessageA(int hWnd, int msg, int num1, int num2);

        final int GW_HWNDNEXT = 2;
    }

    protected static class RECT extends Structure {
        public int left, top, right, bottom;

        @Override
        protected List<String> getFieldOrder() {
            List<String> order = new ArrayList();
            order.add("left");
            order.add("top");
            order.add("right");
            order.add("bottom");
            return order;
        }
    }

    public static class WindowInfo {
        int hwnd;
        RECT rect;
        public String title;

        public WindowInfo(int hwnd, RECT rect, String title) {
            this.hwnd = hwnd;
            this.rect = rect;
            this.title = title;
        }

        public String toString() {
            return String.format("(%d,%d)-(%d,%d) : \"%s\"", rect.left, rect.top, rect.right, rect.bottom, title);
        }
    }
	
	public String getSelectedWindowName() {
		return selectedWindowName;
	}

	public void setSelectedWindowName(String selectedWindowName) {
		this.selectedWindowName = selectedWindowName;
	}
	

}
