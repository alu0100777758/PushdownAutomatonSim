package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import com.sun.org.apache.bcel.internal.generic.NEW;

import scala.collection.parallel.ParIterableLike.Foreach;
import sun.util.resources.cldr.ts.CurrencyNames_ts;

public class PushdownControlsPane extends JPanel {
	// TODO en cada nodo actual escribir sus producciones
	public static final String DEFAULT_INPUT = "A S A S";
	JButton openDir = new JButton("LoadFile");
	JButton insta = new JButton("InstaEj");
	JButton step = new JButton("Step");
	int graphHead;
	Graph graph;
	ArrayList<Node> currentNodes = new ArrayList<>();
	PushdownStack stack = new PushdownStack();
	PushdownTape inputTape = new PushdownTape(DEFAULT_INPUT);
	JTextField inputInput = new JTextField(DEFAULT_INPUT);
	JFileChooser filechooser = new JFileChooser(System.getProperty("user.dir"));
	PushdownFrame frame;
	public PushdownControlsPane(PushdownFrame frame) {
		super();
		this.frame = frame;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		System.out.println("adding controls");
		setPreferredSize(new Dimension(100, 1000));
		setMaximumSize(getPreferredSize());
		inputInput.setPreferredSize(new Dimension(100,20));
		inputInput.setMaximumSize(getPreferredSize());
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
				for (Node node : (ArrayList<Node>)currentNodes.clone()) {
					processNode(node);
				}
				if(inputTape.next())
					end();
				frame.revalidate();
				frame.repaint();
			}
		});	
		inputInput.addKeyListener(new KeyAdapter()
	    {
		      public void keyPressed(KeyEvent e)
		      {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER)
		        {
		          inputTape.setInput(inputInput.getText());
		        }
		      }
		    });
		
		

	}
	private void end() {
		String message;
		boolean atFinalState = false;
		for (Node node : currentNodes) {
			atFinalState = atFinalState || (node.getAttribute(PushDownParser.FINAL_STATE) != null);
		}
		if(atFinalState || stack.isEmpty())
			message = "PASS";
		else
			message = "NOT PASS";
		JOptionPane.showMessageDialog(frame, message);
	}
	protected void loadGraph(File file) {
		graph = PushDownParser.loadGraph(file.getAbsolutePath());
        frame.setGraph(graph);
        for (int i = 0; i < graph.getNodeCount(); i++) {
	        Node node = graph.getNode(i);
	        System.out.println("CurrentNod: " + node.getAttribute("ui.class"));
	        if(node.getAttribute("startingNode") != null){
	        	graphHead = i;
	        	currentNodes.clear();
	        	currentNodes.add(node);
	        	node.setAttribute("ui.class", "current");
	        	break;
	        }
	       
	 }
        System.out.println("Opening: " + file.getName());
        frame.revalidate();
        frame.repaint();
        inputTape.reset();
	}
	public void processNode(Node currentNode){
			for (Edge edge : graph.getEachEdge()) {
				System.out.println(" edge:  " + edge.getAttribute("ui.label"));
				System.out.println("sourceNod = " + edge.getSourceNode());
				if(edge.getSourceNode() == currentNode){
					System.out.println("is currentNode" );
					if(edge.getAttribute(PushDownParser.TAPE_SYMBOL).equals(".")){
						Node nnode = edge.getTargetNode();
						currentNodes.add(nnode);
										}
					if(  isValidTransition(edge)){
						System.out.println("validEdge :" +  edge.getAttribute("ui.label"));
						switchCurrentNode(currentNode, edge.getTargetNode());
						if(!stack.isEmpty())
							stack.pop();
						stack.push(edge.getAttribute(PushDownParser.PUSH_SYMBOL));//split y modificar isercion
						revalidate();
					}else{
						currentNodes.remove(currentNode);
					}
						
				}
			}
	}
	private void switchCurrentNode(Node currentNode, Node targetNode) {
		if(targetNode.getAttribute("finalState")!= null)
			targetNode.addAttribute("ui.class","marked, current");
		else
			targetNode.addAttribute("ui.class","current");
		if(currentNode.getAttribute("finalState")!= null)
			currentNode.addAttribute("ui.class","marked");
		else
			currentNode.removeAttribute("ui.class");
	}
	private boolean isValidTransition(Edge edge) {
		boolean rightInput = edge.getAttribute(PushDownParser.TAPE_SYMBOL).equals(inputTape.getSymbol());
		boolean rightStackState = (stack.isEmpty() || edge.getAttribute(PushDownParser.POP_SYMBOL).equals(stack.getCurrentTop()) );
		System.out.println("input: " + rightInput + "   stack" + rightStackState);
		return  rightInput && rightStackState;
				
	}
}
