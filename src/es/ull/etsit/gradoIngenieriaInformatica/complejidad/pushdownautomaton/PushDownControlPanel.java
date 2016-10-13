package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class PushDownControlPanel extends JPanel {
	private JButton loadFile = new JButton("LoadProgram");
	private JFileChooser filechooser = new JFileChooser(System.getProperty("user.dir"));
	static View view;
	public PushDownControlPanel(View view) {
		//this.graph=view;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(100, 640));
		setMaximumSize(getPreferredSize());
		loadFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 int returnVal = filechooser.showOpenDialog(null);
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
					 	System.out.println("reloading");
			            File file = filechooser.getSelectedFile();
			            //This is where a real application would open the file.
			            System.out.println("Opening: " + file.getName());
			            Graph graph = PushDownParser.loadGraph(file.getAbsolutePath());
			            
			            Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
			    		//graphPanel = viewer.addDefaultView(false);
			    		setPreferredSize(new Dimension(820, 640));
			    		viewer.enableAutoLayout();
			    		PushDownControlPanel.view = viewer.addDefaultView(false);
			
			            
			        } else {
			        	System.out.println("Open command cancelled by user.");
			        }
			}
			});
		add(loadFile);
	}

}
