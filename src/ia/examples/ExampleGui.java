package ia.examples;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ExampleGui extends JFrame{
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu changeCollectorMenu = new JMenu("Changes");
    JMenuItem openRepo = new JMenuItem("Open Repository (.git)");
    JMenuItem exit = new JMenuItem("Exit");

    public ExampleGui() {
        setJMenuBar(menuBar);
        setVisible(true);
        setSize(500, 600);
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
        ExampleGui ex = new ExampleGui();
    }

}
