package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Color;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class PushdownStack extends JPanel{
	Stack<JLabel> labels = new Stack<>();
	public PushdownStack() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.GRAY);
	}
	public void push(String symbol){
		JLabel label = new JLabel(symbol);
		labels.push(label);
		add(label);
		revalidate();
		System.out.println("pushing");
	};
	public String pop(){
		JLabel outLabel =  labels.pop();
		remove(outLabel);
		repaint();
		return outLabel.getText();
	}
	public String getCurrentTop(){
		return labels.lastElement().getText();
	}
	public boolean isEmpty(){
		return labels.isEmpty();
	}
}
