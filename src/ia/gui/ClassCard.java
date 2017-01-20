package ia.gui;

import java.awt.Dimension;

import ia.sourcecodeparser.ClassFile;

import javax.swing.JTextArea;

public class ClassCard extends JTextArea {

	public ClassCard(ClassFile classFile) {
		super(20, 20);
		this.setText("\n  " + classFile.getClassNAme() + "\n");
		this.append("----------------------------------------------------------------------------------------"
				+ "------------------------------------------------------------------------------------\n");
		// this.append("Methods:\n");

		for (int i = 0; i < classFile.getMethodList().size(); i++) {
			this.append("  " + classFile.getMethodList().get(i) + "()\n");
		}

		this.setEditable(false);

	}

}
