package ia.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JTextArea;

public class DependencyResolver extends JTextArea {

	public DependencyResolver(Entry<String, HashSet<String>> cm) {
		super(5, 5);
		String key = cm.getKey();
		HashSet<String> values = cm.getValue();
		if (!values.isEmpty()) {

			this.setText("\n  " + key + "\n");
			this.append("----------------------------------------------------------------------------"
					+ "---------------------------------------------------------------------------\n");
			Iterator<String> iterator = values.iterator();
			while (iterator.hasNext()) {
				this.append("  " + iterator.next() + "\n");
			}
		}
		this.setEditable(false);

	}

}
