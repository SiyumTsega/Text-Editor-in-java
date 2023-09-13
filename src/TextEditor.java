
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener{
	
	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontSizeSpinner;
	JLabel fontLabel;
	JButton fontColorButton;
	JComboBox fontBox;
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	TextEditor(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Text Editor");
		setSize(500,500);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450, 450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fontLabel = new JLabel("Font: ");
		

		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener((e) -> textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int)fontSizeSpinner.getValue())));
		
		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(this);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		///---------Menu Bar----------
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		setJMenuBar(menuBar);
		add(fontLabel);
		add(fontSizeSpinner);
		add(fontColorButton);
		add(fontBox);
		add(scrollPane);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == fontColorButton) {
			
			
			Color color = JColorChooser.showDialog(null, "Choose a color", Color.black);
			
			textArea.setForeground(color);
		}
		
		if(e.getSource() == fontBox) {
			textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
		}
		
		if(e.getSource() == openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File","txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
		
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
			
		
		}
		
		if(e.getSource() == saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				PrintWriter fileOut = null;
				
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
		
		if(e.getSource() == exitItem) {
			System.exit(0);
		}
	}

}
