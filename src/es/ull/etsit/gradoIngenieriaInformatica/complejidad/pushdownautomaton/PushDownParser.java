package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import scala.collection.parallel.ParIterableLike.Foreach;



public class PushDownParser{
	public static final String CURRENT_NODE = "current";
	public static final String START_NODE = "start";

	public static Graph loadGraph(String filePath){
		Graph graph = new SingleGraph(filePath);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			ArrayList<String> stringy = new ArrayList<>();
			int lineCount = 0;
			String styleSheet = new Scanner(new File("pushdown.css")).useDelimiter("\\Z").next();
			System.out.println("style: \n" + styleSheet);
				    graph.addAttribute("ui.stylesheet", styleSheet);
			String [] states;
//		    String tapeAlphabet;
//		    String stackAlphabet;
//		    String initState;
//		    String initStack;
		    String [] finalState;
		    String [] transitions;
		    for(String line; (line = br.readLine()) != null; ) {
		    	line = basicExpansion(line);
		    	stringy.add(line);
		        // process the line.
		    }
		    System.out.println("starting: " + stringy.get(3));
		    System.out.println(stringy);
		    finalState = stringy.get(5).split("\\s+");
		    states = stringy.get(0).split("\\s+");
		    for (String string : states) {
			    System.out.println("state: " + string);
			    Node node = graph.addNode(string);
			    node.addAttribute("ui.label", string);
			    for(String finals : finalState){
			    	System.out.println("Comparing: " + finals);
		    		if(string.equals(finals)){
		    			node.setAttribute("ui.class", "marked");
		    		}
			    }
			    System.out.println("string : " + string +"\t stringy : " + stringy.get(3));
			     if(string.equals(stringy.get(3)))
			    	 node.setAttribute("ui.class", node.getAttribute("ui.class") +  CURRENT_NODE);
//			     if(string.equals("q2"))
//			    	 node.setAttribute("ui.class", CURRENT_NODE);
			}
		    for(int i = 6; i < stringy.size(); i++){
		    	transitions = stringy.get(i).split("\\s+");
		    	// TODO hacer un check del formato
		    	for (String string : transitions) {
		    		System.out.println("transitionData: " + string);
				}
				    Edge edge = graph.addEdge(stringy.get(i), transitions[0], transitions[3]);
				    edge.addAttribute("ui.label", stringy.get(i));
		    }
		    // line is not visible here.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graph;
	}
// antes de cada linea pasar de que tipo es . (parseState(..), parseAlphabet(..)...

	private static String basicExpansion(String line) {
		Pattern p = Pattern.compile("#");
		Matcher m = p.matcher(line);
		if(m.find())
		    return line.substring(0, m.start());
		else
			return line;
	}
}
