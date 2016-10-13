package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class PushdownTape extends JTextArea{
	int pos = 0;
	public PushdownTape(){
		super("input");
	}
	public boolean next(){
		pos++;
		if(pos <= getText().length()){
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
	      int p1 = pos;
	      try {
			highlighter.addHighlight(p0, p1, painter );
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     // repaint();
	}
}
