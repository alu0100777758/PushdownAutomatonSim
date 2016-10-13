package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class PushdownFrame extends JFrame {
	PushdownControlsPane controls;
	View view = null;
	public PushdownFrame() {
		super("Pushdown automaton");
		controls = new PushdownControlsPane(this);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(840,640);
		add(controls);
	}
	public void setGraph(Graph graph){
		if(view != null)
		{
			remove((Component) this.view);
		}
		System.out.println("Testing out");
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();
		View view = viewer.addDefaultView(false);
		this.view = view;
		add((Component) view);
		repaint();
	}

}
