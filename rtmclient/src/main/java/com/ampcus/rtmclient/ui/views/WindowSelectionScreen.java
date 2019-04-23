package com.ampcus.rtmclient.ui.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

//import com.ampcus.rtmclient.ui.custom.override.CustomRobot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ampcus.rtmclient.ui.views.helper.*;
import com.ampcus.rtmclient.config.ClientConfiguration;
import com.ampcus.rtmclient.ui.custom.ButtonSizeLargeDecorator;
import com.ampcus.rtmclient.ui.custom.InstructionsLabelDecorator;
import com.ampcus.rtmclient.ui.custom.TextAreaDecorator;
import com.ampcus.rtmclient.ui.listeners.EventHandler;
import com.ampcus.rtmclient.ui.service.BusinessProcessService;

public class WindowSelectionScreen extends EventHandler implements ActionListener {

	@Autowired
	private Environment env;
	
	private ApplicationContext ctx;
	
	private static final int RIGHT_PANEL_SCROLLPANE_PREFERRED_WIDTH = 1500;
	private static final int RIGHT_PANEL_SCROLLPANE_PREFERRED_HEIGHT = 800;
	private static final int LEFT_PANEL_SCROLLPANE_PREFERRED_WIDTH = 375;
	private static final int LEFT_PANEL_SCROLLPANE_PREFERRED_HEIGHT = 800;
	private static final String FRAME_TITLE = "main.frame.title";
	private static final int FRAME_BOUND_1 = 0;
	private static final int FRAME_BOUND_2 = 0;
	private static final int FRAME_BOUND_3 = 1500;
	private static final int FRAME_BOUND_4 = 800;
	
	private static final String OPENING_LOGO_PATH = "opening.logo.path";
	private static final String BUTTON_SCREEN_AREA_SELECTOR_LABEL = "button.screen.area.selector.label";
	private static final String BUTTON_SAVE_SELECTED_AREA_LABEL = "button.save.selected.area.label";
	private static final String BUTTON_CLICK_WHEN_DONE = "button.complete.crop.selection";
	private static final String LABEL_INSTRUCTIONS_EXTRACTED_TEXT = "label.instructions.extractedtext";
	private static final String INFO_MESSAGE_REQUIREMENT_SAVED="info.message.requirement.saved";
	private static final String ERROR_MESSAGE_REQUIREMENT_TEXT_REQUIRED = "error.message.requirement.text.required";
	private BufferedImage croppedRequirementImage;

	private List<RequirementImageManager.WindowInfo> windowList;

	private JFrame frame;
	private Container cp;
	private EventHandler weHandler;
	private CardLayout cl1;
	JPanel innerLeftPanel, outerRightPanel, innerRightPanel, innerBottomPanel;

	private JTree jt;
	private DefaultMutableTreeNode treeRoot;
	private DefaultMutableTreeNode[] treeNodes;
	private TreePath tp;
	private String path;
	private JScrollPane centerJsp, westJsp;
	private JTextArea textFromImageHolder;
	
	private JButton screenAreaSelectorBtn;
	private JButton saveSelectedAreaBtn;
	private Rectangle selectedArea;
	private Map screenPackage;
	
	private RequirementImageManager screenshotTaker;
	
	private Map screenData;
	Rectangle captureRect;
	
	private String croppedFilePath;

	private BusinessProcessService rtmBusinessProcessService;
	@Autowired
	public void setRtmBusinessProcessService(BusinessProcessService rtmBusinessProcessService) {
		this.rtmBusinessProcessService = rtmBusinessProcessService;
	}

	private ClientConfiguration config;
	public void setConfig(ClientConfiguration config) {
		this.config = config;
	}
	
	private HashMap uiPropertiesMap;

	public WindowSelectionScreen(ApplicationContext ctx, List windowList) {
		this.ctx=ctx;
		uiPropertiesMap = (HashMap) ctx.getBean("uiPropertiesMap");
		this.windowList = windowList;
		this.start();
	}

	public void start() 
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame((String)uiPropertiesMap.get(this.FRAME_TITLE));
		cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		cp.add(BorderLayout.WEST, this.buildLeftPane());
		ImageIcon openingImage = new ImageIcon(getClass().getResource((String)uiPropertiesMap.get(this.OPENING_LOGO_PATH)));
		
		JLabel openingImgLabel = new JLabel(openingImage);

