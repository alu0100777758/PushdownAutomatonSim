package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class PushdownFrame extends JFrame {
	public static final String WINDOW_NAME = "PushDownSim";
	JPanel controls;
	Graph graph;
	View view;
	public PushdownFrame() {
		super(WINDOW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		graph = PushDownParser.loadGraph("input1.txt");
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		//graphPanel = viewer.addDefaultView(false);
		setPreferredSize(new Dimension(820, 640));
		controls = new PushDownControlPanel(view);
		viewer.enableAutoLayout();
		view = viewer.addDefaultView(false);   // false indicates "no JFrame".
		//graphPanel.add((Component) view);
		// ...
		add(controls);
		add((Component) view);
	}

}
