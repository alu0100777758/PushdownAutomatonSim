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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import scala.collection.parallel.ParIterableLike.Foreach;
import sun.util.resources.cldr.ts.CurrencyNames_ts;

public class PushdownControlsPane extends JPanel {
	public static final String DEFAULT_INPUT = "a1 a2 a3 a4";
	JButton openDir = new JButton("LoadFile");
	JButton insta = new JButton("InstaEj");
	JButton step = new JButton("Step");
	int graphHead;
	Graph graph;
	Node currentNode = null;
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
				processNode();
				if(inputTape.next())
					end();
				repaint();
			}
		});	
		inputInput.addKeyListener(new KeyAdapter()
	    {
		      public void keyPressed(KeyEvent e)
		      {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER)
		        {
		          inputTape.setInput(inputInput.getText());
		          inputTape.resetHighlight();
		        }
		      }
		    });
		
		

	}
	private void end() {
		String message;
		if(currentNode.getAttribute(PushDownParser.FINAL_STATE) != null || stack.isEmpty())
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
	        	currentNode = node;
	        	node.setAttribute("ui.class", "current");
	        	break;
	        }
	       
	 }
        System.out.println("Opening: " + file.getName());
        frame.revalidate();
        frame.repaint();
	}
	public void processNode(){
			for (Edge edge : graph.getEachEdge()) {
				System.out.println(" edge:  " + edge.getAttribute("ui.label"));
				System.out.println("sourceNod = " + edge.getSourceNode());
				if(edge.getSourceNode() == currentNode){
					System.out.println("is currentNode" );
					if(  isValidTransition(edge)){
						System.out.println("validEdge :" +  edge.getAttribute("ui.label"));
						switchCurrentNode(edge.getTargetNode());
						if(!stack.isEmpty()) /// or no pop
							stack.pop();
						stack.push(edge.getAttribute(PushDownParser.PUSH_SYMBOL));//split y modificar isercion
					}
				}
			}
	}
	private void switchCurrentNode(Node targetNode) {
		targetNode.removeAttribute("ui.class");
		currentNode.removeAttribute("ui.class");
		targetNode.setAttribute("ui.class", PushDownParser.CURRENT_NODE);
	}
	private boolean isValidTransition(Edge edge) {
		boolean rightInput = edge.getAttribute(PushDownParser.TAPE_SYMBOL).equals(inputTape.getSymbol());
		boolean rightStackState = (stack.isEmpty() || edge.getAttribute(PushDownParser.POP_SYMBOL).equals(stack.getCurrentTop()) );
		System.out.println("input: " + rightInput + "   stack" + rightStackState);
		return  rightInput && rightStackState;
				
	}
}
