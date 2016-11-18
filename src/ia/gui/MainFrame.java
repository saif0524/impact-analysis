package ia.gui;

import javax.swing.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

	JMenuBar menuBar = new JMenuBar();

	JMenu fileMenu = new JMenu("File");
	JMenu changesMenu = new JMenu("Changes");
	JMenu dependencyMenu = new JMenu("Dependencies");

	JMenuItem openRepo = new JMenuItem("Open Repository (.git)");
	JMenuItem exit = new JMenuItem("Exit");

	JMenuItem showChangedFiles = new JMenuItem("Files Only");
	JMenuItem showMethodLists = new JMenuItem("Files with Method List");
	JMenuItem showFunctionCalls = new JMenuItem("Files with Fuctioncalls");

	JMenuItem dependencyLists = new JMenuItem("List file dependency");
	JMenuItem generateDependencyGraph = new JMenuItem(
			"Generate Dependency Graph");

	public MainFrame() {
		menuBarCreator();
	}

	private void menuBarCreator() {
		setJMenuBar(menuBar);
		setVisible(true);
		setSize(500, 600);
		this.setTitle("Impact Analysis");

		fileMenuCreator();
		changesMenuCreator();
		dependencyMenuCreator();
	}

	private void fileMenuCreator() {
		menuBar.add(fileMenu);
		fileMenu.add(openRepo);
		fileMenu.add(exit);

		openRepo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(null);

			}

		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

	}

	private void changesMenuCreator() {
		menuBar.add(changesMenu);
		changesMenu.add(showChangedFiles);
		changesMenu.add(showMethodLists);
		changesMenu.add(showFunctionCalls);

	}

	private void dependencyMenuCreator() {
		menuBar.add(dependencyMenu);
		dependencyMenu.add(dependencyLists);
		dependencyMenu.add(generateDependencyGraph);

	}

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
	}

}
