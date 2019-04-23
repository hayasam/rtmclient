package com.ampcus.rtmclient.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ampcus.rtmclient.config.ClientConfiguration;
import com.ampcus.rtmclient.ui.views.WindowSelectionScreen;
import com.ampcus.rtmclient.ui.views.helper.RequirementImageManager;


public class FrontEndBuilder extends JPanel implements PropertyChangeListener {

	private static JFrame frame;
	private JProgressBar progressBar;
    private JTextArea taskOutput;
    private Task task;
	
	public FrontEndBuilder() {
		
        super(new BorderLayout());

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(progressBar);

        add(panel, BorderLayout.PAGE_START);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
	}

	public FrontEndBuilder(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public FrontEndBuilder(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public FrontEndBuilder(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        } 
    }

	
	
	class Task extends SwingWorker<Void, Void>
	{
		@Override
		public Void doInBackground()
		{
			Random random = new Random();
			int progress =0;
			setProgress(0);
			while(progress<100)
			{
				try
				{
					Thread.sleep(500);
					progress = 5;
					setProgress(Math.min(progress, 100));
					Thread.sleep(500);
					progress = 10;
					setProgress(Math.min(progress, 100));
					Thread.sleep(500);
					progress = 15;
					setProgress(Math.min(progress, 100));
					Thread.sleep(500);
					progress = 20;
					setProgress(Math.min(progress, 100));
					Thread.sleep(500);
					progress = 25;
					setProgress(Math.min(progress, 100));
					Thread.sleep(500);
					progress = 30;
					setProgress(Math.min(progress, 100));
					ApplicationContext ctx = new AnnotationConfigApplicationContext(ClientConfiguration.class);
					progress = 60;
					setProgress(Math.min(progress, 100));
					RequirementImageManager st = new RequirementImageManager();
					Thread.sleep(500);
					progress = 75;
					setProgress(Math.min(progress, 100));
					Thread.sleep(500);
					WindowSelectionScreen toolkit = new WindowSelectionScreen(ctx, st.returnAllOpenWindows()); 
					progress = 100;
					setProgress(Math.min(progress, 100));
					Thread.sleep(500);
					frame.setVisible(false);
				}
				catch(Exception e)
				{
				    System.out.println(e.toString());
				    e.printStackTrace();
				}
			}
			
			return null;
		}
		
		@Override
		public void done()
		{
			Toolkit.getDefaultToolkit().beep();
			setCursor(null); 
		}
	}
	
	private static void buildAndShowGUI()
	{
		try
		{
		       frame = new JFrame("Starting RTM Client");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		        //Create and set up the content pane.
		        JComponent newContentPane = new FrontEndBuilder();
		        newContentPane.setOpaque(true); //content panes must be opaque
		        frame.setContentPane(newContentPane);
		        frame.setSize(500, 200);
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		        //Display the window.
		        frame.setVisible(true);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
					
			public void run()
			{
				buildAndShowGUI();
			}
				
		});
	}
	
}
