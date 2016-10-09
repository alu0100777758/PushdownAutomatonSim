package es.ull.etsit.gradoIngenieriaInformatica.complejidad.pushdownautomaton;

import java.util.ArrayList;
import java.util.Arrays;

public class PushdownAlphabet {
	private ArrayList<String> inputAlphabet = null;
	private String emptySymbol = "\\b";
	public PushdownAlphabet(String empty){
		emptySymbol = empty;
	}
	public PushdownAlphabet(String empty, String [] alphabet){
		this(empty);
		inputAlphabet = new ArrayList<String>( Arrays.asList(alphabet));
	}
	public String toString(){
		return "EmptySymbol = " + emptySymbol + '\n' + inputAlphabet;
	}
	public ArrayList<String> getInputAlphabet() {
		return inputAlphabet;
	}
	public void setInputAlphabet(ArrayList<String> inputAlphabet) {
		this.inputAlphabet = inputAlphabet;
	}
	public String getEmptySymbol() {
		return emptySymbol;
	}
	public void setEmptySymbol(String emptySymbol) {
		this.emptySymbol = emptySymbol;
	}
}