		outerRightPanel = new JPanel();
		outerRightPanel.setLayout(new BorderLayout());
		outerRightPanel.add(BorderLayout.CENTER, this.buildRightPanel(openingImgLabel));
		outerRightPanel.add(BorderLayout.SOUTH, this.buildBottomPanel());
		cp.add(BorderLayout.EAST, outerRightPanel);
		this.configureMainFrameComponent();
		screenshotTaker = new RequirementImageManager();
	}
	
	
	private void configureMainFrameComponent()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.removeWindowListener(weHandler);
		weHandler = new EventHandler();
		frame.addWindowListener(weHandler);
		frame.setBounds(FRAME_BOUND_1,FRAME_BOUND_2,FRAME_BOUND_3,FRAME_BOUND_4);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setCursor(Cursor.DEFAULT_CURSOR);
	}
	
	private JPanel buildLeftPane() 
	{
		innerLeftPanel = new JPanel();
		treeRoot = new DefaultMutableTreeNode("All Open Windows");
		treeNodes = new DefaultMutableTreeNode[windowList.size()];

		String title = null;
		for (int i = 0; i < this.windowList.size(); i++) 
		{
			title = this.windowList.get(i).title;
			if (title.length() > 50) 
			{
				title = title.substring(0, 49);
			}

			treeNodes[i] = new DefaultMutableTreeNode(title);
			treeRoot.add(treeNodes[i]);
		}

		jt = new JTree(treeRoot);
		jt.addMouseListener(this);
		jt.addKeyListener(this);

		westJsp = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		innerLeftPanel.add(westJsp);

		innerLeftPanel.setPreferredSize(new Dimension(LEFT_PANEL_SCROLLPANE_PREFERRED_WIDTH, LEFT_PANEL_SCROLLPANE_PREFERRED_HEIGHT));
		return innerLeftPanel;
	}
	
	private void destroyLeftPanel()
	{
		innerLeftPanel = null;
		treeRoot = null;
		treeNodes = null;
		jt = null;
		westJsp = null;
		innerLeftPanel = null;
	}
	

	private JPanel buildBottomPanel()
	{
		screenAreaSelectorBtn = new JButton((String)uiPropertiesMap.get(this.BUTTON_SCREEN_AREA_SELECTOR_LABEL));
		screenAreaSelectorBtn = ButtonSizeLargeDecorator.getInstance().decorate(screenAreaSelectorBtn);
		screenAreaSelectorBtn.addActionListener(this);
		screenAreaSelectorBtn.setEnabled(false);
		
        saveSelectedAreaBtn = new JButton((String)uiPropertiesMap.get(this.BUTTON_SAVE_SELECTED_AREA_LABEL));
		saveSelectedAreaBtn = ButtonSizeLargeDecorator.getInstance().decorate(saveSelectedAreaBtn);
		saveSelectedAreaBtn.addActionListener(this);
		saveSelectedAreaBtn.setEnabled(false);
		
		innerBottomPanel = new JPanel();
		innerBottomPanel.setLayout(new BorderLayout());
		
		JPanel innerBottomTopPanel = new JPanel();
		JPanel innerBottomCenterPanel = new JPanel();
		JPanel innerBottomBottomPanel = new JPanel();
		
		innerBottomCenterPanel.setLayout(new FlowLayout());
		innerBottomPanel.add(BorderLayout.NORTH, innerBottomTopPanel);
		innerBottomPanel.add(BorderLayout.CENTER, innerBottomCenterPanel);
		innerBottomPanel.add(BorderLayout.SOUTH, innerBottomBottomPanel);
		
		innerBottomTopPanel.add(new JLabel("           "));
		innerBottomCenterPanel.add(screenAreaSelectorBtn);
		innerBottomCenterPanel.add(new JLabel("           "));
		innerBottomCenterPanel.add(saveSelectedAreaBtn);
		
		return innerBottomPanel;
	}
	
	private JPanel buildRightPanel(JLabel content) 
	{
		innerRightPanel = new JPanel();
		centerJsp = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		centerJsp.setPreferredSize(new Dimension(RIGHT_PANEL_SCROLLPANE_PREFERRED_WIDTH, RIGHT_PANEL_SCROLLPANE_PREFERRED_HEIGHT));
		innerRightPanel.add(centerJsp);
		return innerRightPanel;
	}
	
	private void destroyRightPanel()
	{
		innerRightPanel = null;
		centerJsp = null;
	}
	

	private void refreshWindowList() 
	{
		RequirementImageManager st = new RequirementImageManager();
		try 
		{
			this.windowList = st.returnAllOpenWindows();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		cp.add(BorderLayout.WEST, this.buildLeftPane());
	}
	
	private void refreshUserInterface()
	{
		this.frame.dispose();
		this.destroyLeftPanel();
		this.destroyRightPanel();
		frame.setVisible(false);;
		this.refreshWindowList();
		this.start();
		frame.setVisible(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.repaint();
	}
	
	public void actionPerformed(ActionEvent ae) 
	{
		try
		{
			if(ae.getSource()==screenAreaSelectorBtn)
			{
				screenPackage = this.startScreenAreaSelection();
				saveSelectedAreaBtn.setEnabled(true);
				outerRightPanel.remove(innerRightPanel);
				outerRightPanel.add(BorderLayout.CENTER, this.buildRightPanel(new JLabel()));
				cp.add(BorderLayout.CENTER, outerRightPanel);
			}
			if(ae.getSource()==saveSelectedAreaBtn)
			{
				String editedText = textFromImageHolder.getText();
				if(editedText!=null && editedText.length()>0)
				{
					Map imageDataPackage = new HashMap();
					imageDataPackage.put(RequirementImageManager.RESULTS_KEY_IMAGE, new ImageIcon(croppedRequirementImage));
					imageDataPackage.put(RequirementImageManager.RESULTS_KEY_TEXT_FROM_IMAGE, editedText);
					saveSelectedAreaBtn.setEnabled(false);
					rtmBusinessProcessService = (BusinessProcessService) ctx.getBean("rtmBusinessProcessService");
					rtmBusinessProcessService.pushNewRequirement(imageDataPackage);
					JOptionPane.showMessageDialog(frame, (String)uiPropertiesMap.get(this.INFO_MESSAGE_REQUIREMENT_SAVED));
					this.refreshUserInterface();
				}
				else
				{
					JOptionPane.showMessageDialog(frame, (String)uiPropertiesMap.get(this.ERROR_MESSAGE_REQUIREMENT_TEXT_REQUIRED));
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}		
	
	private void buildPicture(String windowTitle) 
	{
		try
		{
			cp.remove(innerRightPanel);
			byte[] imageInBytes = screenshotTaker.takeWindowScreenshot(windowTitle);
			ImageIcon selectedImage = buildImageFromByteArray(imageInBytes);
			JLabel content = new JLabel(selectedImage);
			screenAreaSelectorBtn.setEnabled(true);
			outerRightPanel.remove(innerRightPanel);
			outerRightPanel.add(BorderLayout.CENTER, this.buildRightPanel(content));
			cp.add(BorderLayout.CENTER, outerRightPanel);
			frame.setVisible(true);
			frame.repaint();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private ImageIcon buildImageFromByteArray(byte[] byteArrayData) throws java.io.IOException
	{
		ImageIcon image = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayData);
		BufferedImage bImage = ImageIO.read(bais);
		image = new ImageIcon(bImage);
		
		return image;
	}

	private void showCroppedImage(BufferedImage image, Rectangle area)
	{
		outerRightPanel.remove(innerRightPanel);
		Rectangle clip = area.intersection(new Rectangle(image.getWidth(), image.getHeight()));
		image = image.getSubimage(clip.x, clip.y, clip.width, clip.height);
		this.croppedRequirementImage = image;
		ImageIcon croppedImage = new ImageIcon(image);
		JLabel content = new JLabel(croppedImage);
		screenAreaSelectorBtn.setEnabled(false);
		saveSelectedAreaBtn.setEnabled(true);
		Map croppedScreenResults = screenshotTaker.extractDataFromSelectedArea((BufferedImage)screenPackage.get("image"), (Rectangle)screenPackage.get("area"));
		textFromImageHolder = new JTextArea();
		textFromImageHolder = TextAreaDecorator.getInstance().decorate(textFromImageHolder);
		textFromImageHolder.setText((String)croppedScreenResults.get(RequirementImageManager.RESULTS_KEY_TEXT_FROM_IMAGE));
		
		innerRightPanel.setLayout(new BorderLayout());
		innerRightPanel.add(BorderLayout.CENTER, this.buildRightPanel(content));
		
		JPanel innerRightBottomPanel = new JPanel();
		innerRightBottomPanel.setLayout(new BorderLayout());
		
		JLabel instructionsLabel = new JLabel((String)uiPropertiesMap.get(this.LABEL_INSTRUCTIONS_EXTRACTED_TEXT));
		instructionsLabel = InstructionsLabelDecorator.getInstance().decorate(instructionsLabel);
		
		innerRightBottomPanel.add(BorderLayout.NORTH, instructionsLabel);
		innerRightBottomPanel.add(BorderLayout.SOUTH, textFromImageHolder);
		
		innerRightPanel.add(BorderLayout.SOUTH, innerRightBottomPanel);
		outerRightPanel.add(BorderLayout.CENTER, innerRightPanel);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.repaint();
	}
	
    public void captureRectangle(final BufferedImage screen) 
    {
        final BufferedImage screenCopy = new BufferedImage(
                screen.getWidth(),
                screen.getHeight(),
                screen.getType());
        final JLabel screenLabel = new JLabel(new ImageIcon(screenCopy));
        JScrollPane screenScroll = new JScrollPane(screenLabel);

        screenScroll.setPreferredSize(new Dimension(
                (int)(screen.getWidth()*.85),
                (int)(screen.getHeight()*.85)));

        JPanel panel = new JPanel(new BorderLayout());
       panel.add(screenScroll, BorderLayout.CENTER);

        final JLabel selectionLabel = new JLabel("Drag a rectangle in the screen shot!");
        panel.add(selectionLabel, BorderLayout.SOUTH);

        repaint(screen, screenCopy);
        screenLabel.repaint();

        screenLabel.addMouseMotionListener(new MouseAdapter() {

            Point start = new Point();

            @Override
            public void mouseMoved(MouseEvent me) {
                start = me.getPoint();
                repaint(screen, screenCopy);
                selectionLabel.setText("Start Point: " + start);
                screenLabel.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                Point end = me.getPoint();
                captureRect = new Rectangle(start,
                        new Dimension(end.x-start.x, end.y-start.y));
                repaint(screen, screenCopy);
                screenLabel.repaint();
                selectionLabel.setText("Rectangle: " + captureRect);
            }
            
            @Override
            public void mouseReleased(MouseEvent me) {
                Point end = me.getPoint();
                captureRect = new Rectangle(start,
                        new Dimension(end.x-start.x, end.y-start.y));
            }
        });

        JOptionPane.showMessageDialog(frame, panel);
    }
    
    public void repaint(BufferedImage orig, BufferedImage copy) {
        Graphics2D g = copy.createGraphics();
        g.drawImage(orig,0,0, null);
        if (captureRect!=null) {
            g.setColor(Color.RED);
            g.draw(captureRect);
            g.setColor(new Color(255,255,255,150));
            g.fill(captureRect);
        }
        g.dispose();
    }
    
    public Map startScreenAreaSelection() throws Exception
    {
        screenData = new HashMap(); 
        Robot robot = new Robot();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //System.out.println("Robot height: "+screenSize.height);
        //System.out.println("Robot width: "+screenSize.width);
        final BufferedImage screen = robot.createScreenCapture(new Rectangle(screenSize));
        screenData.put("image", screen);
        
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() {
            	captureRectangle(screen);
                screenData.put("area", captureRect);
        		showCroppedImage(screen, captureRect);
            }
        });
        return screenData;
    }
	
	

	/**
	 * Overridden MouseEvent
	 */
	public void mouseClicked(MouseEvent me) 
	{
		try 
		{
			tp = jt.getPathForLocation(me.getX(), me.getY());
			path = tp.toString();
			path = path.substring(path.lastIndexOf(",") + 2, path.length() - 1);
			System.out.println("path: " + path);

			String windowTitle = null;
			for (int i = 0; i < this.windowList.size(); i++) 
			{
				windowTitle = this.windowList.get(i).title;
				if (windowTitle.startsWith(path)) 
				{
					this.buildPicture(windowTitle);
					break;
				}
			}
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();	
		}
	}

	/**
	 * Overridden WindowEvent
	 */
    public void windowClosing(WindowEvent e) {
    	System.out.println("Now shutting down RTM Client....");
        System.exit(0);
    }

}
