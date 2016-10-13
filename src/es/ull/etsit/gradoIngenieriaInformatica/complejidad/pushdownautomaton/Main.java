package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class Main {
	public static void main(String[] args) {
		
		//Graph parsed = PushDownParser.loadGraph("input0.txt");
		System.out.println("Testing out");
		//Viewer view = parsed.display();
		
		//1. Create the frame.
		JFrame frame = new PushdownFrame();
		//4. Size the frame.
		frame.pack();

		//5. Show it.
		frame.setVisible(true);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		view.disableAutoLayout();
//		Graph graph = new SingleGraph("Tutorial 1");
//
//		graph.addNode("A");
//		graph.addNode("B");
//		graph.addNode("C");
//		graph.addEdge("AB", "A", "B");
//		graph.addEdge("BC", "B", "C");
//		graph.addEdge("CA", "C", "A");
//
//		graph.display();
	}
}
