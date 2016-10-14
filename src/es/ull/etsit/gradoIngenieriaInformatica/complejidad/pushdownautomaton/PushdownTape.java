package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import com.sun.corba.se.spi.orbutil.fsm.Input;

public class PushdownTape extends JTextArea{
	int pos = 0;
	String[] tape;
	public PushdownTape(){
		super("input");
		tape = new String[1];
		tape[0] = "input";
	}
	public boolean next(){
		pos++;
		if(pos <= tape.length){
			updateTape();
			return false;
		}else{
			pos = getText().length();
			return true;
		}
	}
	public void resetHighlight(){
		pos = 0;
		updateTape();
	}
	void updateTape(){
	      Highlighter highlighter = getHighlighter();
	      HighlightPainter painter = 
	             new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
	      int p0 = 0;
	      int p1 = 0;
	      for(int i = 0; i < pos; i++){
	    	  p1 += 1 + tape[i].length();
	      }
	      try {
			highlighter.addHighlight(p0, p1, painter );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     // repaint();
	}
	public String getSymbol(){
		return getText().substring(pos - 1);
	}
	public void setInput(String input){
		tape = input.split("\\s+");
	}
}
