package ia.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.JTextArea;

public class DependencyResolver extends JTextArea {

	public DependencyResolver(Entry<String, HashSet<String>> cm) {
		super(5, 5);
		String key = cm.getKey();
		HashSet<String> values = cm.getValue();
		if (!values.isEmpty()) {

			this.setText(key + "\n");
			this.append("---------------------------------------------------------------------------------------------\n");
			for (int i = 0; i < values.size(); i++) {
				this.append(values.iterator().next() + "\n");
			}
		}

	}

}
