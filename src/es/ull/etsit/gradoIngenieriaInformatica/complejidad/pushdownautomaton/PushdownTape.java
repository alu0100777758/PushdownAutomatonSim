package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import com.sun.corba.se.spi.orbutil.fsm.Input;

public class PushdownTape extends JTextArea{
	int pos = 0;
	int visited = 0;
	String[] tape;
	public PushdownTape(String input){
		setInput(input);
		setPreferredSize(new Dimension(100,20));
		setMaximumSize(getPreferredSize());
	}
	public boolean next(){
		pos++;
		visited++;
		if(pos < tape.length){
			updateTape();
			return false;
		}else{
			pos = tape.length - 1;
			visited = tape.length;
			updateTape();
			return true;
		}
	}
	public void reset(){
		pos = 0;
		visited = 0;
		updateTape();
	}
	void updateTape(){
	      Highlighter highlighter = getHighlighter();
	      HighlightPainter painter = 
	             new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
	      int p0 = 0;
	      int p1 = 0;
	      System.out.println("visted: " + visited );
	      for(int i = 0; i < visited; i++){
	    	  p1 += 1 + tape[i].length();
	      }

	      try {
			highlighter.addHighlight(p0, p1, painter );
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	public String getSymbol(){
		System.out.println("giving: " + tape[pos]);
		return tape[pos];
	}
	public void setInput(String input){
		tape = input.split("\\s+");
		setText(input);
		System.out.println("tape: " + tape);
		reset();
	}
}
