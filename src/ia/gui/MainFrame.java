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

	JMenuItem showChangedFiles = new JMenuItem("Class Only");
	JMenuItem showClassCards = new JMenuItem("Class with methods");
	JMenuItem showFunctionCalls = new JMenuItem("Files with Fuctioncalls");

	JMenuItem dependencyLists = new JMenuItem("List Class Dependency");

	JMenuItem generateDependencyGraph = new JMenuItem(
			"Generate Dependency Graph");
	
	JMenuItem generateDependencyTree = new JMenuItem(
			"Dependency as Tree");

	JTextArea textarea;

	Repository repo;
	ChangeCollector changeCollector;
	ClassDependencyImpl fileDependency;
	List<File> fileList;
	List<DiffEntry> diffEntries;

	List<ClassFile> classList;

	Parser classParserForFileList;

	HashMap<String, HashSet<String>> classMap;

	long startTime, stopTime, elapsedTime, total;
	
	
	JPanel titleBarPanel = new JPanel();
	JPanel itemPanel = new JPanel();
	public MainFrame() {
		super();
		setTitle("Impact Analysis");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		itemPanel.setLayout(new GridLayout());

		this.add(itemPanel);
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
//		changesMenu.add(showFunctionCalls);
	}

	private void dependencyMenuCreator() {
		menuBar.add(dependencyMenu);
		dependencyMenu.add(dependencyLists);
//		dependencyMenu.add(generateDependencyTree);
//		dependencyMenu.add(generateDependencyGraph);

	}

	public void operationalMethod() {
		openRepo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RepositoryReader repoReader = new RepositoryReader();

				repo = repoReader.readRepo();
				if (repo != null) {
					changeCollector = new ChangeCollector();

					try {
						diffEntries = changeCollector.compareTrees(repo);
					} catch (AmbiguousObjectException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (GitAPIException e1) {
						e1.printStackTrace();
					}
				}
				
				startTime = System.currentTimeMillis();
				fileList = new ArrayList<File>();
				fileList = changeCollector.createListOfEntries(repo,
						diffEntries);
				
				fileListGenerator();
				fileDependency = new ClassDependencyImpl();



				classMap = fileDependency.getFileDependency(fileList);
				
				classParserForFileList = new Parser(fileList);
				classList = classParserForFileList.createClassCard(fileList);


				
				
				stopTime = System.currentTimeMillis();
				elapsedTime = stopTime - startTime;
				
				System.out.println(elapsedTime);
			}

		});

		showChangedFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				fileListGenerator();
				
				fileDependency = new ClassDependencyImpl();

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
				startTime = System.currentTimeMillis();
				dependecyViewer();
				stopTime = System.currentTimeMillis();
				elapsedTime += (stopTime - startTime);
				System.out.println(elapsedTime);
			}
;		});

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
		};

		itemPanel.removeAll();

		itemPanel.add(panel);

		this.add(itemPanel);

		this.revalidate();
	}

	public void dependecyViewer() {
		
		textarea = new JTextArea("Class cards of changed files");
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

		itemPanel.removeAll();

		itemPanel.repaint();

		itemPanel.add(panel);
		this.add(textarea);
		this.add(itemPanel);

		this.revalidate();

	}
	
	
public void dependecyListViewer() {
		
		textarea = new JTextArea("Dependency as Tree");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(0, 3);
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);

		for (HashMap.Entry<String, HashSet<String>> cm : classMap.entrySet()) {
			DependencyResolverTree drTree = new DependencyResolverTree(cm);
			JScrollPane scrollPane = new JScrollPane(drTree);
			panel.add(scrollPane);
			panel.add(scrollPane);
		}

		itemPanel.removeAll();

		itemPanel.repaint();

		itemPanel.add(panel);
		this.add(itemPanel);

		this.revalidate();

	}
	
	public void fileListGenerator(){
		
		textarea = new JTextArea("Changed file list");
		
		FileListGenerator fileList = new FileListGenerator(this.fileList);
		
		JScrollPane scrollPane = new JScrollPane(fileList);
		itemPanel.removeAll();

		itemPanel.repaint();

		itemPanel.add(scrollPane);

		this.add(textarea);
		this.add(itemPanel);

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
