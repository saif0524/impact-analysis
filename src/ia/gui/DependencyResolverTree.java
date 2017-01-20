package ia.gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class DependencyResolverTree extends JTree {
	
    private JTree tree;
	public DependencyResolverTree(Entry<String, HashSet<String>> cm) {
		
		
		String key = cm.getKey();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(key);
		HashSet<String> values = cm.getValue();
		if (!values.isEmpty()) {

			Iterator<String> iterator = values.iterator();
			while (iterator.hasNext()) {
				root.add(new DefaultMutableTreeNode(iterator.next()));
			}
		}
		tree = new JTree(root);
        add(tree);
        
	}

}
