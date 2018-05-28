package KEDataGenerator;

import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class TestJFilePicker extends JFrame {

	public TestJFilePicker() {
		super("OER Data Generator");
		
		setLayout(new FlowLayout());

		// set up a file picker component
		JFilePicker filePicker = new JFilePicker("Pick a file", "Browse...", "Upload", "Cancel");
		filePicker.setMode(JFilePicker.MODE_OPEN);
		filePicker.addFileTypeFilter(".xml", "XML Files");
		
		// access JFileChooser class directly
		JFileChooser fileChooser = filePicker.getFileChooser();
		fileChooser.setCurrentDirectory(new File("D:/"));
		
		// add the component to the frame
		add(filePicker);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(600, 300);
		setLocationRelativeTo(null);	// center on screen	
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TestJFilePicker().setVisible(true);
			}
		});
	}

}