package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import scala.collection.parallel.ParIterableLike.Foreach;

public class PushdownControlsPane extends JPanel {
	JButton openDir = new JButton("LoadFile");
	JButton insta = new JButton("InstaEj");
	JButton step = new JButton("Step");
	int graphHead;
	Graph graph;
	Node currentNode = null;
	PushdownStack stack = new PushdownStack();
	PushdownTape inputTape = new PushdownTape();
	JTextField inputInput = new JTextField("Input");
	JFileChooser filechooser = new JFileChooser(System.getProperty("user.dir"));
	PushdownFrame frame;
	public PushdownControlsPane(PushdownFrame frame) {
		super();
		this.frame = frame;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		System.out.println("adding controls");
		setPreferredSize(new Dimension(100, 1000));
		setMaximumSize(getPreferredSize());
		setActions();
		add(openDir);
		add(inputTape);
		add(stack);
		add(step);
		add(inputInput);
		loadGraph(new File("input0.txt"));
	}
	private void setActions(){
		openDir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				        int returnVal = filechooser.showOpenDialog(null);
				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				            File file = filechooser.getSelectedFile();
				            loadGraph(file);
				        } else {
				            System.out.println("Open command cancelled by user.");
				        }
			}
			});
		step.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inputTape.next();
				processNode();
				repaint();
			}
		});	
		inputInput.addKeyListener(new KeyAdapter()
	    {
		      public void keyPressed(KeyEvent e)
		      {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER)
		        {
		          inputTape.setText(inputInput.getText());
		          inputTape.resetHighlight();
		        }
		      }
		    });
		
		

	}
	protected void loadGraph(File file) {
		graph = PushDownParser.loadGraph(file.getAbsolutePath());
        frame.setGraph(graph);
        for (int i = 0; i < graph.getNodeCount(); i++) {
	        Node node = graph.getNode(i);
	        System.out.println("CurrentNod: " + node.getAttribute("ui.class"));
	        if((boolean) node.getAttribute("startingNode")){
	        	graphHead = i;
	        	currentNode = node;
	        	node.setAttribute("ui.class", "current");
	        	break;
	        }
	        // Do something with node
	 }
        System.out.println("Opening: " + file.getName());
	}
	public void processNode(){
		//for (int i = 0; i < graph.getNodeCount(); i++) {
			//Collection<Edge> edges = currentNode.getEdgeSet();
//			if(currentNode.getEdgeSet() != null)
//				System.out.println("notNull edgeset");
			for (Edge edge : graph.getEachEdge()) {
				System.out.println(" edge:  " + edge.getAttribute("ui.label"));
				System.out.println("sourceNod = " + edge.getSourceNode());
				if(edge.getSourceNode() == currentNode){
					System.out.println("is currentNode" );
					if(  isValidTransition(edge)){
						System.out.println("validEdge :" +  edge.getAttribute("ui.label"));
						switchCurrentNode(edge.getTargetNode());
						stack.pop();
						stack.push(edge.getAttribute(PushDownParser.PUSH_SYMBOL));
					}
				}
			}
				System.out.println("notNull edgeset");
			//System.out.println("edgesSize: " + edges.size());
//	        do{
//	        	edge = (Edge)it.next();
//			for (Edge edge2 : edges) {
//				System.out.println("edgeLabel = " + edge2.getAttribute("ui.label"));
//			}
//	        	
//	        }while(edge != null);
	//	}
	}
	private void switchCurrentNode(Node targetNode) {
		targetNode.removeAttribute("ui.class");
		currentNode.removeAttribute("ui.class");
		targetNode.setAttribute("ui.class", PushDownParser.CURRENT_NODE);
	}
	private boolean isValidTransition(Edge edge) {
		System.out.println("aqui explota:");
		boolean rightInput = edge.getAttribute(PushDownParser.TAPE_SYMBOL).equals(inputTape.getSymbol().substring(0,1));
		boolean rightStackState = (stack.isEmpty() || edge.getAttribute(PushDownParser.POP_SYMBOL).equals(stack.getCurrentTop()) );
		return  rightInput && rightStackState;
				
	}
}
