package ia.changecollector;

import ia.examples.ExampleGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class RepositoryReaderGui extends JFrame {

	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu changeCollectorMenu = new JMenu("Changes");
	JMenuItem openRepo = new JMenuItem("Open Repository (.git)");
	JMenuItem exit = new JMenuItem("Exit");

	public RepositoryReaderGui() {
		setJMenuBar(menuBar);
		setVisible(true);
		setSize(400, 200);
		this.setTitle("Impact Analysis");

		menuBar.add(fileMenu);
		menuBar.add(changeCollectorMenu);
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

	public static void main(String[] args) {
		RepositoryReaderGui rrGui = new RepositoryReaderGui();
	}

}