package ia.gui;

import java.awt.Dimension;
import java.io.File;
import java.util.List;

import ia.sourcecodeparser.ClassFile;

import javax.swing.JTextArea;

public class FileListGenerator extends JTextArea {

	public FileListGenerator(List<File> fileList) {
		super();
		// this.append("Methods:\n");

		for (File file: fileList) {
			this.append("  " + file.getAbsolutePath() + "\n");
		}

		this.setEditable(false);

	}

}
