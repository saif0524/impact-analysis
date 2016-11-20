package ia.gui;

import ia.changecollector.ChangeCollector;
import ia.changecollector.RepositoryReader;
import ia.dependencyresolver.ClassDependencyImpl;
import ia.sourcecodeparser.ClassFile;
import ia.sourcecodeparser.Parser;

import javax.swing.*;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.Repository;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainFrame extends JFrame {

	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu changesMenu = new JMenu("Changes");
	JMenu dependencyMenu = new JMenu("Dependencies");

	JMenuItem openRepo = new JMenuItem("Open Repository (.git)");
	JMenuItem exit = new JMenuItem("Exit");

	JMenuItem showChangedFiles = new JMenuItem("Files Only");
	JMenuItem showClassCards = new JMenuItem("Class Cards");
	JMenuItem showFunctionCalls = new JMenuItem("Files with Fuctioncalls");

	JMenuItem dependencyLists = new JMenuItem("List Class Dependency");

	JMenuItem generateDependencyGraph = new JMenuItem(
			"Generate Dependency Graph");

	JTextArea textarea;

	Repository repo;
	ChangeCollector changeCollector;
	ClassDependencyImpl fileDependency;
	List<File> fileList;
	List<DiffEntry> diffEntries;

	List<ClassFile> classList;

	Parser classParserForFileList;

	HashMap<String, HashSet<String>> classMap;

	JPanel jp = new JPanel();

	public MainFrame() {
		super();
		setTitle("Impact Analysis");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jp.setLayout(new GridLayout());

		this.add(jp);
		menuBarCreator();
		// addTextField();

		operationalMethod();

		setVisible(true);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	private void menuBarCreator() {
		setJMenuBar(menuBar);
		fileMenuCreator();
		changesMenuCreator();
		dependencyMenuCreator();

	}

	private void fileMenuCreator() {
		menuBar.add(fileMenu);
		fileMenu.add(openRepo);
		fileMenu.add(exit);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	}

	private void changesMenuCreator() {

		menuBar.add(changesMenu);

		changesMenu.add(showChangedFiles);
		changesMenu.add(showClassCards);
		changesMenu.add(showFunctionCalls);
	}

	private void dependencyMenuCreator() {
		menuBar.add(dependencyMenu);
		dependencyMenu.add(dependencyLists);
		dependencyMenu.add(generateDependencyGraph);

	}

	public void operationalMethod() {
		openRepo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RepositoryReader repoReader = new RepositoryReader();

				repo = repoReader.readRepo();

				changeCollector = new ChangeCollector();

			}

		});

		showChangedFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					diffEntries = changeCollector.compareTrees(repo);
				} catch (AmbiguousObjectException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (GitAPIException e1) {
					e1.printStackTrace();
				}
				fileDependency = new ClassDependencyImpl();

				fileList = new ArrayList<File>();

				fileList = changeCollector.createListOfEntries(repo,
						diffEntries);

				classMap = fileDependency.getFileDependency(fileList);

				classParserForFileList = new Parser(fileList);
				classList = classParserForFileList.createClassCard(fileList);
			}
		});

		showClassCards.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				classCardViewer();
			}
		});

		dependencyLists.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dependecyViewer();
			}
		});

	}

	public void classCardViewer() {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(0, 3);
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);

		for (ClassFile cf : classList) {
			ClassCard cc = new ClassCard(cf);
			JScrollPane scrollPane = new JScrollPane(cc);
			panel.add(scrollPane);
		}

		jp.removeAll();

		jp.add(panel);

		this.add(jp);

		this.revalidate();
	}

	public void dependecyViewer() {
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(0, 3);
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);

		for (HashMap.Entry<String, HashSet<String>> cm : classMap.entrySet()) {
			DependencyResolver dr = new DependencyResolver(cm);
			JScrollPane scrollPane = new JScrollPane(dr);
			panel.add(scrollPane);
			panel.add(scrollPane);
		}

		jp.removeAll();

		jp.repaint();

		jp.add(panel);

		this.add(jp);

		this.revalidate();

	}

	private void addTextField() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		textarea = new JTextArea();
		add(textarea);
	}

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
	}

}
